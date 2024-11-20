import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class Cars extends Sprite implements Runnable {
    private int x, y, width, height, speed;
    private boolean movingLeft; 
    private JButton startButton, visibilityButton;
    private Boolean visible, moving; 
    private Thread t;
    
    //Car 
    private JLabel carLabel; 

    //Adding Character1
    private Character1 character1;
    private JLabel character1Label;
    
    //test
    private GamePrep gamePrep; 

    
    public void setCharacter1(Character1 temp) {
    	this.character1 = temp;
    	this.character1Label = temp.getLabel();  
	}
    public void setcharacter1Label(JLabel temp) {
		this.character1Label = temp;
	}
 
    public void setGamePrep(GamePrep gamePrep) {
        this.gamePrep = gamePrep;
    }
    public void setStartButton(JButton temp) {
    	  this.startButton = temp;
    }

    public void setVisibilityButton(JButton temp) {
        visibilityButton = temp;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Boolean getVisible() {
        return visible;
    }

    public Boolean getMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }
    public Cars() {
 		super();
 		this.moving = false;
 		this.visible = true; 
 	}
     public Cars(int x, int y, int width, int height, String imageName, int speed, boolean movingLeft) {
     	super(x,y,height,width,imageName);
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
         this.visible = true;
         this.moving = false; 
         this.speed = speed;
         this.image = imageName;
         this.movingLeft = movingLeft;
        
         this.carLabel = new JLabel(new ImageIcon(getClass().getResource("images/" + image)));
         this.carLabel.setSize(width, height);  
         this.carLabel.setLocation(x, y);
         
         
         this.r = new Rectangle(x, y, width, height); 
     }
     
     public JLabel getcarLabel() {
         return carLabel;
     }
     public void setcarLabel(JLabel Label) {
     	this.carLabel = Label;
     }
     
    public void hide() {
        this.visible = false;
        carLabel.setVisible(false); 
        visibilityButton.setText("Show");
    }

    public void show() {
        this.visible = true;
        carLabel.setVisible(true);
        visibilityButton.setText("Hide");
    }
    
    //test

    public void startThread() {
        if (!this.moving) {
            this.moving = true;
            if (startButton != null) {
                startButton.setText("Stop");
            }
            setImage("BlueCar.png");
            if (carLabel != null) {
                carLabel.setIcon(new ImageIcon(getClass().getResource("images/" + this.getImage())));
            }

            System.out.println("Start thread");
            t = new Thread(this, "Car thread");
            t.start();
        }
    }

    public void stopThread() {
        if (this.moving) {
            this.moving = false;   
            //test
            if (startButton != null) {
                startButton.setText("Start");
            }
            if (t != null) {
            //
               // startButton.setText("Start");        
               t.interrupt();
            }}
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
        carLabel.setLocation(x, y); 
        r.setLocation(x, y);
    }
    
    //test
    @Override
    public void run() {
        while (this.moving) {
            if (Thread.interrupted()) {
                break; 
            }

            move();
            //test for car label
            carLabel.setLocation(x, y);
            //
            detectCollision();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
               
                Thread.currentThread().interrupt(); 
                break;
            }
        }
        System.out.println("Thread stopped.");
    }

    private void detectCollision() {
        Rectangle carRect = getRectangle(); 
        Rectangle billieRect = character1.getRectangle();
        
        if (carRect.intersects(billieRect)) {
            System.out.println("BOOM! Car hit Billie!");
            
            stopThread();       
            gamePrep.endGame(false); 
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);  
    }
}

