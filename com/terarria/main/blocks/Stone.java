package com.terarria.main.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.terarria.main.Game;
import com.terarria.main.utils.Utils;

public class Stone extends Block {
	
	private static final long serialVersionUID = 7332147764302108859L;
	Random random = new Random();
	int offset = random.nextInt(Sprite.stone.length);
	
	public Stone(int x, int y) {
		super(x, y);
		solid = true;
		breakable = true;
		image = Sprite.stone[offset].image;
		type = TYPE.stone;
		drop = ITEM_TYPES.cobblestone;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, Utils.toScreenX(x), Utils.toScreenY(y), Game.BLOCK_SIZE, Game.BLOCK_SIZE, null);
	}

}
