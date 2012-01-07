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
public class PropDefs extends DefaultHandler {
	
	//Static value representing the total number of PROP IDs avaliable
	public static int PROP_IDS = 256;
	
	//Array representing the props which have been loaded.
	private Prop[] props;
	private int currProp;
	
	//The location where the image files are found
	private String imgRes;
	
	//the saxParser being used to parse the loaded XML file
	private SAXParser saxParser;
	
	//String representing the current text processed by the saxParser.
	//private String tempVal;
	
	public PropDefs(String resXML, String imgRes) throws SlickException {
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
	
	public Prop getPropById(int id) {
		if (id != 0)
			return props[id-1];
		
		return null;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {		
		if ("TILELIST".equalsIgnoreCase(qName)) {
			try {				
				PropDefs.PROP_IDS = Integer.parseInt(attributes.getValue("ids"));
				props = new Prop[PropDefs.PROP_IDS];
			} catch (IllegalFormatException e) {
				System.out.println("ERROR: Unable to allocate array of length: "+attributes.getValue("ids"));
				return;
			}
		} else if (props == null) {
			props = new Prop[PropDefs.PROP_IDS];
		}
		
		if ("PROP".equalsIgnoreCase(qName)) {
			int id = 0;
			
			try {
				id = Integer.parseInt(attributes.getValue("id"));
			} catch (NumberFormatException e) {
				return;
			}
			boolean forceBlocked = Boolean.parseBoolean(attributes.getValue("forceBlocked"));
			
			currProp = id-1;
			props[id-1] = new Prop(id, forceBlocked);
		}
		
		if ("IMG".equalsIgnoreCase(qName)) {
			if (currProp < 0)
				return;
			
			try {
				props[currProp].setImg(AgenosImage.fromImageDef(attributes, imgRes));
			} catch (SlickException e) {
				props[currProp].ready = false;
				return;
			}
		}
		
		/*if ("".equals (uri))
		    System.out.println("Start element: " + qName);
		else
		    System.out.println("Start element: {" + uri + "}" + localName);*/
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		/*if ("BLOCKED".equalsIgnoreCase(qName)) {
			if ("TRUE".equalsIgnoreCase(tempVal)) {
				props[currProp].setBlocked(true);
			} else {
				props[currProp].setBlocked(false);
			}
		}*/
		
		if ("TILE".equalsIgnoreCase(qName)) {
			currProp = -1;
		}
		
		/*if ("".equals (uri))
		    System.out.println("End element: " + qName);
		else
		    System.out.println("End element: {" + uri + "}" + localName);*/
	}
	
	/*public void characters (char ch [], int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}*/

}
