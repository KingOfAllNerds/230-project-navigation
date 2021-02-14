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
		MapGraph mapGraph = new MapGraph();
		frame.setVisible(true);	
	}

}
