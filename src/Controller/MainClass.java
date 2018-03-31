package Controller;

import Model.NetworkData;
import View.MainPanel;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {
	
	@Override
	public void start(Stage mainStage) throws Exception {		
		MainPanel mainPanel = new MainPanel(mainStage);
	}

	public static void main(String args[]) {
		launch(args);
	}	
	
}
