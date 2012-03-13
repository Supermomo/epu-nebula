package nebula.minigame.gravity;

public enum Sprite {
	HAUTEUR(15),
	LARGEUR(15),
	ESPACE(1),
	MARGE(0);
	
	private int valeur;
	Sprite(int i) {
		valeur = i;
	}
	
	public int getValue() {
		return valeur;
	}
}
