module ohm.softa.a07 {
	requires java.sql;
	requires javafx.controls;
	requires javafx.fxml;

	requires org.apache.commons.lang3;
	requires org.apache.logging.log4j;
	requires retrofit2;
	requires retrofit2.converter.gson;
	requires okhttp3;
	requires okhttp3.logging;

	opens ohm.softa.a07 to javafx.fxml;
	opens ohm.softa.a07.controllers to javafx.fxml;
	opens ohm.softa.a07.model to gson;

	exports ohm.softa.a07 to javafx.graphics;
	exports ohm.softa.a07.model;
}
