
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
	 * 
	 */

	
	
	//Characters
	private Character1 character1;
    private Character2 character2;

    //Logs & Cars
    private Logs[] logs;
    private Cars[] cars;

    //GUI variables
    private Container content;
    private JLabel character1Label, character2Label; 
    private ImageIcon character1Image, character2Image;
    
    //2 buttons
    private JButton startBtn, visibilityBtn;
    
    //Background
    private Image backgroundImage;
    private JLabel backgroundLabel; 

   //Game Data & Score
    private GameData gameData; 
    private JLabel scoreLabel;
    
    //Game Started
    private boolean gameStarted = false; 

    public GamePrep(String playername) {
        super("Billie Frogger");
        gameData = new GameData(playername);
        
        scoreLabel = new JLabel("Score: " + gameData.getScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 200, 30);
       
        
        //Characters
        character1 = new Character1(460, 520, 50, 50, "Billie.png");
        character2 = new Character2(0, 10, 45, 45, "BlueCar.png");
        
        //Background
        //.getImage was because my background look good  otherwise
        backgroundImage = new ImageIcon(getClass().getResource("/images/billiebackground.png")).getImage();
        
        //I used .getScaledInstance because it wasn't showing my background properly 
        backgroundImage = backgroundImage.getScaledInstance(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT); 
        backgroundLabel.setLocation(0, 0);

        //Logs
        logs = new Logs[5];
        logs[0] = new Logs(0, 40, 50, 55, "log.png", 3, false); 
        logs[1] = new Logs(0, 80, 50, 55, "log.png", 2, true);  
        logs[2] = new Logs(0, 120, 50, 55, "log.png", 1, false); 
        logs[3] = new Logs(0, 170, 50, 55, "log.png", 3, true);  
        logs[4] = new Logs(0, 220, 50, 55, "log.png", 4, false);  
       
        //Log Thread
        for (Logs log : logs) {
            Thread logThread = new Thread(log);
            logThread.start();
            log.detectCollision(character1);
        }
        
        //Cars
        cars = new Cars[4];
        cars[0] = new Cars(1000, 320, 45, 45, "BlueCar.png", 3, true);
        cars[1] = new Cars(1200, 370, 45, 45, "BlueCar.png", 2, false);
        cars[2] = new Cars(1100, 420, 45, 45, "BlueCar.png", 4, true);
        cars[3] = new Cars(1300, 455, 45, 45, "BlueCar.png", 5, false);
        //i took this car out so it wont hit bill instantly
        //cars[4] = new Cars(1500, 410 + 100, 50, 50, "BlueCar.png", 1, true);
        
        //Car Thread
        for (Cars car : cars) {
            Thread carThread = new Thread(car); 
            carThread.start(); 
        }
        
        
        //For Background
        setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
        content = getContentPane();
        content.setLayout(null);
      
        //Character 1
        character1Label = new JLabel();
        character1Image = new ImageIcon(getClass().getResource("images/"+ character1.getImage()));
        character1Label.setIcon(character1Image);
        character1Label.setLocation(character1.getX(), character1.getY());  
        //got Bill to show
        character1Label.setSize(character1Image.getIconWidth(), character1Image.getIconHeight());
        
       

        character2Label = new JLabel();
        character2Image = new ImageIcon(getClass().getResource("images/" + character2.getImage()));
        character2Label.setIcon(character2Image);
        character2Label.setSize(character2.getWidth(), character2.getHeight());
        character2Label.setLocation(character2.getX(), character2.getY());

        character2.setCharacter1(character1);
        character2.setCharacter1Label(character1Label);
        
        character2.setCharacter2Label(character2Label);
        
        //Logs and Cars
        for (Cars car : cars) {
            content.add(car.getCarLabel());
            
        }

        for (Logs log : logs) {
            content.add(log.getLogLabel());
        }
       
        //Visibility Button
        visibilityBtn = new JButton("Hide");
        visibilityBtn.setSize(100, 50);
        visibilityBtn.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 100);
        visibilityBtn.setFocusable(false);
        visibilityBtn.addActionListener(this);
        character2.setVisibilityButton(visibilityBtn);

        
        //StartButton
        startBtn = new JButton("Run");
        startBtn.setSize(100, 100);
        startBtn.setLocation(GameProperties.SCREEN_WIDTH - 100, GameProperties.SCREEN_HEIGHT - 200);
        startBtn.setFocusable(false);
        startBtn.addActionListener(this);
        
        
        character2.setStartButton(startBtn);

        //Adding
        add(startBtn);
        add(visibilityBtn);
        content.add(character1Label);
        content.add(character2Label);
        content.add(scoreLabel);
        content.add(backgroundLabel);
        
       
        
        content.addKeyListener(this);
        content.setFocusable(true);
      
        
    }
    
    
    //End Game
    public void endGame(boolean successful) {
       

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
        
        character1 = new Character1(460, 520, 50, 50, "Billie.png");
        character2.setX(100);
        character2.setY(100);

        this.setVisible(true);
    }



	@Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int x = character1.getX();
        int y = character1.getY();
        
        //Moving Up
        if (e.getKeyCode() == KeyEvent.VK_UP){
			y -= GameProperties.CHARACTER_STEP;
			if( y + character1.getHeight() <= 0) {
				y = GameProperties.SCREEN_HEIGHT;
			}
			
			
		//Moving Down
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			y += GameProperties.CHARACTER_STEP;
			
			if( y >= character1.getHeight ()) {
				y = -1 * character1.getHeight();
			}
		
		//Moving Left
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			x -= GameProperties.CHARACTER_STEP;
			
			if (x + character1.getWidth() <= 0) {
				x = GameProperties.SCREEN_WIDTH;
			}
			
		//Moving Right
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			x += GameProperties.CHARACTER_STEP;
			
			if ( x >= GameProperties.SCREEN_WIDTH) {
				x = -1 * character1.getWidth();
        }}

        
        
        character1.setX(x);
        character1.setY(y);
        character1Label.setLocation(character1.getX(), character1.getY()); 
        
   
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visibilityBtn) {
            if (character2.getVisible()) {
                character2.hide();
            } else {
                character2.show();
            }
        } else if (e.getSource() == startBtn) {
            if (character2.getMoving()) {
                character2.stopThread();
            } else {
                character2.startThread();
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


	public boolean isGameStarted() {
		return gameStarted;
	}


	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}}



