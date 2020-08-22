package menu;

import java.io.IOException;

import game.GameLogic;
import game.GameStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * @author Wolkenfarmer
 * @version 1.5
 * @since 15.01.2020
*/

// Main Class inheriting the Application
public class Main extends Application {
	
	// launching the program
	public static void main(String[] args) throws IOException {
		//loads old game-state
		new Storage();
		GameStage.difficulty = (int) Storage.load("difficulty");
		GameStage.numLifes = (int) Storage.load("maxLifes");
		//GameLogic.score = Storage.load("highscore");
		launch(args);
	}
	
	// launches application through main launch(args)
	public void start(Stage primaryStage) throws Exception {
		new Settings();
	}
	
	// saves game-state and closes Application
	public static void closeApplication() throws IOException {
		Storage.save("difficulty", Integer.toString(GameStage.difficulty));
		Storage.save("maxLifes", Integer.toString(GameStage.numLifes));
		Storage.save("highscore", Double.toString(GameLogic.score));
		Platform.exit();
		System.out.println("Closed application");
	}
}
