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
	private float attractionCoeff=0.025f;
	
	private ArrayList<Line> remaining=null;;
	
	private int margin=40;
	
	public Flock(int x, int y, float maxSpeed, int fieldx, int fieldy) {
		super(x, y, maxSpeed, fieldx, fieldy);
		
		flockers=new ArrayList<SteeringEntity>();
		for(int i=0;i<flockersNumber;i++){
			flockers.add(new SteeringEntity(x, y, maxSpeed*15, fieldx, fieldy));
		}	
	}
	
	public void moveRandom(int delta, ArrayList<Line> fences ){

		super.setPosition(moveRandomly(delta, fences));
		super.resetMaxSpeedAndMaxRotation();
		updateFlock(delta, fences);
	}
	
	private void updateFlock(float delta, ArrayList<Line> fences){

		Vector2f newPos;
		
		for(SteeringEntity fl : flockers){

			newPos=getFlockingMouvment(fl, delta, fences);
			fl.resetMaxSpeedAndMaxRotation();
			int cpt =0;
			while(!isValidTrajectory(newPos, fences) && cpt <300){
				newPos=getFlockingMouvment(fl, delta, fences);
				//System.out.println("flock failure");
				//fl.setMaxSpeed((float) (fl.getMaxSpeed()*0.9));
				//fl.setMaxRotation((float) (fl.getMaxRotation()*1.5));
				//newPos=fl.moveRandomly(delta, fences);
				cpt ++;
			}
			
			if(cpt==300){
				System.out.println("30000000000000000000000000");
				newPos=super.getPosition();
			}
			fl.setPosition(newPos);
		}
	}
	
	public boolean allInTheHole(Vector2f target, int radius){
		
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
		vect.scale(delta*attractionCoeff);
		
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
	 * able to find out <BR> whether or not this game should end right now
	 * @param fences
	 * @param target
	 * @return
	 */
	public boolean isEnded(final ArrayList<Line> fences, Vector2f target){
		
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
		
		/*ArrayList<Line> remaining=new  ArrayList<Line>();
		for(Line le:fences){
			remaining.add(le);
		}*/
		
		remaining=fences;
		
//		if(remaining==null){
//			remaining=new ArrayList<Line>();
//			for(Line le:fences){
//				remaining.add(le);
//			}
//		}
//		else{
//			remaining.add(fences.get(fences.size()-1));
//		}
		
		ArrayList<Line> shape=new ArrayList<Line>();
		GeneralPath path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		
		path.moveTo(remaining.get(0).getX1(), remaining.get(0).getY1());
		
		int cpt=0;
		//Parcours des lignes présentes
		while(cpt<remaining.size() && remaining.size()>0){
			
			Line l=remaining.get(cpt);
			
			shape=inclusiveSubList(remaining, cpt);
			
			Vector2f inter=intersectItself(shape);
			Vector2f interBord[]=intersectBord(shape, bords);

			//Si on peut faire une forme (avec les lignes ou les bords)
			if(inter!=null  || interBord!=null){
				
				path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);	
				
				//Création de la forme
				if(inter!=null){
					//Recupére les ligne coupé par la ligne courante
					ArrayList<Line> interList=intersectLine(shape, l);
					//Parcours des lignes coupants la ligne courante
					for(int e=0;e<interList.size();e++){
						
						path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);	
						Line lili=interList.get(e);
						inter=lili.intersect(l, true);
						//On remet la liste de travail a son état au début de la boucle
						shape=inclusiveSubList(remaining, cpt);

						//Retire toutes les lignes précédant la ligne coupé par la ligne courante
						while(!shape.get(0).equals(lili)){
							shape.remove(0);
						}
						
						//On crée la forme
						path.moveTo(inter.x,inter.y);
						int h =0;
						while(h<shape.size()-1 && 
								!(e+1<interList.size() && shape.get(h).equals(interList.get(e+1)))){
							
							Line li=shape.get(h);
							path.lineTo(li.getX2(), li.getY2());
							h++;
						}
						if(h!=shape.size()-1){
							Vector2f point=interList.get(e+1).intersect(l, true);
							path.lineTo(point.x,point.y);
						}
						path.lineTo(inter.x,inter.y);
						
						//Si le leader du flock est dans une forme et que la cible n'y est pas
						if(path.contains(target.x, target.y) != path.contains(super.getPosition().x, super.getPosition().y)){
							System.out.println("e : "+e+" l :"+cpt);
							return true;
						}	

					}

				}
				else if(interBord!=null){
					
					System.out.println("border intersection");
					
					//Point in the middle of the line formed by the two bord intersection
					Vector2f middle=new Vector2f((interBord[0].x+interBord[1].x)/2,
							(interBord[0].y+interBord[1].y)/2);
					
					System.out.println(" middle : x "+middle.x+" y : "+middle.y);
					//Closest corner to that point
					Vector2f closesstCorner=getClosestCorner(middle);
					
					System.out.println("closest : x : "+closesstCorner.x+" y : "+closesstCorner.y);
					//Recupére la premiere ligne coupant un bord
					Line border=new Line(closesstCorner,interBord[0]);
					//border=intersectLine(shape, l);
					int indexFirstLine = shape.indexOf(border)+1;
					
					System.out.println("indice : "+remaining.indexOf(shape.get(indexFirstLine)));
					//Ajout du coin de départ et de la premiere ligne (du coin a la premiere intersection avec le bord)
					path.moveTo(closesstCorner.x,closesstCorner.y);
					path.lineTo(shape.get(indexFirstLine).getX1(), shape.get(indexFirstLine).getY1());
					
					for(int h =indexFirstLine;h<shape.size();h++){
						Line li=shape.get(h);
						path.lineTo(li.getX2(), li.getY2());
					}
					path.lineTo(closesstCorner.x,closesstCorner.y);
					
					//On retire les ligne de la forme crée de la list pour le pas les traiter 2 fois.
					//substractList(remaining, shape);
					
					cpt++;
				}
				
				//Si le leader du flock est dans une forme et que la cible n'y est pas
				if(path.contains(target.x, target.y) != path.contains(super.getPosition().x, super.getPosition().y)){
					return true;
				}				
			}
			cpt++;
			
			
			
		}//Fin while
		
		return false;
	}
	
	/**
	 * return the corner which is the closest to this point
	 * @param point
	 * @return
	 */
	private Vector2f getClosestCorner(Vector2f point){
		Vector2f tab[]=new Vector2f[4];
		tab[0]=new Vector2f(margin,margin);
		tab[1]=new Vector2f(margin,super.getYfield()-margin);
		tab[2]=new Vector2f(super.getXfield()-margin,margin);
		tab[3]=new Vector2f(super.getXfield()-margin,super.getYfield()-margin);
		
		Vector2f closest=tab[0];
		for(Vector2f ve : tab){
			if(point.distance(ve)<point.distance(closest)){
				closest=ve;
			}
		}
		return closest;
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
				if( !l1.equals(l2) && l1.intersect(l2, true)!=null){
					if(Math.abs(shape.indexOf(l1)-shape.indexOf(l2))>1){
						return l1.intersect(l2, true);
					}		
				}
			}
		}
		return null;
	}
	
	/**
	 * Return the next Two intersection of the shape with some borders or null
	 * @param shape
	 * @param Bords
	 * @return
	 */
	private Vector2f[]  intersectBord(ArrayList<Line> shape, ArrayList<Line>Bords){
		
		Vector2f tab[]=new Vector2f[2];
		int cpt=0;
		for(Line b: Bords){
			for(Line l : shape){
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
	 * Return the lines of the list that are intersecting the parameter line
	 * @param fences
	 * @param line
	 * @return
	 */
	private ArrayList<Line> intersectLine(ArrayList<Line> fences, Line line){
		Vector2f vect=null;
		ArrayList<Line> res=new ArrayList<Line>();
		for(Line l : fences){
			vect=l.intersect(line,true);
			if(vect!=null &&!l.equals(line)){
				res.add(l);
			}
		}
		return res;
	}
	
	/**
	 * return the sub list of the parameter list, from 0 to the given index
	 * @param list
	 * @param index
	 * @return
	 */
	private ArrayList<Line> inclusiveSubList(ArrayList<Line> list, int index){
		ArrayList<Line> res=new ArrayList<Line>();
		for(int i=0;i<=index;i++){
			res.add(list.get(i));
		}
		return res;
	}
}
