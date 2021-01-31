package com.terarria.main.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.terarria.main.Game;
import com.terarria.main.utils.Utils;

public class Wood extends Block {
	
	private static final long serialVersionUID = -2564345609719102389L;
	Random random = new Random();
	int offset = random.nextInt(Sprite.wood.length);

	public Wood(int x, int y) {
		super(x, y);
		solid = true;
		breakable = true;
		image = Sprite.wood[offset].image;
		type = TYPE.wood;
		drop = ITEM_TYPES.wood;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, Utils.toScreenX(x), Utils.toScreenY(y), Game.BLOCK_SIZE, Game.BLOCK_SIZE, null);
	}

}
