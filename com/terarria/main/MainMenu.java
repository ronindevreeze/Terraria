package com.terarria.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

import javax.swing.*;

public class MainMenu extends Canvas implements Runnable {
	@Serial
	private static final long serialVersionUID = -5602039780217021092L;

	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame(Game.TITLE);
		frame.setPreferredSize(new Dimension(Game.WIDTH / 2, Game.HEIGHT));
		frame.setMaximumSize(new Dimension(Game.WIDTH / 2, Game.HEIGHT));
		frame.setMinimumSize(new Dimension(Game.WIDTH / 2, Game.HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		// -----------------------------------------------------------------------

		File directoryPath = new File(System.getProperty("user.dir") + "/com/terarria/main/saves/");
		String[] names = directoryPath.list();
		
		JList<String> lstLoad = new JList<>(names);
		lstLoad.setBounds((Game.WIDTH / 4) - 128, (Game.HEIGHT / 2) - 256, 256, 256);
		frame.add(lstLoad);
		
		JButton btnLoad = new JButton("Load World");
		btnLoad.setBounds((Game.WIDTH / 4) - 128, (Game.HEIGHT / 2) + 16, 256, 32);
		btnLoad.setFocusPainted(false);
		btnLoad.setContentAreaFilled(false);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!lstLoad.isSelectionEmpty()) {
					String[] args = new String[1];
					assert names != null;
					args[0] = names[lstLoad.getSelectedIndex()];
					Game.main(args);
				}
			}
		});
		frame.add(btnLoad);

		// -----------------------------------------------------------------------
		
		JTextField fldNew = new JTextField();
		fldNew.setBounds((Game.WIDTH / 4) - 128, (Game.HEIGHT / 2) + 96, 256, 32);
		frame.add(fldNew);
		
		JButton btnNew = new JButton("New World");
		btnNew.setFocusPainted(false);
		btnNew.setContentAreaFilled(false);
		btnNew.setBounds((Game.WIDTH / 4) - 128, (Game.HEIGHT / 2) + 144, 256, 32);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        if(fldNew.getText().length() > 0) {
		        	System.out.println("Creating new world: " + fldNew.getText());
					if(fldNew.getText() != null) {
						String[] args = new String[1];
						args[0] = fldNew.getText() + ".txt";
						Game.main(args);
					}
		        }
			}
		});
		frame.add(btnNew);
		
		// -----------------------------------------------------------------------
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds((Game.WIDTH / 4) - 128, (Game.HEIGHT / 2) + 224, 256, 32);
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.addActionListener(arg0 -> System.exit(0));
		frame.add(btnExit);
		frame.requestFocus();
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}