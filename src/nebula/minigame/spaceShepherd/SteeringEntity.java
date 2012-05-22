package nebula.minigame.spaceShepherd;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class SteeringEntity {

	/**The position of the steeringEntity*/
	private Vector2f position;
	/**the orientation of the steering in degree*/
	private float orientation;
	/**The velocity (i.e the direction and speed) of the entity*/
	private Vector2f velocity;
	/*The rotation to be applyt*/
	private float rotation;
	/**The maximum spped*/
	private float maxSpeed;
	/**The maximum roation in degree*/
	private float maxRotation=5;
	
	private float InitialMaxRotation=5;
	
	private float InitialMaxSpeed=5;

	private int Xfield;
	private int Yfield;


	
	public SteeringEntity(int x, int y, float maxSpeed, int fieldx, int fieldy){
		position = new Vector2f(x,y);
		this.maxSpeed=maxSpeed;
		rotation=0f;
		orientation=90;
		velocity=new Vector2f(0,0);
		Xfield=fieldx;
		Yfield=fieldy;
		InitialMaxSpeed=this.maxSpeed;
	}
	
	protected Vector2f moveRandomlyInternal(float delta){

		Random r=new Random();
		float val=(float) r.nextGaussian() *180;
		rotation=val;
		rotation=rotation*delta;
		if(Math.abs(rotation)>maxRotation){
			if(rotation>0){
				rotation=maxRotation;
			}
			else{
				rotation=-1*maxRotation;
			}
		}
		orientation+=rotation;
		Vector2f orientationVector=new Vector2f(orientation);
		orientationVector.normalise();
		
		velocity.set(orientationVector);
		velocity.normalise();
		velocity.scale(maxSpeed);
		
		Vector2f newPos=new Vector2f(position);
		velocity.scale(delta);
		newPos.add(velocity);
		
		return newPos;
	}
	
	public void resetMaxSpeedAndMaxRotation(){
		maxRotation=InitialMaxRotation;
		maxSpeed=InitialMaxSpeed;
	}
	
	/**
	 * Get the position after a random move
	 * @param delta
	 * @param fences
	 * @return the position after moving
	 */
	protected Vector2f moveRandomly(float delta, ArrayList<Line> fences ){

		Vector2f newPos=null;
		newPos=moveRandomlyInternal(delta);		
		resetMaxSpeedAndMaxRotation();
		while(!isValidTrajectory(newPos, fences)){
			//new position from a ramdom deplacement
			newPos=moveRandomlyInternal(delta);
			System.out.println("random move pb");
			maxRotation*=3;
			maxSpeed*=1.1f;
		}//end while
		
		return newPos;
	}
	
	/**
	 * Apply the given velocity (scaled at max speed) to the steering entity, moving it to a new position
	 * @param velocity
	 */
	public void apply(Vector2f velocity){
		velocity.normalise();
		velocity.scale(maxSpeed);
		position.add(velocity);
	}
	
	public boolean isValidTrajectory(Vector2f newPos, ArrayList<Line> fences){

		Line line;
		
		//check for out of area issues
		if(newPos.x<10 || newPos.x+10>Xfield || newPos.y<10 || newPos.y+10>Yfield){
			return false;
		}
		else{
			//Line from old to new position
			line=new Line(newPos, position);
			//check if we cross the fences
			for(Line l : fences){
				if(line.intersect(l, true) != null){
					return false;
				}
			}
			//end for
		}//end else
		
		return true;
	}
	
	/**
	 * Seek for the target
	 * @param target
	 * @return the velocity needed to reach the target at max speed (or not)
	 */
	public Vector2f seek(Vector2f target){
		Vector2f res=new Vector2f(target.x-position.x,target.y-position.y);
		/*if(res.length()>maxSpeed){
			res.normalise();
			res.scale(maxSpeed);
		}*/
		return res;
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public float getOrientation() {
		return orientation;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public float getRotation() {
		return rotation;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public void setRotation(float roation) {
		this.rotation = roation;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	

	public int getXfield() {
		return Xfield;
	}

	public int getYfield() {
		return Yfield;
	}

	public float getMaxRotation() {
		return maxRotation;
	}

	public void setMaxRotation(float maxRotation) {
		this.maxRotation = maxRotation;
	}

}
