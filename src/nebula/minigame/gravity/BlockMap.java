package nebula.minigame.gravity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
	private TiledMap map;
	private boolean[][] blocked;
	private int tileHeight;
	private int tileWidth;

	public BlockMap(String reference) {
		try {
			map = new TiledMap(reference);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Récupération des données sur la carte
		tileHeight = map.getTileHeight();
		tileWidth = map.getTileWidth();
		
		// Construction de la matrice de collision
		blocked = new boolean[map.getWidth()][map.getHeight()];
		for (int xAxis=0;xAxis<map.getWidth(); xAxis++) {
			for (int yAxis=0;yAxis<map.getHeight(); yAxis++) {
				if (map.getTileId(xAxis, yAxis, 0)!=0) blocked[xAxis][yAxis] = true;
			}
		}
	}

	public TiledMap getTiledMap() {
		return map;
	}
	
	public boolean deplacementBloquant(int xAxis, int yAxis) {
		return (map.getTileId(xAxis/tileWidth, yAxis/tileHeight, 0)!=0);
	}
}
