import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Character2 extends Sprite implements Runnable {
    private Boolean visible, moving; 
    private Thread t;

    private JLabel character2Label;
    private JButton startButton, visibilityButton;
    
    private Character1 character1;
    private JLabel character1Label;
    
    public Character2(int x, int y, int height, int width, String image) {
        super(x, y, height, width, image);
        this.moving = false;
        this.visible = true;
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

    public void setStartButton(JButton temp) {
        startButton = temp;
    }

    public void setVisibilityButton(JButton temp) {
        visibilityButton = temp;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }

    public void startThread() {
        if (!this.moving) {
            this.moving = true;
            startButton.setText("Stop");

            this.setImage("Billie.png");
            character1Label.setIcon(new ImageIcon(getClass().getResource("images/" + character1.getImage())));
            this.setImage("BlueCar.png");
            character2Label.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));
            
            
            t = new Thread(this, "Character2 thread");
            t.start();
            System.out.println("Starting thread");
        }
    }

    public void stopThread() {
        if (this.moving) {
            this.moving = false;
            startButton.setText("Start");
            t.interrupt();
            System.out.println("Stopping thread");
        }
    }

    @Override
    public void run() {
        System.out.println("Run triggered");

        while (this.moving) {
            int x = this.x;
            x += GameProperties.CHARACTER_STEP;

            if (x >= GameProperties.SCREEN_WIDTH) {
                x = -1 * this.width;
            }

            this.setX(x); 
            character2Label.setLocation(this.x, this.y); 

            if (this.visible) {
                this.detectCollision();  
            }

            System.out.println("x, y: " + this.x + " " + this.y);

            try {
                Thread.sleep(100);  
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            	break;
            }
        }

        System.out.println("Thread Stopped");
    }

    public void hide() {
        this.visible = false;
        character2Label.setVisible(this.visible);
        visibilityButton.setText("Show");
    }

    public void show() {
        this.visible = true;
        character2Label.setVisible(this.visible);
        visibilityButton.setText("Hide");
    }

    private void detectCollision() {
    	
    	
    	   Rectangle carRect = this.getRectangle();
    	   Rectangle billieRect = character1.getRectangle();
    	   
    	   
    	    
        if (carRect.intersects(billieRect)) {
            System.out.println("BOOM! Hit Character1!");

            this.stopThread();

            this.setImage("GreyBillie.png");
            character1.setImage("GreyBillie.png");

            character1Label.setIcon(new ImageIcon(getClass().getResource("images/" + character1.getImage())));
            character2Label.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));

         gameOver();
        }

    }
    private void gameOver() {
        System.out.println("Game Over!");
 }


    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
