package nebula.minigame.spaceShepherd;

import java.awt.geom.GeneralPath;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
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

	
	/**
	 * Some bad ass kamouloxick method who <BR> is kind of magically
	 * able to find out <BR> whther or not this game should end right now
	 * @param fences
	 * @param target
	 * @return
	 */
	public boolean isEnded(final ArrayList<Line> fences, Vector2f target){
		int margin=2;
		Line left=new Line(margin,margin,margin,super.getYfield()-margin);
		Line top=new Line(margin,margin,super.getXfield()-margin,margin);
		Line bottom=new Line(margin,super.getYfield()-margin,super.getXfield()-margin
				,super.getYfield()-margin);
		Line right=new Line(super.getXfield()-margin,margin,super.getXfield()-margin
				,super.getYfield()-margin);
		
		ArrayList<Line> bords=new ArrayList<Line>();
		bords.add(left);
		bords.add(top);
		bords.add(bottom);
		bords.add(right);
		
		ArrayList<Line> remaining=new  ArrayList<Line>(fences);
		
		ArrayList<Line> shape=new ArrayList<Line>();
		GeneralPath path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		
		path.moveTo(remaining.get(0).getX1(), remaining.get(0).getY1());
		
		int cpt=0;
		//Parcours des lignes présentes
		while(cpt<remaining.size() && remaining.size()>0){
			
			Line l=remaining.get(cpt);
			
			shape.add(l);
			Vector2f inter=intersectItself(shape);
			Vector2f interBord[]=intersectBord(shape, bords);

			//Si on peut faire une forme (avec les lignes ou les bords)
			if(shape.size()>1 && (inter!=null  || interBord!=null)){
				
				path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);	
				
				//Création de la forme
				if(inter!=null){
					System.out.println(cpt);
					System.out.println("intersection en :"+inter.x+", "+inter.y);
					
					//Recupére la ligne coupé par la ligne courante
					Line lili=new Line(inter.x,inter.y, l.getX1(),l.getY1());
					lili=intersectFences(shape, lili);
					
					//Retire toutes les lignes précédant la ligne coupé par la ligne courante
					for(int in=0;in<shape.indexOf(lili)-1;in++){
						shape.remove(in);
					}
					
					path.moveTo(inter.x,inter.y);
					for(int h =0;h<shape.size()-1;h++){
						Line li=shape.get(h);
						path.lineTo(li.getX2(), li.getY2());
					}
					path.lineTo(inter.x,inter.y);
				}
				else if(interBord!=null){
					System.out.println("border intersection");
					path.moveTo(interBord[0].x,interBord[0].y);
					for(int h =0;h<shape.size()-1;h++){
						Line li=shape.get(h);
						path.lineTo(li.getX2(), li.getY2());
					}
					path.lineTo(interBord[1].x,interBord[1].y);
					path.lineTo(interBord[0].x,interBord[0].y);
				}
				
				//On retire les ligne de la forme crée de la list pour le pas les traiter 2 fois.
				substractList(remaining, shape);
				
				//remise a zéro de la forme
				shape.clear();
				
				// on remet la premiere ligne dans la forme
				shape.add(l);
				remaining.add(0,l);
				
				System.out.println("position contenue : "+
						path.contains(super.getPosition().x, super.getPosition().y));
				System.out.println("cible contenue : " +path.contains(target.x, target.y) );
				
				//Si le leader du flock est dans une forme et que la cible n'y est pas
				if(path.contains(target.x, target.y) != path.contains(super.getPosition().x, super.getPosition().y)){
					return true;
				}
				cpt=1;
			}
			else{
				cpt++;
			}
			
			
		}//Fin while
		
		return false;
	}
	
	/**
	 * remove the elements of the sub list from the first list
	 * @param list
	 * @param sub
	 */
	private void substractList(ArrayList<Line>list, ArrayList<Line> sub){
		for(Line l : sub){
			list.remove(l);
		}
	}
	
	/**
	 * Returnthe first intersection of this shape with itself
	 * @param shape
	 * @return
	 */
	private Vector2f intersectItself(ArrayList<Line> shape){
		for(Line l1 : shape){
			for(Line l2 : shape){
				if(l1.intersect(l2, true)!=null){
					if(Math.abs(shape.indexOf(l1)-shape.indexOf(l2))>1){
						return l1.intersect(l2, true);
					}		
				}
			}
		}
		return null;
	}
	
	/**
	 * Return the next Two intersection of the shpe with some borders or null
	 * @param shape
	 * @param Bords
	 * @return
	 */
	private Vector2f[]  intersectBord(ArrayList<Line> shape, ArrayList<Line>Bords){
		
		Vector2f tab[]=new Vector2f[2];
		int cpt=0;
		for(Line l : shape){
			for(Line b: Bords){
				tab[cpt]=l.intersect(b, true);
				if(tab[cpt]!=null){
					cpt++;
				}
				if(cpt==2){
					return tab;
				}
			}
		}
		return null;
	}
	
	/**
	 * Return the first line of the list which is intersecting the line
	 * @param fences
	 * @param line
	 * @return
	 */
	private Line intersectFences(ArrayList<Line> fences, Line line){
		Vector2f vect=null;
		for(Line l : fences){
			vect=l.intersect(line,true);
			if(vect!=null){
				return l;
			}
		}
		return null;
	}
}
