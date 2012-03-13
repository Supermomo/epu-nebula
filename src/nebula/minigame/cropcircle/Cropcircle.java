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
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class Cropcircle extends BasicGame {

	private Image land = null;
	private float x = 400;
	private float y = 300;
	private ArrayList<ArrayList<MyLine>> loadedTracks;
	private ArrayList<MyLine> track;
	private Image imgPath;
	private ArrayList<Vector2f> path;

	public Cropcircle() {
		super("Slick2DPath2Glory - SimpleGame");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		track = new ArrayList<MyLine>();
		try {
			load();
			System.out.println("size : "+loadedTracks.size()+"\n");
			Random r=new Random();
			track=loadedTracks.get(r.nextInt(loadedTracks.size()));
			
		} catch (Exception e) {
			loadedTracks=new ArrayList<ArrayList<MyLine>>();
		}

		ArrayList<MyLine> sav=new ArrayList<MyLine>();
		MyLine l=new MyLine(250, 50, 250, 150);
		sav.add(l);
		l=new MyLine(350, 50, 350, 350);
		sav.add(l);
		l=new MyLine(300, 400, 400, 400);
		sav.add(l);
		l=new MyLine(200, 300, 300, 400);
		sav.add(l);
		l=new MyLine(400, 400, 500, 300);
		sav.add(l);
		
		track=sav;
		
		path = new ArrayList<Vector2f>();
		imgPath = new Image("assets/cropCircle/braise.png");
		land = new Image("assets/cropCircle/spritBleu.jpg");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		 //x = input.getMouseX();
		 //y = input.getMouseY();

		if (input.isKeyDown(Input.KEY_SPACE)) {
			if ((path.size() >= 1 && path.get(path.size() - 1) != null && (Math
					.abs(path.get(path.size() - 1).x - x) > 30 || Math.abs(path
					.get(path.size() - 1).y
					- y) > 30))
					|| path.isEmpty()) {
			
				Vector2f p = new Vector2f(x,y);
				if (validDistanceFromClosestLine(p)) {
					path.add(p);
				}

			}
		}

		
		if (input.isKeyDown(Input.KEY_ENTER)) {
			ArrayList<MyLine> sav=new ArrayList<MyLine>();
			MyLine l=new MyLine(250, 50, 250, 150);
			sav.add(l);
			l=new MyLine(350, 50, 350, 350);
			sav.add(l);
			l=new MyLine(300, 400, 400, 400);
			sav.add(l);
			l=new MyLine(200, 300, 300, 400);
			sav.add(l);
			l=new MyLine(400, 400, 500, 300);
			sav.add(l);
			
			loadedTracks.add(sav);
			try {
				save();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			x--;
			x--;
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			x++;
			x++;
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			y--;
			y--;
		}
		
		if (input.isKeyDown(Input.KEY_DOWN)) {
			y++;
			y++;
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		land.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.white);
		
		for (Line p : track) {
			g.setLineWidth(10);
			g.drawLine(p.getX1(),p.getY1(),p.getX2(),p.getY2());
		}

		for (Vector2f v : path) {
			imgPath.draw(v.x - 30, v.y - 30);
		}
		g.setColor(Color.yellow);
		g.drawLine(x, y, gc.getWidth() / 2, gc.getHeight());
		
	}

	private boolean validDistanceFromClosestLine(Vector2f vect){
		int i=0;
		Line line;
		try {
			line=track.get(i);
			while(line != null){
				//TODO
				if(Math.abs(line.distance(vect))<10){
					return true;
				}
				i++;
				line=track.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	private void save() throws ClassNotFoundException {
		String filename = "trackList.data";
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;
		
		try {
			fis = new FileOutputStream(filename);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(loadedTracks);
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void load() throws Exception {
		String filename = "trackList.data";
		FileInputStream fos=null;
		ObjectInputStream out=null;

			fos = new FileInputStream(filename);
			out = new ObjectInputStream(fos);
			loadedTracks =  (ArrayList<ArrayList<MyLine>>) out.readObject();
			out.close();

	}
	
	private class MyLine extends Line implements Serializable{

		private static final long serialVersionUID = 1L;

		public MyLine(float x1, float y1, float x2, float y2) {
			super(x1, y1, x2, y2);
		}
		
	}
	
	
	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new Cropcircle());

		app.setDisplayMode(800, 600, true);
		app.start();
	}
}