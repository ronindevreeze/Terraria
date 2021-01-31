package com.terarria.main.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

public abstract class Creature implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5304095769826038781L;
	public int x, y, velx, vely;
	
	public Creature(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract Rectangle getBounds(int offsetX, int offsetY);
}
