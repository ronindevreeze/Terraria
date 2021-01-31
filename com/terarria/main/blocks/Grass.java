package com.terarria.main.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import com.terarria.main.Game;
import com.terarria.main.utils.Utils;

public class Grass extends Block implements Serializable {
	
	/**
	 * 
	 */

	private static final long serialVersionUID = -3789828644424206149L;
	Random random = new Random();
	int offset = random.nextInt(Sprite.grass.length);
	
	public Grass(int x, int y) {
		super(x, y);
		solid = true;
		breakable = true;
		image = Sprite.grass[offset].image;
		type = TYPE.grass;

		drop = ITEM_TYPES.dirt;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, Utils.toScreenX(x), Utils.toScreenY(y), Game.BLOCK_SIZE, Game.BLOCK_SIZE, null);
	}

}
