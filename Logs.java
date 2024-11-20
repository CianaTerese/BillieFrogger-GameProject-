import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Rectangle;

public class Logs extends Sprite implements Runnable {
    private int x, y, width, height, speed;
    private boolean movingLeft;  
    private JLabel logLabel;
    private Rectangle r;
    private Character1 character1;
    private JLabel character1Label;
    private GamePrep gamePrep;
    

    public Logs(int x, int y, int width, int height, String imageName, int speed, boolean movingLeft) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.movingLeft = movingLeft; 
        this.logLabel = new JLabel();
        
        logLabel = new JLabel(new ImageIcon(getClass().getResource("images/" + imageName)));
        logLabel.setSize(width, height);
        logLabel.setLocation(x, y);
        
        this.r = new Rectangle(x, y, width, height);
    }
    
    public void setCharacter1(Character1 temp) {
    	this.character1 = temp;
    	this.character1Label = temp.getLabel();  
	}
    public void setcharacter1Label(JLabel temp) {
		this.character1Label = temp;
	}    
	    
	public JLabel getCharacter1Label() {
		return character1Label; }
	
    public void startThread() {
        Thread t = new Thread(this, "Log thread");
        t.start(); 
    }

    public void stopThread() {
    
    }
    
    public void setGamePrep(GamePrep gamePrep) {
        this.gamePrep = gamePrep;
    }
    

    public Rectangle getRectangle() {
        return r;
    }

    public int getSpeed() {
        return speed;
    }

    public void move() {
        if (movingLeft) {
            x -= speed;
            if (x + width < 0) {
                x = GameProperties.SCREEN_WIDTH; 
            }
        } else {
            x += speed;
            if (x > GameProperties.SCREEN_WIDTH) {
                x = -width; 
            }
        }
        logLabel.setLocation(x, y); 
        r.setLocation(x, y);
    }

    public JLabel getLogLabel() {
        return logLabel;
    }
    
    public void detectCollision() {
    	if (this.r.intersects(character1.getRectangle())) {
    		
    		character1.setX(x);
    		character1Label.setLocation(this.x , this.y);  
    	}
    	
    }
    @Override
    public void run() {
        while (true) {
            try {
                move(); 
               detectCollision();
               if (character1.isOnLog()) {
                   character1Label.setLocation(character1.getX(), character1.getY());
               }
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}



