package com.terarria.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.terarria.main.blocks.Block;
import com.terarria.main.blocks.TYPE;
import com.terarria.main.creatures.Creature;
import com.terarria.main.utils.Utils;

public class GameHandler {

	int colliderDepth = 5;
	private boolean go = false;
	Input input;

	public LinkedList<Creature> creatures;
	public Map<Point, Chunk> chunks = new HashMap<Point, Chunk>();
	public Rectangle renderChunks = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT);
	public Rectangle collisionChunks = new Rectangle(0, 0, Game.BLOCK_SIZE * colliderDepth, Game.BLOCK_SIZE * colliderDepth);
	
	public GameHandler(Input input) {
		this.input = input;
		creatures = new LinkedList<Creature>();
	}
	
	public void generateTerrain() {
		for(int yy = 0; yy < Game.CHUNKS.y; yy++) {
			for(int xx = 0; xx < Game.CHUNKS.x; xx++) {
				chunks.put(new Point(xx, yy), new Chunk(xx * Game.CHUNK_SIZE * Game.BLOCK_SIZE, yy * Game.CHUNK_SIZE * Game.BLOCK_SIZE, null));
			}
		}
	}
	
	public void tick() {
		if(go) {
			for(Creature c: creatures) {
				c.tick();
			}
			
			renderChunks.x = -Game.focusX - (Game.WIDTH / 2);
			renderChunks.y = -Game.focusY - (Game.HEIGHT / 2);
		}		
	}
	
	public void setChunks(HashMap<Point, HashMap<Point, TYPE>> mapToCopy, boolean loadSave) {
		if(loadSave) {
			for (Map.Entry<Point, HashMap<Point, TYPE>> entry : mapToCopy.entrySet()) {
				System.out.println(entry.getValue());
				chunks.put(entry.getKey(), new Chunk(entry.getKey().x * Game.CHUNK_SIZE * Game.BLOCK_SIZE, entry.getKey().y * Game.CHUNK_SIZE * Game.BLOCK_SIZE, entry.getValue()));
			}
		} else {
			generateTerrain();
		}
		
		go = true;
	}
	
	public LinkedList<Rectangle> getColliders(int playerX, int playerY) {
		collisionChunks.x = playerX - (collisionChunks.width / 2); // in world positions
		collisionChunks.y = playerY - (collisionChunks.height / 2); // in world positions
		
		LinkedList<Rectangle> colliders = new LinkedList<Rectangle>();
		for (Map.Entry<Point, Chunk> entry : chunks.entrySet()) {
			Rectangle chunkBox = new Rectangle(entry.getValue().x, entry.getValue().y, Game.CHUNK_SIZE * Game.BLOCK_SIZE, Game.CHUNK_SIZE * Game.BLOCK_SIZE); // In world positions
			if(collisionChunks.intersects(chunkBox)) {
				for(Block[] row: entry.getValue().blocks) {
					for(Block b: row) {
						if(b != null) {
							if(b.solid) {
								Rectangle r = new Rectangle(b.x, b.y, Game.BLOCK_SIZE, Game.BLOCK_SIZE);
								if(collisionChunks.intersects(r)) {
									colliders.add(r);
								}
							}
						}
					}
				}
				
			}
	    }
		return colliders;
	}
	
	public void render(Graphics g) {
		if(go) {
			
			for (Entry<Point, Chunk> entry : chunks.entrySet()) {
				g.drawRect(entry.getKey().x, entry.getKey().y, Game.BLOCK_SIZE * Game.CHUNK_SIZE, Game.BLOCK_SIZE * Game.CHUNK_SIZE);
			}
			
			for (Map.Entry<Point, Chunk> entry : chunks.entrySet()) {
				Rectangle box = new Rectangle(entry.getValue().x, entry.getValue().y, Game.CHUNK_SIZE * Game.BLOCK_SIZE, Game.CHUNK_SIZE * Game.BLOCK_SIZE);
				if(renderChunks.intersects(box)) {
					entry.getValue().render(g);
				}
	        }
			
			for(Creature c: creatures) {
				c.render(g);
			}
			
			if(Game.DEBUG) {
				g.setColor(Color.BLACK);

				g.drawString("X: " + Utils.getChunk(input.mousePosition.x, input.mousePosition.y).x, input.mousePosition.x + 16, input.mousePosition.y + 16);
				g.drawString("Y: " + Utils.getChunk(input.mousePosition.x, input.mousePosition.y).y, input.mousePosition.x + 16, input.mousePosition.y + 48);
				g.drawRect(Utils.toScreenX(collisionChunks.x),  Utils.toScreenY(collisionChunks.y),  collisionChunks.width,  collisionChunks.height);
			}

			Point beginChunks = Utils.getChunk(0, 0);
			Point endChunks = Utils.getChunk(Game.WIDTH, Game.HEIGHT);

			for (int y = beginChunks.y; y <= endChunks.y; y++) {
				for (int x = beginChunks.x; x <= endChunks.x; x++) {
					if (!chunks.containsKey(new Point(x, y))) {
						chunks.put(new Point(x, y), new Chunk(x * Game.CHUNK_SIZE * Game.BLOCK_SIZE, y * Game.CHUNK_SIZE * Game.BLOCK_SIZE, null));
					}
				}
			}
		}
	}
	
	public void addCreature(Creature c) {
		creatures.add(c);
	}
}
