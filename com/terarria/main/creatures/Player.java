package com.terarria.main.creatures;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.terarria.main.Game;
import com.terarria.main.GameHandler;
import com.terarria.main.Input;
import com.terarria.main.blocks.Air;
import com.terarria.main.blocks.Block;
import com.terarria.main.blocks.ITEM_TYPES;
import com.terarria.main.blocks.Wood;
import com.terarria.main.utils.Utils;

public class Player extends Creature {

	Input input;
	GameHandler handler;
	boolean onGround = false;
	public Map<ITEM_TYPES, Integer> inventory = new HashMap<ITEM_TYPES, Integer>();
	public ITEM_TYPES heldItem = ITEM_TYPES.wood;
	int speed = 3;

	public Player(int x, int y, Input input, GameHandler handler) {
		super(x, y);
		this.input = input;
		this.handler = handler;
		inventory.put(ITEM_TYPES.wood, 3);
	}
	
	public void tick() {
		heldItem = ITEM_TYPES.values()[input.scrollPosition % inventory.size()];

		getInput();
		
		for(int xx = 0; xx < Math.abs(velx); xx++) {
			for(Rectangle r: handler.getColliders(x, y)) {
				if(r.intersects(getBounds((int)Math.signum(velx), 0))) {
					velx = 0;
				}
			}
			
			x += Math.signum(velx);
		}
		
		for(int yy = 0; yy < Math.abs(vely); yy++) {
			for(Rectangle r: handler.getColliders(x, y)) {
				if(r.intersects(getBounds(0, (int)Math.signum(vely)))) {
					vely = 0;
				}
			}
			
			y += Math.signum(vely);
		}
		
		onGround = false;
		for(Rectangle r: handler.getColliders(x, y)) {
			if(r.intersects(getBounds(0, 1))) {
				onGround = true;
			}
		}
		
		if(input.mouseButtons[0] || input.mouseButtons[2]) {
			editBlocks();
		}
	}
	
	private void editBlocks() {
		int mouseScreenX = input.mousePosition.x;
		int mouseScreenY = input.mousePosition.y;
		
		int mouseWorldX = Utils.toWorldX(mouseScreenX);
		int mouseWorldY = Utils.toWorldY(mouseScreenY);
		
		int mouseCellX = (int) (Math.floor((double)mouseWorldX / (double)Game.BLOCK_SIZE));
		int mouseCellY = (int) (Math.floor((double)mouseWorldY / (double)Game.BLOCK_SIZE));
		
		int mouseChunkX = (int) (Math.floor((double)mouseCellX / (double)Game.CHUNK_SIZE));
		int mouseChunkY = (int) (Math.floor((double)mouseCellY / (double)Game.CHUNK_SIZE));
		
		int modCellX = Math.abs(mouseCellX % Game.CHUNK_SIZE);
		if(mouseCellX < 0) { modCellX = 8 - modCellX; }
		if(modCellX == 8) {  modCellX = 0; }
		int modCellY = Math.abs(mouseCellY % Game.CHUNK_SIZE);
		if(mouseCellY < 0) { modCellY = 8 - modCellY; }
		if(modCellY == 8) {  modCellY = 0; }

		if(input.mouseButtons[0]) {
			input.tick();
			if(handler.chunks.containsKey(new Point(mouseChunkX, mouseChunkY))) {
				if(modCellX < 8 && modCellX >= 0 && modCellY < 8 && modCellY >= 0) {
					if(handler.chunks.get(new Point(mouseChunkX, mouseChunkY)).blocks[modCellX][modCellY].breakable) {
						ITEM_TYPES drop = handler.chunks.get(new Point(mouseChunkX, mouseChunkY)).blocks[modCellX][modCellY].drop;
						if(inventory.containsKey(drop)) {
							inventory.replace(ITEM_TYPES.wood, inventory.get(drop) + 1);
						} else {
							inventory.put(drop, 1);
						}
						handler.chunks.get(new Point(mouseChunkX, mouseChunkY)).blocks[modCellX][modCellY] = new Air(mouseCellX * Game.BLOCK_SIZE, mouseCellY * Game.BLOCK_SIZE);
					}
				}
			}
		}
		
		if(input.mouseButtons[2]) {
			if(handler.chunks.containsKey(new Point(mouseChunkX, mouseChunkY))) {
				if(modCellX < 8 && modCellX >= 0 && modCellY < 8 && modCellY >= 0) {
					if(!handler.chunks.get(new Point(mouseChunkX, mouseChunkY)).blocks[modCellX][modCellY].solid) {
						if(inventory.containsKey(ITEM_TYPES.wood)) {
							if (inventory.get(ITEM_TYPES.wood) > 0) {
								inventory.replace(ITEM_TYPES.wood, inventory.get(ITEM_TYPES.wood) - 1);
								handler.chunks.get(new Point(mouseChunkX, mouseChunkY)).blocks[modCellX][modCellY] = new Wood(mouseCellX * Game.BLOCK_SIZE, mouseCellY * Game.BLOCK_SIZE);
							}
						}
					}
				}
			}
		}
	}

	private void getInput() {
		// Reset horizontal movement
		velx = 0;
		
		// Check horizontal movement;
		velx += input.LEFT * -speed;
		velx += input.RIGHT * speed;
		
		// Jump if on the ground
		if(onGround) {
			vely += input.UP * -speed * 4;
		}
		
		// Apply gravity if not going to fast
		if(vely < 20 && !onGround) {
			vely += 1;
		}
	}
	
	public void render(Graphics g) {		
		// Draw player
		g.setColor(new Color(80, 80, 80));
		g.fillRect(Game.WIDTH / 2, Game.HEIGHT / 2, Game.BLOCK_SIZE, Game.BLOCK_SIZE);

		g.drawString(heldItem.toString(), 32, 32);
		g.drawString(heldItem.toString(), 96, 32);
		
		// Draw coordinates, colliders, onGround and velocities
		if(Game.DEBUG) {
			// Draw colliders
			for(Rectangle r: handler.getColliders(x, y)) {
				g.fillRect(Utils.toScreenX(r.x), Utils.toScreenY(r.y), r.width, r.height);
			}
			
			// Draw coordinates, onGround and velocities
			g.drawString("X: " + x + " | Y: " + y, Game.WIDTH / 2 + 48, Game.HEIGHT / 2 - 16);
			g.drawString("vX: " + velx + " | vY: " + vely, Game.WIDTH / 2 + 48, Game.HEIGHT / 2);
			g.drawString("onGround: " + onGround, Game.WIDTH / 2 + 48, Game.HEIGHT / 2 + 16);
		}
	}

	public Rectangle getBounds(int offsetX, int offsetY) {
		return new Rectangle(x + offsetX, y + offsetY, Game.BLOCK_SIZE, Game.BLOCK_SIZE);
	}

}
