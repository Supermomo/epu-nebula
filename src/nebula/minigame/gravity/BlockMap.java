package nebula.minigame.gravity;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class BlockMap {



	private TiledMap map;
	private BlockType[][] blocks;
	private int tileHeight;
	private int tileWidth;

	private Point depart;

	public Point getDepart() {
		return depart;
	}
	public void setDepart(float x, float y) {
		depart = new Point((int) Math.floor(x/tileWidth)*tileWidth,(int) Math.floor(y/tileHeight)*tileHeight);
	}

	public BlockMap(String reference) throws SlickException {

		map = new TiledMap(reference);

		// Récupération des données sur la carte
		tileHeight = map.getTileHeight();
		tileWidth = map.getTileWidth();

		// Construction de la matrice de blocks
		blocks = new BlockType[map.getWidth()][map.getHeight()];

		// --- Récupération des tileSets de blocs
		for(int xAxis=0;xAxis<map.getWidth(); xAxis++) {
			for (int yAxis=0;yAxis<map.getHeight(); yAxis++) {
				//--- Éléments bloquant
				if (map.getTileId(xAxis, yAxis, map.getLayerIndex("block"))!=0) blocks[xAxis][yAxis] = BlockType.BLOCK;
				else if(map.getTileId(xAxis, yAxis, map.getLayerIndex("end"))!=0) blocks[xAxis][yAxis] = BlockType.END;
				else if(map.getTileId(xAxis, yAxis, map.getLayerIndex("death"))!=0) blocks[xAxis][yAxis] = BlockType.DEATH;
			}
		}

		// Récupération des Objets
		int id_layer_objet = 0; // Impossible de récupérer les layers d'objets avec le parser TiledMap de Slick
		for(int j=0; j<map.getObjectCount(id_layer_objet); j++) {
			// Récupération du départ
			if("start".equals(map.getObjectName(id_layer_objet,j)) && "default".equals(map.getObjectType(id_layer_objet,j))) {
				setDepart(map.getObjectX(id_layer_objet, j)/60*60+60, map.getObjectY(id_layer_objet, j)/60*60+60);
			} else if("warp".equals(map.getObjectName(id_layer_objet,j))) {
				map.getObjectX(0, j);
				map.getObjectY(0, j);
			}
		}

		if(depart==null) throw new SlickException("Aucun objet start enregistré dans la carte "+reference);

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
