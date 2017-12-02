package org.catan.gui;
//incldue package

import org.catan.cards.*;
//import org.catan.gui.DrawingPanel.MyMouseListener;
import org.catan.map.*;
import org.catan.players.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CatanGui extends JFrame {
	private Map map;
	private JMenuBar menuBar;
	private JMenu options;

	// Options submenus

	private JMenuItem optionClose;
	private JMenuItem optionRules;
	private JMenuItem optionCredits;
	private JButton b1;
	private JComboBox cb;
	private JComboBox nodes;
	private JComboBox edges;
	private int playernumber;
	private ArrayList<Player> players;
	private JPanel startScreen;
	private JPanel HexoptionsScreen;
	private int initial = 0;
	private int countIntial = 0;
	private DrawingPanel initialHex;

	public CatanGui() {
		super("Catan");

		map = new Map();// creates the map

		setSize(1000, 1000); // size
		// setLayout(new FlowLayout(FlowLayout.CENTER));//Layout
		startScreen();
		//initialbuildScreen();

		// add(new JLabel("<HTML><center>Welcome to Catan!</center><HTML>" ));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();
		setVisible(true);
		// Panels will be built here
		// There will be a switch in
	}

	public void buildGUI() {
		menuBar = new JMenuBar();

		options = new JMenu("Options");
		optionClose = new JMenuItem("Close");
		optionRules = new JMenuItem("Rules");
		optionCredits = new JMenuItem("Credits");

		optionClose.addActionListener(new MenuListener());
		optionRules.addActionListener(new MenuListener());
		optionCredits.addActionListener(new MenuListener());

		options.add(optionClose);
		options.add(optionRules);
		options.add(optionCredits);

		menuBar.add(options);

		setJMenuBar(menuBar);
	}

	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// JMenuItem source = (JMenuItem)(e.getSource());

			if (e.getSource() == optionClose) {
				System.exit(0);
			} else if (e.getSource() == optionRules) {
				handleRules();
			} else if (e.getSource() == optionCredits) {
				handleCredits();
			} else if (e.getSource() == b1) {
				handleStart();
			}
			// else if(e.getSource() == action){
			// handleFunction();}include handlers here
			// in the handler there will be the pop up windows/turning on and off the Panels
		}

		private void handleRules() {
			JTextArea textArea = new JTextArea(50, 50);
			textArea.setText("Rules");
			textArea.setEditable(false);

			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			JOptionPane.showMessageDialog(null, scrollPane, "Catan Rules", JOptionPane.PLAIN_MESSAGE);
		}

		private void handleCredits() {
		}

		// private void handleFunction() {}
		private void handleStart() {

			ArrayList<String> playernames = new ArrayList<String>();
			JTextField player1 = new JTextField(10);
			JTextField player2 = new JTextField(10);
			JTextField player3 = new JTextField(10);
			JTextField player4 = new JTextField(10);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(6, 6));

			playernumber = Integer.parseInt((String) cb.getSelectedItem());// gets the number of players from the combo
																			// box
			if (playernumber > 1) {// 2 players enter names
				myPanel.add(new JLabel("Player 1:"), BorderLayout.WEST);
				myPanel.add(player1, BorderLayout.EAST);
				myPanel.add(new JLabel("Player 2:"), BorderLayout.WEST);
				myPanel.add(player2, BorderLayout.EAST);
				// playernames.add(player1.getText());
				// System.out.println("Hit");
				// playernames.add(player2.getText());
			}
			if (playernumber > 2) {// 3
				myPanel.add(new JLabel("Player 3:"), BorderLayout.WEST);
				myPanel.add(player3, BorderLayout.EAST);
				// playernames.add(player3.getText());
			}
			if (playernumber > 3) {// 4
				myPanel.add(new JLabel("Player 4:"), BorderLayout.WEST);
				myPanel.add(player4, BorderLayout.EAST);
				// playernames.add(player4.getText());
			}
			int result = JOptionPane.showConfirmDialog(null, myPanel, "Add Course", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				if (playernumber > 1) {
					playernames.add(player1.getText());
					playernames.add(player2.getText());
				}
				if (playernumber > 2) {
					playernames.add(player3.getText());
				}
				if (playernumber > 3) {
					playernames.add(player4.getText());
				}

				players = Start.createPlayers(playernumber, playernames);// returns arraylist of players
				if (players.size() == playernumber) {
					startScreen.setVisible(false);
					initialbuildScreen();
				}
			}
		}

	}

	private void startScreen() {
		startScreen = new JPanel();// create the
		String comboBoxItems[] = { "2", "3", "4" };
		this.cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		startScreen.add(new JLabel("<HTML><center>Welcome to Catan!</center><HTML>"));
		startScreen.add(new JLabel("Number of Players"));
		startScreen.add(cb);
		// this.getContentPane().add(startScreen);

		b1 = new JButton("Start");
		b1.addActionListener(new MenuListener());
		JTextField players = new JTextField(10);
		startScreen.add(b1);
		// panel = new JPanel(new CardLayout());
		// panel.add(b1);
		this.getContentPane().add(startScreen);
		// this.getContentPane().add(panel);

	}

	private void initialbuildScreen() {
		hexmech.setXYasVertex(false);
		hexmech.setHeight(60);
		hexmech.setBorders(15);

		initialHex = new DrawingPanel();
		// JFrame frame = new JFrame("Works");
		// frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = this.getContentPane();
		content.add(initialHex);
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		initialHex.setVisible(true);
	}


	class DrawingPanel extends JPanel {
		int[][] board = new int[5][5];

		// ArrayList<Hex> resources;
		// Map map;
		public DrawingPanel() {
			setBackground(new Color(78, 97, 144));
			// map= new Map();
			// map = map;
			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			super.paint(g2d);
			ArrayList<Integer> array = new ArrayList<Integer>(
					Arrays.asList(7, 12, 16, 3, 8, 13, 17, 0, 4, 9, 14, 18, 1, 5, 10, 15, 2, 6, 11));
			// draw grid
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					// System.out.println(i + " " + j);
					if ((i == 0 && j > 0 && j < 4) || (i == 1 && j < 4) || (i == 2) || (i == 3 && j < 4)
							|| (i == 4 && j > 0 && j < 4)) {
						hexmech.drawHex(i, j, g2d, map.getHexes().get(array.get(0)));
						map.getHexes().get(array.get(0)).setXandY(i, j);
						board[i][j] = map.getHexes().get(array.get(0)).getDiceNumber();
						if (map.getHexes().get(array.get(0)).getRobberStatus() == true) {
							board[i][j] = 20;
						}
						// System.out.println(resources.get(array.get(0)).getDiceNumber());
						array.remove(0);
						hexmech.fillHex(i, j, board[i][j], g2d);
					}
				}
			}
		}

		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				//int count = 0;
				//int reverse = 0;
				Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= 5 || p.y >= 5)
					return;
				System.out.println(p.x + " " + p.y);
				// need to open up a new tab in here
				//for(int i = 0; i < playernumber; i++) {
				buildHexOptions(p.x, p.y, initial); //initial hex options
				repaint();
				//Will need to add cases in which this is not the player
				if(initial < (playernumber -1) && countIntial == 0) {//countIntial reverses player order
				initial++;
		
				}
				else if( initial == 0 && countIntial == 1 ) {//Initial stage ends when the count goes back to the first player
					initialHex.setVisible(false);
					System.out.println("END");
				}
				else if(initial == playernumber -1 && countIntial == 0) {
					countIntial = 1;
				}
				else {
					initial--; 
					//countIntial = 1;  //if the count has hit the end of the player array, go backwards
				}
				//}
			}
		}

	}

	private void buildHexOptions(int x, int y, int playerinarray) {// need to associate p.x and p.y to the hexes
		
		//FindHex(x,y);
		String selectNode;
		String selectEdge;
		HexoptionsScreen = new JPanel();// create the panel that allows selections around the hex
		HexoptionsScreen .setLayout(new GridLayout(6, 6));
		String Edges[] = { "top", "top right", "top left", "bottom right", "bottom left","bottom" };
		String Nodes[] = { "top right", "top left", "middle right", " middle left", "bottom right", "bottom left" };
		nodes = new JComboBox(Nodes);
		edges = new JComboBox(Edges);
		HexoptionsScreen.add(new JLabel("Settlement Placement:"), BorderLayout.WEST);
		HexoptionsScreen.add(nodes,BorderLayout.EAST);
		HexoptionsScreen.add(new JLabel("Road Placement:"), BorderLayout.WEST);
		HexoptionsScreen.add(edges,BorderLayout.EAST);
		
		int result = JOptionPane.showConfirmDialog(null, HexoptionsScreen,playerinarray + "Initial Settlement", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION){
			//make sure the road is next to the node
			//map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0);
			int success = 0;
			if(nodes.getSelectedItem() == Nodes[0] && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0)));
				success = 1;
			}
			else if (nodes.getSelectedItem() == Nodes[1] && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(1).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(1)));
				success = 1;
			}
			else if (nodes.getSelectedItem() == Nodes[2] &&  map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(2).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(2)));
				success = 1;
			}
			else if (nodes.getSelectedItem() == Nodes[3] && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(3).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(3)));
				success = 1;
			}
			else if (nodes.getSelectedItem() == Nodes[4]  && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(4).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(4)));
				success = 1;
			}
			else if (nodes.getSelectedItem() == Nodes[5]  && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(5).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(5)));
				success = 1;
			}
			if(edges.getSelectedItem() == Edges[0] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(0)));
				success = 0;
			}
			else if(edges.getSelectedItem() == Edges[1] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(1)));
				success = 0;
			}
			else if(edges.getSelectedItem() == Edges[2] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(2)));
				success = 0;
			}
			else if(edges.getSelectedItem() == Edges[3] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(3)));
				success = 0;
			}
			else if(edges.getSelectedItem() == Edges[4] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(4)));
				success = 0;
			}
			else if(edges.getSelectedItem() == Edges[5] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(5)));
				success = 0;
			}
			
		}
	//	System.out.println(players.get(0).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0)));//build an initial settlement in the top right position
		//System.out.println();
	}

	private int FindHex(int x, int y) {
		for (int i = 0; i < map.getHexes().size(); i++) {
			if(x == map.getHexes().get(i).getX() && y == map.getHexes().get(i).getY()) {
				return i;
			}
		}

		return 20;
	}
	

	
	
	
}
