package menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import game.GameStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * @author Wolkenfarmer
*/

// first window opened - settings
public class Settings extends Stage{
	static Group root;
	static Scene mainScene;
	static ArrayList<String> input = new ArrayList<String>();
	static int numLifes;
	static byte difficulty;
	
	// builds the settings stage
	public Settings() throws FileNotFoundException { 
    	System.out.println("starting settings");
    	this.setTitle("Dino Thirst: Settings");
        root = new Group();
        mainScene = new Scene(root, 785, 540, Color.grayRgb(40));
        this.setScene(mainScene);
        this.setResizable(false);
        
        Background bNormalButton = new Background (new BackgroundFill(Color.grayRgb(220), new CornerRadii(10),  null));
        Background bPressedButton = new Background (new BackgroundFill(Color.DARKGREEN, new CornerRadii(10),  null));
        Background bFocusedButton = new Background (new BackgroundFill(Color.grayRgb(150), new CornerRadii(10),  null));
        Font fNormalText = new Font("Arial", 18);
        
        Button bStartGame = new Button("Start Dino Thirst!");
        bStartGame.setBackground(bNormalButton);
        bStartGame.setFont(new Font("Comic Sans MS", 25));
        bStartGame.setPrefHeight(60);
        bStartGame.setPrefWidth(585);
        bStartGame.setLayoutX(100);
        bStartGame.setLayoutY(80);
        root.getChildren().add(bStartGame);
        
        
        // elements for setting your difficulty:
        Group difficultySettings = new Group();
	        Label lDifficulty = new Label("Set your difficulty:");
	        lDifficulty.setFont(fNormalText);
	        lDifficulty.setTextFill(Color.WHITESMOKE);
	        lDifficulty.setLayoutX(100);
	        lDifficulty.setLayoutY(208);
	        difficultySettings.getChildren().add(lDifficulty);
	        
	        HBox difficultyButtonsHBox = new HBox();
	        difficultyButtonsHBox.setSpacing(14);
	        difficultyButtonsHBox.setLayoutX(260);
	        difficultyButtonsHBox.setLayoutY(200);
		        Button bDifSandbox = new Button("Sandbox");
		        bDifSandbox.setBackground(bNormalButton);
		        bDifSandbox.setFont(fNormalText);
		        bDifSandbox.setPrefHeight(40);
		        bDifSandbox.setPrefWidth(95);
		        difficultyButtonsHBox.getChildren().add(bDifSandbox);
		        
		        Button bDifEasy = new Button("Easy");
		        bDifEasy.setBackground(bNormalButton);
		        bDifEasy.setFont(fNormalText);
		        bDifEasy.setPrefHeight(40);
		        bDifEasy.setPrefWidth(95);
		        difficultyButtonsHBox.getChildren().add(bDifEasy);
		        
		        Button bDifMedium = new Button("Medium");
		        bDifMedium.setBackground(bNormalButton);
		        bDifMedium.setFont(fNormalText);
		        bDifMedium.setPrefHeight(40);
		        bDifMedium.setPrefWidth(95);
		        difficultyButtonsHBox.getChildren().add(bDifMedium);
		        
		        Button bDifHard = new Button("Hard");
		        bDifHard.setBackground(bNormalButton);
		        bDifHard.setFont(fNormalText);
		        bDifHard.setPrefHeight(40);
		        bDifHard.setPrefWidth(95);
		        difficultyButtonsHBox.getChildren().add(bDifHard);
	        difficultySettings.getChildren().add(difficultyButtonsHBox);
	    root.getChildren().add(difficultySettings);       
	    
        Label lNumLifes = new Label("Set your maximum number of lifes:");
        lNumLifes.setFont(fNormalText);
        lNumLifes.setTextFill(Color.WHITESMOKE);
        lNumLifes.setLayoutX(100);
        lNumLifes.setLayoutY(305.8);
        root.getChildren().add(lNumLifes);
                
        TextField tfNumLifes = new TextField();
        tfNumLifes.setFont(fNormalText);
        tfNumLifes.setPromptText("enter a number");
        tfNumLifes.setText(Integer.toString(GameStage.numLifes));
        tfNumLifes.setStyle("-fx-text-inner-color: WHITESMOKE;");
        tfNumLifes.setBackground(new Background(new BackgroundFill(Color.grayRgb(90), new CornerRadii(5),  null)));
        tfNumLifes.setPrefWidth(285);
        tfNumLifes.setLayoutX(400);
        tfNumLifes.setLayoutY(300);
        root.getChildren().add(tfNumLifes);
                
        
        // different Exceptions
        TextFlow tfExceptions = new TextFlow();
        tfExceptions.setLayoutX(100);
        tfExceptions.setLayoutY(380);
        root.getChildren().add(tfExceptions);
        Text tExceptionDif = new Text("Please select a difficulty!\n\n");
        tExceptionDif.setFont(fNormalText);
        tExceptionDif.setFill(Color.INDIANRED);
        Text tExceptionNLnull = new Text("Please enter your number of lifes!\n\n");
        tExceptionNLnull.setFont(fNormalText);
        tExceptionNLnull.setFill(Color.INDIANRED);
        Text tExceptionNL0 = new Text("Your number of lifes cannot be zero!\n\n");
        tExceptionNL0.setFont(fNormalText);
        tExceptionNL0.setFill(Color.INDIANRED);
        Text tExceptionNL201 = new Text("Your number of lifes cannot be over 200!\n\n");
        tExceptionNL201.setFont(fNormalText);
        tExceptionNL201.setFill(Color.INDIANRED);
        
        
        
        // showing last preferences
        switch (GameStage.difficulty) {
	        case 0:
	        	bDifSandbox.setTextFill(Color.WHITESMOKE);
				bDifSandbox.setBackground(bPressedButton);
				break;
	        case 1:
	        	bDifEasy.setTextFill(Color.WHITESMOKE);
				bDifEasy.setBackground(bPressedButton);
				break;
	        case 2:
	        	bDifMedium.setTextFill(Color.WHITESMOKE);
				bDifMedium.setBackground(bPressedButton);
				break;
	        case 3:
	        	bDifHard.setTextFill(Color.WHITESMOKE);
				bDifHard.setBackground(bPressedButton);
				break;
			default:
				System.out.println("Preset difficulty: " + GameStage.difficulty);
        }
        
        
        
        // event handler
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
                    
                    if (input.contains("ENTER"))  {
                    	bStartGame.fire();
                    }
                    
                	// input handling
                    String code = e.getCode().toString();
                    input.remove(code);
                }
            }
        );
		
		// force the field to be numeric only
		tfNumLifes.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	tfNumLifes.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		// Button EventHandlers 
		bStartGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				tfExceptions.getChildren().clear();
				if (GameStage.difficulty == 7) {tfExceptions.getChildren().add(tExceptionDif);}
				if (tfNumLifes.getText().equals("")) {
					tfExceptions.getChildren().add(tExceptionNLnull);
				} else {
					int numLifes = Integer.parseInt(tfNumLifes.getText());
					if (numLifes < 1) {tfExceptions.getChildren().add(tExceptionNL0);}
					if (numLifes >= 201) {tfExceptions.getChildren().add(tExceptionNL201);}
					if (0 < numLifes && numLifes < 201 && GameStage.difficulty != 7) {
						GameStage.numLifes = Integer.parseInt(tfNumLifes.getText());
						startGame();
					}
				}	
	        }
	    });
		bStartGame.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
		        bStartGame.setBackground(new Background(new BackgroundFill(Color.grayRgb(150), new CornerRadii(10),  null)));
			}
	    });
		bStartGame.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
		        bStartGame.setBackground(bNormalButton);
			}
		});
		bDifSandbox.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				GameStage.difficulty = 0;
				bDifSandbox.setTextFill(Color.WHITESMOKE);
				bDifEasy.setTextFill(Color.BLACK);
				bDifMedium.setTextFill(Color.BLACK);
				bDifHard.setTextFill(Color.BLACK);
				bDifSandbox.setBackground(bPressedButton);
				bDifEasy.setBackground(bNormalButton);
				bDifMedium.setBackground(bNormalButton);
				bDifHard.setBackground(bNormalButton);
				System.out.println("Changed difficulty to sandbox");
	        }
	    });
		bDifSandbox.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 0) {bDifSandbox.setBackground(bFocusedButton);}
			}
	    });
		bDifSandbox.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 0) {bDifSandbox.setBackground(bNormalButton);}
			}
		});
		bDifEasy.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				GameStage.difficulty = 1;
				bDifSandbox.setTextFill(Color.BLACK);
				bDifEasy.setTextFill(Color.WHITESMOKE);
				bDifMedium.setTextFill(Color.BLACK);
				bDifHard.setTextFill(Color.BLACK);
				bDifSandbox.setBackground(bNormalButton);
				bDifEasy.setBackground(bPressedButton);
				bDifMedium.setBackground(bNormalButton);
				bDifHard.setBackground(bNormalButton);
				System.out.println("Changed difficulty to easy");
	        }
	    });
		bDifEasy.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 1) {bDifEasy.setBackground(bFocusedButton);}
			}
	    });
		bDifEasy.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 1) {bDifEasy.setBackground(bNormalButton);}
			}
		});
		bDifMedium.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				GameStage.difficulty = 2;
				bDifSandbox.setTextFill(Color.BLACK);
				bDifEasy.setTextFill(Color.BLACK);
				bDifMedium.setTextFill(Color.WHITESMOKE);
				bDifHard.setTextFill(Color.BLACK);
				bDifSandbox.setBackground(bNormalButton);
				bDifEasy.setBackground(bNormalButton);
				bDifMedium.setBackground(bPressedButton);
				bDifHard.setBackground(bNormalButton);
				System.out.println("Changed difficulty to medium");
	        }
	    });
		bDifMedium.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 2) {bDifMedium.setBackground(bFocusedButton);}
			}
	    });
		bDifMedium.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 2) {bDifMedium.setBackground(bNormalButton);}
			}
		});
		bDifHard.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				GameStage.difficulty = 3;
				bDifSandbox.setTextFill(Color.BLACK);
				bDifEasy.setTextFill(Color.BLACK);
				bDifMedium.setTextFill(Color.BLACK);
				bDifHard.setTextFill(Color.WHITESMOKE);
				bDifSandbox.setBackground(bNormalButton);
				bDifEasy.setBackground(bNormalButton);
				bDifMedium.setBackground(bNormalButton);
				bDifHard.setBackground(bPressedButton);
				System.out.println("Changed difficulty to hard");
	        }
	    });
		bDifHard.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 3) {bDifHard.setBackground(bFocusedButton);}
			}
	    });
		bDifHard.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (GameStage.difficulty != 3) {bDifHard.setBackground(bNormalButton);}
			}
		});
		
		
		this.show();
                
		System.out.println("settings build finished");	
    }
	
	
	// closes the settings stage and opens the game stage
	void startGame() {
		try {
			System.out.println("startGame method running");
			this.close();
			new GameStage();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
