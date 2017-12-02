package org.catan.gui;

import java.awt.*;
import javax.swing.*;
import org.catan.gui.*;
import org.catan.map.*;
//import hexgame.DrawingPanel.MyMouseListener;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays; 
/*
class DrawingPanel extends JPanel {
	int[][] board = new int [5][5];
	//ArrayList<Hex> resources;
	Map map;
	public DrawingPanel (Map map) {
		setBackground(new Color(78, 97, 144));
		this.map= new Map();
		this.map = map;
		MyMouseListener ml = new MyMouseListener();            
		addMouseListener(ml);
	}
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		super.paint(g2d);
	    ArrayList<Integer> array  = new ArrayList<Integer>(Arrays.asList(7, 12, 16, 3, 8, 13, 17, 0, 4, 9, 14, 18, 1, 5, 10, 15, 2, 6, 11));
	  //draw grid
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				//System.out.println(i + " " + j);
				if( (i == 0 && j > 0 && j < 4) || (i == 1 && j < 4) || (i ==2) || (i == 3 && j < 4) || (i == 4 && j > 0 && j < 4)) {
				hexmech.drawHex(i,j,g2d, map.getHexes().get(array.get(0)));
				board[i][j] =map.getHexes().get(array.get(0)).getDiceNumber();
				if(map.getHexes().get(array.get(0)).getRobberStatus() == true) {
					board[i][j] = 20;
				}
				//System.out.println(resources.get(array.get(0)).getDiceNumber());
				array.remove(0);
				hexmech.fillHex(i,j,board[i][j],g2d);
				}
			}
		}
	}
	
	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			Point p = new Point( hexmech.pxtoHex(e.getX(),e.getY()) );
			if (p.x < 0 || p.y < 0 || p.x >= 5|| p.y >= 5) return;
			System.out.println( p.x +" " + p.y);
			//need to open up a new tab in here
			repaint();
		}
	}
	
}*/