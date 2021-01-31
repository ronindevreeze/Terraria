package com.terarria.main;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window extends Canvas {

	private static final long serialVersionUID = 8625267849442674426L;

	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) { }

			@Override
			public void windowClosed(WindowEvent arg0) { }

			@Override
			public void windowClosing(WindowEvent arg0) { game.save(); }

			@Override
			public void windowDeactivated(WindowEvent arg0) { }

			@Override
			public void windowDeiconified(WindowEvent arg0) { }

			@Override
			public void windowIconified(WindowEvent arg0) { }

			@Override
			public void windowOpened(WindowEvent arg0) { }
		}
		);
		game.start();
	}
}
