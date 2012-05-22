package nebula.minigame.boss;

import org.newdawn.slick.Graphics;
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
	
	public void rotate(float angle, boolean droite)
	{	 
		if(!droite)
		{
			this.getImage().rotate(-angle);
			this.setViseX(this.getCenterX() * (float)Math.cos(Math.toRadians(this.getImage().getRotation())));
		}
		else
		{
			this.getImage().rotate(-angle);
			this.setViseX(this.getCenterX() * ((float)Math.cos(Math.toRadians(this.getImage().getRotation())) + 1));
		}
			
	}
	
	public void vise(Vaisseau v,  Graphics g)
	{
		float angle = (float)Math.atan(((v.getX() + v.getImage().getWidth()/2) - this.getViseX())/((v.getY() + v.getImage().getHeight()/2) - this.getViseY()));
		this.setViseY(v.getY() + v.getImage().getHeight()/2);
		if(v.getX() < this.getCenterX())
			rotate(angle,false);
		else
			rotate(angle, true);
		
		g.drawLine(getCenterX(), getCenterY(), viseX, viseY);
		
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
