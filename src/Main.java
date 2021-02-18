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
import javax.swing.JCheckBox;
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
				frame.dispose();
			}

			public void TripHelper() {
				JFrame frame = new JFrame();
				JTextField startLocation = new JTextField(5);
				JTextField endLocation = new JTextField(5);
				JTextField dist = new JTextField(5);
				JTextField Time = new JTextField(5);
				JButton begin = new JButton("Begin Navigation");
				editNewFrame(frame, startLocation, endLocation, dist, Time, begin);
				begin.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String start = startLocation.getText();
						String end = endLocation.getText();
						List<String> Itinerary = null;
						if (!Time.getText().isEmpty()) {
							int time = Integer.parseInt(Time.getText());
							mapGraph.findShortestPathFromPlace(start, true);
							List<String> into = new ArrayList<String>();
							Itinerary = mapGraph.places.get(start).helperTime(into, time);
						}
						if (!dist.getText().isEmpty()) {
							int distance = Integer.parseInt(dist.getText());
							mapGraph.findShortestPathFromPlace(start, false);
							List<String> into = new ArrayList<String>();
							Itinerary = mapGraph.places.get(start).helperDistance(into, distance);
						}
						if (Time.getText().isEmpty() && dist.getText().isEmpty()) {
							mapGraph.findShortestPathFromPlace(start, false);
							Itinerary = mapGraph.getShortestPathTo(end);
						}
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
						JPanel biggerPanel = new JPanel();
						biggerPanel.add(frameList, BorderLayout.CENTER);
						frame.add(biggerPanel, BorderLayout.CENTER);
						frame.setVisible(true);
						frame.repaint();
						Itinerary.clear();
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
			
			
			private void editNewFrame(JFrame frame, JTextField startLocation, JTextField endLocation, JTextField dist,
					JTextField Time, JButton begin) {
				frame.setSize(SIZE);
				JLabel input1 = new JLabel("Starting Location:");
				JLabel input2 = new JLabel("Ending Location:");
				JLabel input3 = new JLabel("Maximum Distance to Travel?");
				JLabel input4 = new JLabel("Maximum Time on Road?");
				JLabel input5 = new JLabel("Need help deciding where to go? Click Here!");
				JLabel input6 = new JLabel("Do you prioritize distance or time?");
				JCheckBox checkBox = new JCheckBox();
				JComboBox<String> select = new JComboBox<String>();
				select.addItem("--Select One--");
				select.addItem("Distance");
				select.addItem("Time");

				select.setVisible(false);
				input3.setVisible(false);
				input4.setVisible(false);
				dist.setVisible(false);
				Time.setVisible(false);
				input6.setVisible(false);

				checkBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (checkBox.isSelected()) {
							select.setVisible(true);
							input2.setVisible(false);
							input6.setVisible(true);
							endLocation.setVisible(false);
						} else {
							select.setVisible(false);
							input2.setVisible(true);
							input6.setVisible(false);
							endLocation.setVisible(true);
						}
					}

				});

				JButton reset = new JButton("Reset");
				reset.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						TripHelper();
					}
				});
				select.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (select.getSelectedIndex() == 1) {
							input3.setVisible(true);
							input4.setVisible(false);
							dist.setVisible(true);
							Time.setVisible(false);
						} else if (select.getSelectedIndex() == 2) {
							input3.setVisible(false);
							input4.setVisible(true);
							Time.setVisible(true);
							dist.setVisible(false);
						} else {
							input3.setVisible(false);
							input4.setVisible(false);
							dist.setVisible(false);
							Time.setVisible(false);
						}

					}

				});
				JPanel newP = new JPanel(new GridLayout(8, 4));
				JPanel bigPanel = new JPanel();
				newP.add(input1);
				newP.add(startLocation);
				newP.add(input2);
				newP.add(endLocation);
				newP.add(input5);
				newP.add(checkBox);
				newP.add(input6);
				newP.add(select);
				newP.add(input3);
				newP.add(dist);
				newP.add(input4);
				newP.add(Time);
				newP.add(begin);
				newP.add(reset);
				bigPanel.add(newP, BorderLayout.SOUTH);
				frame.add(bigPanel, BorderLayout.NORTH);
			}
		});
	}

}
