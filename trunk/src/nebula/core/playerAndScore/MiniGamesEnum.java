package nebula.core.playerAndScore;

public enum MiniGamesEnum {
	
	SPACEINVADERS("Space Invaders"),
	BREAKOUT("Breakout"),
	GRAVITY("Gravity"),
	SPACESHEPHERD("Space Shepherd")
	;
	
	private String gameName;
	
	MiniGamesEnum(String game){
		gameName=game;
	}
	
	public String toString(){
		return gameName;
	}
	
	
	/**
	 * Get the enum associated with the parameter string
	 * return null if the string doesn't match with any Enum
	 * @param game
	 * @return
	 */
	public static MiniGamesEnum stringToEnum(String game){
		for(MiniGamesEnum e : MiniGamesEnum.values()){
			if(e.toString().equals(game)){
				return e;
			}
		}
		return null;		
	}
}
