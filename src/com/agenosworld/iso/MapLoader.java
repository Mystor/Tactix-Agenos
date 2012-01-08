/**
 * 
 */
package com.agenosworld.iso;

import com.agenosworld.basicgame.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.newdawn.slick.SlickException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Michael
 *
 */
public class MapLoader extends DefaultHandler {
	
	private static TileDefs tileDefs;
	private static PropDefs propDefs;
	
	private static Tile[][] tiles;
	private static int mapWidth;
	private static int mapHeight;
	
	private String currentCharacters;
	
	public static IsoMap loadMap(String res, TileDefs tileDefs, PropDefs propDefs, GameBasics game) throws SlickException {
		
		MapLoader.tiles = null;
		MapLoader.tileDefs = tileDefs;
		MapLoader.propDefs = propDefs;
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			
			parser.parse(res, new MapLoader());
		} catch (Exception e) {
			throw new SlickException(e.getMessage());
		}
		
		return new IsoMap(tiles, mapWidth, mapHeight, game);
	}
	
	public MapLoader() { }
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
	}
		
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
	}
		
	public void characters (char ch [], int start, int length) throws SAXException {
		currentCharacters = new String(ch, start, length);
	}

}
