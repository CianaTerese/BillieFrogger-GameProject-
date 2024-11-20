import java.awt.Rectangle;

public class Sprite {
	protected int x, y; 
	protected int height, width; 
	protected String image;
	protected Rectangle r;
	
	public Sprite() {
		super();
		r = new Rectangle (this.x, this.y, this.width,this.height);
	}
	
	public Sprite(int x, int y, int height, int width, String image ) {
		
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
		r = new Rectangle (this.x, this.y, this.width,this.height);	
		
	}
	
	 public Rectangle getRectangle() {
	        return new Rectangle(x, y, width, height);
	    }

	//Getters and setters
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
		this.r.x= this.x;
	}

	
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
		this.r.y= this.y;
	}

	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		this.r.height = this.height;
	}

	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		this.r.width = this.width;
	}

	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
