package nebula.minigame.spaceShepherd;

import java.util.ArrayList;
import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.minigame.Minigame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class SpaceShepherd extends Minigame {

	private Image land = null;
	private float x = 400;
	private float y = 300;
	/** Radius of one plot */
	private int plotRadius ;
	private int flockRadius;
	private int fenceThickness;
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
	private int targetRadius=40;
	
	private int borderMargin=50;
	
	private Image victoryImg;
	private Image  lossImg;
	private Image  flockImg;
	private Image  leadImg;
	private Image  plotImg;
	private Image  fenceImg;
	private String pathVictoryImg="ressources/images/spaceInvaders/victoire.png";
	private String pathlLossImg="ressources/images/spaceInvaders/defaite.png";
	private String pathFlockImg="ressources/images/spaceShepherd/nebula-farfadets.png";
	private String pathLeadImg="ressources/images/spaceShepherd/nebula-farLeader.png";
	private String pathPlotImg="ressources/images/spaceShepherd/nebula-plot.png";
	private String pathFenceImg="ressources/images/spaceShepherd/sabre-laser.png";
	private Sound victoSound;


	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
	    
	    // Call super method
        super.init(gc, game);
	    
		plotRadius=(int) (gc.getScreenWidth()*0.04);
		flockRadius=(int) (gc.getScreenWidth()*0.03);
		fenceThickness=(int) (gc.getScreenWidth()*0.03);
		fences=new ArrayList<Line>();
		flock=new Flock(gc.getWidth()/2,gc.getHeight()/2,0.16f, gc.getWidth(), gc.getHeight());
		
		targetCenter=new Vector2f(new Random().nextInt(gc.getWidth()-60)
				,new Random().nextInt(gc.getHeight()-60));
		
		victoryImg=new Image(pathVictoryImg);
		lossImg=new Image(pathlLossImg);
		flockImg=new Image(pathFlockImg);
		leadImg=new Image(pathLeadImg);
		plotImg=new Image(pathPlotImg);
		fenceImg=new Image(pathFenceImg);
		victoSound=new Sound("ressources/sons/cropCircle/odetojoy.ogg");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
	    throws SlickException {
	    
	    // Call super method
        super.update(gc, game, delta);

		Input input = gc.getInput();

		flock.moveRandom(delta, fences);
		
		if (input.isKeyDown(Input.KEY_M)) {

		}

		if (input.isKeyDown(Input.KEY_LEFT) && x-(delta * pointerSpeed) > 0+borderMargin) {
			x -= (delta * pointerSpeed);
		}
		
		if (input.isKeyDown(Input.KEY_RIGHT) && x+(delta * pointerSpeed)< gc.getWidth()-borderMargin) {
			x += (delta * pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_UP) && y -(delta * pointerSpeed)> 0+borderMargin) {
			y -= (delta * pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_DOWN) && y+(delta * pointerSpeed) < gc.getHeight()-borderMargin) {
			y += (delta * pointerSpeed);
		}
		
		if(!input.isKeyDown(Input.KEY_SPACE)){
			spaceReleased=true;
		}

		if (input.isKeyDown(Input.KEY_SPACE) && spaceReleased) {
			
			spaceReleased=false;
			Vector2f plot=new Vector2f(x,y);
			
			if(lastPlot==null){
				lastPlot=new Vector2f(x,y);
			}
			else if (validDistanceFromLastPoint() && !flock.isDividing(new Line(lastPlot,plot))) {
				
				System.out.println("x : "+x+" y : "+y);
				fences.add(new Line(lastPlot,plot));
				lastPlot=null;
				lastPlot=new Vector2f(x,y);
				
				if(flock.isEnded(fences, targetCenter)){
					System.out.println("Fail");
					gc.pause();
					lossImg.draw(0, 0, gc.getWidth(), gc.getHeight());
					if(!victoSound.playing())
						victoSound.play();
				}
			}
			else{
				System.out.println("Divide");
			}
			System.out.println("x : "+x+"  y : "+y);
			
			System.out.println("////////////////////////////////////////////////////////");
		}
		
		
		
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
	    throws SlickException {

	    // Call super method
        super.render(gc, game, g);
        
		g.setAntiAlias(true);
		//land.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.yellow);
		g.fillOval(targetCenter.x-targetRadius, targetCenter.y-targetRadius, targetRadius*2, targetRadius*2);
		g.setColor(Color.white);
		g.setLineWidth(10);
		
		g.setColor(Color.red);
		for(Line l : fences){
			plotImg.draw(l.getX1()-plotRadius/2, l.getY1()-plotRadius/2, plotRadius, plotRadius);
			plotImg.draw(l.getX2()-plotRadius/2, l.getY2()-plotRadius/2, plotRadius, plotRadius);
			
			g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
//			fenceImg.setCenterOfRotation(0, 0);
//			double cos=(l.getX1()-l.getX2())/l.length();
//			double angle= Math.acos(cos) * 180/Math.PI;
//			fenceImg.rotate((float) angle);
//			fenceImg.draw(l.getX1(), l.getY1(),Math.abs(l.getX2()-l.getX1()), Math.abs(l.getY1()-l.getY2()));
//			//remet l'image a plat pour la prochaine fois
//			fenceImg.rotate((float) (360-angle));
			
			g.drawString(""+fences.indexOf(l), l.getX1()+20, l.getY1()+20);
		}

		if(lastPlot!=null){
			plotImg.draw(lastPlot.getX()-plotRadius/2, lastPlot.getY()-plotRadius/2, plotRadius, plotRadius);
		}
		
		leadImg.draw(flock.getPosition().x-(flockRadius/2), flock.getPosition().y-(flockRadius/2), 
				flockRadius, flockRadius);
		
		g.setColor(Color.red);
		for(SteeringEntity st : flock.getFlockers()){
			flockImg.draw(st.getPosition().x-(flockRadius/2), st.getPosition().y-(flockRadius/2), 
					flockRadius, flockRadius);
		}
		
		g.setColor(Color.blue);
		g.drawOval(x, y, 8, 8,80);

		
		if(flock.allInTheHole(targetCenter, targetRadius)){	
			victoryImg.draw(0,0,gc.getWidth(),gc.getHeight());
			if(!victoSound.playing()){
				victoSound.play();
			}
			gc.pause();
		}
		
	}
	
	
	private boolean validDistanceFromLastPoint() {
		return (Math.abs(lastPlot.x - x) > plotRadius || Math.abs(lastPlot.y
				- y) > plotRadius);
	}

    @Override
    public int getID ()
    {
        return NebulaState.SpaceShepherd.id;
    }
}
