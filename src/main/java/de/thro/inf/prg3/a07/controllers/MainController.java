package de.thro.inf.prg3.a07.controllers;

import de.thro.inf.prg3.a07.api.OpenMensaAPI;
import de.thro.inf.prg3.a07.model.Meal;
import de.thro.inf.prg3.a07.utils.MealsFilterUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
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
	private CheckMenuItem vegetarianChkbox;

	@FXML
	private ListView<Meal> mealsList;

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
	}

	private void loadMensaData() {
		logger.debug("Starting call to fetch data from API");
		openMensaAPI.getMeals(dateFormat.format(new Date())).enqueue(new Callback<List<Meal>>() {

			@Override
			public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
				logger.debug("Handling positive response from API...");
				if (response.body() == null) return;
				Platform.runLater(() -> {

					mealsList.getItems().clear();

					if (vegetarianChkbox.isSelected()) {
						mealsList.getItems().addAll(MealsFilterUtility.filterForVegetarian(response.body()));
					} else {
						mealsList.getItems().addAll(response.body());
					}
				});
			}

			@Override
			public void onFailure(Call<List<Meal>> call, Throwable t) {
				logger.error("Error occured while fetching data from API", t);
				/* Show an alert if loading of mealsProperty fails */
				Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Failed to get mealsProperty", ButtonType.OK).showAndWait());
			}

		});
	}
}
