package com.terarria.main.blocks;

import java.awt.Graphics;
import com.terarria.main.blocks.ITEM_TYPES;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Block implements Serializable {

	private static final long serialVersionUID = -296836852628380349L;
	public int x, y;
	public transient Image image;
	public ITEM_TYPES drop;

	public boolean solid;
	public boolean breakable;
	public TYPE type;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Graphics g);
}
