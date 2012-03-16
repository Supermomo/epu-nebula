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
	private Image imgPath;
	/**The pass that has been followed*/
	private ArrayList<Vector2f> path;

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
		
		path = new ArrayList<Vector2f>();
		imgPath = new Image("assets/cropCircle/braise.png");
		land = new Image("assets/cropCircle/spritBleu.jpg");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		 //System.out.println("x : "+input.getMouseX()+"\n");
		 //System.out.println("y = "+input.getMouseY()+"\n");

		if (input.isKeyDown(Input.KEY_SPACE)) {
			if ((path.size() >= 1 && path.get(path.size() - 1) != null && (Math
					.abs(path.get(path.size() - 1).x - x) > imgRadius || Math.abs(path
					.get(path.size() - 1).y
					- y) > imgRadius))
					|| path.isEmpty()) {
			
				Vector2f p = new Vector2f(x,y);
				if (validDistanceFromClosestLine(p)) {
					path.add(p);
				}
				else{
					malusTank++;
				}

			}
		}

		if (input.isKeyDown(Input.KEY_M)) {
			(new QuickSort()).sort(path);
			System.out.println(malusTank*malusTankCoef+getTrackMalus()*malusScoreCoef);
		}
		
		if (input.isKeyDown(Input.KEY_ENTER)) {
			//TODO mettre le dessin proportionelle à la taille de l'écran.
			ArrayList<Line> sav=new ArrayList<Line>();
			Line l;
			l=new Line(250, 50, 250, 150);
			sav.add(l);
			l=new Line(350, 50, 350, 150);
			sav.add(l);
			l=new Line(250, 400, 350, 400);
			sav.add(l);
			l=new Line(150, 300, 250, 400);
			sav.add(l);
			l=new Line(350, 400, 450, 300);
			sav.add(l);
			
			loadedTracks.add(sav);
			try {
				save();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			x-=(delta*0.3f);
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			x+=(delta*0.3f);;
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			y-=(delta*0.3f);;
		}
		
		if (input.isKeyDown(Input.KEY_DOWN)) {
			y+=(delta*0.3f);;
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		land.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.white);
		g.setLineWidth(10);
		
		for (Line p : track) {			
			g.drawLine(p.getX1(),p.getY1(),p.getX2(),p.getY2());
		}

		for (Vector2f v : path) {
			imgPath.draw(v.x - imgRadius, v.y - imgRadius);
		}
		g.setColor(Color.yellow);
		g.drawLine(x, y, gc.getWidth() / 2, gc.getHeight());
		
	}

	private boolean validDistanceFromClosestLine(Vector2f vect){

		try {
			
			for(Line line : track){
				if(Math.abs(line.distance(vect))<10){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	
		
	private int getTrackMalus(){
		int malus=0;
		if(path!=null && path.size()>0){
			Vector2f last=path.get(0);
			for(Vector2f vect : path){
				if(vect.distance(last)>60){
					malus++;
				}
			}
		}
		return malus;
	}
	
	
	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new Cropcircle());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}