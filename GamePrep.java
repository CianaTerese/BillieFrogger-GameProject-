import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class GamePrep extends JFrame implements KeyListener, ActionListener {
    
    /**
	 1.) i worked on use the code you gave us in the demo in class 
	 2.) Cars/Log Movement and array
	 3.) Collision
	 4.) Score Label
	 5.) Getting background to work and getting billie.png to get shown above background
	 6.) Getting Billie.png finally to show and test collision 
	 7.) somehow broke my bill :( 
	
	
	after first look with darren
	1.)fixed billie (and her rectangle because it wasnt moving and its x,y are 0) 
	2.)fixed threads
	3.)fixed connections between gameprep, sprite, logs, cars
	4.)fixed cars collision
	5.)fixed logs 
	6.)made a water bounds so if she goes in the water she dies
	7.)made the top space saying you win and get points
	
	 
	 * 
	 */

	
	
	//Character
	private Character1 character1;
  

    //Logs & Cars
    private Logs[] logs;
    private Cars[] cars;

    //GUI variables
    private Container content;
    private JLabel character1Label;
    //CarsLabel; 
    private ImageIcon character1Image;
    
    //2 buttons
    private JButton startBtn, visibilityBtn;
    
    //Background
    private Image backgroundImage;
    private JLabel backgroundLabel; 

   //Game Data & Score
    private GameData gameData; 
    private JLabel scoreLabel;

    
    public GamePrep(String playername) {
        super("Billie Frogger");
        
        
        //Screen
        setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
        content = getContentPane();
        content.setLayout(null);

        //For Background
        backgroundImage = new ImageIcon(getClass().getResource("/images/billiebackground.png")).getImage();  
        //I used .getScaledInstance because it wasn't showing my background properly 
        backgroundImage = backgroundImage.getScaledInstance(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
        //i changed the size of the images and moved the cars&logs
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT); 
        backgroundLabel.setLocation(0, 0);
        
 
        //Game Data and Score Setup
        gameData = new GameData(playername);
        scoreLabel = new JLabel("Score: " + gameData.getScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 200, 30);
       
        
        //Character1
        character1 = new Character1(460, 520, 30, 30, "Billie.png");
        character1Label = new JLabel();
        character1Image = new ImageIcon(getClass().getResource("images/"+ character1.getImage()));
        character1Label.setIcon(character1Image);
        character1Label.setSize(character1Image.getIconWidth(), character1Image.getIconHeight());
        
        character1Label = character1.getCharacter1Label(); 
        content.add(character1Label);
        

        character1Label.setLocation(character1.getX(), character1.getY());
        add(character1Label);
        
        //Logs
        logs = new Logs[5];
        logs[0] = new Logs(0, 40, 50, 55, "log.png", 3, false); 
        logs[1] = new Logs(0, 80, 50, 55, "log.png", 2, true);  
        logs[2] = new Logs(0, 120, 50, 55, "log.png", 1, false); 
        logs[3] = new Logs(0, 160, 50, 55, "log.png", 2, true);  
        logs[4] = new Logs(0, 200, 50, 55, "log.png", 3, false);  
        
	  //Cars
        cars = new Cars[4];
        cars[0] = new Cars(850, 320, 50, 55, "BlueCar.png", 9, true);
        cars[1] = new Cars(850, 380, 50, 55, "BlueCar.png", 5, false);
        cars[2] = new Cars(850, 420, 50, 55, "BlueCar.png", 7, true);
        cars[3] = new Cars(850, 460 , 50, 55, "BlueCar.png", 6, false);
        
        for (Cars car : cars) {
        	car.setCharacter1(character1);
      	car.setStartButton(startBtn); 
            car.setVisibilityButton(visibilityBtn);
            car.setGamePrep(this);
            JLabel carLabel = car.getcarLabel();
            content.add(carLabel);
        }     
      
        for (Logs log : logs) {
        log.setCharacter1(character1);
        log.setGamePrep(this);
        content.add(log.getLogLabel());
        }
        
        //Visibility Button
        visibilityBtn = new JButton("Hide");
        visibilityBtn.setSize(100, 50);
        visibilityBtn.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 100);
        visibilityBtn.setFocusable(false);
        visibilityBtn.addActionListener(this);
        
        //StartButton
        startBtn = new JButton("Run");
        startBtn.setSize(100, 100);
        startBtn.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 200);
        startBtn.setFocusable(false);
        startBtn.addActionListener(this);

        //Adding
        add(startBtn);
        add(visibilityBtn);  
        add(scoreLabel);
        add(backgroundLabel);
     
        content.addKeyListener(this);
        content.setFocusable(true);
    }

    
    //End Game
    public void endGame(boolean successful) {
       //test/
    	System.out.println("End game triggered");

        String message = successful ? "Congratulations! You won!" : "Game Over! You lost!";
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

       if (successful) {
            gameData.addPoints(50); 
        } else {
            gameData.subtractPoints(50); 
        }

        JOptionPane.showMessageDialog(this, "Your final score is: " + gameData.getScore());

        int response = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Restart Game", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            restartGame(); 
        } else {
            System.exit(0); 
        }
    }

    //Restart Game
    public void restartGame() {
        gameData = new GameData(gameData.getPlayerName()); 
        scoreLabel.setText("Score: " + gameData.getScore()); 
        //character1 = new Character1(460, 520, 30, 30, "Billie.png");
        character1.setX(460);
        character1.setY(520);
        character1Label.setLocation(character1.getX(), character1.getY());
        
        for (Cars car : cars) {
            car.setX(car.getX());  
            car.setY(car.getY());
            car.stopThread();  
        }
        for (Logs log : logs) {
            log.stopThread();  
        }

       
        for (Cars car : cars) {
            car.startThread();
        }
        for (Logs log : logs) {
            new Thread(log).start(); 
        }
        
        this.setVisible(true);
    }



	@Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int x = character1.getX();
        int y = character1.getY();
        
		if (e.getKeyCode() == KeyEvent.VK_UP){
			y -= GameProperties.CHARACTER_STEP;
			
			
			if( y + character1.getHeight() <= 0) {
				y = GameProperties.SCREEN_HEIGHT;
				endGame(true);
				System.out.println("up");
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			y += GameProperties.CHARACTER_STEP;
			
			if( y + character1.getHeight () > GameProperties.SCREEN_HEIGHT) {
				//y = -1 * character1.getHeight();
				y = GameProperties.SCREEN_HEIGHT - character1.getHeight();
				System.out.println("down");
			}

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			x -= GameProperties.CHARACTER_STEP;
			
			if (x + character1.getWidth() <= 0) {
				x = GameProperties.SCREEN_WIDTH;
				System.out.println("left");
			}
			

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			x += GameProperties.CHARACTER_STEP;
			
			if ( x >= GameProperties.SCREEN_WIDTH) {
				x = -1 * character1.getWidth();
				System.out.println("right");
			}

		}
        
        character1.setX(x);
        character1.setY(y);
      
        character1Label.setLocation(character1.getX(), character1.getY()); 
 
        boolean isOnLog = false;
        for (Logs log : logs) {
            if (log.getRectangle().intersects(character1.getRectangle())) {
                isOnLog = true;
                break;
            }
        }

        if (isOnLog) {
            System.out.println("Billie is on the log!");
        } else if (character1.getY() >= 30 && character1.getY() <= 240) {
            System.out.println("Billie is in the water!");
            endGame(false);  
            return;
        }
   
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visibilityBtn) {
        	for (Cars car: cars)
            if (car.getVisible()) {
                car.hide();
            } else {
                car.show();
            }
        } else if (e.getSource() == startBtn) {
        	for (Cars car: cars)
            if (car.getMoving()) {
                car.stopThread();
                for (Logs log : logs) {
                    log.stopThread();
                }
            } else {
                car.startThread();
               
                for (Logs log : logs) {
                    new Thread(log).start();  
                }
            }
        }
    }
    

    public static void main(String[] args) {
    	   String playerName = JOptionPane.showInputDialog("Enter your name:");

    	    if (playerName == null || playerName.trim().isEmpty()) {
    	        playerName = "Player";  
    	    }

    	    GamePrep myGame = new GamePrep(playerName); 
    	    myGame.setVisible(true); 
    }
}
