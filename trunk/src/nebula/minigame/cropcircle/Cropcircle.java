package nebula.minigame.cropcircle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import nebula.core.helper.QuickSort;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class Cropcircle extends BasicGame implements Serializable{

	private static final long serialVersionUID = -2880478680378397924L;
	
	private Image land = null;
	private float x = 400;
	private float y = 300;
	/**Radius of one image*/
	//TODO mettre proportionnelle à l'écran
	private int imgRadius=30;
	/**Coefficient for the malus you get when you don't fill the track*/
	private float malusScoreCoef=1.0f;
	/**Coefficient for the malus you get when you draw on side of the track*/
	private float malusTankCoef=1.0f;
	/**Keep the malus accumulated by drawing aside from the track*/
	private int malusTank=0;
	/**The list of the available tracks*/
	private ArrayList<ArrayList<Line>> loadedTracks;
	/**The track to follow*/
	private ArrayList<Line> track;
	/**The imga printed on each poit of the path*/
	private Image imgPath;
	/**The path that has been followed*/
	private ArrayList<ArrayList<Vector2f>> path;
	/**the last drawnPoint*/
	private Vector2f lastPoint=new Vector2f(0,0);
	private float pointerSpeed=0.3f;

	public Cropcircle() {
		super("DeVint - CropCircle");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		track = new ArrayList<Line>();
		try {
			load();
			System.out.println("size : "+loadedTracks.size()+"\n");
			Random r=new Random();
			track=loadedTracks.get(r.nextInt(loadedTracks.size()));
			
		} catch (Exception e) {
			loadedTracks=new ArrayList<ArrayList<Line>>();
		}

		/*ArrayList<Line> sav=new ArrayList<Line>();
		Line l=new Line(250, 50, 250, 150);
		sav.add(l);
		l=new Line(350, 50, 350, 350);
		sav.add(l);
		l=new Line(300, 400, 400, 400);
		sav.add(l);
		l=new Line(200, 300, 300, 400);
		sav.add(l);
		l=new Line(400, 400, 500, 300);
		sav.add(l);
		
		track=sav;*/
		
		path = new ArrayList<ArrayList<Vector2f>>();
		for(Line l : track){
			path.add(new ArrayList<Vector2f>());
		}
		imgPath = new Image("assets/cropCircle/braise.png");
		land = new Image("assets/cropCircle/spritBleu.jpg");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();

		 //System.out.println("x : "+input.getMouseX()+"\n");
		 //System.out.println("y = "+input.getMouseY()+"\n");
				

		if (input.isKeyDown(Input.KEY_M)) {


			System.out.println("TankMalus "+malusTank*malusTankCoef+"\n track malus "+getTrackMalus()*malusScoreCoef);
		}
		
		if (input.isKeyDown(Input.KEY_ENTER)) {
			//TODO mettre le dessin proportionelle à la taille de l'écran.
			ArrayList<Line> sav=new ArrayList<Line>();
			Line l;
			l=new Line(250, 50, 250, 150);
			sav.add(l);
			l=new Line(350, 50, 350, 150);
			sav.add(l);
			l=new Line(200, 350, 400, 350);
			sav.add(l);
			
			loadedTracks.add(sav);
			try {
				save();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			x-=(delta*pointerSpeed);
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			x+=(delta*pointerSpeed);;
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			y-=(delta*pointerSpeed);;
		}
		
		if (input.isKeyDown(Input.KEY_DOWN)) {
			y+=(delta*pointerSpeed);;
		}
		
		if (input.isKeyDown(Input.KEY_SPACE)) {
			
			if (validDistanceFromLastPoint()) {

				Vector2f p = new Vector2f(x,y);
				if (!addToClosestLines(p)) {
					malusTank++;
				}
				else{
					lastPoint=new Vector2f(x,y);
				}
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		land.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.white);
		g.setLineWidth(10);
		
		for (Line p : track) {			
			g.drawLine(p.getX1(),p.getY1(),p.getX2(),p.getY2());
		}

		for (ArrayList<Vector2f> v : path) {
			for(Vector2f vect:v){
				imgPath.draw(vect.x - imgRadius, vect.y - imgRadius);
			}	
		}
		g.setColor(Color.yellow);
		g.drawLine(x, y, gc.getWidth() / 2, gc.getHeight());
		
	}

	

	private void save() throws ClassNotFoundException {
		String filename = "assets/cropCircle/trackList.data";
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;
		
		try {
			fis = new FileOutputStream(filename);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(ListLineToListMyLine(loadedTracks));
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void load() throws Exception {
		String filename = "assets/cropCircle/trackList.data";
		FileInputStream fos=null;
		ObjectInputStream out=null;

			fos = new FileInputStream(filename);
			out = new ObjectInputStream(fos);
			loadedTracks =  ListMyLineToListLine((ArrayList<ArrayList<MyLine>>) out.readObject());
			out.close();

	}
	
	private ArrayList<ArrayList<MyLine>> ListLineToListMyLine(ArrayList<ArrayList<Line>> list){
		ArrayList<ArrayList<MyLine>> res= new ArrayList<ArrayList<MyLine>>();
		
		for(ArrayList<Line> sList : list){
			res.add(new ArrayList<MyLine>());
			for(Line p: sList){
				res.get(res.size()-1).add(new MyLine(p));
			}
		}
		
		return res;
	}
	
	private ArrayList<ArrayList<Line>> ListMyLineToListLine(ArrayList<ArrayList<MyLine>> list){
		ArrayList<ArrayList<Line>> res=new ArrayList<ArrayList<Line>>();
		
		for(ArrayList<MyLine>slist : list){
			
			res.add(new ArrayList<Line>());
			
			for(MyLine p : slist){
			
				res.get(res.size()-1).add(MyLineToLine(p));
			}
		}
		
		return res;
	}
	
	private Line MyLineToLine(MyLine ml){
		return new Line(ml.X1, ml.Y1, ml.X2, ml.Y2);
	}
	
	/**
	 * get the malus due to not filling the lines
	 * @return
	 */
	private int getTrackMalus(){
		int malus=0;
		int malusX;
		int malusY;
		int lineCpt=0;
		
		for(ArrayList<Vector2f>lineVect:path){
			
			(new QuickSort()).sortByX(lineVect);
			malusX=getLineMalus(lineVect, track.get(lineCpt));
			(new QuickSort()).sortByY(lineVect);
			malusY=getLineMalus(lineVect, track.get(lineCpt));
			
			malus+=Math.min(malusX, malusY);
			
			lineCpt++;
		}
		return malus;
	}
	
	/**
	 * Return the malus got when a line is not filled
	 * @param linePath
	 * @param line
	 * @return
	 */
	private int getLineMalus(ArrayList<Vector2f> linePath, Line line){
        int malus=0;
        if(linePath!=null && linePath.size()>1){
                Vector2f last=linePath.get(0);
                for(Vector2f vect : linePath){
                        if(vect.distance(last)>imgRadius*2){
                                malus+=vect.distance(last);
                        }
                        last=vect;
                }
        }
        else{
        	malus+=100;
        }
        return malus;

	}
	private boolean validDistanceFromLastPoint(){
		return (Math.abs(lastPoint.x - x) > imgRadius
				|| Math.abs(lastPoint.y- y) > imgRadius)
						|| path.isEmpty() || path==null;
	}
	
	/**
	 * return true if the point has been successfully added
	 * return false otherwise
	 * @param p
	 * @return
	 */
	private boolean addToClosestLines(Vector2f p){
		boolean res=false;
		for(int i=0;i<track.size();i++){
			if(track.get(i).distance(p)<imgRadius){
				path.get(i).add(p);
				res=true;
			}
		}
		return res;
	}
	
	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new Cropcircle());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}