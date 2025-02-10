package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	/*
	 * Função que recupera o Stage a partir do EVENTO (o clicar de um botão, por exemplo)
	 */
	public static Stage currentStage(ActionEvent event) {
		Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		return currentStage;
	}
	
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			return null;
		}
	}
}
