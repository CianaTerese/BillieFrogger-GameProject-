import java.awt.Rectangle;

public class Character1 extends Sprite {
    private int x, y, width, height;
    private boolean isOnLog = false;
    
	public Character1(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
	}

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public void endGame(boolean win) {
        if (!win) {
            System.out.println("Game Over");
        } else {
            System.out.println("You Win!");
        }
    }

	public boolean isOnLog() {
		return isOnLog;
	}

	public void setOnLog(boolean isOnLog) {
		this.isOnLog = isOnLog;
	}
}
