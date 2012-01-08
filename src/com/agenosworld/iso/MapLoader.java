/**
 * 
 */
package com.agenosworld.iso;

import com.agenosworld.basicgame.*;

/*import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;*/

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.*;

/**
 * @author Michael
 *
 */
public class MapLoader {
	
	private static Tile[][] tiles;
	private static int mapWidth;
	private static int mapHeight;
	
	public static IsoMap loadMap(String ref, TileDefs tileDefs, PropDefs propDefs, GameBasics game) throws SlickException {
		
		MapLoader.tiles = null;
		
		XMLParser parser = new XMLParser();
		
		XMLElement baseElement = parser.parse(ref);
		
		//Getting width + height
		XMLElementList widthElements = baseElement.getChildrenByName("width");
		XMLElementList heightElements = baseElement.getChildrenByName("height");
		
		if (widthElements.size()>=1) {
			mapWidth = Integer.parseInt(widthElements.get(0).getContent().trim());
		} else {
			mapWidth = 1;
		}
		
		if (heightElements.size()>=1) {
			mapHeight = Integer.parseInt(heightElements.get(0).getContent().trim());
		} else {
			mapHeight = 1;
		}
		
		//Creating the Tile[][] array.
		tiles = new Tile[mapWidth][mapHeight];
		
		String tilesElement = baseElement.getChildrenByName("tiles").get(0).getContent();
		String elevationsElement = baseElement.getChildrenByName("elevations").get(0).getContent();
		
		String[] tilesArray = tilesElement.split(",");
		String[] elevationsArray = elevationsElement.split(",");
		
		
		int tileID = 0;
		int elevation = 0;
		for (int y=0; y<mapHeight; y++) {
			for (int x=0; x<mapWidth; x++) {
				try {
					tileID = Integer.parseInt(tilesArray[x+mapWidth*y].trim());
					elevation = Integer.parseInt(elevationsArray[x+mapWidth*y].trim());
				} catch (Exception e) {
					tileID = 0;
					elevation = 0;
				}
				
				TileDef def = tileDefs.getTileById(tileID);
				
				tiles[x][y] = new Tile(x, y, elevation, def);
			}
		}
		
		//Adding the props to the tiles.
		
		XMLElementList props = baseElement.getChildrenByName("props").get(0).getChildrenByName("tile");
		
		for (int i=0; i<props.size(); i++) {
			XMLElement propInfo = props.get(i);
			int x = propInfo.getIntAttribute("x");
			int y = propInfo.getIntAttribute("y");
			Tile tile = tiles[x][y];
			
			String[] propIDs = propInfo.getAttribute("props").split(",");
			
			for (String propID : propIDs) {
				tile.addProp(propDefs.getPropById(Integer.parseInt(propID)));
			}
			
		}
		
		/*SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			
			parser.parse(res, new MapLoader());
		} catch (Exception e) {
			throw new SlickException(e.getMessage());
		}*/
		
		return new IsoMap(tiles, mapWidth, mapHeight, game);
	}
	
	
	
	/*public MapLoader() { }
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if ("MAP".equalsIgnoreCase(qName)) {
			
		}
		
		
	}
		
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("WIDTH".equalsIgnoreCase(qName)) {
			try {
				MapLoader.mapWidth = Integer.parseInt(currentCharacters.trim());
			} catch (NumberFormatException e) {
				MapLoader.mapWidth = -1;
			}
		}
		
		if ("HEIGHT".equalsIgnoreCase(qName)) {
			try {
				MapLoader.mapHeight = Integer.parseInt(currentCharacters.trim());
			} catch (NumberFormatException e) {
				MapLoader.mapHeight = -1;
			}
		}
		
		if ("TILES".equalsIgnoreCase(qName)) {
			String[] strings = currentCharacters.split(",");
			MapLoader.tileID = new int[strings.length];
			
			for (int i=0; i<strings.length; i++) {
				try {
					MapLoader.tileID[i] = Integer.parseInt(strings[i].trim());
				} catch (NumberFormatException e) {
					MapLoader.tileID[i] = 0;
				}
			}
		}
		
		if ("ELEVATIONS".equalsIgnoreCase(qName)) {
			String[] strings = currentCharacters.split(",");
			MapLoader.elevations = new int[strings.length];
			
			for (int i=0; i<strings.length; i++) {
				try {
					MapLoader.elevations[i] = Integer.parseInt(strings[i].trim());
				} catch (NumberFormatException e) {
					MapLoader.tileID[i] = 0;
					MapLoader.elevations[i] = 0;
				}
			}
		}
	}
		
	public void characters (char ch [], int start, int length) throws SAXException {
		currentCharacters = new String(ch, start, length);
	}*/

}
