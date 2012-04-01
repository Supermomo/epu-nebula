package nebula.minigame.gravity;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {
	
	
	
	private TiledMap map;
	private BlockType[][] blocks;
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
		
		// Construction de la matrice de blocks
		blocks = new BlockType[map.getWidth()][map.getHeight()];
		
		
		for (int xAxis=0;xAxis<map.getWidth(); xAxis++) {
			for (int yAxis=0;yAxis<map.getHeight(); yAxis++) {
				//--- Éléments bloquant
				if (map.getTileId(xAxis, yAxis, 0)!=0) blocks[xAxis][yAxis] = BlockType.BLOCK;
				else if(map.getTileId(xAxis, yAxis, 1)!=0) blocks[xAxis][yAxis] = BlockType.END;
				else if(map.getTileId(xAxis, yAxis, 3)!=0) blocks[xAxis][yAxis] = BlockType.DEATH;
			}
		}
		
	}

	public TiledMap getTiledMap() {
		return map;
	}
	
	public BlockType[][] getBlocks() {
		return blocks;
	}
	
	/**
	 * Regarde si au moins un des deux points est placé sur unr tuile block
	 * @param p Les deux points à tester
	 * @return Vrai si le déplacement est bloquant, faux sinon
	 */
	public boolean deplacementBloquant(Point[] p) {
		if( blocks[(int) (p[0].getX()/tileWidth)][(int) (p[0].getY()/tileHeight)] == BlockType.BLOCK
		||	blocks[(int) (p[1].getX()/tileWidth)][(int) (p[1].getY()/tileHeight)] == BlockType.BLOCK)
			return true;
		return false;
	}
	
	/**
	 * Regarde si au moins un des deux points est placé sur unr tuile block
	 * @param p Les deux points à tester
	 * @return Le type de tuile sur lequel est positionné le point
	 */	
	public BlockType collisionType(Point[] p) {
		BlockType b1 = blocks[(int) (p[0].getX()/tileWidth)][(int) (p[0].getY()/tileHeight)];
		BlockType b2 = blocks[(int) (p[1].getX()/tileWidth)][(int) (p[1].getY()/tileHeight)];
		// Classement par ordre de priorité
		if(b1 == BlockType.BLOCK|| b2 == BlockType.BLOCK)	return BlockType.BLOCK;
		if(b1 == BlockType.END	|| b2 == BlockType.END) 	return BlockType.END;
		if(b1 == BlockType.DEATH|| b2 == BlockType.DEATH)	return BlockType.DEATH;
		return BlockType.VIDE;
	}

}
