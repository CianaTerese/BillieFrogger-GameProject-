import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Character1 extends Sprite {
    private int x, y, width, height;
    private boolean isOnLog = false;
    private JLabel character1Label;
    private boolean isMoving = true;
    private Rectangle r;
    
	public Character1(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
	    this.x = x;
	    this.y = y;
	    this.width = width;
        this.height = height;
        this.image = image;
        
        this.r = new Rectangle(x, y, width, height);
        
        this.character1Label = new JLabel(new ImageIcon(getClass().getResource("images/" + image)));
        this.character1Label.setSize(width, height);  
        this.character1Label.setLocation(x, y);   
	}
	
	
    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }
    
	 public JLabel getLabel() {
	        return character1Label;
	    }

    public Rectangle getRectangle() {
    	return r;
    }
   
   

    public void setX(int x) {
        this.x = x;
       this.r.setLocation(this.x, y); 
       this.character1Label.setLocation(x, this.y);
        
    }

    public void setY(int y) {
        this.y = y;
        this.r.setLocation(x, this.y);
        this.character1Label.setLocation(this.x, y); 
    }

    public int getX() {return x;}
    public int getY() {return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public boolean isInWater(Water water) {
        return this.r.intersects(water.getRectangle());
    }

	public boolean isOnLog() {
		return isOnLog;
	}

	public void setOnLog(boolean isOnLog) {
		this.isOnLog = isOnLog;
	}

	public void setCharacter1Label(JLabel Label) {
		this.character1Label= Label; }
	    
	    
	public JLabel getCharacter1Label() {
		return character1Label; }
	
}

