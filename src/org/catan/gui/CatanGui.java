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
	private JMenuItem optionVictoryPts;
	private JMenuItem optionRoad;
	private JMenuItem optionSettlement;
	private JMenuItem optionCity;
	//private JMenuItem optionCheckCards;
	
	private JButton tradeWithP1;
	private JButton tradeWithP2;
	private JButton tradeWithP3;
	private JButton tradeWithP4;
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
	private JComboBox<String> playerRes;
	private JComboBox<String> bankRes;
	private JComboBox<String> harborRes;
	private JComboBox<String> harborCB;
	String resourceList[] = { "brick", "lumber", "grain", "wool", "ore" };
	String harborList[] = { "Harbor 1", "Harbor 2", "Harbor 3", "Harbor 4", "Harbor 5", "Harbor 6", "Harbor 7", "Harbor 8", "Harbor 9", };
	private int playernumber;
	private ArrayList<Player> players;
	
	private JFrame playerTrade;
	
	private JPanel startScreen;
	private JPanel HexoptionsScreen;
	private JPanel PlayeroptionsScreen;
	private JPanel resourcePanel;
	private JPanel victoryPointsPanel;
	private JPanel bankTrade;
	private JPanel harborTrade;
	private JPanel confirmPanel;
	
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
			else if (e.getSource() == tradeWithP1) {
				playerTrade.dispose();
				
				JPanel p1TradeDeal = new JPanel();
				JPanel playerResLeft = new JPanel();
				JPanel playerResRight = new JPanel();
				p1TradeDeal.setLayout(new GridLayout(0, 2));
				playerResLeft.setLayout(new GridLayout(0, 2, 5, 10));
				playerResLeft.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				playerResRight.setLayout(new GridLayout(0, 2, 5, 10));
				playerResRight.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
				JTextField pcBrick = new JTextField("0", 3);
				JTextField pcLumber = new JTextField("0", 3);
				JTextField pcGrain = new JTextField("0", 3);
				JTextField pcWool = new JTextField("0", 3);
				JTextField pcOre = new JTextField("0", 3);
				JTextField p1Brick = new JTextField("0", 3);
				JTextField p1Lumber = new JTextField("0", 3);
				JTextField p1Grain = new JTextField("0", 3);
				JTextField p1Wool = new JTextField("0", 3);
				JTextField p1Ore = new JTextField("0", 3);
				
				playerResLeft.add(new JLabel("Which Resources?"));
				playerResLeft.add(new JLabel("Trade how much?"));
				playerResLeft.add(new JLabel("Brick (You have " + players.get(currentPlayer).getResources("brick") + "):"));
				playerResLeft.add(pcBrick);
				playerResLeft.add(new JLabel("Lumber (You have " + players.get(currentPlayer).getResources("lumber") + "):"));
				playerResLeft.add(pcLumber);
				playerResLeft.add(new JLabel("Grain (You have " + players.get(currentPlayer).getResources("grain") + "):"));
				playerResLeft.add(pcGrain);
				playerResLeft.add(new JLabel("Wool (You have " + players.get(currentPlayer).getResources("wool") + "):"));
				playerResLeft.add(pcWool);
				playerResLeft.add(new JLabel("Ore (You have " + players.get(currentPlayer).getResources("ore") + "):"));
				playerResLeft.add(pcOre);
				playerResRight.add(new JLabel("Which Resources?"));
				playerResRight.add(new JLabel("Trade how much?"));
				playerResRight.add(new JLabel("Brick (You have " + players.get(0).getResources("brick") + "):"));
				playerResRight.add(p1Brick);
				playerResRight.add(new JLabel("Lumber (You have " + players.get(0).getResources("lumber") + "):"));
				playerResRight.add(p1Lumber);
				playerResRight.add(new JLabel("Grain (You have " + players.get(0).getResources("grain") + "):"));
				playerResRight.add(p1Grain);
				playerResRight.add(new JLabel("Wool (You have " + players.get(0).getResources("wool") + "):"));
				playerResRight.add(p1Wool);
				playerResRight.add(new JLabel("Ore (You have " + players.get(0).getResources("ore") + "):"));
				playerResRight.add(p1Ore);
				
				p1TradeDeal.add(new JLabel(players.get(currentPlayer).getName() + "'s Current Resources:"));
				p1TradeDeal.add(new JLabel(players.get(0).getName() + "'s Current Resources:"));
				p1TradeDeal.add(playerResLeft);
				p1TradeDeal.add(playerResRight);
				
				int result1 = JOptionPane.showConfirmDialog(null, p1TradeDeal, "Make a Trade Deal with " + players.get(0).getName() + "!", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				int[] pcTradeList = { Integer.parseInt(pcBrick.getText()),
						Integer.parseInt(pcLumber.getText()),
						Integer.parseInt(pcGrain.getText()),
						Integer.parseInt(pcWool.getText()),
						Integer.parseInt(pcOre.getText()) };
				int[] p1TradeList = { Integer.parseInt(p1Brick.getText()),
						Integer.parseInt(p1Lumber.getText()),
						Integer.parseInt(p1Grain.getText()),
						Integer.parseInt(p1Wool.getText()),
						Integer.parseInt(p1Ore.getText()) };
				
				if (result1 == JOptionPane.OK_OPTION) {
					confirmPanel = new JPanel();
					confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(players.get(0), pcTradeList, p1TradeList)));
					JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
				}
			}
			else if (e.getSource() == tradeWithP2) {
				playerTrade.dispose();
				
				JPanel p2TradeDeal = new JPanel();
				JPanel playerResLeft = new JPanel();
				JPanel playerResRight = new JPanel();
				p2TradeDeal.setLayout(new GridLayout(0, 2));
				playerResLeft.setLayout(new GridLayout(0, 2, 5, 10));
				playerResLeft.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				playerResRight.setLayout(new GridLayout(0, 2, 5, 10));
				playerResRight.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
				JTextField pcBrick = new JTextField("0", 3);
				JTextField pcLumber = new JTextField("0", 3);
				JTextField pcGrain = new JTextField("0", 3);
				JTextField pcWool = new JTextField("0", 3);
				JTextField pcOre = new JTextField("0", 3);
				JTextField p2Brick = new JTextField("0", 3);
				JTextField p2Lumber = new JTextField("0", 3);
				JTextField p2Grain = new JTextField("0", 3);
				JTextField p2Wool = new JTextField("0", 3);
				JTextField p2Ore = new JTextField("0", 3);
				
				playerResLeft.add(new JLabel("Which Resources?"));
				playerResLeft.add(new JLabel("Trade how much?"));
				playerResLeft.add(new JLabel("Brick (You have " + players.get(currentPlayer).getResources("brick") + "):"));
				playerResLeft.add(pcBrick);
				playerResLeft.add(new JLabel("Lumber (You have " + players.get(currentPlayer).getResources("lumber") + "):"));
				playerResLeft.add(pcLumber);
				playerResLeft.add(new JLabel("Grain (You have " + players.get(currentPlayer).getResources("grain") + "):"));
				playerResLeft.add(pcGrain);
				playerResLeft.add(new JLabel("Wool (You have " + players.get(currentPlayer).getResources("wool") + "):"));
				playerResLeft.add(pcWool);
				playerResLeft.add(new JLabel("Ore (You have " + players.get(currentPlayer).getResources("ore") + "):"));
				playerResLeft.add(pcOre);
				playerResRight.add(new JLabel("Which Resources?"));
				playerResRight.add(new JLabel("Trade how much?"));
				playerResRight.add(new JLabel("Brick (You have " + players.get(1).getResources("brick") + "):"));
				playerResRight.add(p2Brick);
				playerResRight.add(new JLabel("Lumber (You have " + players.get(1).getResources("lumber") + "):"));
				playerResRight.add(p2Lumber);
				playerResRight.add(new JLabel("Grain (You have " + players.get(1).getResources("grain") + "):"));
				playerResRight.add(p2Grain);
				playerResRight.add(new JLabel("Wool (You have " + players.get(1).getResources("wool") + "):"));
				playerResRight.add(p2Wool);
				playerResRight.add(new JLabel("Ore (You have " + players.get(1).getResources("ore") + "):"));
				playerResRight.add(p2Ore);
				
				p2TradeDeal.add(new JLabel(players.get(currentPlayer).getName() + "'s Current Resources:"));
				p2TradeDeal.add(new JLabel(players.get(1).getName() + "'s Current Resources:"));
				p2TradeDeal.add(playerResLeft);
				p2TradeDeal.add(playerResRight);
				
				int result2 = JOptionPane.showConfirmDialog(null, p2TradeDeal, "Make a Trade Deal with " + players.get(1).getName() + "!", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				int[] pcTradeList = { Integer.parseInt(pcBrick.getText()),
						Integer.parseInt(pcLumber.getText()),
						Integer.parseInt(pcGrain.getText()),
						Integer.parseInt(pcWool.getText()),
						Integer.parseInt(pcOre.getText()) };
				int[] p2TradeList = { Integer.parseInt(p2Brick.getText()),
						Integer.parseInt(p2Lumber.getText()),
						Integer.parseInt(p2Grain.getText()),
						Integer.parseInt(p2Wool.getText()),
						Integer.parseInt(p2Ore.getText()) };
				
				if (result2 == JOptionPane.OK_OPTION) {
					confirmPanel = new JPanel();
					confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(players.get(1), pcTradeList, p2TradeList)));
					JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
				}				
			}
			else if (e.getSource() == tradeWithP3) {
				playerTrade.dispose();
				
				JPanel p3TradeDeal = new JPanel();
				JPanel playerResLeft = new JPanel();
				JPanel playerResRight = new JPanel();
				p3TradeDeal.setLayout(new GridLayout(0, 2));
				playerResLeft.setLayout(new GridLayout(0, 2, 5, 10));
				playerResLeft.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				playerResRight.setLayout(new GridLayout(0, 2, 5, 10));
				playerResRight.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
				JTextField pcBrick = new JTextField("0", 3);
				JTextField pcLumber = new JTextField("0", 3);
				JTextField pcGrain = new JTextField("0", 3);
				JTextField pcWool = new JTextField("0", 3);
				JTextField pcOre = new JTextField("0", 3);
				JTextField p3Brick = new JTextField("0", 3);
				JTextField p3Lumber = new JTextField("0", 3);
				JTextField p3Grain = new JTextField("0", 3);
				JTextField p3Wool = new JTextField("0", 3);
				JTextField p3Ore = new JTextField("0", 3);
				
				playerResLeft.add(new JLabel("Which Resources?"));
				playerResLeft.add(new JLabel("Trade how much?"));
				playerResLeft.add(new JLabel("Brick (You have " + players.get(currentPlayer).getResources("brick") + "):"));
				playerResLeft.add(pcBrick);
				playerResLeft.add(new JLabel("Lumber (You have " + players.get(currentPlayer).getResources("lumber") + "):"));
				playerResLeft.add(pcLumber);
				playerResLeft.add(new JLabel("Grain (You have " + players.get(currentPlayer).getResources("grain") + "):"));
				playerResLeft.add(pcGrain);
				playerResLeft.add(new JLabel("Wool (You have " + players.get(currentPlayer).getResources("wool") + "):"));
				playerResLeft.add(pcWool);
				playerResLeft.add(new JLabel("Ore (You have " + players.get(currentPlayer).getResources("ore") + "):"));
				playerResLeft.add(pcOre);
				playerResRight.add(new JLabel("Which Resources?"));
				playerResRight.add(new JLabel("Trade how much?"));
				playerResRight.add(new JLabel("Brick (You have " + players.get(2).getResources("brick") + "):"));
				playerResRight.add(p3Brick);
				playerResRight.add(new JLabel("Lumber (You have " + players.get(2).getResources("lumber") + "):"));
				playerResRight.add(p3Lumber);
				playerResRight.add(new JLabel("Grain (You have " + players.get(2).getResources("grain") + "):"));
				playerResRight.add(p3Grain);
				playerResRight.add(new JLabel("Wool (You have " + players.get(2).getResources("wool") + "):"));
				playerResRight.add(p3Wool);
				playerResRight.add(new JLabel("Ore (You have " + players.get(2).getResources("ore") + "):"));
				playerResRight.add(p3Ore);
				
				p3TradeDeal.add(new JLabel(players.get(currentPlayer).getName() + "'s Current Resources:"));
				p3TradeDeal.add(new JLabel(players.get(2).getName() + "'s Current Resources:"));
				p3TradeDeal.add(playerResLeft);
				p3TradeDeal.add(playerResRight);
				
				int result3 = JOptionPane.showConfirmDialog(null, p3TradeDeal, "Make a Trade Deal with " + players.get(2).getName() + "!", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				int[] pcTradeList = { Integer.parseInt(pcBrick.getText()),
						Integer.parseInt(pcLumber.getText()),
						Integer.parseInt(pcGrain.getText()),
						Integer.parseInt(pcWool.getText()),
						Integer.parseInt(pcOre.getText()) };
				int[] p3TradeList = { Integer.parseInt(p3Brick.getText()),
						Integer.parseInt(p3Lumber.getText()),
						Integer.parseInt(p3Grain.getText()),
						Integer.parseInt(p3Wool.getText()),
						Integer.parseInt(p3Ore.getText()) };
				
				if (result3 == JOptionPane.OK_OPTION) {
					confirmPanel = new JPanel();
					confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(players.get(2), pcTradeList, p3TradeList)));
					JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
				}					
			}
			else if (e.getSource() == tradeWithP4) {
				playerTrade.dispose();
				
				JPanel p4TradeDeal = new JPanel();
				JPanel playerResLeft = new JPanel();
				JPanel playerResRight = new JPanel();
				p4TradeDeal.setLayout(new GridLayout(0, 2));
				playerResLeft.setLayout(new GridLayout(0, 2, 5, 10));
				playerResLeft.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				playerResRight.setLayout(new GridLayout(0, 2, 5, 10));
				playerResRight.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
				JTextField pcBrick = new JTextField("0", 3);
				JTextField pcLumber = new JTextField("0", 3);
				JTextField pcGrain = new JTextField("0", 3);
				JTextField pcWool = new JTextField("0", 3);
				JTextField pcOre = new JTextField("0", 3);
				JTextField p4Brick = new JTextField("0", 3);
				JTextField p4Lumber = new JTextField("0", 3);
				JTextField p4Grain = new JTextField("0", 3);
				JTextField p4Wool = new JTextField("0", 3);
				JTextField p4Ore = new JTextField("0", 3);
				
				playerResLeft.add(new JLabel("Which Resources?"));
				playerResLeft.add(new JLabel("Trade how much?"));
				playerResLeft.add(new JLabel("Brick (You have " + players.get(currentPlayer).getResources("brick") + "):"));
				playerResLeft.add(pcBrick);
				playerResLeft.add(new JLabel("Lumber (You have " + players.get(currentPlayer).getResources("lumber") + "):"));
				playerResLeft.add(pcLumber);
				playerResLeft.add(new JLabel("Grain (You have " + players.get(currentPlayer).getResources("grain") + "):"));
				playerResLeft.add(pcGrain);
				playerResLeft.add(new JLabel("Wool (You have " + players.get(currentPlayer).getResources("wool") + "):"));
				playerResLeft.add(pcWool);
				playerResLeft.add(new JLabel("Ore (You have " + players.get(currentPlayer).getResources("ore") + "):"));
				playerResLeft.add(pcOre);
				playerResRight.add(new JLabel("Which Resources?"));
				playerResRight.add(new JLabel("Trade how much?"));
				playerResRight.add(new JLabel("Brick (You have " + players.get(3).getResources("brick") + "):"));
				playerResRight.add(p4Brick);
				playerResRight.add(new JLabel("Lumber (You have " + players.get(3).getResources("lumber") + "):"));
				playerResRight.add(p4Lumber);
				playerResRight.add(new JLabel("Grain (You have " + players.get(3).getResources("grain") + "):"));
				playerResRight.add(p4Grain);
				playerResRight.add(new JLabel("Wool (You have " + players.get(3).getResources("wool") + "):"));
				playerResRight.add(p4Wool);
				playerResRight.add(new JLabel("Ore (You have " + players.get(3).getResources("ore") + "):"));
				playerResRight.add(p4Ore);
				
				p4TradeDeal.add(new JLabel(players.get(currentPlayer).getName() + "'s Current Resources:"));
				p4TradeDeal.add(new JLabel(players.get(3).getName() + "'s Current Resources:"));
				p4TradeDeal.add(playerResLeft);
				p4TradeDeal.add(playerResRight);
				
				int result4 = JOptionPane.showConfirmDialog(null, p4TradeDeal, "Make a Trade Deal with " + players.get(3).getName() + "!", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				int[] pcTradeList = { Integer.parseInt(pcBrick.getText()),
						Integer.parseInt(pcLumber.getText()),
						Integer.parseInt(pcGrain.getText()),
						Integer.parseInt(pcWool.getText()),
						Integer.parseInt(pcOre.getText()) };
				int[] p4TradeList = { Integer.parseInt(p4Brick.getText()),
						Integer.parseInt(p4Lumber.getText()),
						Integer.parseInt(p4Grain.getText()),
						Integer.parseInt(p4Wool.getText()),
						Integer.parseInt(p4Ore.getText()) };
				
				if (result4 == JOptionPane.OK_OPTION) {
					confirmPanel = new JPanel();
					confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(players.get(3), pcTradeList, p4TradeList)));
					JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
				}					
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
			else if (e.getSource() == optionVictoryPts) {
				handleVictoryPts();
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
			//	diceScreen();
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

			boolean noBlankNames = false;
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
			
			while(noBlankNames == false) {
				//must initially set noBlankNames to true so that OK_CANCEL works
				noBlankNames = true;
				int result = JOptionPane.showConfirmDialog(null, myPanel, "Set Names", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
				
					if (playernumber > 1) {
						if((player1.getText().compareTo("")== 0) || (player2.getText().compareTo("")== 0)) {
							noBlankNames = false;
						}
						else {
							noBlankNames = true;
							playernames.add(player1.getText());
							playernames.add(player2.getText());
						}

						
					}
					if (playernumber > 2) {
						if((player3.getText().compareTo("")== 0)) {
							noBlankNames = false;
						}
						else {
							noBlankNames = true;
							playernames.add(player3.getText());
						}
					
						
					}
					if (playernumber > 3) {
						if((player4.getText().compareTo("")== 0)) {
							noBlankNames = false;
						}
						else {
							noBlankNames = true;
							playernames.add(player4.getText());
						}
						
					}
					if(noBlankNames == true) {//if we finally get names, make the map
						players = Start.createPlayers(playernumber, playernames);// returns arraylist of players
						if (players.size() == playernumber) {
							startScreen.setVisible(false);
							currentPlayerbuildScreen();
						}
					}
				}//end of if (OK_OPTION)
			}//end of while(noBlankNames == false)
			
		}
		
		private void handlePlayerTrade() {
			playerTrade = new JFrame("Trade with another player!");
			playerTrade.setSize(200 * players.size(), 250);
			playerTrade.setLayout(new GridLayout(0, players.size()));
			playerTrade.setVisible(true);
			
			JLabel p1Resources = new JLabel();
			JLabel p2Resources = new JLabel();
			JLabel p3Resources = new JLabel();
			JLabel p4Resources = new JLabel();
			if (players.size() >= 2) {
				p1Resources.setLayout(new GridLayout(0, 1));
				p1Resources.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				p1Resources.add(new JLabel(players.get(0).getName() + "'s Resources:"));
				p1Resources.add(new JLabel("Brick: " + players.get(0).getResources("brick")));
				p1Resources.add(new JLabel("Lumber: " + players.get(0).getResources("lumber")));
				p1Resources.add(new JLabel("Grain: " + players.get(0).getResources("grain")));
				p1Resources.add(new JLabel("Wool: " + players.get(0).getResources("wool")));
				p1Resources.add(new JLabel("Ore: " + players.get(0).getResources("ore")));	
				tradeWithP1 = new JButton("Trade with " + players.get(0).getName());
				tradeWithP1.addActionListener(new MenuListener());
				p1Resources.add(tradeWithP1);
				if (currentPlayer == 0) {
					tradeWithP1.setEnabled(false);
					tradeWithP1.setVisible(false);
				}
				playerTrade.add(p1Resources);	
				
				p2Resources.setLayout(new GridLayout(0, 1));
				p2Resources.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				p2Resources.add(new JLabel(players.get(1).getName() + "'s Resources:"));
				p2Resources.add(new JLabel("Brick: " + players.get(1).getResources("brick")));
				p2Resources.add(new JLabel("Lumber: " + players.get(1).getResources("lumber")));
				p2Resources.add(new JLabel("Grain: " + players.get(1).getResources("grain")));
				p2Resources.add(new JLabel("Wool: " + players.get(1).getResources("wool")));
				p2Resources.add(new JLabel("Ore: " + players.get(1).getResources("ore")));
				tradeWithP2 = new JButton("Trade with " + players.get(1).getName());
				tradeWithP2.addActionListener(new MenuListener());
				p2Resources.add(tradeWithP2);
				if (currentPlayer == 1) {
					tradeWithP2.setEnabled(false);
					tradeWithP2.setVisible(false);					
				}
				playerTrade.add(p2Resources);
			}
			if (players.size() >= 3) {
				p3Resources.setLayout(new GridLayout(0, 1));
				p3Resources.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				p3Resources.add(new JLabel(players.get(2).getName() + "'s Resources:"));
				p3Resources.add(new JLabel("Brick: " + players.get(2).getResources("brick")));
				p3Resources.add(new JLabel("Lumber: " + players.get(2).getResources("lumber")));
				p3Resources.add(new JLabel("Grain: " + players.get(2).getResources("grain")));
				p3Resources.add(new JLabel("Wool: " + players.get(2).getResources("wool")));
				p3Resources.add(new JLabel("Ore: " + players.get(2).getResources("ore")));
				tradeWithP3 = new JButton("Trade with " + players.get(2).getName());
				tradeWithP3.addActionListener(new MenuListener());
				p3Resources.add(tradeWithP3);
				if (currentPlayer == 2) {
					tradeWithP3.setEnabled(false);
					tradeWithP3.setVisible(false);					
				}
				playerTrade.add(p3Resources);
			}
			if (players.size() == 4) {
				p4Resources.setLayout(new GridLayout(0, 1));
				p4Resources.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				p4Resources.add(new JLabel(players.get(3).getName() + "'s Resources:"));
				p4Resources.add(new JLabel("Brick: " + players.get(3).getResources("brick")));
				p4Resources.add(new JLabel("Lumber: " + players.get(3).getResources("lumber")));
				p4Resources.add(new JLabel("Grain: " + players.get(3).getResources("grain")));
				p4Resources.add(new JLabel("Wool: " + players.get(3).getResources("wool")));
				p4Resources.add(new JLabel("Ore: " + players.get(3).getResources("ore")));
				tradeWithP4 = new JButton("Trade with " + players.get(3).getName());
				tradeWithP4.addActionListener(new MenuListener());
				p4Resources.add(tradeWithP4);
				if (currentPlayer == 3) {
					tradeWithP4.setEnabled(false);
					tradeWithP4.setVisible(false);					
				}
				playerTrade.add(p4Resources);
			}
		}
		
		private void handleBankTrade() {
			bankTrade = new JPanel();
			bankTrade.setLayout(new GridLayout(2, 2));
			
			bankTrade.add(new JLabel("Which resource do you want to give to the bank?     "));
			playerRes = new JComboBox(resourceList);
			playerRes.setEditable(false);
			bankTrade.add(playerRes);
			
			bankTrade.add(new JLabel("Which resource do you want in return?     "));
			bankRes = new JComboBox(resourceList);
			bankRes.setEditable(false);
			bankTrade.add(bankRes);
			
			int result = JOptionPane.showConfirmDialog(null, bankTrade, "Trade resources with the bank at a 4:1 rate", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			String pResource = (String) playerRes.getSelectedItem();
			String bResource = (String) bankRes.getSelectedItem();
			
			if (result == JOptionPane.OK_OPTION) {
				confirmPanel = new JPanel();
				confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(pResource, bResource)));
				JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		private void handleHarborTrade() {
			harborTrade = new JPanel();		
			harborTrade.setLayout(new BorderLayout());
			
			JPanel harborNames = new JPanel();
			harborNames.setLayout(new GridLayout(11, 1));
			harborNames.add(new JLabel(""));
			harborNames.add(new JLabel("Harbor 1's trade rate is " + map.getH1Rate() + " for 1 of any resource"));
			harborNames.add(new JLabel("Harbor 2's trade rate is " + map.getH2Rate() + " for 1 grain"));
			harborNames.add(new JLabel("Harbor 3's trade rate is " + map.getH3Rate() + " for 1 ore"));
			harborNames.add(new JLabel("Harbor 4's trade rate is " + map.getH4Rate() + " for 1 of any resource"));
			harborNames.add(new JLabel("Harbor 5's trade rate is " + map.getH5Rate() + " for 1 wool"));
			harborNames.add(new JLabel("Harbor 6's trade rate is " + map.getH6Rate() + " for 1 of any resource"));
			harborNames.add(new JLabel("Harbor 7's trade rate is " + map.getH7Rate() + " for 1 of any resource"));
			harborNames.add(new JLabel("Harbor 8's trade rate is " + map.getH8Rate() + " for 1 brick"));
			harborNames.add(new JLabel("Harbor 9's trade rate is " + map.getH9Rate() + " for 1 lumber"));
			harborNames.add(new JLabel(""));
						
			harborTrade.add(new JLabel("Here are the trade rates of the 9 harbors. Please select a harbor to trade with:"), BorderLayout.NORTH);
			harborTrade.add(harborNames, BorderLayout.CENTER);
			harborCB = new JComboBox(harborList);
			harborCB.setEditable(false);
			harborTrade.add(harborCB, BorderLayout.SOUTH);
	
			int result = JOptionPane.showConfirmDialog(null, harborTrade, "Trade resources with a harbor!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			String harbor = (String) harborCB.getSelectedItem();
			

			if (result == JOptionPane.OK_OPTION) {
				Node nodeFlag = new Node();
				
				if (harbor.compareTo("Harbor 1") == 0) {
					for (Node harbor1Node: map.getH1Nodes()) {
						if (harbor1Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor1Node;
						}
					}
				}	
				else if (harbor.compareTo("Harbor 2") == 0) {
					for (Node harbor2Node: map.getH2Nodes()) {
						if (harbor2Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor2Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 3") == 0) {
					for (Node harbor3Node: map.getH3Nodes()) {
						if (harbor3Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor3Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 4") == 0) {
					for (Node harbor4Node: map.getH4Nodes()) {
						if (harbor4Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor4Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 5") == 0) {
					for (Node harbor5Node: map.getH5Nodes()) {
						if (harbor5Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor5Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 6") == 0) {
					for (Node harbor6Node: map.getH6Nodes()) {
						if (harbor6Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor6Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 7") == 0) {
					for (Node harbor7Node: map.getH7Nodes()) {
						if (harbor7Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor7Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 8") == 0) {
					for (Node harbor8Node: map.getH8Nodes()) {
						if (harbor8Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor8Node;
						}
					}
				}
				else if (harbor.compareTo("Harbor 9") == 0) {
					for (Node harbor9Node: map.getH9Nodes()) {
						if (harbor9Node.getOwner() == players.get(currentPlayer)) {
							nodeFlag = harbor9Node;
						}
					}
				}
				
				if (nodeFlag.getOwner() == null) {
					JPanel errorPanel = new JPanel();
					errorPanel.add(new JLabel("You don't own a node at " + harbor + "!"));
					JOptionPane.showMessageDialog(null, errorPanel, "", JOptionPane.ERROR_MESSAGE);					
				}
				else {
					String harborLine = "";
					if (harbor.compareTo("Harbor 1") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH1Rate() + " any resource : 1 " + map.getH1Resource();
					else if (harbor.compareTo("Harbor 2") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH2Rate() + " any resource : 1 " + map.getH2Resource();
					else if (harbor.compareTo("Harbor 3") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH3Rate() + " any resource : 1 " + map.getH3Resource();
					else if (harbor.compareTo("Harbor 4") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH4Rate() + " any resource : 1 " + map.getH4Resource();
					else if (harbor.compareTo("Harbor 5") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH3Rate() + " any resource : 1 " + map.getH5Resource();
					else if (harbor.compareTo("Harbor 6") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH2Rate() + " any resource : 1 " + map.getH6Resource();
					else if (harbor.compareTo("Harbor 7") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH3Rate() + " any resource : 1 " + map.getH7Resource();
					else if (harbor.compareTo("Harbor 8") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH2Rate() + " any resource : 1 " + map.getH8Resource();
					else if (harbor.compareTo("Harbor 9") == 0)
						harborLine = harbor + "'s trade rate is " + map.getH3Rate() + " any resource : 1 " + map.getH9Resource();
					else 
						harborLine = "Something weird happened...";
						
					JPanel harborSpecTrade = new JPanel();
					harborSpecTrade.setVisible(true);
					harborSpecTrade.setLayout(new GridLayout(0, 2));					
					harborSpecTrade.add(new JLabel(harborLine));
					harborSpecTrade.add(new JLabel(""));

					harborSpecTrade.add(new JLabel("Which resource do you want to give to " + harbor + "?     "));
					playerRes = new JComboBox(resourceList);
					playerRes.setEditable(false);
					harborSpecTrade.add(playerRes);
					
					harborSpecTrade.add(new JLabel("Which resource do you want in return?     "));
					harborRes = new JComboBox(resourceList);
					harborRes.setEditable(false);
					harborSpecTrade.add(harborRes);
				
					int newResult = JOptionPane.showConfirmDialog(null, harborSpecTrade, "Trade in progress...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					
					String pResource = (String) playerRes.getSelectedItem();
					String hResource = (String) harborRes.getSelectedItem();
					
					if (newResult == JOptionPane.OK_OPTION) {
						confirmPanel = new JPanel();
						confirmPanel.add(new JLabel(players.get(currentPlayer).tradeResources(harbor, map, pResource, hResource)));
						JOptionPane.showMessageDialog(null, confirmPanel, "", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
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
		
		private void handleVictoryPts() {	
			victoryPointsPanel = new JPanel();
			victoryPointsPanel.setLayout(new GridLayout(0, 1));
			victoryPointsPanel.add(new JLabel(players.get(currentPlayer).getName() + " has " + Integer.toString(players.get(currentPlayer).getPoints()) + " victory points."));
			victoryPointsPanel.add(new JLabel("You need " + Integer.toString(10 - players.get(currentPlayer).getPoints()) + " more victory points to win!"));
			JOptionPane.showMessageDialog(null, victoryPointsPanel, players.get(currentPlayer).getName() + "'s Victory Points" ,JOptionPane.PLAIN_MESSAGE );
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
		  else if( s == "bottomRight") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(3));
		  }
		  else if( s == "bottomLeft") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(4));
		  }
		  else if(s == "bottom") {
			  result = players.get(currentPlayer).buildRoad(map.getHexes().get(FindHex(tempX, tempY)).getNearbyEdges().get(5));
		  }
		  place = "Road";
		}
		  else if (buildcount == 2) {
			  if(s == "middleRight") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(3));
			  }
			  else if(s == "topRight") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(0));
			  }
			  else if( s == "topLeft") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(1));
			  }
			  else if( s == "bottomRight") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(4));
			  }
			  else if( s == "bottomLeft") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(5));
			  }
			  else if(s == "middleLeft") {
				  result = players.get(currentPlayer).buildSettlement(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(3));
			  }
			 place = "Settlement";
		  }
		  else if (buildcount ==3) {
			  //Fix Citys
			  if(s == "top") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(0));
			  }
			  else if(s == "topRight") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(1));
			  }
			  else if( s == "topLeft") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(2));
			  }
			  else if( s == "bottomRight") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(3));
			  }
			  else if( s == "bottomLeft") {
				  result = players.get(currentPlayer).buildCity(map.getHexes().get(FindHex(tempX, tempY)).getNearbyNodes().get(4));
			  }
			  else if(s == "bottom") {
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

		if (currentPlayer == 0)
			player = new JMenu("<html><font color=\"red\">" + players.get(currentPlayer).getName() + "</font></html>");
		else if (currentPlayer == 1)
			player = new JMenu("<html><font color=\"blue\">" + players.get(currentPlayer).getName() + "</font></html>");
		else if (currentPlayer == 2)
			player = new JMenu("<html><font color=\"orange\">" + players.get(currentPlayer).getName() + "</font></html>");
		else if (currentPlayer == 3)
			player = new JMenu("<html><font color=\"gray\">" + players.get(currentPlayer).getName() + "</font></html>");
		else
			player = new JMenu(players.get(currentPlayer).getName());
		
		optionResources = new JMenuItem("Check Resources");
		optionVictoryPts = new JMenuItem("Check Victory Points");
		
		optionResources.addActionListener(new MenuListener());
		optionVictoryPts.addActionListener(new MenuListener());
		
		player.add(optionResources);
		player.add(optionVictoryPts);
		
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
					initialOK = 0;
					buildBuildOptions(p.x,p.y,currentPlayer);
					System.out.println(buildcount);
				}
				else {
					initialOK = 0;
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
		
		int result = JOptionPane.showConfirmDialog(null, HexoptionsScreen,players.get(playerinarray).getName() + "'s Starting Settlement and Road", JOptionPane.OK_CANCEL_OPTION,
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
		
		System.out.print(success +""+initialOK);
			if(edges.getSelectedItem() == Edges[0] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(0)));
				//success = 0;
				initialOK = 1;
				System.out.print(success +""+initialOK);
			}
			else if(edges.getSelectedItem() == Edges[1] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(1)));
				//success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[2] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(2)));
				//success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[3] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(3)));
				//success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[4] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(4)));
				//success = 0;
				initialOK = 1;
			}
			else if(edges.getSelectedItem() == Edges[5] && success == 1) {
				System.out.println(players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(5)));
			//	success = 0;
			//	if()players.get(playerinarray).buildStartingRoad(map.getHexes().get(FindHex(x, y)).getNearbyEdges().get(5)== "Settlement successfully built!")
				initialOK = 1;
			}
		if( success ==1 && initialOK == 0) {
			players.get(playerinarray).getNodes().remove(players.get(playerinarray).getNodes().size() -1);
			System.out.println(players.get(playerinarray).getNodes().size());
			
			JOptionPane.showMessageDialog(null, Confirm, "Invaid Road" ,JOptionPane.PLAIN_MESSAGE );
/////////////////////////Add to panel right here to ask for road
			//comboBox that include options for road 
			
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
