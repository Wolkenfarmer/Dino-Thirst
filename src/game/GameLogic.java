package game;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import Objects.ObjCactus;
import Objects.ObjDino;
import Objects.ObjPassiv;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Wolkenfarmer
*/

public class GameLogic {
	static ObjDino dino;
	public static ObjPassiv floor;
	ObjPassiv edgeL;
	ObjPassiv edgeR;
	static ObjCactus[] cactus = new ObjCactus[15];
	public static double score;	
	int countCactus;
	double timeCactus;
	static double cactusIntervall = 3;
	static double secondStageIntervallBuffer = 0.15;
	Image iDesert;
	GuiContent gui = new GuiContent();
	static boolean onGround;

	
	public GameLogic() throws FileNotFoundException {			
		// loads images of the permanent, visible game elements
		FileInputStream fisDe = new FileInputStream("resources/images/hBackground.png");
		iDesert = new Image(fisDe, GameStage.windowWidth, GameStage.windowHeight, false, true);
		FileInputStream fisDi = new FileInputStream("resources/images/hDino.PNG");
		Image iDino = new Image(fisDi, 0, GameStage.windowHeight * 0.1, true, true);
		FileInputStream fisFo = new FileInputStream("resources/images/hGround.PNG");
		Image iFloor = new Image(fisFo, GameStage.windowWidth, GameStage.windowHeight * 0.09, false, true);
		
		
		// creates permanent game objects
		dino = new ObjDino();
		dino.setPositionX(GameStage.windowWidth / 4);
		dino.setPositionY(GameStage.windowHeight / 4);
		dino.setDimensions(iDino.getWidth(), iDino.getHeight());
		dino.setImage(iDino);
		
		floor = new ObjPassiv();
		floor.setPosition(0, GameStage.windowHeight - (GameStage.windowHeight * 0.09));
		floor.setDimensions(GameStage.windowWidth, GameStage.windowHeight * 0.09);
		floor.setImage(iFloor);
				
		edgeL = new ObjPassiv();
		edgeL.setPosition(-200, 0);
		edgeL.setDimensions(200, GameStage.windowHeight);
		
		edgeR = new ObjPassiv();
		edgeR.setPosition(GameStage.windowWidth, 0);
		edgeR.setDimensions(200, GameStage.windowHeight);
				
		for (int i = 0; i < cactus.length; i++) {
			cactus[i] = new ObjCactus(0);
			cactus[i].setPositionX(GameStage.windowWidth + 10000);
        }
		
		onGround = false;
		
		
		// sets the Lifes for the first run after starting the program
		GameStage.currentLifes = GameStage.numLifes;
	}
	
	
	
