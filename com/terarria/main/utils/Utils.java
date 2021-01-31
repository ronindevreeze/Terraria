package com.terarria.main.utils;

import com.terarria.main.Game;

import java.awt.*;

public class Utils {
	
	public static int toWorldX(int p) { return p - Game.focusX - (Game.WIDTH / 2); }
	
	public static int toWorldY(int p) { return p - Game.focusY - (Game.HEIGHT / 2); }
	
	public static int toScreenX(int p) { return p + Game.focusX + (Game.WIDTH / 2); }
	
	public static int toScreenY(int p) { return p + Game.focusY + (Game.HEIGHT / 2); }

	public static Point getChunk(int mouseScreenX, int mouseScreenY) {
		int mouseWorldX = Utils.toWorldX(mouseScreenX);
		int mouseWorldY = Utils.toWorldY(mouseScreenY);

		int mouseCellX = (int) (Math.floor((double)mouseWorldX / (double)Game.BLOCK_SIZE));
		int mouseCellY = (int) (Math.floor((double)mouseWorldY / (double)Game.BLOCK_SIZE));

		int mouseChunkX = (int) (Math.floor((double)mouseCellX / (double)Game.CHUNK_SIZE));
		int mouseChunkY = (int) (Math.floor((double)mouseCellY / (double)Game.CHUNK_SIZE));

		return new Point(mouseChunkX, mouseChunkY);
	}
}