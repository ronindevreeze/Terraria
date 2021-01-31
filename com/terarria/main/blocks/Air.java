package com.terarria.main.blocks;

import java.awt.Graphics;
import java.io.Serializable;

public class Air extends Block implements Serializable {

	private static final long serialVersionUID = 6185052094006932317L;

	public Air(int x, int y) {
		super(x, y);
		solid = false;
		breakable = false;
		type = TYPE.air;
	}

	@Override
	public void render(Graphics g) { 
		// Draw nothing
	}
}