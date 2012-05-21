package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

public class Tourelle 
{
	private static Image image = null;
	private float x;
	private float y;
	private float viseX;
	private float viseY;
	private float centerX;
	private float centerY;
	
	
	public Tourelle() throws SlickException
	{
		image = new Image("ressources/images/boss/ship.png");
	}
	
	public void rotate(float delta, boolean droite)
	{	 
		if(droite)
		{
        	this.getImage().rotate(-0.2f * delta);
            this.setViseX(delta * 0.4f * (float)Math.sin(Math.toRadians(this.getImage().getRotation())));
		}
        else
        {
        	this.getImage().rotate(0.2f * delta);
        	this.setViseX(delta * 0.4f * (float)Math.sin(Math.toRadians(this.getImage().getRotation())));
        }
	}
	
	public void vise(Vaisseau v, float delta)
	{
		Line l1 = new Line(getCenterX(), getCenterY(), getViseX(), getViseY());
		Line l2 = new Line(v.getX() + v.getImage().getWidth()/2, v.getY() + v.getImage().getHeight()/2, 0, v.getY() + v.getImage().getHeight()/2);
		
		Vector2f p = l1.intersect(l2, false);
		
		System.out.println("x : " + p.getX());
		System.out.println("y : " + p.getY());
		System.out.println("vaisseau x : " + v.getX());
		System.out.println("vaisseau y : " + v.getY());
		
		if(p.getX() > v.getX())
		{
			rotate(delta, false);
		}
		else
		{
			rotate(delta, true);
		}
	}

	public Image getImage() 
	{
		return image;
	}

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}

	public float getViseX() {
		return viseX;
	}

	public void setViseX(float viseX) {
		this.viseX = viseX;
	}

	public float getViseY() {
		return viseY;
	}

	public void setViseY(float viseY) {
		this.viseY = viseY;
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}
	
	
}
