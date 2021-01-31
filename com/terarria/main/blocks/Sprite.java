package com.terarria.main.blocks;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Sprite implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2819063835425232389L;
	static String path = "C:\\Users\\ronin\\eclipse-workspace\\Terraria\\com\\terarria\\main\\blocks\\textures\\New Piskel (9).png";
	Image image;

	public static final Sprite[] grass = createFrames(path, 0, 0, 8, 8, 4);
	public static final Sprite[] dirt = createFrames(path, 8, 0, 8, 8, 4);
	public static final Sprite[] wood = createFrames(path, 16, 0, 8, 8, 4);
	public static final Sprite[] stone = createFrames(path, 24, 0, 8, 8, 4);

	public static final Sprite[] itemGrass = createFrames(path, 0, 0, 4, 4, 1);
	public static final Sprite[] itemDirt = createFrames(path, 4, 0, 4, 4, 1);
	public static final Sprite[] itemWood = createFrames(path, 8, 0, 4, 4, 1);
	public static final Sprite[] itemStone = createFrames(path, 12, 0, 4, 4, 1);
	
	public Sprite(String sheet, int x, int y, int width, int height) {
		try {
			image = ImageIO.read(new File(sheet)).getSubimage(x, y, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Sprite[] createFrames(String sheet, int x, int y, int cellWidth, int cellHeight, int amount) {
		Sprite[] list = new Sprite[amount];
		for(int i = 0; i < amount; i++) {
			list[i] = new Sprite(sheet, x, y + (cellHeight * i), cellWidth, cellHeight);
		}
		return list;
	}
}
