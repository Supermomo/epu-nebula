package nebula.minigame.gravity;

public enum Sprite {
	HAUTEUR(60),
	LARGEUR(60),
	ESPACE(0),
	MARGE(0);
	
	private int valeur;
	Sprite(int i) {
		valeur = i;
	}
	
	public int getValue() {
		return valeur;
	}
}
