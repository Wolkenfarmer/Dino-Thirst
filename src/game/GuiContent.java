package game;

import java.io.FileNotFoundException;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author Wolkenfarmer
*/

public class GuiContent {
	static Canvas gameBackground;
	GraphicsContext gcGameBa;
	static BorderPane gameScreen;
	Canvas gameCanvas;
	GraphicsContext gcGameCanvas;
	Text textScoreDisplay;
	Text textFPSDisplay;
	static Rectangle[] lifes = new Rectangle[GameStage.numLifes];
	static Group deathScreen;
	static Text textFinalScore;
	static double lifeSpacing;
	
	// builds all the gui-content around the game itself
	public void buildGui() throws FileNotFoundException {
		gameBackground = new Canvas(GameStage.windowWidth, GameStage.windowHeight);
        gcGameBa = gameBackground.getGraphicsContext2D();
        GameStage.root.getChildren().add(gameBackground);
        
        
        // builds the whole game-screen
        gameScreen = new BorderPane();
        gameScreen.prefHeightProperty().bind(GameStage.mainScene.heightProperty());
        gameScreen.prefWidthProperty().bind(GameStage.mainScene.widthProperty());
        gameScreen.setPadding(new Insets(10, 20, 10, 20));
	        gameCanvas = new Canvas(GameStage.windowWidth, GameStage.windowHeight);
	        gcGameCanvas = gameCanvas.getGraphicsContext2D();
	        
	        // left texts (version)
	        Text textVersionDisplay = new Text();
	        textVersionDisplay.setText("Version 1.5");
	        textVersionDisplay.setFont(Font.font("Verdana", GameStage.windowWidth * 0.028));
	        textVersionDisplay.setFill(Color.WHITESMOKE);
	        VBox leftVBox = new VBox();
	        leftVBox.getChildren().add(textVersionDisplay);
	        leftVBox.setMinWidth(GameStage.windowWidth / 4.5);
	        leftVBox.setMaxWidth(GameStage.windowWidth / 4.5);
	        leftVBox.setAlignment(Pos.TOP_LEFT);
	        
	        // right texts (score + FPS)
	        textScoreDisplay = new Text();
	        textScoreDisplay.setFont(Font.font("Verdana", GameStage.windowWidth * 0.028));
	        textScoreDisplay.setFill(Color.WHITESMOKE);
	        textFPSDisplay = new Text();
	        textFPSDisplay.setFont(Font.font("Verdana", GameStage.windowWidth * 0.021));
	        textFPSDisplay.setFill(Color.GREY);
	        VBox rightVBox = new VBox();
	        rightVBox.setSpacing(5);
	        ObservableList<Node> list = rightVBox.getChildren(); 
	        list.addAll(textScoreDisplay, textFPSDisplay);
	        rightVBox.setMinWidth(GameStage.windowWidth / 4.5);
	        rightVBox.setMaxWidth(GameStage.windowWidth / 4.5);
	        rightVBox.setAlignment(Pos.TOP_RIGHT);
	        
	        // lifebar
	        if (lifes.length <= 10) {lifeSpacing = 5;}
	        else if (lifes.length <= 20) {lifeSpacing = 4;}
	        else if (lifes.length <= 30) {lifeSpacing = 2;}
	        else if (lifes.length <= 40) {lifeSpacing = 1;}
	        else if (lifes.length <= 50) {lifeSpacing = 0;}
	        for (int i = 0; i < lifes.length; i++) {
	        	lifes[i] = new Rectangle();
	        	lifes[i].setHeight(GameStage.windowHeight * 0.04);
	        	lifes[i].setWidth(((GameStage.windowWidth * 0.45) / lifes.length) - (lifeSpacing / (lifes.length - 1)));
	        	lifes[i].setStrokeWidth(4);
	        	lifes[i].setStrokeType(StrokeType.INSIDE);
	        	lifes[i].setFill(Color.GREEN);
	        	lifes[i].setStroke(Color.DARKGREEN);
	        	lifes[i].setVisible(true);
	        }
	        HBox lifeHBox = new HBox();
	        lifeHBox.setSpacing(lifeSpacing);
	        for (int i = 0; i < lifes.length; i++) {lifeHBox.getChildren().add(lifes[i]);}
	        lifeHBox.setVisible(true);
	        lifeHBox.setAlignment(Pos.TOP_CENTER);
	        
	        Text textControls = new Text();
	        textControls.setText("restart -> \"r\"      escape -> \"Esc\"");
	        textControls.setFont(Font.font("Consolas", GameStage.windowWidth * 0.028));
	        textControls.setFill(Color.HOTPINK);
	        HBox bottomHBox = new HBox();
	        bottomHBox.getChildren().add(textControls);
	        bottomHBox.setAlignment(Pos.BOTTOM_CENTER);
	        
	        gameScreen.setLeft(leftVBox);
	        gameScreen.setRight(rightVBox);
	        gameScreen.setCenter(lifeHBox);
	        gameScreen.setBottom(bottomHBox);
	    gameScreen.getChildren().addAll(gameCanvas);
	    GameStage.root.getChildren().add(gameScreen);
	    
	    
	    // builds the game-over screen
        deathScreen = new Group();
        	Rectangle darkerBackground = new Rectangle(0, 0, GameStage.windowWidth, GameStage.windowHeight);
        	darkerBackground.setFill(Color.grayRgb(40, 0.85));
        	
        	Text textGameOver = new Text();
        	textGameOver.setText("Game over!");
        	textGameOver.setFont(Font.font("Verdana", FontWeight.BOLD, GameStage.windowWidth * 0.098));
        	textGameOver.setFill(Color.DARKRED);
        	textGameOver.setStroke(Color.BLACK);
        	textGameOver.setStrokeLineCap(StrokeLineCap.ROUND);
        	textGameOver.setStrokeWidth(4.0);
        	textGameOver.setX((GameStage.windowWidth - textGameOver.getBoundsInLocal().getWidth()) / 2);
        	textGameOver.setY(GameStage.windowHeight * 0.3);
        	
        	Text textRevive = new Text();
        	textRevive.setText("Press \"enter\" or \"R\" in order to restart");
        	textRevive.setFont(Font.font("Verdana", FontWeight.BOLD, GameStage.windowWidth * 0.042));
        	textRevive.setFill(Color.WHITE);
        	textRevive.setStroke(Color.BLACK);
        	textRevive.setStrokeWidth(2.0);
        	textRevive.setX((GameStage.windowWidth - textRevive.getBoundsInLocal().getWidth()) / 2);
        	textRevive.setY(GameStage.windowHeight * 0.5);
        	
        	Text textExit = new Text();
        	textExit.setText("or \"escape\" to exit");
        	textExit.setFont(Font.font("Verdana", FontWeight.BOLD, GameStage.windowWidth * 0.035));
        	textExit.setFill(Color.WHITE);
        	textExit.setStroke(Color.BLACK);
        	textExit.setStrokeWidth(2.0);
        	textExit.setX((GameStage.windowWidth - textExit.getBoundsInLocal().getWidth()) / 2);
        	textExit.setY(GameStage.windowHeight * 0.6);
        	
        	textFinalScore = new Text();
        	textFinalScore.setFont(Font.font("Verdana", GameStage.windowWidth * 0.028));
        	textFinalScore.setFill(Color.WHITE);
        	textFinalScore.setStroke(Color.BLACK);
        	textFinalScore.setStrokeWidth(1.0);
        	textFinalScore.setX((GameStage.windowWidth - textFinalScore.getBoundsInLocal().getWidth()) / 2);
        	textFinalScore.setY(GameStage.windowHeight * 0.75);
        deathScreen.getChildren().addAll(darkerBackground, textGameOver, textRevive, textExit, textFinalScore);
        GameStage.root.getChildren().add(deathScreen);
	}
	
	
	public static void deleteLife(int lostLife) {
		lostLife--;
		lifes[lostLife].setFill(Color.RED);
		lifes[lostLife].setStroke(Color.DARKRED);
	}
	
	public static void fillLifes() {
		for (int i = 0; i < lifes.length; i++) {
			lifes[i].setFill(Color.GREEN);
			lifes[i].setStroke(Color.DARKGREEN);
		}
	}
}
