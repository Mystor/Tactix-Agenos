/**
 * 
 */
package com.agenosworld.iso;

import java.util.IllegalFormatException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;
import org.newdawn.slick.SlickException;

import com.agenosworld.basicgame.AgenosImage;

/**
 * @author Michael
 *
 */
public class TileDefs extends DefaultHandler {
	
	//Static value representing the total number of TILE IDs avaliable
	public static int TILE_IDS = 256;
	
	//Array representing the tileDefs which have been loaded.
	private TileDef[] tiles;
	private int currTile;
	
	//The location where the image files are found for the tileDefs to use.
	private String imgRes;
	
	//the saxParser being used to parse the loaded XML file
	private SAXParser saxParser;
	
	//String representing the current text processed by the saxParser.
	private String tempVal;
	
	public TileDefs(String resXML, String imgRes) throws SlickException {
		this.imgRes = imgRes;
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();
			
			saxParser.parse(resXML, this);
		} catch (Exception e) {
			System.out.println(e.toString());
			//throw new SlickException(e.toString());
		}
	}
	
	public TileDef getTileById(int id) {
		if (id != 0)
			return tiles[id-1];
		return null;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {		
		if ("TILELIST".equalsIgnoreCase(qName)) {
			try {				
				tiles = new TileDef[Integer.parseInt(attributes.getValue("ids"))];
				TileDefs.TILE_IDS = Integer.parseInt(attributes.getValue("ids"));
			} catch (IllegalFormatException e) {
				System.out.println("ERROR: Unable to allocate array of length: "+attributes.getValue("ids"));
				return;
			}
		} else if (tiles == null) {
			tiles = new TileDef[TileDefs.TILE_IDS];
		}

		if ("TILE".equalsIgnoreCase(qName)) {
			int id = Integer.parseInt(attributes.getValue("id"));
			currTile = id-1;
			tiles[id-1] = new TileDef(id);
		}
		
		if ("TILEIMG".equalsIgnoreCase(qName)) {
			try {
				tiles[currTile].setTileImg(AgenosImage.fromImageDef(attributes, imgRes));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		if ("MAINVERT".equalsIgnoreCase(qName)) {
			try {
				tiles[currTile].setMainVert(AgenosImage.fromImageDef(attributes, imgRes));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		if ("TOPVERT".equalsIgnoreCase(qName)) {
			try {
				tiles[currTile].setTopVert(AgenosImage.fromImageDef(attributes, imgRes));
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		/*if ("".equals (uri))
		    System.out.println("Start element: " + qName);
		else
		    System.out.println("Start element: {" + uri + "}" + localName);*/
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("BLOCKED".equalsIgnoreCase(qName)) {
			if ("TRUE".equalsIgnoreCase(tempVal)) {
				tiles[currTile].setBlocked(true);
			} else {
				tiles[currTile].setBlocked(false);
			}
		}
		
		if ("TILE".equalsIgnoreCase(qName)) {
			currTile = -1;
		}
		
		/*if ("".equals (uri))
		    System.out.println("End element: " + qName);
		else
		    System.out.println("End element: {" + uri + "}" + localName);*/
	}
	
	public void characters (char ch [], int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}

}
