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
	private JMenu trading;
	private JMenu turnEnd;
	private JMenu player;
	private JMenu build;
	//private JMenu playerHand;

	// Options submenus

	private JMenuItem optionClose;
	private JMenuItem optionRules;
	private JMenuItem optionCredits;
	private JMenuItem optionPlayer;
	private JMenuItem optionBank;
	private JMenuItem optionHarbor;
	private JMenuItem optionTurnEnd;
	private JMenuItem optionResources;
	private JMenuItem optionRoad;
	private JMenuItem optionSettlement;
	private JMenuItem optionCity;
	//private JMenuItem optionCheckCards;
	
	private JButton b1;
	private JButton top;
	private JButton topRight;
	private JButton topLeft;
	private JButton bottomRight;
	private JButton bottomLeft;
	private JButton bottom;
	private JButton middleRight;
	private JButton middleLeft;
	
	
	private JComboBox<String> cb;
	private JComboBox<String> nodes;
	private JComboBox<String> edges;
	private JComboBox<String> traders;
	private String tradeEntity;
	private int playernumber;
	private ArrayList<Player> players;
	
	
	private JPanel startScreen;
	private JPanel HexoptionsScreen;
	private JPanel PlayeroptionsScreen;
	private JPanel resourcePanel;
	
	private int initialOK = 0;
	private int currentPlayer = 0;
	private int countIntial = 0;
	private DrawingPanel currentPlayerHex;
	private int gameStart = 0;
	private int buildcount = 0; //int 0 =  not building, 1 = road, 2 = settlement, 3 = city
	//private String title = "Catan";
	private int moveRobby = 0;
	private int tempX;
	private int tempY;

	public CatanGui() {
		super("Catan");
		 currentPlayer = 0;
		 countIntial = 0;
		 gameStart = 0;
		 buildcount = 0; //int 0 =  not building, 1 = road, 2 = settlement, 3 = city
		//private String title = "Catan";
		 moveRobby = 0;
		
		map = new Map();// creates the map
		
		setSize(450, 500);
		startScreen();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();
		//System.out.println("stage1");
		setVisible(true);
		
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
			} 
			else if (e.getSource() == optionRules) {
				handleRules();
			} 
			else if (e.getSource() == optionCredits) {
				handleCredits();
			} 
			else if (e.getSource() == b1) {
				handleStart();
			}
			else if (e.getSource() == optionPlayer) {
				handlePlayerTrade();
			} 
			else if (e.getSource() == optionBank) {
				handleBankTrade();
			} 
			else if (e.getSource() == optionHarbor) {
				handleHarborTrade();
			}
			else if (e.getSource() == optionResources) {
				handleResources();
			}
			else if (e.getSource() == optionTurnEnd) {
				menuBar.removeAll();
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
				if(players.get(currentPlayer).getPoints() >= 10) {
					
					JPanel winner = new JPanel();
					winner.add(new JLabel(players.get(currentPlayer).getName() + " has won!"));
					JOptionPane.showMessageDialog(null,winner, "This guys is better than the others" ,JOptionPane.PLAIN_MESSAGE );
					currentPlayer = 0;
					 countIntial = 0;
					 gameStart = 0;
					 initialOK = 0;
					 buildcount = 0; //int 0 =  not building, 1 = road, 2 = settlement, 3 = city
					//private String title = "Catan";
					 moveRobby = 0;
					menuBar.removeAll();
					map = new Map();
					currentPlayerHex.setVisible(false);
					currentPlayerHex = null;
					startScreen();
					buildGUI();
				}
				else {	
					if ((currentPlayer + 1) < players.size()){
						currentPlayer++;
					}
					else {
						currentPlayer = 0;
					}
				PlayerScreen();
				System.out.println(players.get(currentPlayer).getPoints());
				diceScreen();
				setName(players.get(currentPlayer).getName());}
			}
			else if(e.getSource() == optionRoad) {
				buildcount = 1;
			}
			else if(e.getSource() == optionSettlement) {
				buildcount = 2;
			}
			else if(e.getSource() == optionCity) {
				buildcount = 3;
			}
			else if (e.getSource() == top) {
				handleBuild("top");
			}
			else if (e.getSource() == topRight) {
				handleBuild("topRight");
			}
			else if (e.getSource() == topLeft) {
				handleBuild("topLeft");
			}
			else if (e.getSource() == bottomRight) {
				handleBuild("bottomRight");
			}
			else if (e.getSource() == bottomLeft) {
				handleBuild("bottomLeft");
			}
			else if (e.getSource() == bottom) {
				handleBuild("bottom");
			}
			else if (e.getSource() == middleRight) {
				handleBuild("middleRight");
			}
			else if (e.getSource() == middleLeft) {
				handleBuild("middleLeft");
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
					
					currentPlayerbuildScreen();
				}
			}
		}
		
		private void handlePlayerTrade() {
		}
		
		private void handleBankTrade() {
		}
		
		private void handleHarborTrade() {
		}
		
		private void handleResources() {
			
			resourcePanel = new JPanel();			
			resourcePanel.setLayout(new GridLayout(5, 1));			

			resourcePanel.add(new JLabel("Amount of brick: " + Integer.toString(players.get(currentPlayer).getResources("brick"))));
			resourcePanel.add(new JLabel("Amount of lumber: " + Integer.toString(players.get(currentPlayer).getResources("lumber"))));
			resourcePanel.add(new JLabel("Amount of grain: " + Integer.toString(players.get(currentPlayer).getResources("grain"))));
			resourcePanel.add(new JLabel("Amount of wool: " + Integer.toString(players.get(currentPlayer).getResources("wool"))));
			resourcePanel.add(new JLabel("Amount of ore: " + Integer.toString(players.get(currentPlayer).getResources("ore"))));
			
			JOptionPane.showMessageDialog(null, resourcePanel, "Resources" ,JOptionPane.PLAIN_MESSAGE );
		}
	 		
	  private void handleBuild(String s) {
		  System.out.println("handleBuild");
		  System.out.println(tempX + " " + tempY);
		  String result = "";
		  String place =  "";
		  JPanel buildPanel = new JPanel();	 
		  if(buildcount == 1) {
		  if(s == "top") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(0));
		  }
		  else if(s == "topRight") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(1));
		  }
		  else if( s == "topLeft") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(2));
		  }
		  else if( s == "bottom") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(3));
		  }
		  else if( s == "bottomRight") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(4));
		  }
		  else if(s == "bottomLeft") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(5));
		  }
		  place = "Road";
		}
		  else if (buildcount == 2) {
			  if(s == "top") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(0));
			  }
			  else if(s == "topRight") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(1));
			  }
			  else if( s == "topLeft") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(2));
			  }
			  else if( s == "bottom") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(3));
			  }
			  else if( s == "bottomRight") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(4));
			  }
			  else if(s == "bottomLeft") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(5));
			  }
			 place = "Settlement";
		  }
		  else if (buildcount ==3) {
			  if(s == "top") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(0));
			  }
			  else if(s == "topRight") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(1));
			  }
			  else if( s == "topLeft") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(2));
			  }
			  else if( s == "bottom") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(3));
			  }
			  else if( s == "bottomRight") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(4));
			  }
			  else if(s == "bottomLeft") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(5));
			  }  
			  place = "City";
		  }
		  buildPanel.add(new JLabel(result));
		  JOptionPane.showMessageDialog(null, buildPanel, place + " to " +s + " of " + map.getHex(FindHex(tempX,tempY)).getResourceType() + " " + map.getHex(FindHex(tempX,tempY)).getDiceNumber() ,JOptionPane.PLAIN_MESSAGE );
		  
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

	private void currentPlayerbuildScreen() {
		hexmech.setXYasVertex(false);
		hexmech.setHeight(60);
		hexmech.setBorders(15);

		currentPlayerHex = new DrawingPanel();
		// JFrame frame = new JFrame("Works");
		// frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = this.getContentPane();
		content.add(currentPlayerHex);
		setSize(450, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		currentPlayerHex.setVisible(true);
	}

	private void PlayerScreen() {
		trading = new JMenu("Trade");
		optionPlayer = new JMenuItem("Player");
		optionBank = new JMenuItem("Bank");
		optionHarbor = new JMenuItem("Harbor");
		optionRoad = new JMenuItem("Road");
		optionSettlement = new JMenuItem("Settlement");
		optionCity = new JMenuItem("City");
		
		
		
		build = new JMenu("Build");
		
		optionPlayer.addActionListener(new MenuListener());
		optionBank.addActionListener(new MenuListener());
		optionHarbor.addActionListener(new MenuListener());
		optionRoad.addActionListener(new MenuListener());
		optionSettlement.addActionListener(new MenuListener());
		optionCity.addActionListener(new MenuListener());
		
		
		trading.add(optionPlayer);
		trading.add(optionBank);
		trading.add(optionHarbor);
		
		build.add(optionRoad);
		build.add(optionSettlement);
		build.add(optionCity);

		
		player = new JMenu(players.get(currentPlayer).getName());
		optionResources = new JMenuItem("Check Resources");
		
		optionResources.addActionListener(new MenuListener());
		
		player.add(optionResources);
		
		
		turnEnd = new JMenu("End Turn");
		optionTurnEnd = new JMenuItem("End Turn");
		
		optionTurnEnd.addActionListener(new MenuListener());
		
		turnEnd.add(optionTurnEnd);
		
	if(moveRobby == 0) {
		menuBar.add(trading);
		menuBar.add(build);
		menuBar.add(player);
		menuBar.add(turnEnd);
	}
	
		setJMenuBar(menuBar);
		
		diceScreen();
	
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
			g2d.setStroke(new BasicStroke(1));
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					// System.out.println(i + " " + j);
					if ((i == 0 && j > 0 && j < 4) || (i == 1 && j < 4) || (i == 2) || (i == 3 && j < 4)
							|| (i == 4 && j > 0 && j < 4)) {
						hexmech.drawHex(i, j, g2d, array.get(0), map);
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
			
			//draw the player's stuff
			//draw roads
			g2d.setStroke(new BasicStroke(3));
			for(int iii = 0; iii < map.getEdges().size(); iii++) {
				if(map.getEdges().get(iii).getOwner() == players.get(0)) {
						g2d.setColor(Color.RED);
						g2d.drawLine(map.getEdge(iii).getNearbyNodes().get(0).getXpix(), map.getEdge(iii).getNearbyNodes().get(0).getYpix(), map.getEdge(iii).getNearbyNodes().get(1).getXpix(), map.getEdge(iii).getNearbyNodes().get(1).getYpix());
				}
				if(map.getEdges().get(iii).getOwner() == players.get(1)) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(map.getEdge(iii).getNearbyNodes().get(0).getXpix(), map.getEdge(iii).getNearbyNodes().get(0).getYpix(), map.getEdge(iii).getNearbyNodes().get(1).getXpix(), map.getEdge(iii).getNearbyNodes().get(1).getYpix());
				}
				if(players.size() > 2) {
					if(map.getEdges().get(iii).getOwner() == players.get(2)) {
						g2d.setColor(Color.ORANGE);
						g2d.drawLine(map.getEdge(iii).getNearbyNodes().get(0).getXpix(), map.getEdge(iii).getNearbyNodes().get(0).getYpix(), map.getEdge(iii).getNearbyNodes().get(1).getXpix(), map.getEdge(iii).getNearbyNodes().get(1).getYpix());
					}
					
				}
				
				if(players.size() > 3) {
					if(map.getEdges().get(iii).getOwner() == players.get(3)) {
						g2d.setColor(Color.WHITE);
						g2d.drawLine(map.getEdge(iii).getNearbyNodes().get(0).getXpix(), map.getEdge(iii).getNearbyNodes().get(0).getYpix(), map.getEdge(iii).getNearbyNodes().get(1).getXpix(), map.getEdge(iii).getNearbyNodes().get(1).getYpix());
					}
					
				}
				
				
			}
			//draw settlements
			for(int ii = 0; ii < map.getNodes().size(); ii++) {
				if(map.getNodes().get(ii).getOwner() == players.get(0)) {
						g2d.setColor(Color.RED);
						g2d.fillOval((map.getNodes().get(ii).getXpix() - 5),(map.getNodes().get(ii).getYpix() - 5),10,10);
						if(map.getNodes().get(ii).getStatus().compareTo("c")== 0) {
							g2d.drawArc((map.getNodes().get(ii).getXpix() - 10),(map.getNodes().get(ii).getYpix() - 10), 20, 20, 0, 360);
						}
				}
				if(map.getNodes().get(ii).getOwner() == players.get(1)) {
						g2d.setColor(Color.BLUE);
						g2d.fillOval((map.getNodes().get(ii).getXpix() - 5),(map.getNodes().get(ii).getYpix() - 5),10,10);
						if(map.getNodes().get(ii).getStatus().compareTo("c")== 0) {
							g2d.drawArc((map.getNodes().get(ii).getXpix() - 10),(map.getNodes().get(ii).getYpix() - 10), 20, 20, 0, 360);
						}
				}
				
				if(players.size() > 2) {
					if(map.getNodes().get(ii).getOwner() == players.get(2)) {
							g2d.setColor(Color.ORANGE);
							g2d.fillOval((map.getNodes().get(ii).getXpix() - 5),(map.getNodes().get(ii).getYpix() - 5),10,10);
							if(map.getNodes().get(ii).getStatus().compareTo("c")== 0) {
								g2d.drawArc((map.getNodes().get(ii).getXpix() - 10),(map.getNodes().get(ii).getYpix() - 10), 20, 20, 0, 360);
							}
					}
				}
				
				if(players.size() > 3) {
					if(map.getNodes().get(ii).getOwner() == players.get(3)) {
							g2d.setColor(Color.WHITE);
							g2d.fillOval((map.getNodes().get(ii).getXpix() - 5),(map.getNodes().get(ii).getYpix() - 5),10,10);
							if(map.getNodes().get(ii).getStatus().compareTo("c")== 0) {
								g2d.drawArc((map.getNodes().get(ii).getXpix() - 10),(map.getNodes().get(ii).getYpix() - 10), 20, 20, 0, 360);
							}
					}
				}
				
			}
			
			
			
			/*test code///////////////////////////////////////////////
			System.out.println("node 3 is at X: " + map.getNodes().get(3).getXpix() + "and Y: " +map.getNodes().get(3).getYpix());
			System.out.println("node 0 is at X: " + map.getNodes().get(0).getXpix() + "and Y: " +map.getNodes().get(0).getYpix());
			System.out.println("node 4 is at X: " + map.getNodes().get(4).getXpix() + "and Y: " +map.getNodes().get(4).getYpix());
			System.out.println("node 8 is at X: " + map.getNodes().get(8).getXpix() + "and Y: " +map.getNodes().get(8).getYpix());
			System.out.println("node 12 is at X: " + map.getNodes().get(12).getXpix() + "and Y:" +map.getNodes().get(12).getYpix());
			System.out.println("node 7 is at X: " + map.getNodes().get(7).getXpix() + "and Y: " +map.getNodes().get(7).getYpix());

			//////////////////////////*/
		}

		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				//int count = 0;
				//int reverse = 0;
				Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
			/*	if (p.x < 0 || p.y < 0 || p.x >= 5 || p.y >= 5) {
					return;
				}*/
				if((p.x == 0 && p.y > 0 && p.y < 4) || (p.x == 1 && p.y < 4) || (p.x == 2) || (p.x == 3 && p.y < 4)
							|| (p.x == 4 && p.y > 0 && p.y < 4)) {
					
				
				System.out.println(p.x + " " + p.y);
				// need to open up a new tab in here
				//for(int i = 0; i < playernumber; i++) {
				//buildHexOptions(p.x, p.y, currentPlayer); //currentPlayer hex options
				repaint();
				//Will need to add cases in which this is not the player
				//if statement game start == 0
			
				
				
				
			
				if(moveRobby ==0) {
				if(gameStart == 0) {	
				buildHexOptions(p.x, p.y, currentPlayer); 
				if(currentPlayer < (playernumber -1) && countIntial == 0 && initialOK == 1) {//countIntial reverses player order
				currentPlayer++;
				initialOK = 0;
				}
				else if( currentPlayer == 0 && countIntial == 1 && initialOK == 1) {//currentPlayer stage ends when the count goes back to the first player
					//currentPlayerHex.setVisible(true);
					PlayerScreen();
					System.out.println("END");
					gameStart = 1;
					initialOK = 0;
					//game start = 1
				}
				else if(currentPlayer == playernumber -1 && countIntial == 0 && initialOK == 1) {
					countIntial = 1;
					initialOK = 0;
				}
				else if(initialOK == 1) {
					currentPlayer--; 
					initialOK = 0;
					//countIntial = 1;  //if the count has hit the end of the player array, go backwards
				}
				}
				else if (buildcount != 0) {
					buildBuildOptions(p.x,p.y,currentPlayer);
					System.out.println(buildcount);
				}
				else {
					System.out.println(buildcount);
				buildPlayerOptions(p.x,p.y, currentPlayer);	
					
				}
			}
				else {
					MoveRobber(p.x,p.y,currentPlayer);
					moveRobby = 0;
				}
				
			repaint();
				//}
			}
				else {return;}	
		}
		}
		
	}

	private void buildHexOptions(int x, int y, int playerinarray) {// need to associate p.x and p.y to the hexes
		
		//FindHex(x,y);
		
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
		JPanel Confirm = new JPanel();
		Confirm.add(new JLabel("Go again"));
		
		int result = JOptionPane.showConfirmDialog(null, HexoptionsScreen,players.get(playerinarray).getName() + "'scurrentPlayer Settlement", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION){
			//make sure the road is next to the node
			//map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0);
			int success = 0;
		for(int i = 0; i < 6; i++) {	
			if(nodes.getSelectedItem() == Nodes[i] && map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getStatus() == "a") {
				System.out.println(players.get(playerinarray).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i)));
				if (countIntial == 1 ){
					for(int j = 0; j < map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getNearbyHexes().size(); j++) {
				players.get(playerinarray).addResources(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getNearbyHexes().get(j).getResourceType(), 1);
				//success = 1;
				}
				//	success = 1;
			}
				success = 1;
			}
		}	if(success == 0) {
			
			JOptionPane.showMessageDialog(null, Confirm, "Invaid Settlement" ,JOptionPane.PLAIN_MESSAGE );
		}
		
		
			if(edges.getSelectedItem() == Edges[0] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(0)));
				success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[1] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(1)));
				success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[2] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(2)));
				success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[3] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(3)));
				success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[4] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(4)));
				success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[5] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(5)));
				success = 0;
				initialOK = 1;
			}
		if(initialOK == 0 && success ==1) {
			players.get(playerinarray).getNodes().remove(players.get(playerinarray).getNodes().size() -1);
			JOptionPane.showMessageDialog(null, Confirm, "Invaid Road" ,JOptionPane.PLAIN_MESSAGE );
		}
			
			
		}
		
	//	System.out.println(players.get(0).buildStartingSettlement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(0)));//build an initial settlement in the top right position
		//System.out.println();
	}

	private void buildPlayerOptions(int x, int y, int playerinarray) {
		PlayeroptionsScreen = new JPanel();// create the panel that allows selections around the hex
		PlayeroptionsScreen .setLayout(new GridLayout(0,4 ));
		String Edges[] = { "top", "top right", "top left", "bottom right", "bottom left","bottom" };
		String Nodes[] = { "top right", "top left", "middle right", " middle left", "bottom right", "bottom left" };
		//String name;
		//nodes = new JComboBox(Nodes);
		//edges = new JComboBox(Edges);
		for(int i =0; i < 6; i++) {
				PlayeroptionsScreen.add(new JLabel(Nodes[i] + ":")); 
			if(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getStatus() == "na" ) {
				PlayeroptionsScreen.add(new JLabel("Too close to another settlement")); 
			}
			else if(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getStatus() == "a") {
				PlayeroptionsScreen.add(new JLabel("Available")); 
			}
			else {
				PlayeroptionsScreen.add(new JLabel( map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getOwner().getName() + "'s " +  nodePlacement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i))));
			}		
			PlayeroptionsScreen.add(new JLabel(Edges[i] + ":")); 
			if(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(i).getStatus() == "na" ) {
				PlayeroptionsScreen.add(new JLabel(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(i).getOwner().getName() + "'s Road")); 
			}
			else if(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(i).getStatus() == "a") {
				PlayeroptionsScreen.add(new JLabel("Available")); 
			}
			
		}
		
		
		
		
		int result = JOptionPane.showConfirmDialog(null, PlayeroptionsScreen,map.getHex(FindHex(x,y)).getResourceType() + " " + map.getHex(FindHex(x,y)).getDiceNumber(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		//PlayeroptionsScreen.add(new JLabel("Top Settlement:" + map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getOwner().getName()  ));}
		/*PlayeroptionsScreen.add(new JLabel( map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i).getOwner().getName() + "'s " +  nodePlacement(map.getHexes().get(FindHex(x, y)).getNearbyNodes().get(i))));
		PlayeroptionsScreen.add(new JLabel("Road Placement:"), BorderLayout.WEST);
		PlayeroptionsScreen.add(edges,BorderLayout.EAST);
		}
	*/
		
	}
	
	private String nodePlacement(Node n) {
		String node = "";
		if(n.getStatus() == "a") {
			node = "Available";
		}
		else if(n.getStatus() == "na") {
			node = "Not Available";
		}
		else if(n.getStatus() == "s") {
			node = "Settlement";
		}
		else if(n.getStatus() == "c") {
			node = "City";
		}
		
		return node;
	}
	private int FindHex(int x, int y) {
		for (int i = 0; i < map.getHexes().size(); i++) {
			if(x == map.getHexes().get(i).getX() && y == map.getHexes().get(i).getY()) {
				return i;
			}
		}

		return 20;
	}
	
	
	private void buildBuildOptions( int x, int y, int playerinarray) {
		JPanel Buildoptions = new JPanel();
		String Edges[] = { "top", "top right", "top left", "bottom right", "bottom left","bottom" };
		String Nodes[] = { "top right", "top left", "middle right", " middle left", "bottom right", "bottom left" };
		
		 top			= new JButton("Top");
		 topRight 	= new JButton("Top Right");
		 topLeft		= new JButton("Top Left");
		 bottomRight	= new JButton("Bottom Right");
		 bottomLeft	= new JButton("Bottom Left");	
		 bottom		= new JButton("Bottom");
		 middleRight	= new JButton("Middle Right");
	     middleLeft	= new JButton("Middle Left");
	    tempX = x;
	    tempY = y;
	
		top.addActionListener(new MenuListener());
		topRight.addActionListener(new MenuListener());
		topLeft.addActionListener(new MenuListener());
		bottomRight.addActionListener(new MenuListener());
		bottomLeft.addActionListener(new MenuListener());
		bottom.addActionListener(new MenuListener());
		middleRight.addActionListener(new MenuListener());
		middleLeft.addActionListener(new MenuListener());
		
		
		if(buildcount ==1 ) {
			Buildoptions.setLayout(new GridLayout(0,3));
			
			Buildoptions.add(topLeft);
			Buildoptions.add(top);	
			Buildoptions.add(topRight);
			Buildoptions.add(bottomLeft);
			Buildoptions.add(bottom);
			Buildoptions.add(bottomRight);
			int result = JOptionPane.showConfirmDialog(null, Buildoptions, "Add Road to " + map.getHex(FindHex(x,y)).getResourceType() + " " + map.getHex(FindHex(x,y)).getDiceNumber() , JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION) {
				
			}
			buildcount = 0;
			
		}
		else if(buildcount == 2) {
			Buildoptions.setLayout(new GridLayout(0,2));	
			
			Buildoptions.add(topLeft);
			Buildoptions.add(topRight);
			Buildoptions.add(middleRight);
			Buildoptions.add(middleLeft);
			Buildoptions.add(bottomLeft);
			Buildoptions.add(bottomRight);
			int result = JOptionPane.showConfirmDialog(null, Buildoptions, "Add Settlement" + map.getHex(FindHex(x,y)).getResourceType() + " " + map.getHex(FindHex(x,y)).getDiceNumber() , JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			{
				
			}
			buildcount = 0;
			
		}
		else if(buildcount == 3) {
			Buildoptions.setLayout(new GridLayout(0,2));
			
			Buildoptions.add(topLeft);
			Buildoptions.add(topRight);
			Buildoptions.add(middleRight);
			Buildoptions.add(middleLeft);
			Buildoptions.add(bottomLeft);
			Buildoptions.add(bottomRight);
			int result = JOptionPane.showConfirmDialog(null, Buildoptions, "Add City" + map.getHex(FindHex(x,y)).getResourceType() + " " + map.getHex(FindHex(x,y)).getDiceNumber() , JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				
			}
			buildcount = 0;
			
			}
			
		
	
		buildcount = 0;
		
		
	
	}

	private void diceScreen(){
		int dice = Start.DiceRoll();
		
	if( dice == 7) {
		JPanel MoveRobber = new JPanel();
		moveRobby = 1;
		MoveRobber.add(new JLabel(" A 7 was rolled! Move the robber to continue your turn"));
		JOptionPane.showMessageDialog(null, MoveRobber, players.get(currentPlayer).getName() + " move the Robber", JOptionPane.PLAIN_MESSAGE);
	}
	else {
		JPanel Roll = new JPanel();
		Roll.add(new JLabel("A " + dice +" was rolled!"));
		JOptionPane.showMessageDialog(null, Roll, "DiceRoll", JOptionPane.PLAIN_MESSAGE);
		for(int i = 0; i < 19; i++) {
			//got through map map.getHexes().get(i).getDiceNumber() == dice then go map.getHexes().get(i).get
			if(map.getHexes().get(i).getDiceNumber() == dice) {//for all hexes with the dice number
				for(int j = 0; j < 6; j++) {//all the nodes
					if(map.getHexes().get(i).getNearbyNodes().get(j).getOwner() != null && map.getHexes().get(i).getRobberStatus() == false) {//if the nodes have players and the hex doesn't have the robber
						if(map.getHexes().get(i).getNearbyNodes().get(j).getStatus() == "c") {
							map.getHexes().get(i).getNearbyNodes().get(j).getOwner().addResources(map.getHexes().get(i).getResourceType(), 2);
						}
						else {
							map.getHexes().get(i).getNearbyNodes().get(j).getOwner().addResources(map.getHexes().get(i).getResourceType(), 1);
						}
						
					}
				}
			}
		}	
	}
	
	}  
	
	private void MoveRobber(int x, int y, int playerinarray) {
		JPanel Robber = new JPanel();
		for(int i = 0; i < 19; i++) {
			if(map.getHexes().get(i).getRobberStatus() == true) {
				map.getHexes().get(i).setRobberStatus(false); 
			}
		}
		
		map.getHexes().get( FindHex( x, y)).setRobberStatus(true);
		
		moveRobby = 0;
		
	}

	
	
	
}
