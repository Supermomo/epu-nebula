package nebula.minigame.cropcircle;

import java.io.Serializable;

import org.newdawn.slick.geom.Line;

public class MyLine implements Serializable{

	private static final long serialVersionUID = 3080104614196960036L;
	float X1;
	float Y1;
	float X2;
	float Y2;
	
	public MyLine( Line l){
		X1=l.getX1();
		Y1=l.getY1();
		X2=l.getX2();
		Y2=l.getY2();
	}
}
