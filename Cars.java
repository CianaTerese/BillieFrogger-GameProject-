import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Rectangle;

public class Cars extends Sprite implements Runnable {
    private int x, y, width, height, speed;
    private boolean movingLeft;
    private JLabel carLabel;
    
    private boolean moving;
 
    
    //Adding Character2
    private JLabel character2Label;
    
    //Adding Character1
    private Character1 character1;
    private JLabel character1Label;

    public Cars(int x, int y, int width, int height, String imageName, int speed, boolean movingLeft) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;  
        this.speed = speed;
        this.movingLeft = movingLeft;
        
        this.moving = true; 

        carLabel = new JLabel();
        ImageIcon carIcon = new ImageIcon(getClass().getResource("/images/" + imageName));
        Image carImage = carIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        carLabel.setIcon(new ImageIcon(carImage));
        carLabel.setSize(width, height);
        carLabel.setLocation(x, y);
    }

    public void setCharacter1(Character1 temp) {
		character1 = temp;
	}
    public void setCharacter1Label(JLabel temp) {
		character1Label = temp;
	}
	public void setCharacter2Label(JLabel temp) {
		character2Label = temp;
	}
    
    public void move() {
    	 if (moving) {
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
        carLabel.setLocation(x, y); 
    }
    }

    @Override
    public void run() {
        while (true) {
            move(); 
            try {
                Thread.sleep(50);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void detectCollision() {
    	if(this.r.intersects( character1.getRectangle() ) ) {
            System.out.println("Collision detected! Car hit Billie.");
            this.stopMoving();
            
            this.setImage("Billie.png");
            character1.setImage("GreyBillie.png");
           
            
            
            character1Label.setIcon(new ImageIcon(getClass().getResource("images/" + character1.getImage())));
			character2Label.setIcon(new ImageIcon (getClass().getResource("images/" +this.getImage())));
        }
    }
    
    

    private void stopMoving() {
		// TODO Auto-generated method stub
		
	}

	public JLabel getCarLabel() {
        return carLabel;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);  
    }
}







