package nebula.minigame.cropcircle;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public static ArrayList<ArrayList<MyLine>> ListLineToListMyLine(ArrayList<ArrayList<Line>> list){
		ArrayList<ArrayList<MyLine>> res= new ArrayList<ArrayList<MyLine>>();
		
		for(ArrayList<Line> sList : list){
			res.add(new ArrayList<MyLine>());
			for(Line p: sList){
				res.get(res.size()-1).add(new MyLine(p));
			}
		}
		
		return res;
	}
	
	public static ArrayList<ArrayList<Line>> ListMyLineToListLine(ArrayList<ArrayList<MyLine>> list){
		
		ArrayList<ArrayList<Line>> res=new ArrayList<ArrayList<Line>>();
		
		for(ArrayList<MyLine>slist : list){
			
			res.add(new ArrayList<Line>());
			
			for(MyLine p : slist){
			
				res.get(res.size()-1).add(MyLineToLine(p));
			}
		}
		
		return res;
	}
	
	public static Line MyLineToLine(MyLine ml){
		return new Line(ml.X1, ml.Y1, ml.X2, ml.Y2);
	}
}
