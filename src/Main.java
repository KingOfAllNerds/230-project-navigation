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
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class Main {

	public static final Dimension SIZE = new Dimension(600, 600);
	public static final Dimension TitleSize = new Dimension(100, 100);
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		StartScreen(frame);
	}

	private static void StartScreen(JFrame frame) {
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
		MapGraph mapGraph = new MapGraph();
		//mapGraph.findShortestPathFromPlace("Baker, Ore.", false);
		//System.out.println(mapGraph.getShortestPathTo("Austin, Tex."));
		frame.setVisible(true);	
		TravelGuide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TripHelper();
			}	
			
			public void TripHelper() {
				frame.setVisible(false);
				JFrame frame = new JFrame();
				frame.setSize(SIZE);
				JLabel title = new JLabel("Travel Planner");
				JLabel input1 = new JLabel("Starting Location:");
				JLabel input2 = new JLabel("Ending Location:");
				JButton begin = new JButton("Begin Navigation");
				title.setFont(new Font("serif", Font.PLAIN, 70));
				mapGraph.getPlaces();
				JTextField startLocation = new JTextField(5);
				JTextField endLocation = new JTextField(5);
				JPanel newP = new JPanel();
				newP.add(input1);
				newP.add(startLocation);
				newP.add(input2);
				newP.add(endLocation);
				newP.add(begin);
				frame.add(title,BorderLayout.NORTH);
				frame.add(newP, BorderLayout.CENTER);
				begin.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String start = startLocation.getText();
						String end = endLocation.getText();
						mapGraph.findShortestPathFromPlace(start, false);
						System.out.println(mapGraph.getShortestPathTo(end));
						List<String> Itinerary = mapGraph.getShortestPathTo(end);
						String output = "Starting at: ";
						for(int i = 0; i < Itinerary.size(); i++) {
							output = output + Itinerary.get(i);
							if(i < Itinerary.size() - 1) {
								output = output + " Next stop: ";
							}
						}
						System.out.println(output);
						JLabel Destinations = new JLabel(output);
						frame.add(Destinations, BorderLayout.SOUTH);
						frame.repaint();
						frame.setVisible(true);
						}
				});
				frame.setVisible(true);
				
			}
		});
	}

}
