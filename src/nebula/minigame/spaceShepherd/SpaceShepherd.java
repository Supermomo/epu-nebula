package nebula.minigame.spaceShepherd;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import nebula.core.helper.QuickSort;
import nebula.minigame.cropcircle.Cropcircle;
import nebula.minigame.cropcircle.CustomProgressBar;
import nebula.minigame.cropcircle.MyLine;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

public class SpaceShepherd extends BasicGame{

	private Image land = null;
	private float x = 400;
	private float y = 300;
	/** Radius of one image */
	private int imgRadius ;
	/** Coefficient for the malus you get when you don't fill the track */
	private float malusScoreCoef = 1.0f;
	/**the list of all the fences that has been created */
	private ArrayList<Line> fences;

	/** the last drawnPoint */
	private Vector2f lastPlot = null;
	/** Variator for the deplacement speed */
	private float pointerSpeed = 0.4f;
	
	private Flock flock;
	private boolean spaceReleased=true;

	private Vector2f targetCenter;
	private int targetRadius=60;
	
	private Image victoryImg;
	private Image  lossImg;
	private String pathVictoryImg="assets/images/spaceInvaders/victoire.png";
	private String pathlLossImg="assets/images/spaceInvaders/defaite.png";
	
	public SpaceShepherd(){
		super("SpaceShepherd");
	}



	@Override
	public void init(GameContainer gc) throws SlickException {
		imgRadius=(int) (gc.getScreenWidth()*0.01);
		fences=new ArrayList<Line>();
		flock=new Flock(gc.getWidth()/2,gc.getHeight()/2,0.3f, gc.getWidth(), gc.getHeight());
		
		targetCenter=new Vector2f(new Random().nextInt(gc.getWidth()-60)
				,new Random().nextInt(gc.getHeight()-60));
		
		victoryImg=new Image(pathVictoryImg);
		lossImg=new Image(pathlLossImg);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		flock.moveRandom(delta, fences);
		
		if (input.isKeyDown(Input.KEY_M)) {

		}

		if (input.isKeyDown(Input.KEY_LEFT) && x > 0) {
			x -= (delta * pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_RIGHT) && x+10 < gc.getWidth()) {
			x += (delta * pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_UP) && y > 0) {
			y -= (delta * pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_DOWN) && y+10 < gc.getHeight()) {
			y += (delta * pointerSpeed);
		}
		
		if(!input.isKeyDown(Input.KEY_SPACE)){
			spaceReleased=true;
		}

		if (input.isKeyDown(Input.KEY_SPACE) && spaceReleased) {
			
			spaceReleased=false;
			
			if(lastPlot==null){
				lastPlot=new Vector2f(x,y);
			}
			else if (validDistanceFromLastPoint()) {
				Vector2f plot=new Vector2f(x,y);
				fences.add(new Line(lastPlot,plot));
				lastPlot=null;
			}
			
		}
		
		if(flock.allIntheHole(targetCenter, targetRadius)){
			gc.pause();
			
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		//land.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.yellow);
		g.fillOval(targetCenter.x-targetRadius, targetCenter.y-targetRadius, targetRadius*2, targetRadius*2);
		g.setColor(Color.white);
		g.setLineWidth(10);
		for(Line l : fences){
			g.drawOval(l.getX1(), l.getY1(), imgRadius, imgRadius);
			g.drawOval(l.getX2(), l.getY2(), imgRadius, imgRadius);
			g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
		}
		if(lastPlot!=null){
			g.drawOval(lastPlot.x, lastPlot.y, imgRadius, imgRadius);
		}
		
		g.setColor(Color.green);
		g.drawOval(flock.getPosition().x, flock.getPosition().y, 5,5, 16);
		
		g.setColor(Color.red);
		for(SteeringEntity st : flock.getFlockers()){
			g.fillOval(st.getPosition().x, st.getPosition().y, 10, 10);
		}
		
		g.setColor(Color.blue);
		g.drawOval(x, y, 30, 30, 8);

		
		if(flock.allIntheHole(targetCenter, targetRadius)){	
			victoryImg.draw(0,0,gc.getWidth(),gc.getHeight());
		}
		
	}
	
	
	private boolean validDistanceFromLastPoint() {
		return (Math.abs(lastPlot.x - x) > imgRadius || Math.abs(lastPlot.y
				- y) > imgRadius);
	}

	
	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new SpaceShepherd());
		/*app.setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height, true);*/
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(60);
		app.start();
	}
	
}
