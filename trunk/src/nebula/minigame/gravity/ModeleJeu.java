package nebula.minigame.gravity;

public class ModeleJeu {

	Player hero;
	BlockMap map;
	
	public ModeleJeu(Player hero, BlockMap map) {
		setHero(hero);
		setMap(map);
	}

	public Player getHero() {
		return hero;
	}
	public void setHero(Player hero) {
		this.hero = hero;
	}
	
	public BlockMap getMap() {
		return map;
	}
	public void setMap(BlockMap map) {
		this.map = map;
	}
	
	//---
	// Méthode de déplacement du héro
	/////////////////////////////////
	public void deplacementGauche(int delta) {
		if(	!map.deplacementBloquant(hero.getBordGauche()))
		{
			hero.moveX(-delta);
			hero.setAnimGauche(); //if(hero.isStill()) 
		} else hero.moveX(+1);
	}
	public void deplacementDroite(int delta) {
		if(	!map.deplacementBloquant(hero.getBordDroit()))
		{
			hero.moveX(+delta);
			hero.setAnimDroite(); //if(hero.isStill())
		}
	}
	public void deplacementHaut(int delta) {
		if(	!map.deplacementBloquant(hero.getBordHaut()))
			hero.moveY(-delta);
	}
	public void deplacementBas(int delta) {
		if(	!map.deplacementBloquant(hero.getBordBas()))
			hero.moveY(+delta);
	}

	public void courrir() {
		// TODO Auto-generated method stub
		
	}

	public void inverserGravite() {
		// TODO Auto-generated method stub
		System.out.println("Inversion de la gravité");
	}
}
