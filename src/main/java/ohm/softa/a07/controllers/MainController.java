package ohm.softa.a07.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ohm.softa.a07.api.OpenMensaAPI;
import ohm.softa.a07.model.Meal;
import ohm.softa.a07.utils.MealsFilterUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
	private static final Logger logger = LogManager.getLogger(MainController.class);
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	private final OpenMensaAPI openMensaAPI;

	@FXML
	private CheckBox chkVegetarian;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnRefresh;

	@FXML
	private ListView<Meal> mealsList;

	private ObservableList<Meal> meals;

	public MainController() {
		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://openmensa.org/api/v2/")
			.build();

		openMensaAPI = retrofit.create(OpenMensaAPI.class);
	}

	@FXML
	private void onRefreshItem() {
		logger.debug("Handling refresh menu item...");
		loadMensaData();
	}

	@FXML
	private void onVegetarianChkbox() {
		logger.debug("Handling interaction of vegetarian checkbox");
		loadMensaData();
	}

	@FXML
	private void onCloseItem() {
		logger.debug("Handling close menu item");

		logger.debug("Starting Platform.exit()...");
		Platform.exit();

		logger.debug("Starting System.exit(0)");
		System.exit(0);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		logger.debug("Initializing the MainController");
		loadMensaData();

		// hook up our model
		meals = mealsList.getItems();
	}

	private void loadMensaData() {
		logger.debug("Starting call to fetch data from API");
		openMensaAPI.getMeals(dateFormat.format(new Date())).enqueue(new Callback<List<Meal>>() {

			@Override
			public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
				if (!response.isSuccessful())
					return;

				logger.debug("Handling positive response from API...");
				if (response.body() == null) {
					logger.error("Response did not contain valid body");
					return;
				}

				logger.debug(MealsFilterUtility.filterForVegetarian(response.body()));

				Platform.runLater(() -> {
					meals.clear();
					meals.addAll(chkVegetarian.isSelected()
						? MealsFilterUtility.filterForVegetarian(response.body())
						: response.body());
				});
			}

			@Override
			public void onFailure(Call<List<Meal>> call, Throwable t) {
				logger.error("Error occured while fetching data from API", t);
				/* Show an alert if loading of mealsProperty fails */
				Platform.runLater(() -> {
					meals.clear();
					new Alert(Alert.AlertType.ERROR, "Failed to get mealsProperty", ButtonType.OK).showAndWait();
				});
			}
		});
	}
}
