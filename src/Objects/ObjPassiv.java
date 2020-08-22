package Objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Wolkenfarmer
*/

public class ObjPassiv {
	private Image image;
    private double positionX;
    private double positionY;
    private double width;
    private double height;
 
    
    // basic methods for working with this object
    public void setPosition(double x, double y) {
    	positionX = x;
    	positionY = y;
    }
    public void setDimensions(double w, double windowHeight) {
    	width = w;
    	height = windowHeight;
    }
    public void setImage(Image i) {image = i;}
    public double getHeight() {return height;}
 
    // repaints the object
    public void render(GraphicsContext gc) {gc.drawImage(image, positionX, positionY);}
    
    // gives rectangle of object for collsion detection
    public Rectangle2D getBoundary() {return new Rectangle2D(positionX, positionY, width, height);}
}