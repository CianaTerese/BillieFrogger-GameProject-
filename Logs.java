import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.Rectangle;

public class Logs extends Sprite implements Runnable {
    private int x, y, width, height, speed;
    private boolean movingLeft;  
    private JLabel logLabel;
    private Rectangle r;
    
    //Game Started
    private boolean gameStarted = false; 
    

    public Logs(int x, int y, int width, int height, String imageName, int speed, boolean movingLeft) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.movingLeft = movingLeft;
        
        r = new Rectangle(x, y, width, height);
        
        logLabel = new JLabel();
        ImageIcon logIcon = new ImageIcon(getClass().getResource("/images/" + imageName));
        Image logImage = logIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        logLabel.setIcon(new ImageIcon(logImage));
        logLabel.setSize(width, height);
        logLabel.setLocation(x, y);
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
    }

    public JLabel getLogLabel() {
        return logLabel;
    }
    
    
    public void detectCollision(Character1 character) {
        if (!gameStarted) {
            return;
        }

        Rectangle characterRect = character.getRectangle();
        boolean isOnLog = false;

      if (r.intersects(characterRect)) {
            if (characterRect.y + characterRect.height <= r.y + r.height && characterRect.y + characterRect.height >= r.y) {
            	character.setY(r.y - characterRect.height);
            	character.setX(character.getX() + speed);  
                isOnLog = true;
            }
        }

       if (!isOnLog) {
            character.endGame(false);
        }
    }
    
    
    
    
    @Override
    public void run() {
        while (true) {
            try {
                move(); 
                Thread.sleep(50); 
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



