package org.catan.gui;

import org.catan.cards.*;
import org.catan.map.*;
import org.catan.players.*;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/* This is a companion class to hexgame.java. It handles all of the mechanics related to hexagon grids. */

public class hexmech
{
  /* Helpful references: 
http://www.codeproject.com/Articles/14948/Hexagonal-grid-for-games-and-other-projects-Part-1
http://weblogs.java.net/blog/malenkov/archive/2009/02/hexagonal_tile.html
http://www.tonypa.pri.ee/tbw/tut25.html
	 */

	/*
#define HEXEAST 0
#define HEXSOUTHEAST 1
#define HEXSOUTHWEST 2
#define HEXWEST 3
#define HEXNORTHWEST 4
#define HEXNORTHEAST 5
	 */
	final static Color COLORWOOL = new Color (104, 141, 60);
	final static Color COLORWHEAT = new Color (219, 202, 105);
	final static Color COLORORE = new Color(163, 173,184);
	final static Color COLORDESERT = new Color(185, 158, 107);
	final static Color COLORBRICK = new Color(143, 59,27);
	final static Color COLORLUMBER = new Color(64, 79, 36);
	final static int EMPTY = 0;
	final static int BSIZE = 5; //board size.
	final static int HEXSIZE = 60;	//hex size in pixels
	//final static int BORDERS = 15;  
	final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + 15*3; //screen size (vertical dimension).
	//Constants
	public final static boolean orFLAT= true;
	public final static boolean orPOINT= false;
	public static boolean ORIENT= orFLAT;  //this is not used. We're never going to do pointy orientation

	public static boolean XYVertex=true;	//true: x,y are the co-ords of the first vertex.
	//false: x,y are the co-ords of the top left rect. co-ord.

	private static int BORDERS=50;	//default number of pixels for the border.

	private static int s=0;	// length of one side
	private static int t=0;	// short side of 30o triangle outside of each hex
	private static int r=0;	// radius of inscribed circle (centre to middle of each side). r= h/2
	private static int h=0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

	public static void setXYasVertex(boolean b) {
		XYVertex=b;
	}
	public static void setBorders(int b){
		BORDERS=b;
	}

	/** This functions takes the Side length in pixels and uses that as the basic dimension of the hex.
            It calculates all other needed constants from this dimension.
	*/
	public static void setSide(int side) {
		s=side;
		t =  (int) (s / 2);			//t = s sin(30) = (int) CalculateH(s);
		r =  (int) (s * 0.8660254037844);	//r = s cos(30) = (int) CalculateR(s); 
		h=2*r;
	}
	public static void setHeight(int height) {
		h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
		r = h/2;			// r = radius of inscribed circle
		s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
		t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
	}

/*********************************************************
Name: hex()
Parameters: (x0,y0) This point is normally the top left corner 
    of the rectangle enclosing the hexagon. 
    However, if XYVertex is true then (x0,y0) is the vertex of the 
    top left corner of the hexagon. 
Returns: a polygon containing the six points.
Called from: drawHex(), fillhex()
Purpose: This function takes two points that describe a hexagon
and calculates all six of the points in the hexagon.
*********************************************************/
	public static Polygon hex (int x0, int y0) {

		int y = y0 + BORDERS;
		int x = x0 + BORDERS; // + (XYVertex ? t : 0); //Fix added for XYVertex = true. 
				      // NO! Done below in cx= section
		if (s == 0  || h == 0) {
			System.out.println("ERROR: size of hex has not been set");
			return new Polygon();
		}

		int[] cx,cy;

		//I think that this XYvertex stuff is taken care of in the int x line above. Why is it here twice?
		if (XYVertex) 
			cx = new int[] {x,x+s,x+s+t,x+s,x,x-t};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
		else
			cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};	//this is for the whole hexagon to be below and to the right of this point

		cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
		return new Polygon(cx,cy,6);

		/*
		   x=200;
		   poly = new Polygon();
		   poly.addPoint(x,y);
		   poly.addPoint(x+s,y);
		   poly.addPoint(x+s+t,y+r);
		   poly.addPoint(x+s,y+r+r);
		   poly.addPoint(x,y+r+r);
		   poly.addPoint(x-t,y+r);
		 */
	}