	public void gameLogicTick() {
		// enemy-spawning
		timeCactus += GameStage.elapsedTime;
		if (timeCactus >= cactusIntervall) {
        	double random = ThreadLocalRandom.current().nextDouble(GameStage.windowWidth + 200, GameStage.windowWidth + 600);
        	int randomT = ThreadLocalRandom.current().nextInt(0, 2);
			cactus[countCactus] = new ObjCactus(randomT);
			cactus[countCactus].setPositionX(random);
            System.out.println("cactus " + countCactus + " with type " + randomT + " spawned at: " + random);
            countCactus ++;
            if (countCactus == 15) {countCactus = 0;}
            
        	timeCactus = 0;
        	
        	switch (GameStage.difficulty) {
        	case 0:
        		cactusIntervall = Double.MAX_VALUE;
        		break;
        	case 1:
        		if (cactusIntervall > 1.5) {cactusIntervall = cactusIntervall * 0.99;}
        		else {cactusIntervall = 1.35 + secondStageIntervallBuffer * 0.99;}
        		break;
        	case 2:
        		if (cactusIntervall > 1.25) {cactusIntervall = cactusIntervall * 0.975;}
        		else {cactusIntervall = 1.1 + secondStageIntervallBuffer * 0.99;}
        		break;
        	case 3:
        		if (cactusIntervall > 1) {cactusIntervall = cactusIntervall * 0.95;}
        		else {cactusIntervall = 0.85 + secondStageIntervallBuffer * 0.99;}
        	}
        }
		
		// updates the game elements
		dino.update(GameStage.elapsedTime);
		for (int i = 0; i < cactus.length; i++) {
			cactus[i].update(GameStage.elapsedTime);
			
			// score mechanics
			if ((cactus[i].alreadyPassed == false) && (cactus[i].getPositionX() + cactus[i].getWidth()) < dino.getPositionX()) {
				score += 1;
				cactus[i].alreadyPassed = true;       		
			}
		}        
        
        // checks collision between dino and cactus
        for (int i = 0; i < cactus.length; i++) {
    		if (dino.intersectsC(cactus[i])) {
        		if (!cactus[i].getDeliveredDamages()) {
        			if (pixelCollision(dino.getPositionX(), dino.getPositionY(), dino.getImage(), cactus[i].getPositionX(), 
        					cactus[i].getPositionY(), cactus[i].getImage())) {
        				GuiContent.deleteLife(GameStage.currentLifes);
        				GameStage.currentLifes--;
        				if (GameStage.currentLifes == 0) {
        					GameStage.stillAlive = false;
        					GameStage.onDeath();
        				}
        				cactus[i].setDeliveredDamage(true);
        				System.out.println("player got damage by cactus " + i);
        			}
        		}
        	}
        }
        
        // checks collision between dino and screen borders
        if (dino.intersectsP(edgeL)) {
        	dino.setPositionX(0);
        } else if (dino.intersectsP(edgeR)) {
        	dino.setPositionX(GameStage.windowWidth - dino.getWidth());
        }
        
        // controls over dino and gravity
        if (onGround) {
        	if (GameStage.input.contains("UP") || GameStage.input.contains("W") || GameStage.input.contains("SPACE")) {
        		dino.setVelocityY(GameStage.windowHeight * -1.0);
        		onGround = false;
        	}        	
        } else {
        	if (dino.intersectsP(floor)) {
        		dino.setVelocityY(0);
  			  	dino.setPositionY(GameStage.windowHeight - floor.getHeight() - dino.getHeight());
  			  	onGround = true;
  			  	if (GameStage.input.contains("UP") || GameStage.input.contains("W") || GameStage.input.contains("SPACE")) {
  			  		dino.setVelocityY(GameStage.windowHeight * -1.0);
  			  		onGround = false;
  			  	}
        	} else {
        		dino.addVelocityY(GameStage.windowHeight * 1.6 * GameStage.elapsedTime);
        	}
        }
        
		if (GameStage.input.contains("LEFT") || GameStage.input.contains("A")) dino.setVelocityX(GameStage.windowWidth * -0.15);
        if (GameStage.input.contains("RIGHT") || GameStage.input.contains("D")) dino.setVelocityX(GameStage.windowWidth * 0.15);
        if (!GameStage.input.contains("LEFT") && !GameStage.input.contains("RIGHT") && 
        		!GameStage.input.contains("A") && !GameStage.input.contains("D")) dino.setVelocityX(0);
	}
	
	
	// performs pixel-perfect collision detection between two objects
	public boolean pixelCollision(double x1, double y1, Image fxImage1, double x2, double y2, Image fxImage2) {		
		BufferedImage image1 = SwingFXUtils.fromFXImage(fxImage1, null);
		BufferedImage image2 = SwingFXUtils.fromFXImage(fxImage2, null);
		
		double width1 = x1 + image1.getWidth();
		double height1 = y1 + image1.getHeight();
	    double width2 = x2 + image2.getWidth();
	    double height2 = y2 + image2.getHeight();

		int xstart = (int) Math.max(x1, x2); 
		int ystart = (int) Math.max(y1, y2); 
		int xend = (int) Math.min(width1, width2);
	    int yend = (int) Math.min(height1, height2);

		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);

		for (int y = 1; y < toty - 1; y++) {
			int ny = Math.abs(ystart - (int) y1) + y;
			int ny1 = Math.abs(ystart - (int) y2) + y;

			for (int x = 1; x < totx - 1; x++) {
				int nx = Math.abs(xstart - (int) x1) + x;
				int nx1 = Math.abs(xstart - (int) x2) + x;
				if (((image1.getRGB(nx, ny) & 0xFF000000) != 0x00) && ((image2.getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {return true;}
			}
		}
		return false;
	}
	
	
	// extra render mthod for the desert, because it has no extra object-class
	public void renderDesert(GraphicsContext gc) {
		gc.drawImage(iDesert, 0, 0);
    }
}
