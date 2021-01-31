package com.terarria.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;

import com.terarria.main.blocks.Air;
import com.terarria.main.blocks.Block;
import com.terarria.main.blocks.Dirt;
import com.terarria.main.blocks.Grass;
import com.terarria.main.blocks.Stone;
import com.terarria.main.blocks.TYPE;
import com.terarria.main.blocks.Wood;
import com.terarria.main.utils.PerlinNoise;
import com.terarria.main.utils.Utils;

public class Chunk implements Serializable {
	
	private static final long serialVersionUID = 6274390105242946878L;
	
	int x, y;
	public Block[][] blocks = new Block[Game.CHUNK_SIZE][Game.CHUNK_SIZE];
	
	public Chunk(int x, int y, HashMap<Point, TYPE> map) {
		if(map == null) {
			// Generate new
			this.x = x;
			this.y = y;
			
			// Generate blocks in chunk
			for(int yy = 0; yy < Game.CHUNK_SIZE; yy++) {
				for(int xx = 0; xx < Game.CHUNK_SIZE; xx++) {
					double px = ((xx * Game.BLOCK_SIZE) + x) * Game.TERRAIN_SIZE;
					double py = ((yy * Game.BLOCK_SIZE) + y) * Game.TERRAIN_SIZE;

					if(PerlinNoise.noise(px, py, 0) > 0 && PerlinNoise.noise(px, py, 0) < 0.2) {
						// Grass of Dirt
						if(PerlinNoise.noise(px, (((yy - 1) * Game.BLOCK_SIZE) + y) * Game.TERRAIN_SIZE, 0) < 0) {
							blocks[xx][yy] = new Grass(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						} else {
							blocks[xx][yy] = new Dirt(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						}
					} else if(PerlinNoise.noise(px, py, 0) > 0.2) {
						blocks[xx][yy] = new Stone(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
					} else {
						blocks[xx][yy] = new Air(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
					}
				}
			}
		} else {
			this.x = x;
			this.y = y;
			
			// Load from map
			for(int yy = 0; yy < Game.CHUNK_SIZE; yy++) {
				for(int xx = 0; xx < Game.CHUNK_SIZE; xx++) {

					switch(map.get(new Point(xx, yy))) {
					case wood:
						blocks[xx][yy] = new Wood(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						break;
					case grass:
						blocks[xx][yy] = new Grass(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						break;
					case dirt:
						blocks[xx][yy] = new Dirt(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						break;
					case stone:
						blocks[xx][yy] = new Stone(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						break;
					case air:
						blocks[xx][yy] = new Air(x + (Game.BLOCK_SIZE * xx), y + (Game.BLOCK_SIZE * yy));
						break;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for(int yy = 0; yy < Game.CHUNK_SIZE; yy++) {
			for(int xx = 0; xx < Game.CHUNK_SIZE; xx++) {
				if(blocks[xx][yy] != null) {
					blocks[xx][yy].render(g);
				}
			}
		}
		
		if(Game.DEBUG) {
			g.setColor(Color.BLACK);
			g.drawRect(Utils.toScreenX(x), Utils.toScreenY(y), Game.CHUNK_SIZE * Game.BLOCK_SIZE, Game.CHUNK_SIZE * Game.BLOCK_SIZE);
			g.drawString("x: " + x + " | y: " + y, Utils.toScreenX(x) + 10, Utils.toScreenY(y) + 20);
		}
	}
}
