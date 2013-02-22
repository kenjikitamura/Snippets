package jp.rainbowdevil.snippets.ui.mac;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MacMainWindow extends JFrame{
	
	public static void main(String[] args){
		MacMainWindow frame = new MacMainWindow();

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(10, 10, 300, 200);
	    frame.setTitle("hoge");
	    frame.setVisible(true);
	  }

	public MacMainWindow(){
		JLabel label1 = new JLabel("left");
		JScrollPane leftJScrollPane = new JScrollPane(label1);
		
		JLabel label2 = new JLabel("top");
		JScrollPane topJScrollPane = new JScrollPane(label2);
		
		JLabel label3 = new JLabel("btm");
		JScrollPane bottomJScrollPane = new JScrollPane(label3);
		
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, label2, label3);
		
		///splitPane1.setOneTouchExpandable(true);
		splitPane1.setDividerSize(2);
		splitPane1.setDividerLocation(0.5);
		
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, label1, splitPane1);
		//splitPane2.setOneTouchExpandable(true);
		splitPane2.setDividerSize(2);
		splitPane2.setDividerLocation(0.5);
		
		getContentPane().add(splitPane2);
	  }
}
