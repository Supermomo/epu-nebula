package nebula.minigame.cropcircle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CustomProgressBar {

	private Image imgProgressBar;
	private int malusMax=Cropcircle.MALUS_TANK_MAX;
	private String imgBarPath="assets/images/cropCircle/FlamProgressBar.png";
	private float pct=1.0f;
	
	public CustomProgressBar(){
		try {
			imgProgressBar=new Image(imgBarPath);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param gc
	 * @param pct %of the bar to draw
	 */
	public Image getBar(){
		float width=imgProgressBar.getWidth()*pct;
		int height=imgProgressBar.getHeight();
		return imgProgressBar.getSubImage(0, 0, (int)width, height);
	}
	
	public void updatePct(float malus){
		pct=1-(malus/malusMax);
	}
}
