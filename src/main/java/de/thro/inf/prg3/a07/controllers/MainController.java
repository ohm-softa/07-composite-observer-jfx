package de.thro.inf.prg3.a07.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private ListView<String> mealsList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the event handler (callback)
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// create a new (observable) list and tie it to the view
				ObservableList<String> list = FXCollections.observableArrayList("Hans", "Dampf");
				mealsList.setItems(list);
			}
		});
	}
}
