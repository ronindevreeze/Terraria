package com.terarria.main.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import com.terarria.main.Game;
import com.terarria.main.utils.Utils;

public class Dirt extends Block implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882317682372261097L;
	public Random random = new Random();
	int offset = random.nextInt(Sprite.dirt.length);
	
	public Dirt(int x, int y) {
		super(x, y);
		solid = true;
		breakable = true;
		image = Sprite.dirt[offset].image;
		type = TYPE.dirt;
		drop = ITEM_TYPES.dirt;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, Utils.toScreenX(x), Utils.toScreenY(y), Game.BLOCK_SIZE, Game.BLOCK_SIZE, null);
	}

}
