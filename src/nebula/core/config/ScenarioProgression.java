package nebula.core.config;

public enum ScenarioProgression {
	
	START("start"),
	BOUGIBOUGA("Bougi Bouga"),
	OTHERS("Other")
	;
	
	private String gameName;
	
	private ScenarioProgression(String game){
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

