/**
 * Nebula - Copyright (C) 2012
 * Gwenn Aubert, Thomas Di'Meco, Matthieu Maugard, Gaspard Perrot
 *
 * This file is part of project 'Nebula'
 *
 * 'Nebula' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'Nebula' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Nebula'. If not, see <http://www.gnu.org/licenses/>.
 */
package nebula.minigame.spaceShepherd;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Flock extends SteeringEntity {

	private int InitialFlockersNumber;
	private ArrayList<SteeringEntity> flockers;

	/**
	 * the coeff for the power of attraction (ie : the velocity of the
	 * seek(positionCenter))
	 */
	private float attractionCoeff;

	private ArrayList<Line> remaining = null;;

	public Flock(int x, int y, float maxSpeed, int fieldx, int fieldy,
			int flNb, float attCoef) {
		super(x, y, maxSpeed, fieldx, fieldy);
		InitialFlockersNumber = flNb;
		attractionCoeff = attCoef;
		flockers = new ArrayList<SteeringEntity>();
		for (int i = 0; i < InitialFlockersNumber; i++) {
			flockers.add(new SteeringEntity(x, y, maxSpeed, fieldx, fieldy));
		}
	}

	public void moveRandom(int delta, ArrayList<Line> fences) {

		super.setPosition(moveRandomly(delta, fences));
		super.resetMaxSpeedAndMaxRotation();
		updateFlock(delta, fences);
	}

	private void updateFlock(float delta, ArrayList<Line> fences) {

		Vector2f newPos;

		for (SteeringEntity fl : flockers) {

			newPos = getFlockingMouvment(fl, delta, fences);
			fl.resetMaxSpeedAndMaxRotation();
			int cpt = 0;
			while (!isValidTrajectory(newPos, fences) && cpt < 300) {
				newPos = getFlockingMouvment(fl, delta, fences);
				cpt++;
			}

			if (cpt == 300) {
				newPos = super.getPosition();
			}
			fl.setPosition(newPos);
		}
	}

	/**
	 * return true if all the flocker are in the vortex
	 *
	 * @param target
	 * @param radius
	 * @return
	 */
	public boolean allInTheHole(Vector2f target, int radius) {
		boolean res = true;
		for (int i = 0; i < flockers.size(); i++) {
			if (flockers.get(i).getPosition().distance(target) >= radius) {
				res = false;
			} else {
				flockers.remove(i);
				i--;
			}
		}
		return res;
	}

	/**
	 * Get the mouvment for an entity of the flock based in the folloowing of
	 * the leader and on a pseud random behavior
	 *
	 * @param fl
	 * @param delta
	 * @return the position that would be gotten after making that move
	 */
	private Vector2f getFlockingMouvment(SteeringEntity fl, float delta,
			ArrayList<Line> fences) {

		Vector2f vect;// seek vector
		Vector2f vect2;// random mouvment vector
		Vector2f newPos;

		vect = fl.seek(super.getPosition());
		vect.scale(delta * attractionCoeff);

		vect2 = fl.moveRandomly(delta, fences);
		vect2 = vect2.sub(fl.getPosition());

		vect.add(vect2);
		vect.normalise();
		vect.scale(fl.getMaxSpeed());
		vect.scale(delta);
		newPos = fl.getPosition().add(vect);
		return newPos;
	}

	public ArrayList<SteeringEntity> getFlockers() {
		return flockers;
	}

	public boolean isDividing(Line line) {
		Line l;
		for (SteeringEntity fl : flockers) {
			l = new Line(this.getPosition(), fl.getPosition());
			if (l.intersect(line, true) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Some bad ass kamouloxick method who <BR>
	 * is kind of magically able to find out <BR>
	 * whether or not this game should end right now
	 *
	 * @param fences
	 * @param target
	 * @return
	 */
	public boolean isEnded(final ArrayList<Line> fences, Vector2f target) {

		// remaining = fences;

		if (remaining == null) {
			remaining = new ArrayList<Line>();
			for (Line le : fences) {
				remaining.add(le);
			}
		} else {
			remaining.add(fences.get(fences.size() - 1));
		}

		ArrayList<Line> shape = new ArrayList<Line>();
		GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

		path.moveTo(remaining.get(0).getX1(), remaining.get(0).getY1());

		int cpt = 0;
		// Parcours des lignes présentes
		while (cpt < remaining.size() && remaining.size() > 0) {

			Line l = remaining.get(cpt);

			shape = inclusiveSubList(remaining, cpt);

			Vector2f inter = intersectItself(shape);

			// Création de la forme
			if (inter != null) {

				path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

				// Recupére les ligne coupé par la ligne courante
				ArrayList<Line> interList = intersectLine(shape, l);
				// Parcours des lignes coupants la ligne courante
				for (int e = 0; e < interList.size(); e++) {

					path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
					Line lili = interList.get(e);
					inter = lili.intersect(l, true);
					// On remet la liste de travail a son état au début de
					// la boucle
					shape = inclusiveSubList(remaining, cpt);

					// Retire toutes les lignes précédant la ligne coupé par
					// la ligne courante
					while (!shape.get(0).equals(lili)) {
						shape.remove(0);
					}

					// On crée la forme
					path.moveTo(inter.x, inter.y);
					int h = 0;
					while (h < shape.size() - 1
							&& !(e + 1 < interList.size() && shape.get(h)
									.equals(interList.get(e + 1)))) {

						Line li = shape.get(h);
						path.lineTo(li.getX2(), li.getY2());
						h++;
					}

					if (h != shape.size() - 1) {
						Vector2f point = interList.get(e + 1)
								.intersect(l, true);
						path.lineTo(point.x, point.y);
					}
					path.lineTo(inter.x, inter.y);

					// Si le leader du flock est dans une forme et que la
					// cible n'y est pas
					if (path.contains(target.x, target.y) != path.contains(
							super.getPosition().x, super.getPosition().y)) {
						return true;
					}

				}

				// Si le leader du flock est dans une forme et que la cible n'y
				// est pas
				if (path.contains(target.x, target.y) != path.contains(super
						.getPosition().x, super.getPosition().y)) {
					return true;
				}
			}
			cpt++;

		}// Fin while

		return false;
	}

	/**
	 * Returnthe first intersection of this shape with itself
	 *
	 * @param shape
	 * @return
	 */
	private Vector2f intersectItself(ArrayList<Line> shape) {
		for (Line l1 : shape) {
			for (Line l2 : shape) {
				if (!l1.equals(l2) && l1.intersect(l2, true) != null) {
					if (Math.abs(shape.indexOf(l1) - shape.indexOf(l2)) > 1) {
						return l1.intersect(l2, true);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Return the lines of the list that are intersecting the parameter line
	 *
	 * @param fences
	 * @param line
	 * @return
	 */
	private ArrayList<Line> intersectLine(ArrayList<Line> fences, Line line) {
		Vector2f vect = null;
		ArrayList<Line> res = new ArrayList<Line>();
		for (Line l : fences) {
			vect = l.intersect(line, true);
			if (vect != null && !l.equals(line)) {
				res.add(l);
			}
		}
		return res;
	}

	/**
	 * return the sub list of the parameter list, from 0 to the given index
	 *
	 * @param list
	 * @param index
	 * @return
	 */
	private ArrayList<Line> inclusiveSubList(ArrayList<Line> list, int index) {
		ArrayList<Line> res = new ArrayList<Line>();
		for (int i = 0; i <= index; i++) {
			res.add(list.get(i));
		}
		return res;
	}
}
