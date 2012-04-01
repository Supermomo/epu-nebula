package nebula.minigame.gravity;

public class Point extends java.awt.geom.Point2D.Float{

	private static final long serialVersionUID = 4274776390000672958L;
	
	public Point() {
		super();
	}
	public Point(float x, float y) {
		super(x,y);
	}
	
	public void translate(float dx, float dy) {
		setLocation(x+dx, y+dy);
	}
	
}
