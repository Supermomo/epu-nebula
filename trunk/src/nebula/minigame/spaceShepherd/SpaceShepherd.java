package nebula.minigame.spaceShepherd;

import java.util.ArrayList;
import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class SpaceShepherd extends AbstractMinigameState {

	private float x = 400;
	private float y = 300;
	/** Radius of one plot */
	private int plotRadius ;
	private int flockRadius;
	private int cursorRadius;
	/** Coefficient for the malus you get when you don't fill the track */
	private float malusScoreCoef = 1.0f;
	/**the list of all the fences that has been created */
	private ArrayList<Line> fences;

	/** the last drawnPoint */
	private Vector2f lastPlot;
	/** Variator for the deplacement speed */
	private float pointerSpeed = 0.4f;
	
	private Flock flock;
	private boolean spaceReleased;

	private Vector2f targetCenter;
	private int targetRadius;
	
	private int borderMargin=50;
	
	private Image victoryImg;
	private Image  lossImg;
	private Image  flockImg;
	private Image  leadImg;
	private Image  plotImg;
	private Image  cursorImg;
	private Image  targetImg;
	private Image  backgroundImg;
	private String pathVictoryImg="ressources/images/spaceInvaders/victoire.png";
	private String pathlLossImg="ressources/images/spaceInvaders/defaite.png";
	private String pathFlockImg="ressources/images/spaceShepherd/nebula-farfadets.png";
	private String pathLeadImg="ressources/images/spaceShepherd/nebula-farLeader.png";
	private String pathPlotImg="ressources/images/spaceShepherd/nebula-plot.png";
	private String pathCursorImg="ressources/images/spaceShepherd/saucer.png";
	private String pathTargetImg="ressources/images/spaceShepherd/vortex.png";
	private String pathBackgroundImg="ressources/images/spaceShepherd/background.png";
	private Sound victoSound;


	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
	    
	    // Call super method
        super.init(gc, game);
	    lastPlot=null;
	    spaceReleased=true;
		plotRadius=(int) (gc.getScreenWidth()*0.04);
		flockRadius=(int) (gc.getScreenWidth()*0.03);
		cursorRadius=(int) (gc.getScreenWidth()*0.07);
		targetRadius=(int) (gc.getScreenWidth()*0.12);
		fences=new ArrayList<Line>();
		Random r =new Random();
		int valx=r.nextInt(gc.getWidth());
		int valy=r.nextInt(gc.getHeight());
		flock=new Flock(valx,valy,0.16f, gc.getWidth(), gc.getHeight());
		
		targetCenter=new Vector2f(targetRadius+ new Random().nextInt(gc.getWidth()-(targetRadius*2))
				,targetRadius+ new Random().nextInt(gc.getHeight()-(targetRadius*2)));
		
		victoryImg=new Image(pathVictoryImg);
		lossImg=new Image(pathlLossImg);
		flockImg=new Image(pathFlockImg);
		leadImg=new Image(pathLeadImg);
		plotImg=new Image(pathPlotImg);
		cursorImg=new Image(pathCursorImg);
		targetImg=new Image(pathTargetImg);
		backgroundImg=new Image(pathBackgroundImg);
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
					this.gameDefeat();
				}
			}
			else{
				System.out.println("Divide");
			}
			System.out.println("x : "+x+"  y : "+y);
			
			System.out.println("////////////////////////////////////////////////////////");
		}
		
		if(flock.allInTheHole(targetCenter, targetRadius/2)){	
			this.gameVictory();
		}
		
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
	    throws SlickException {

	    // Call super method
        super.render(gc, game, g);
        
		g.setAntiAlias(true);

		targetImg.drawFlash(targetCenter.x-targetRadius/2 -2, targetCenter.y-targetRadius/2 -2, 
				targetRadius+4, targetRadius+4);
		targetImg.draw(targetCenter.x-targetRadius/2, targetCenter.y-targetRadius/2, 
				targetRadius, targetRadius);

		g.setColor(Color.red);
		g.setLineWidth(10);

		for(Line l : fences){		
			g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
			g.drawString(""+fences.indexOf(l), l.getX1()+20, l.getY1()+20);
		}
		
		for(Line l : fences){
			plotImg.draw(l.getX1()-plotRadius/2, l.getY1()-plotRadius/2, plotRadius, plotRadius);
			//plotImg.draw(l.getX2()-plotRadius/2, l.getY2()-plotRadius, plotRadius, plotRadius);
		}

		if(lastPlot!=null){
			plotImg.draw(lastPlot.getX()-plotRadius/2, lastPlot.getY()-plotRadius/2, plotRadius, plotRadius);
		}
		
		leadImg.draw(flock.getPosition().x-(flockRadius/2), flock.getPosition().y-(flockRadius/2), 
				flockRadius, flockRadius);
		
		for(SteeringEntity st : flock.getFlockers()){
			flockImg.draw(st.getPosition().x-(flockRadius/2), st.getPosition().y-(flockRadius/2), 
					flockRadius, flockRadius);
		}
		
		cursorImg.draw(x-cursorRadius/2, y-cursorRadius/2, cursorRadius, cursorRadius);

		
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
