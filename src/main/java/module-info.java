module ohm.softa.a07 {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.apache.commons.lang3;

	opens ohm.softa.a07 to javafx.fxml;
	opens ohm.softa.a07.controllers to javafx.fxml;

	exports ohm.softa.a07 to javafx.graphics;
}
