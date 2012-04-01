package nebula.minigame.gravity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

public class AnimGravite {

	private Animation[][] animation;
	private int i;
	
	public AnimGravite(SpriteSheet sheet) {
		animation = new Animation[3][2];
		
		// Création des animations
		// -- Animation Still
		animation[0][0] = new Animation();
		animation[0][0].addFrame(sheet.getSprite(0,0), 150);
		animation[0][0].addFrame(sheet.getSprite(1,0), 150);
		
		// -- Animation Gauche
		animation[1][0] = new Animation();
		animation[1][0].addFrame(sheet.getSprite(4,0), 150);
		animation[1][0].addFrame(sheet.getSprite(5,0), 150);
		
		// -- Animation Droite
		animation[2][0] = new Animation();
		animation[2][0].addFrame(sheet.getSprite(2,0), 150);
		animation[2][0].addFrame(sheet.getSprite(3,0), 150);
		
		// -- Animation Still
		animation[0][1] = new Animation();
		animation[0][1].addFrame(sheet.getSprite(0,1), 150);
		animation[0][1].addFrame(sheet.getSprite(1,1), 150);
		
		// -- Animation Gauche
		animation[1][1] = new Animation();
		animation[1][1].addFrame(sheet.getSprite(4,1), 150);
		animation[1][1].addFrame(sheet.getSprite(5,1), 150);
		
		// -- Animation Droite
		animation[2][1] = new Animation();
		animation[2][1].addFrame(sheet.getSprite(2,1), 150);
		animation[2][1].addFrame(sheet.getSprite(3,1), 150);
		
		i = 0;
	}
	
	public Animation gauche(boolean reversed) {
		i = 1;
		if(reversed) return animation[1][1];
		return animation[1][0];
	}
	public Animation droite(boolean reversed) {
		i = 2;
		if(reversed) return animation[2][1];
		return animation[2][0];
	}
	public Animation still(boolean reversed) {
		i = 0;
		if(reversed) return animation[0][1];
		return animation[0][0];
	}
	
	public Animation retourner (Animation anim) {
		if(anim.equals(animation[i][0])) {
			return animation[i][1];
		} else if(anim.equals(animation[i][1])) {
			return animation[i][0];
		}
		return animation[0][0];
	}
}
