import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main {

	public static final Dimension SIZE = new Dimension(600, 600);
	public static final Dimension TitleSize = new Dimension(100, 100);
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		StartScreen(frame);// TODO Auto-generated method stub
	}

	private static void StartScreen(JFrame frame) {
		// TODO Auto-generated method stub
		//l
		frame.setSize(SIZE);
		JPanel Start = new JPanel();
		JLabel title = new JLabel("Navigation System");
		title.setFont(new Font("serif", Font.PLAIN, 70));
		ImageIcon map = new ImageIcon("src/USA2.png");
		JLabel center = new JLabel(map);
		JButton TravelGuide = new JButton("Travel Manager");
		JButton About =  new JButton("About");
		Start.add(About);
		Start.add(TravelGuide);
		frame.add(Start, BorderLayout.SOUTH);
		frame.add(title, BorderLayout.NORTH);
		frame.add(center, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		NavMap Nav = new NavMap();
		frame.setVisible(true);	
	}
	
	private double distanceCalculator(double[] Loc1, double[] Loc2) {
		//distance using latitude and longitude
		//Assumes latitude is stored in first slot in double[] ex Loc2 = [Latitude,Longitude]
		double distLat = Math.toRadians(Loc2[0] - Loc1[0]);
		double distLon = Math.toRadians(Loc2[1] - Loc1[1]);
		
		double lat1Radians = Math.toRadians(Loc1[0]);
		double lat2Radians = Math.toRadians(Loc2[0]);
		
		double haversine = Math.pow(Math.sin(distLat / 2), 2) +
						   Math.pow(Math.sin(distLon / 2), 2) *
						   Math.cos(lat1Radians) *
						   Math.cos(lat2Radians); //haversine formula used from coordinates
		
		double radius = 6371; //radius of earth
		double distanceKM = 2 * Math.asin(Math.sqrt(haversine)) * radius; //second part of haversine formula
		double distanceInMiles = distanceKM / 1.609344;
		return distanceInMiles;
	}
	
	private int timeCalculator(double distance) {
		int averageSpeed = 0;
		if(distance <= 99) averageSpeed = 40;
		if(distance >= 51 && distance <= 99) averageSpeed = 50;
		if(distance >= 100) averageSpeed = 60;
		int estimatedTime = (int) (distance / averageSpeed);
		return estimatedTime;
	}

}
