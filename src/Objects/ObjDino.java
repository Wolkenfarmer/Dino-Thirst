package Objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Wolkenfarmer
*/

public class ObjDino {
	private Image image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    
    
    // basic methods for working with this object    
    public void setPositionX(double d) {positionX = d;}
    public void setPositionY(double d) {positionY = d;}    
    public void setDimensions(double w, double h) {
    	width = w;
    	height = h;
    }
    public void setImage(Image i) {image = i;}    
    public void setVelocityX(double d) {velocityX = d;}
    public void setVelocityY(double velY) {velocityY = velY;}    
    public void addVelocityY(double d) {velocityY += d;}
	public double getPositionX() {return positionX;}
	public double getPositionY() {return positionY;}
	public double getVelocityY() {return velocityX;}
	public double getWidth() {return width;}
	public double getHeight() {return height;}
	public Image getImage() {return image;}
	
	// repaints the object
	public void render(GraphicsContext gc) {gc.drawImage(image, positionX, positionY);}    
	
	// updates the position of the object
	public void update(double time) {
		positionX += velocityX * time;
		positionY += velocityY * time;
	} 
	
	
	// methods for collsion detection
	
	// gives rectangle of object for collsion detection
	public Rectangle2D getBoundary() {
		return new Rectangle2D(positionX, positionY, width, height);
	}
	// checks rectangle-collision with a cactus
	public boolean intersectsC(ObjCactus c) {
		return this.getBoundary().intersects(c.getBoundary());
	}
	// checks rectanngle-collsion with game borders
	public boolean intersectsP(ObjPassiv p) { 
		return this.getBoundary().intersects(p.getBoundary()); 
	}
}
