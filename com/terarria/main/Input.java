package com.terarria.main;
import java.awt.Point;
import java.awt.event.*;

public class Input extends KeyAdapter implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	public Game game;
	
	public Input(Game game) {
		this.game = game;
	}

	public int UP = 0;
	public int DOWN = 0;
	public int LEFT = 0;
	public int RIGHT = 0;

	public int scrollPosition = 0;

	public boolean[] mouseButtons = new boolean[3];
	public Point mousePosition = new Point();

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 65 -> LEFT = 1;
			case 87 -> UP = 1;
			case 68 -> RIGHT = 1;
			case 83 -> DOWN = 1;
			case 112 -> Game.DEBUG = !Game.DEBUG;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 65 -> LEFT = 0;
			case 87 -> UP = 0;
			case 68 -> RIGHT = 0;
			case 83 -> DOWN = 0;
		}
	}

	public void mouseMoved(MouseEvent e) {
		mousePosition.x = e.getX();
		mousePosition.y = e.getY();
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() <= mouseButtons.length) {
			mouseButtons[e.getButton() - 1] = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() <= mouseButtons.length) {
			mouseButtons[e.getButton() - 1] = false;
		}
	}

	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) { }

	public void tick() {
		for(int i = 0; i < mouseButtons.length; i++) {
			if(mouseButtons[i]) {
				mouseButtons[i] = false;
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollPosition += e.getWheelRotation();
		System.out.println("Scrolled!!!");
	}
}