package org.catan.players;

import org.catan.map.*;
import java.util.ArrayList;

public class PlayerDriver {
	public static void main (String[] args) {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		String resourceList[] = {"brick", "lumber", "grain", "wool", "ore"};		
		
		p1.setName("Justin");
		p2.setName("Kyler");
		p3.setName("Jimmy");
		System.out.println("The players' names are: " + p1.getName() + ", " + p2.getName() + ", " + p3.getName());
		
		for (String resource : resourceList) {
			p1.addResources(resource, 5);
			p2.addResources(resource, 5);
			p3.addResources(resource, 5);
		}
		
		for (String resource : resourceList) {
			System.out.println(p1.getName() + " has " + p1.getResources(resource) + " " + resource);
			System.out.println(p2.getName() + " has " + p2.getResources(resource) + " " + resource);
			System.out.println(p3.getName() + " has " + p3.getResources(resource) + " " + resource);
		}
		
		
		// Create a pseudo-map for testing building
		Node leftNode = new Node();
		Node midNode = new Node();
		Node rightNode = new Node();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int i = 0;
		for (i = 0; i < 7; ++i) {
			edges.add(new Edge());
		}
		leftNode.addNearbyEdge(edges.get(0));
		leftNode.addNearbyEdge(edges.get(1));
		leftNode.addNearbyEdge(edges.get(2));
		leftNode.addNearbyNode(midNode);
		midNode.addNearbyEdge(edges.get(2));
		midNode.addNearbyEdge(edges.get(3));
		midNode.addNearbyEdge(edges.get(4));
		midNode.addNearbyNode(leftNode);
		midNode.addNearbyNode(rightNode);
		rightNode.addNearbyEdge(edges.get(4));
		rightNode.addNearbyEdge(edges.get(5));
		rightNode.addNearbyEdge(edges.get(6));
		rightNode.addNearbyNode(rightNode);
		edges.get(0).addNearbyNode(leftNode);
		edges.get(0).addNearbyEdge(edges.get(1));
		edges.get(0).addNearbyEdge(edges.get(2));
		edges.get(1).addNearbyNode(leftNode);
		edges.get(1).addNearbyEdge(edges.get(0));
		edges.get(1).addNearbyEdge(edges.get(2));
		edges.get(2).addNearbyNode(leftNode);
		edges.get(2).addNearbyNode(midNode);
		edges.get(2).addNearbyEdge(edges.get(0));
		edges.get(2).addNearbyEdge(edges.get(1));
		edges.get(2).addNearbyEdge(edges.get(3));
		edges.get(2).addNearbyEdge(edges.get(4));
		edges.get(3).addNearbyNode(midNode);
		edges.get(3).addNearbyEdge(edges.get(2));
		edges.get(3).addNearbyEdge(edges.get(4));
		edges.get(4).addNearbyNode(midNode);
		edges.get(4).addNearbyNode(rightNode);
		edges.get(4).addNearbyEdge(edges.get(2));
		edges.get(4).addNearbyEdge(edges.get(3));
		edges.get(4).addNearbyEdge(edges.get(5));
		edges.get(4).addNearbyEdge(edges.get(6));
		edges.get(5).addNearbyNode(rightNode);
		edges.get(5).addNearbyEdge(edges.get(4));
		edges.get(5).addNearbyEdge(edges.get(6));
		edges.get(6).addNearbyNode(rightNode);
		edges.get(6).addNearbyEdge(edges.get(5));
		edges.get(6).addNearbyEdge(edges.get(6));
		
		
		System.out.println("\nTrying to build a settlement without roads attached:");
		System.out.println(p1.buildSettlement(leftNode));
		edges.get(0).setOwner(p1);
		edges.get(6).setOwner(p2);
		edges.get(4).setOwner(p3);
		System.out.println("\nBuilding a settlement with a road attached:");
		System.out.println(p1.buildSettlement(leftNode));
		System.out.println("\nTrying to build a settlement on an already-owned node:");
		System.out.println(p2.buildSettlement(leftNode));
		System.out.println("\nBuilding a settlement while obeying the distance rule:");
		System.out.println(p2.buildSettlement(rightNode));
		System.out.println("\nTrying to build a settlement that breaks the distance rule:");
		System.out.println(p3.buildSettlement(midNode));
		System.out.println("\nTrying to build a road that does not touch another road or owned node:");
		System.out.println(p3.buildRoad(edges.get(1)));
		System.out.println("\nTrying to build a road on another road:");
		System.out.println(p3.buildRoad(edges.get(0)));
		System.out.println("\nBuilding a road attached to a road owned:");
		System.out.println(p3.buildRoad(edges.get(3)));
		System.out.println("\nBuilding a road attached to a settlement:");
		System.out.println(p1.buildRoad(edges.get(1)));
		System.out.println(p1.buildRoad(edges.get(2)));
		
		int array1[] = {9, 9, 9, 9, 9};
		int array2[] = {0, 0, 1, 2, 0};
		int array3[] = {0, 0, 0, 0, 3};
		System.out.println("\nTrying impossible trades:");
		System.out.println(p1.tradeResources("lumber", "ore"));
		System.out.println(p1.tradeResources(p2, array1, array1));
		System.out.println("\nTrying possible trades:");
		System.out.println(p2.tradeResources(p3, array2, array3));
		System.out.println(p3.tradeResources("wool", "brick"));
		System.out.println("\nTrying to build a city on an unowned node:");
		System.out.println(p1.buildCity(rightNode));
		System.out.println("\nBuilding a city:");
		System.out.println(p1.buildCity(leftNode));
		
		for (String resource : resourceList) {
			System.out.println(p1.getName() + " has " + p1.getResources(resource) + " " + resource);
			System.out.println(p2.getName() + " has " + p2.getResources(resource) + " " + resource);
			System.out.println(p3.getName() + " has " + p3.getResources(resource) + " " + resource);
		}
	}
}
