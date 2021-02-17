import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
		JButton About = new JButton("About");
		Start.add(About);
		Start.add(TravelGuide);
		frame.add(Start, BorderLayout.SOUTH);
		frame.add(title, BorderLayout.NORTH);
		frame.add(center, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapGraph mapGraph = new MapGraph();
		// mapGraph.findShortestPathFromPlace("Baker, Ore.", false);
		// System.out.println(mapGraph.getShortestPathTo("Austin, Tex."));
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
				JLabel input3 = new JLabel("Desired distance");
				JLabel input4 = new JLabel("Desired Time");
				JButton begin = new JButton("Begin Navigation");
				title.setFont(new Font("serif", Font.PLAIN, 70));
				mapGraph.getPlaces();
				JTextField startLocation = new JTextField(5);
				JTextField endLocation = new JTextField(5);
				JTextField dist = new JTextField(5);
				JTextField Time = new JTextField(5);
				JPanel newP = new JPanel(new GridLayout(6, 4));
				// frame.add(title);
				JPanel bigPanel = new JPanel();
				newP.add(input1);
				newP.add(startLocation);
				newP.add(input2);
				newP.add(endLocation);
				newP.add(input3);
				newP.add(dist);
				newP.add(input4);
				newP.add(Time);
				newP.add(begin);
				// bigPanel.add(title, BorderLayout.NORTH);
				bigPanel.add(newP, BorderLayout.SOUTH);
				JPanel biggerPanel = new JPanel();
				// biggerPanel.add(bigPanel);
				frame.add(bigPanel, BorderLayout.NORTH);
				begin.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String start = startLocation.getText();
						String end = endLocation.getText();
						if (!Time.getText().isEmpty()) {
							int time = Integer.parseInt(Time.getText());
							System.out.println(time);
						}
						if (!dist.getText().isEmpty()) {
							int distance = Integer.parseInt(dist.getText());
							System.out.println(distance);
						}
						if (Time.getText().isEmpty() && dist.getText().isEmpty()) {
							mapGraph.findShortestPathFromPlace(start, false);
							List<String> Itinerary = mapGraph.getShortestPathTo(end);
							String[] list = new String[Itinerary.size()];
							String output = "Starting at: ";
							for (int i = 0; i < Itinerary.size(); i++) {
								output = output + Itinerary.get(i);
								list[i] = Itinerary.get(i);
								if (i < Itinerary.size() - 1) {
									output = output + " Next stop: ";
								}
							}
							JList<String> frameList = new JList<String>(list);
							frameList.setVisible(true);
							biggerPanel.add(frameList, BorderLayout.CENTER);
							frame.add(biggerPanel, BorderLayout.CENTER);
							frame.setVisible(true);
							frame.repaint();
							Itinerary.clear();
						}
						if (!Time.getText().isEmpty()) {

						}
						if (!dist.getText().isEmpty()) {

						}
					}
				});

				JButton printLocations = new JButton("Show Locations");
				printLocations.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						Enumeration<String> keys = MapGraph.places.keys();

						while (keys.hasMoreElements()) {
							System.out.println(keys.nextElement());
						}
					}
				});
				frame.add(printLocations, BorderLayout.SOUTH);
				frame.setVisible(true);
				frame.repaint();

			}
		});
	}

}
