package nebula.minigame.spaceShepherd;

import java.util.ArrayList;
import java.util.Random;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;
import nebula.core.helper.Utils;
import nebula.core.state.AbstractMinigameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	private float scoreCoef;
	private float timeCoef;
	private float fenceCoef;
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

	//Time accorded to win the game, in miliseconds
	private int remainingTime;
	private int startingTime;
	private int flockNumber;

	private Image  flockImg;
	private Image  leadImg;
	private Image  plotImg;
	private Image  plotLightImg;
	private Image  cursorImg;
	private Image  targetImg;
	private Sound ambianceSound;
	private Sound wrongMoveSound;
	private Sound flockSound;
	private Sound plotSound;
	private String pathPlotSound="ressources/sons/spaceShepherd/plot.ogg";
	private String pathAmbianceSound="ressources/sons/spaceShepherd/BubblesUnderwater.ogg";
	private String pathWrongMoveSound="ressources/sons/spaceShepherd/Buzzer.ogg";
	private String pathFlockSound="ressources/sons/spaceShepherd/WiggleRiser.ogg";
	private String pathFlockImg="ressources/images/spaceShepherd/nebula-farfadets2.png";
	private String pathLeadImg="ressources/images/spaceShepherd/nebula-farLeader2.png";
	private String pathPlotImg="ressources/images/spaceShepherd/nebula-plot.png";
	private String pathPlotLightImg="ressources/images/spaceShepherd/nebula-plot2.png";
	private String pathCursorImg="ressources/images/spaceShepherd/saucer.png";
	private String pathTargetImg="ressources/images/spaceShepherd/vortex.png";

	private Font font;



	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {

	    // Call super method
        super.init(gc, game);

        flockSound=new Sound(pathFlockSound);
        wrongMoveSound=new Sound(pathWrongMoveSound);
        ambianceSound=new Sound(pathAmbianceSound);
        plotSound=new Sound(pathPlotSound);
        
        font = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);

	    lastPlot=null;
	    spaceReleased=true;
		plotRadius=(int) (gc.getScreenWidth()*0.04);
		flockRadius=(int) (gc.getScreenWidth()*0.03);
		cursorRadius=(int) (gc.getScreenWidth()*0.07);
		targetRadius=(int) (gc.getScreenWidth()*0.10);

		fences=new ArrayList<Line>();
		Random r =new Random();

		flockNumber=0;
		float speed=0;
		float attractionCoef=0;
		if(Difficulty.Easy.equals(difficulty)){
			flockNumber=8;
			remainingTime=120*1000;
			speed=0.16f;
			attractionCoef=0.003f;
			scoreCoef = 275.0f;
			timeCoef=8.0f;
			fenceCoef=8.0f;
		}
		else if(Difficulty.Hard.equals(difficulty)){
			flockNumber=32;
			remainingTime=60*1000;
			speed=0.3f;
			attractionCoef=0.0015f;
			scoreCoef = 130.0f;
			timeCoef=12.0f;
			fenceCoef=12.0f;
		}
		else if(Difficulty.Insane.equals(difficulty)){
			flockNumber=64;
			remainingTime=45*1000;
			speed=0.4f;
			attractionCoef=0.001f;
			scoreCoef = 90.0f;
			timeCoef=15.0f;
			fenceCoef=15.0f;
		}
		else{//MEDIUM
			flockNumber=16;
			remainingTime=100*1000;
			speed=0.2f;
			attractionCoef=0.002f;
			scoreCoef = 200.0f;
			timeCoef=9.0f;
			fenceCoef=9.0f;
		}
		
		startingTime=remainingTime;

		int valx=r.nextInt(gc.getWidth());
		int valy=r.nextInt(gc.getHeight());

		flock=new Flock(valx,valy,speed, gc.getWidth(), gc.getHeight(),flockNumber, attractionCoef);

		int vx=targetRadius+ new Random().nextInt(gc.getWidth()-(targetRadius*2));
		int vy=targetRadius+ new Random().nextInt(gc.getHeight()-(targetRadius*2));

		while(Math.abs(valx-vx)<targetRadius || Math.abs(valy-vy)<targetRadius){
			vx=targetRadius+ new Random().nextInt(gc.getWidth()-(targetRadius*2));
			vy=targetRadius+ new Random().nextInt(gc.getHeight()-(targetRadius*2));
		}

		targetCenter=new Vector2f(vx, vy);



		flockImg=new Image(pathFlockImg);
		leadImg=new Image(pathLeadImg);
		plotImg=new Image(pathPlotImg);
		cursorImg=new Image(pathCursorImg);
		targetImg=new Image(pathTargetImg);
		plotLightImg=new Image(pathPlotLightImg);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
	    throws SlickException {

	    // Call super method
        super.update(gc, game, delta);
        remainingTime=remainingTime-delta;

        if(remainingTime<=0){
        	stopAllSound();
        	this.gameDefeat();
        }

        if(!wrongMoveSound.playing() && !ambianceSound.playing() && !flockSound.playing() && !plotSound.playing()){
        	ambianceSound.play();
        }
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

			stopAllSound();
			spaceReleased=false;
			Vector2f plot=new Vector2f(x,y);
			
			if(lastPlot==null){
				lastPlot=new Vector2f(x,y);
				plotSound.play();
			}
			else if (validDistanceFromLastPoint() && !flock.isDividing(new Line(lastPlot,plot))) {

				System.out.println("x : "+x+" y : "+y);
				fences.add(new Line(lastPlot,plot));
				lastPlot=null;
				lastPlot=new Vector2f(x,y);
				plotSound.play();
				
				if(flock.isEnded(fences, targetCenter)){
					this.gameDefeat();
				}
			}
			else{
				wrongMoveSound.play();
				System.out.println("Divide");
			}
			System.out.println("x : "+x+"  y : "+y);

			System.out.println("////////////////////////////////////////////////////////");
		}
		
		int nb=flock.getFlockers().size();
		if(flock.allInTheHole(targetCenter, targetRadius/2)){
			score=computeScore();
			stopAllSound();
			this.gameVictory();
		}
		else if(nb!=flock.getFlockers().size()){
			stopAllSound();
			flockSound.play();
		}

	}

	private void stopAllSound(){
		wrongMoveSound.stop();
		flockSound.stop();
		ambianceSound.stop();
		plotSound.stop();
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
		}

		if(lastPlot!=null){
			plotLightImg.draw(lastPlot.getX()-plotRadius/2, lastPlot.getY()-plotRadius/2, plotRadius, plotRadius);
		}
		
		cursorImg.draw(x-cursorRadius/2, y-cursorRadius/2, cursorRadius, cursorRadius);
		String sec=Utils.secondsToString(remainingTime/1000);
		font.drawString(gc.getWidth()/2 -font.getWidth(sec)/2, 40, sec);

		score=computeScore();
		renderScore(gc, ScorePosition.TopRight);
		
		for(SteeringEntity st : flock.getFlockers()){
			flockImg.draw(st.getPosition().x-(flockRadius/2), st.getPosition().y-(flockRadius/2),
					flockRadius, flockRadius);
		}

		leadImg.draw((int)(flock.getPosition().x-(flockRadius*1.5/2)),(int)(flock.getPosition().y-(flockRadius*1.5/2)),
				(int)(flockRadius*1.5),(int) (flockRadius*1.5));

	}

	private int computeScore(){
		return (int) ((flockNumber-flock.getFlockers().size())*scoreCoef+(remainingTime-startingTime)*timeCoef/1000 - fences.size()*fenceCoef);
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
