package nebula.minigame.spaceShepherd;

import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Flock extends SteeringEntity{

	private int flockersNumber=12;
	private ArrayList<SteeringEntity> flockers;

	/**the coeff for the power of attraction (ie : the velocity of the seek(positionCenter))*/
	private float attractionCoeff=0.015f;
	
	
	public Flock(int x, int y, float maxSpeed, int fieldx, int fieldy) {
		super(x, y, maxSpeed, fieldx, fieldy);
		
		flockers=new ArrayList<SteeringEntity>();
		for(int i=0;i<flockersNumber;i++){
			flockers.add(new SteeringEntity(x, y, maxSpeed*15, fieldx, fieldy));
		}	
	}
	
	public void moveRandom(int delta, ArrayList<Line> fences ){
		super.setPosition(moveRandomly(delta, fences));
		updateFlock(delta, fences);
	}
	
	private void updateFlock(float delta, ArrayList<Line> fences){

		Vector2f newPos;
		
		for(SteeringEntity fl : flockers){

			newPos=getFlockingMouvment(fl, delta, fences);
			
			while(!isValidTrajectory(newPos, fences)){
				newPos=getFlockingMouvment(fl, delta, fences);
			}
			fl.setPosition(newPos);
		}
	}
	
	public boolean allIntheHole(Vector2f target, int radius){
		
		for(SteeringEntity st: flockers){
			if(st.getPosition().distance(target)>=radius){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Get the mouvment for an entity of the flock based in the folloowing of the leader and on a 
	 * pseud random behavior
	 * @param fl
	 * @param delta
	 * @return the position that would be gotten after making that move
	 */
	private Vector2f getFlockingMouvment(SteeringEntity fl, float delta,ArrayList<Line> fences){
		
		Vector2f vect;//seek vector
		Vector2f vect2;//random mouvment vector
		Vector2f newPos;
		
		vect=fl.seek(super.getPosition());
		vect.scale(delta*super.getPosition().distance(fl.getPosition())*attractionCoeff);
		
		vect2=fl.moveRandomly(delta,fences);
		vect2=vect2.sub(fl.getPosition());
		
		vect.add(vect2);
		vect.normalise();
		vect.scale(fl.getMaxSpeed());
		newPos=fl.getPosition().add(vect);
		return newPos;
	}
	
	public ArrayList<SteeringEntity> getFlockers() {
		return flockers;
	}
	
	public boolean isDividing(Line line){
		Line l;
		for(SteeringEntity fl: flockers){
			l=new Line(this.getPosition(),fl.getPosition());
			if(l.intersect(line, true)!=null){
				return true;
			}
		}
		return false;
	}

	public boolean isEnded(final ArrayList<Line> fences){
		
		Line left=new Line(0,0,0,super.getYfield());
		Line top=new Line(0,0,super.getXfield(),0);
		Line bottom=new Line(0,super.getYfield(),super.getXfield(),super.getYfield());
		Line right=new Line(super.getXfield(),0,super.getXfield(),super.getYfield());
		
		Shape sh=new Shape() {
			
			@Override
			public Shape transform(Transform transform) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected void createPoints() {
				int i=0;
				for(Line l : fences){
					this.points[i]=l.getX1();
					this.points[i+1]=l.getY1();
					this.points[i]=l.getX2();
					this.points[i+1]=l.getY2();
					i+=4;
				}
				
			}
		};;;
		
		if(sh.contains(this.getPosition().x, this.getPosition().y)){
			return true;
		}
		
		return false;
	}
}
