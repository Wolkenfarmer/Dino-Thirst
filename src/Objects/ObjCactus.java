package Objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.GameLogic;
import game.GameStage;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Wolkenfarmer
*/

public class ObjCactus {
	private Image image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double width;
    private double height;
    private boolean deliveredDamage;
    public boolean alreadyPassed;
 
    
    // spawns given cactus type
    public ObjCactus(int t) {
    	switch (t) {
    		case 0:
				try {
					FileInputStream fisCl = new FileInputStream("resources/images/hCactusL2.png");
					Image iCl = new Image(fisCl, 0, GameStage.windowHeight * 0.1, true, true);
	    	    	setImage(iCl);
	    	    	setDimensions(iCl.getWidth(), iCl.getHeight());
	    	    	setPositionY(GameStage.windowHeight - getHeight() - GameLogic.floor.getHeight());
				} catch (FileNotFoundException e) {System.out.println("error: little cactus image missing \n" + e);}
    			break;
    		case 1:
				try {
					FileInputStream fisCb = new FileInputStream("resources/images/hCactusB2.png");
					Image iCb = new Image(fisCb, 0, GameStage.windowHeight * 0.2, true, true);
	    	    	setImage(iCb);
	    	    	setDimensions(iCb.getWidth(), iCb.getHeight());
	    	    	setPositionY(GameStage.windowHeight - getHeight() - GameLogic.floor.getHeight());
				} catch (FileNotFoundException e) {System.out.println("error: big cactus image missing \n" + e);}
				break;
			default:
				System.out.println("error: Enemy-type wrong");
    	}
    	setVelocityX(GameStage.windowWidth * -0.15);
    	deliveredDamage = false;
    }
    
    
    // basic methods for working with this object
    public void setPositionX(double d) {positionX = d;}
    public void setPositionY(double d) {positionY = d;}   
    public void setDimensions(double d, double e) {
    	width = d;
    	height = e;
    }    
    public void setImage(Image i) {image = i;}    
    public void setVelocityX(double d) {velocityX = d;}  
    public void setDeliveredDamage(boolean deliveredDamage) {this.deliveredDamage = deliveredDamage;}
    public double getPositionX() {return positionX;}
    public double getPositionY() {return positionY;}
    public Image getImage() {return image;}
    public double getWidth() {return width;} 
    public double getHeight() {return height;} 
    public boolean getDeliveredDamages() {return deliveredDamage;}
    
    // updates the position of the object
    public void update(double time) {positionX += velocityX * time;}
    
    // repaints the object
    public void render(GraphicsContext gc) {gc.drawImage(image, positionX, positionY);} 
    
    // gives rectangle of object for collsion detection
    public Rectangle2D getBoundary() {return new Rectangle2D(positionX, positionY, width, height);}    
}