/********************************************************************
Name: drawHex()
Parameters: (i,j) : the x,y coordinates of the inital point of the hexagon
	    g2: the Graphics2D object to draw on.
Returns: void
Calls: hex() 
Purpose: This function draws a hexagon based on the initial point (x,y).
The hexagon is drawn in the colour specified in hexgame.COLOURELL.
*********************************************************************/
	public static void drawHex(int i, int j, Graphics2D g2, Integer resource, Map map) {// add new input that links the hexes to the grid
		int x = i * (s+t);
		int y = j * h + (i%2) * h/2;
		Polygon poly = hex(x,y);
		if(map.getHexes().get(resource).getResourceType() == "brick") {
		g2.setColor(COLORBRICK);}
		else if(map.getHexes().get(resource).getResourceType() == "lumber") {
		g2.setColor(COLORLUMBER);
		}
		else if(map.getHexes().get(resource).getResourceType() == "ore") {
		g2.setColor(COLORORE);
		}
		else if(map.getHexes().get(resource).getResourceType() == "grain") {
		g2.setColor(COLORWHEAT);	
		}
		else if (map.getHexes().get(resource).getResourceType() == "wool") {
		g2.setColor(COLORWOOL);	
		}
		else {
		g2.setColor(COLORDESERT);	
		}
		
		
		//section that sets the nodes xpix and ypix for the hex at resource based on the poly created in this method
		switch (resource) {
		case 0://we drew hex 0
			map.getNodes().get(3).setXpix(poly.xpoints[0]);
			map.getNodes().get(3).setYpix(poly.ypoints[0]);
			map.getNodes().get(0).setXpix(poly.xpoints[1]);
			map.getNodes().get(0).setYpix(poly.ypoints[1]);
			map.getNodes().get(4).setXpix(poly.xpoints[2]);
			map.getNodes().get(4).setYpix(poly.ypoints[2]);
			map.getNodes().get(8).setXpix(poly.xpoints[3]);
			map.getNodes().get(8).setYpix(poly.ypoints[3]);
			map.getNodes().get(12).setXpix(poly.xpoints[4]);
			map.getNodes().get(12).setYpix(poly.ypoints[4]);
			map.getNodes().get(7).setXpix(poly.xpoints[5]);
			map.getNodes().get(7).setYpix(poly.ypoints[5]);
			break;
		case 1:
			map.getNodes().get(4).setXpix(poly.xpoints[0]);
			map.getNodes().get(4).setYpix(poly.ypoints[0]);
			map.getNodes().get(1).setXpix(poly.xpoints[1]);
			map.getNodes().get(1).setYpix(poly.ypoints[1]);
			map.getNodes().get(5).setXpix(poly.xpoints[2]);
			map.getNodes().get(5).setYpix(poly.ypoints[2]);
			map.getNodes().get(9).setXpix(poly.xpoints[3]);
			map.getNodes().get(9).setYpix(poly.ypoints[3]);
			map.getNodes().get(13).setXpix(poly.xpoints[4]);
			map.getNodes().get(13).setYpix(poly.ypoints[4]);
			map.getNodes().get(8).setXpix(poly.xpoints[5]);
			map.getNodes().get(8).setYpix(poly.ypoints[5]);
			break;
		case 2:
			map.getNodes().get(5).setXpix(poly.xpoints[0]);
			map.getNodes().get(5).setYpix(poly.ypoints[0]);
			map.getNodes().get(2).setXpix(poly.xpoints[1]);
			map.getNodes().get(2).setYpix(poly.ypoints[1]);
			map.getNodes().get(6).setXpix(poly.xpoints[2]);
			map.getNodes().get(6).setYpix(poly.ypoints[2]);
			map.getNodes().get(10).setXpix(poly.xpoints[3]);
			map.getNodes().get(10).setYpix(poly.ypoints[3]);
			map.getNodes().get(14).setXpix(poly.xpoints[4]);
			map.getNodes().get(14).setYpix(poly.ypoints[4]);
			map.getNodes().get(9).setXpix(poly.xpoints[5]);
			map.getNodes().get(9).setYpix(poly.ypoints[5]);
			break;
		case 3:
			map.getNodes().get(11).setXpix(poly.xpoints[0]);
			map.getNodes().get(11).setYpix(poly.ypoints[0]);
			map.getNodes().get(7).setXpix(poly.xpoints[1]);
			map.getNodes().get(7).setYpix(poly.ypoints[1]);
			map.getNodes().get(12).setXpix(poly.xpoints[2]);
			map.getNodes().get(12).setYpix(poly.ypoints[2]);
			map.getNodes().get(17).setXpix(poly.xpoints[3]);
			map.getNodes().get(17).setYpix(poly.ypoints[3]);
			map.getNodes().get(22).setXpix(poly.xpoints[4]);
			map.getNodes().get(22).setYpix(poly.ypoints[4]);
			map.getNodes().get(16).setXpix(poly.xpoints[5]);
			map.getNodes().get(16).setYpix(poly.ypoints[5]);
			break;
		case 4:
			map.getNodes().get(12).setXpix(poly.xpoints[0]);
			map.getNodes().get(12).setYpix(poly.ypoints[0]);
			map.getNodes().get(8).setXpix(poly.xpoints[1]);
			map.getNodes().get(8).setYpix(poly.ypoints[1]);
			map.getNodes().get(13).setXpix(poly.xpoints[2]);
			map.getNodes().get(13).setYpix(poly.ypoints[2]);
			map.getNodes().get(18).setXpix(poly.xpoints[3]);
			map.getNodes().get(18).setYpix(poly.ypoints[3]);
			map.getNodes().get(23).setXpix(poly.xpoints[4]);
			map.getNodes().get(23).setYpix(poly.ypoints[4]);
			map.getNodes().get(17).setXpix(poly.xpoints[5]);
			map.getNodes().get(17).setYpix(poly.ypoints[5]);
			break;
		case 5:
			map.getNodes().get(13).setXpix(poly.xpoints[0]);
			map.getNodes().get(13).setYpix(poly.ypoints[0]);
			map.getNodes().get(9).setXpix(poly.xpoints[1]);
			map.getNodes().get(9).setYpix(poly.ypoints[1]);
			map.getNodes().get(14).setXpix(poly.xpoints[2]);
			map.getNodes().get(14).setYpix(poly.ypoints[2]);
			map.getNodes().get(19).setXpix(poly.xpoints[3]);
			map.getNodes().get(19).setYpix(poly.ypoints[3]);
			map.getNodes().get(24).setXpix(poly.xpoints[4]);
			map.getNodes().get(24).setYpix(poly.ypoints[4]);
			map.getNodes().get(18).setXpix(poly.xpoints[5]);
			map.getNodes().get(18).setYpix(poly.ypoints[5]);
			break;
		case 6:
			map.getNodes().get(14).setXpix(poly.xpoints[0]);
			map.getNodes().get(14).setYpix(poly.ypoints[0]);
			map.getNodes().get(10).setXpix(poly.xpoints[1]);
			map.getNodes().get(10).setYpix(poly.ypoints[1]);
			map.getNodes().get(15).setXpix(poly.xpoints[2]);
			map.getNodes().get(15).setYpix(poly.ypoints[2]);
			map.getNodes().get(20).setXpix(poly.xpoints[3]);
			map.getNodes().get(20).setYpix(poly.ypoints[3]);
			map.getNodes().get(25).setXpix(poly.xpoints[4]);
			map.getNodes().get(25).setYpix(poly.ypoints[4]);
			map.getNodes().get(19).setXpix(poly.xpoints[5]);
			map.getNodes().get(19).setYpix(poly.ypoints[5]);
			break;
		case 7:
			map.getNodes().get(21).setXpix(poly.xpoints[0]);
			map.getNodes().get(21).setYpix(poly.ypoints[0]);
			map.getNodes().get(16).setXpix(poly.xpoints[1]);
			map.getNodes().get(16).setYpix(poly.ypoints[1]);
			map.getNodes().get(22).setXpix(poly.xpoints[2]);
			map.getNodes().get(22).setYpix(poly.ypoints[2]);
			map.getNodes().get(28).setXpix(poly.xpoints[3]);
			map.getNodes().get(28).setYpix(poly.ypoints[3]);
			map.getNodes().get(33).setXpix(poly.xpoints[4]);
			map.getNodes().get(33).setYpix(poly.ypoints[4]);
			map.getNodes().get(27).setXpix(poly.xpoints[5]);
			map.getNodes().get(27).setYpix(poly.ypoints[5]);
			break;
		case 8:
			map.getNodes().get(22).setXpix(poly.xpoints[0]);
			map.getNodes().get(22).setYpix(poly.ypoints[0]);
			map.getNodes().get(17).setXpix(poly.xpoints[1]);
			map.getNodes().get(17).setYpix(poly.ypoints[1]);
			map.getNodes().get(23).setXpix(poly.xpoints[2]);
			map.getNodes().get(23).setYpix(poly.ypoints[2]);
			map.getNodes().get(29).setXpix(poly.xpoints[3]);
			map.getNodes().get(29).setYpix(poly.ypoints[3]);
			map.getNodes().get(34).setXpix(poly.xpoints[4]);
			map.getNodes().get(34).setYpix(poly.ypoints[4]);
			map.getNodes().get(28).setXpix(poly.xpoints[5]);
			map.getNodes().get(28).setYpix(poly.ypoints[5]);
			break;
		case 9:
			map.getNodes().get(23).setXpix(poly.xpoints[0]);
			map.getNodes().get(23).setYpix(poly.ypoints[0]);
			map.getNodes().get(18).setXpix(poly.xpoints[1]);
			map.getNodes().get(18).setYpix(poly.ypoints[1]);
			map.getNodes().get(24).setXpix(poly.xpoints[2]);
			map.getNodes().get(24).setYpix(poly.ypoints[2]);
			map.getNodes().get(30).setXpix(poly.xpoints[3]);
			map.getNodes().get(30).setYpix(poly.ypoints[3]);
			map.getNodes().get(35).setXpix(poly.xpoints[4]);
			map.getNodes().get(35).setYpix(poly.ypoints[4]);
			map.getNodes().get(29).setXpix(poly.xpoints[5]);
			map.getNodes().get(29).setYpix(poly.ypoints[5]);
			break;
		case 10:
			map.getNodes().get(24).setXpix(poly.xpoints[0]);
			map.getNodes().get(24).setYpix(poly.ypoints[0]);
			map.getNodes().get(19).setXpix(poly.xpoints[1]);
			map.getNodes().get(19).setYpix(poly.ypoints[1]);
			map.getNodes().get(25).setXpix(poly.xpoints[2]);
			map.getNodes().get(25).setYpix(poly.ypoints[2]);
			map.getNodes().get(31).setXpix(poly.xpoints[3]);
			map.getNodes().get(31).setYpix(poly.ypoints[3]);
			map.getNodes().get(36).setXpix(poly.xpoints[4]);
			map.getNodes().get(36).setYpix(poly.ypoints[4]);
			map.getNodes().get(30).setXpix(poly.xpoints[5]);
			map.getNodes().get(30).setYpix(poly.ypoints[5]);
			break;
		case 11:
			map.getNodes().get(25).setXpix(poly.xpoints[0]);
			map.getNodes().get(25).setYpix(poly.ypoints[0]);
			map.getNodes().get(20).setXpix(poly.xpoints[1]);
			map.getNodes().get(20).setYpix(poly.ypoints[1]);
			map.getNodes().get(26).setXpix(poly.xpoints[2]);
			map.getNodes().get(26).setYpix(poly.ypoints[2]);
			map.getNodes().get(32).setXpix(poly.xpoints[3]);
			map.getNodes().get(32).setYpix(poly.ypoints[3]);
			map.getNodes().get(37).setXpix(poly.xpoints[4]);
			map.getNodes().get(37).setYpix(poly.ypoints[4]);
			map.getNodes().get(31).setXpix(poly.xpoints[5]);
			map.getNodes().get(31).setYpix(poly.ypoints[5]);
			break;
		case 12:
			map.getNodes().get(33).setXpix(poly.xpoints[0]);
			map.getNodes().get(33).setYpix(poly.ypoints[0]);
			map.getNodes().get(28).setXpix(poly.xpoints[1]);
			map.getNodes().get(28).setYpix(poly.ypoints[1]);
			map.getNodes().get(34).setXpix(poly.xpoints[2]);
			map.getNodes().get(34).setYpix(poly.ypoints[2]);
			map.getNodes().get(39).setXpix(poly.xpoints[3]);
			map.getNodes().get(39).setYpix(poly.ypoints[3]);
			map.getNodes().get(43).setXpix(poly.xpoints[4]);
			map.getNodes().get(43).setYpix(poly.ypoints[4]);
			map.getNodes().get(38).setXpix(poly.xpoints[5]);
			map.getNodes().get(38).setYpix(poly.ypoints[5]);
			break;
		case 13:
			map.getNodes().get(34).setXpix(poly.xpoints[0]);
			map.getNodes().get(34).setYpix(poly.ypoints[0]);
			map.getNodes().get(29).setXpix(poly.xpoints[1]);
			map.getNodes().get(29).setYpix(poly.ypoints[1]);
			map.getNodes().get(35).setXpix(poly.xpoints[2]);
			map.getNodes().get(35).setYpix(poly.ypoints[2]);
			map.getNodes().get(40).setXpix(poly.xpoints[3]);
			map.getNodes().get(40).setYpix(poly.ypoints[3]);
			map.getNodes().get(44).setXpix(poly.xpoints[4]);
			map.getNodes().get(44).setYpix(poly.ypoints[4]);
			map.getNodes().get(39).setXpix(poly.xpoints[5]);
			map.getNodes().get(39).setYpix(poly.ypoints[5]);
			break;
		case 14:
			map.getNodes().get(35).setXpix(poly.xpoints[0]);
			map.getNodes().get(35).setYpix(poly.ypoints[0]);
			map.getNodes().get(30).setXpix(poly.xpoints[1]);
			map.getNodes().get(30).setYpix(poly.ypoints[1]);
			map.getNodes().get(36).setXpix(poly.xpoints[2]);
			map.getNodes().get(36).setYpix(poly.ypoints[2]);
			map.getNodes().get(41).setXpix(poly.xpoints[3]);
			map.getNodes().get(41).setYpix(poly.ypoints[3]);
			map.getNodes().get(45).setXpix(poly.xpoints[4]);
			map.getNodes().get(45).setYpix(poly.ypoints[4]);
			map.getNodes().get(40).setXpix(poly.xpoints[5]);
			map.getNodes().get(40).setYpix(poly.ypoints[5]);
			break;
		case 15:
			map.getNodes().get(36).setXpix(poly.xpoints[0]);
			map.getNodes().get(36).setYpix(poly.ypoints[0]);
			map.getNodes().get(31).setXpix(poly.xpoints[1]);
			map.getNodes().get(31).setYpix(poly.ypoints[1]);
			map.getNodes().get(37).setXpix(poly.xpoints[2]);
			map.getNodes().get(37).setYpix(poly.ypoints[2]);
			map.getNodes().get(42).setXpix(poly.xpoints[3]);
			map.getNodes().get(42).setYpix(poly.ypoints[3]);
			map.getNodes().get(46).setXpix(poly.xpoints[4]);
			map.getNodes().get(46).setYpix(poly.ypoints[4]);
			map.getNodes().get(41).setXpix(poly.xpoints[5]);
			map.getNodes().get(41).setYpix(poly.ypoints[5]);
			break;
		case 16:
			map.getNodes().get(43).setXpix(poly.xpoints[0]);
			map.getNodes().get(43).setYpix(poly.ypoints[0]);
			map.getNodes().get(39).setXpix(poly.xpoints[1]);
			map.getNodes().get(39).setYpix(poly.ypoints[1]);
			map.getNodes().get(44).setXpix(poly.xpoints[2]);
			map.getNodes().get(44).setYpix(poly.ypoints[2]);
			map.getNodes().get(48).setXpix(poly.xpoints[3]);
			map.getNodes().get(48).setYpix(poly.ypoints[3]);
			map.getNodes().get(51).setXpix(poly.xpoints[4]);
			map.getNodes().get(51).setYpix(poly.ypoints[4]);
			map.getNodes().get(47).setXpix(poly.xpoints[5]);
			map.getNodes().get(47).setYpix(poly.ypoints[5]);
			break;
		case 17:
			map.getNodes().get(44).setXpix(poly.xpoints[0]);
			map.getNodes().get(44).setYpix(poly.ypoints[0]);
			map.getNodes().get(40).setXpix(poly.xpoints[1]);
			map.getNodes().get(40).setYpix(poly.ypoints[1]);
			map.getNodes().get(45).setXpix(poly.xpoints[2]);
			map.getNodes().get(45).setYpix(poly.ypoints[2]);
			map.getNodes().get(49).setXpix(poly.xpoints[3]);
			map.getNodes().get(49).setYpix(poly.ypoints[3]);
			map.getNodes().get(52).setXpix(poly.xpoints[4]);
			map.getNodes().get(52).setYpix(poly.ypoints[4]);
			map.getNodes().get(48).setXpix(poly.xpoints[5]);
			map.getNodes().get(48).setYpix(poly.ypoints[5]);
			break;
		case 18:
			map.getNodes().get(45).setXpix(poly.xpoints[0]);
			map.getNodes().get(45).setYpix(poly.ypoints[0]);
			map.getNodes().get(41).setXpix(poly.xpoints[1]);
			map.getNodes().get(41).setYpix(poly.ypoints[1]);
			map.getNodes().get(46).setXpix(poly.xpoints[2]);
			map.getNodes().get(46).setYpix(poly.ypoints[2]);
			map.getNodes().get(50).setXpix(poly.xpoints[3]);
			map.getNodes().get(50).setYpix(poly.ypoints[3]);
			map.getNodes().get(53).setXpix(poly.xpoints[4]);
			map.getNodes().get(53).setYpix(poly.ypoints[4]);
			map.getNodes().get(49).setXpix(poly.xpoints[5]);
			map.getNodes().get(49).setYpix(poly.ypoints[5]);
			break;
		default:
			break;
	}
		
		
		
		//
		
		
		//g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}

/***************************************************************************
* Name: fillHex()
* Parameters: (i,j) : the x,y coordinates of the initial point of the hexagon
		n   : an integer number to indicate a letter to draw in the hex
		g2  : the graphics context to draw on
* Return: void
* Called from:
* Calls: hex()
*Purpose: This draws a filled in polygon based on the coordinates of the hexagon.
	  The colour depends on whether n is negative or positive.
	  The colour is set by hexgame.COLOURONE and hexgame.COLOURTWO.
	  The value of n is converted to letter and drawn in the hexagon.
*****************************************************************************/
	public static void fillHex(int i, int j, int n, Graphics2D g2) {
	int c;
	char rob = 'R';
		int x = i * (s+t);
		int y = j * h + (i%2) * h/2;
		//g2.drawString(""+c, x+r+BORDERS, y+r+BORDERS+4);
		/*if (n < 0) {
			g2.setColor(hexgame.COLOURONE);
			g2.fillPolygon(hex(x,y));
			//g2.setColor(hexgame.COLOURONETXT);
			c = (char)(-n);
			g2.drawString(""+c, x+r+BORDERS, y+r+BORDERS+4); //FIXME: handle XYVertex
			//g2.drawString(x+","+y, x+r+BORDERS, y+r+BORDERS+4);
		}
		if (n > 0) {*/
			//g2.setColor(hexgame.COLOURTWO);
			//g2.fillPolygon(hex(x,y));
			g2.setColor(Color.BLACK);
			c = n;
			if (c == 20) {
				g2.drawString(""+rob, x+r+BORDERS, y+r+BORDERS+4);
			}
			else if(c != 1) {g2.drawString(""+c, x+r+BORDERS, y+r+BORDERS+4);} //FIXME handle XYVertex
			//g2.drawString(i+","+j, x+r+BORDERS, y+r+BORDERS+4);
		//}
		
	}

	//This function changes pixel location from a mouse click to a hex grid location
/*****************************************************************************
* Name: pxtoHex (pixel to hex)
* Parameters: mx, my. These are the co-ordinates of mouse click.
* Returns: point. A point containing the coordinates of the hex that is clicked in.
           If the point clicked is not a valid hex (ie. on the borders of the board, (-1,-1) is returned.
* Function: This only works for hexes in the FLAT orientation. The POINTY orientation would require
            a whole other function (different math).
            It takes into account the size of borders.
            It also works with XYVertex being True or False.
*****************************************************************************/
	public static Point pxtoHex(int mx, int my) {
		Point p = new Point(-1,-1);

		//correction for BORDERS and XYVertex
		mx -= BORDERS;
		my -= BORDERS;
		if (XYVertex) mx += t;

		int x = (int) (mx / (s+t)); //this gives a quick value for x. It works only on odd cols and doesn't handle the triangle sections. It assumes that the hexagon is a rectangle with width s+t (=1.5*s).
		int y = (int) ((my - (x%2)*r)/h); //this gives the row easily. It needs to be offset by h/2 (=r)if it is in an even column

		/******FIX for clicking in the triangle spaces (on the left side only)*******/
		//dx,dy are the number of pixels from the hex boundary. (ie. relative to the hex clicked in)
		int dx = mx - x*(s+t);
		int dy = my - y*h;

		if (my - (x%2)*r < 0) return p; // prevent clicking in the open halfhexes at the top of the screen

		//System.out.println("dx=" + dx + " dy=" + dy + "  > " + dx*r/t + " <");
		
		//even columns
		if (x%2==0) {
			if (dy > r) {	//bottom half of hexes
				if (dx * r /t < dy - r) {
					x--;
				}
			}
			if (dy < r) {	//top half of hexes
				if ((t - dx)*r/t > dy ) {
					x--;
					y--;
				}
			}
		} else {  // odd columns
			if (dy > h) {	//bottom half of hexes
				if (dx * r/t < dy - h) {
					x--;
					y++;
				}
			}
			if (dy < h) {	//top half of hexes
				//System.out.println("" + (t- dx)*r/t +  " " + (dy - r));
				if ((t - dx)*r/t > dy - r) {
					x--;
				}
			}
		}
		p.x=x;
		p.y=y;
		return p;
	}
}