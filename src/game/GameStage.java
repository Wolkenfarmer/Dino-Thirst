package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import menu.Main;
 
/**
 * @author Wolkenfarmer
*/

public class GameStage extends Stage {
	static Group root;
	static Scene mainScene;
	static GuiContent gui;
	static GameLogic game;
	static ArrayList<String> input = new ArrayList<String>();
	static long lastNanoTime = System.nanoTime();
	public static double windowWidth;
	public static double windowHeight;
	static boolean stillAlive = true;
	static double elapsedTime;
	static double fps;
	public static int numLifes = 1;
	static int currentLifes;
	public static int difficulty = 90;
	
    
    
    // the window of the game + main logic for game
    @SuppressWarnings("static-access")
	public GameStage() throws FileNotFoundException { 
    	System.out.println("starting game");
    	this.setTitle("Dino Thirst");
    	this.setFullScreen(true);
    	this.setFullScreenExitHint("");
        root = new Group();
        mainScene = new Scene(root, Color.grayRgb(40));
        this.setScene(mainScene);
        
        
        // listener
        mainScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) input.add(code);
                }
            }
    	);
		mainScene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                	// exit game
                    if (input.contains("ESCAPE")) {
                    	try {Main.closeApplication();
						} catch (IOException e1) {e1.printStackTrace();}
                    }
                    // restart game
                	if ((!stillAlive && input.contains("ENTER")) || input.contains("R")) {revive();}
                	// input handling
                    String code = e.getCode().toString();
                    input.remove(code);
                }
            }
        );
		
		this.show();
		windowWidth = this.getWidth();
        windowHeight = this.getHeight();
        System.out.println("Window dimensions: " + this.getWidth() + " * " + this.getHeight());
        
        
        // calls and organizes the other game parts
        if (numLifes < 1) {numLifes = 1;}
        gui = new GuiContent();
        gui.buildGui();
        gui.gameBackground.toFront();
        gui.gameScreen.toFront();
        
        game = new GameLogic();
        game.renderDesert(gui.gcGameBa);
        GameLogic.floor.render(gui.gcGameBa);
        lastNanoTime = System.nanoTime();
                
		System.out.println("game build finished");	
				
		
		// the refreshing core of the application
        new AnimationTimer() {
			public void handle(long currentNanoTime) {                
                if (stillAlive) {
                	// game
                	gui.gcGameCanvas.clearRect(0, 0, windowWidth, windowHeight);
                	game.gameLogicTick();
                	GameLogic.dino.render(gui.gcGameCanvas);
                    for (int i = 0; i < GameLogic.cactus.length; i++) {GameLogic.cactus[i].render(gui.gcGameCanvas);}
                    gui.textFPSDisplay.setText("FPS: " + Math.round(fps));
        	        gui.textScoreDisplay.setText("Score: " + GameLogic.score);
        	        
        	        // time and speed managment
        	        elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                    lastNanoTime = currentNanoTime;
                    fps = 1 / elapsedTime;
                }
            }
        }.start();
    }
    
    
    // death screen 
    public static void onDeath() {
    	GuiContent.textFinalScore.setText("Your final score was: " + GameLogic.score);
    	GuiContent.textFinalScore.setX((windowWidth - GuiContent.textFinalScore.getBoundsInLocal().getWidth()) / 2);
    	GuiContent.deathScreen.toFront();
        System.out.println("Game over \nYour final score was: " + GameLogic.score);
    }
    
    
    // restart game
    public static void revive() {
		lastNanoTime = System.nanoTime();	
    	stillAlive = true;
    	GuiContent.fillLifes();
    	GuiContent.gameBackground.toFront();
    	GuiContent.gameScreen.toFront();
    	
    	GameLogic.dino.setPositionX(GameStage.windowWidth * 0.3);
		GameLogic.dino.setPositionY(GameStage.windowHeight * 0.3);
		GameLogic.dino.setVelocityY(0);
		GameLogic.onGround = false;
    	for (int i = 0; i < GameLogic.cactus.length; i++) {GameLogic.cactus[i].setPositionX(20000);}
    	GameLogic.score = 0;
    	GameLogic.cactusIntervall = 3;
    	GameLogic.secondStageIntervallBuffer = 0.15;
    	currentLifes = numLifes;
    	System.out.println("Game restarted");
    }
}