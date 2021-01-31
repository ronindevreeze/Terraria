package com.terarria.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.terarria.main.blocks.Air;
import com.terarria.main.blocks.Block;
import com.terarria.main.blocks.Stone;
import com.terarria.main.blocks.TYPE;
import com.terarria.main.creatures.Creature;
import com.terarria.main.creatures.Player;
import com.terarria.main.utils.Utils;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -8921419424614180143L;
	public static boolean DEBUG = false;
	private boolean running = false;
	private Thread thread;
	private Window frame;

	public static final int WIDTH = 1400;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Game";

	public static Color skyColor = new Color(227, 248, 255);
	public static final double TERRAIN_SIZE = 0.003D;
	public static String saveName = "";
	public static final boolean loadSave = true;
	
	public static final int BLOCK_SIZE = 32;
	public static final int CHUNK_SIZE = 8;
	public static final Point CHUNKS = new Point(32, 32);

	Input input = new Input(this);
	public GameHandler gameHandler = new GameHandler(input);

	public Creature player = new Player(256, 256, input, gameHandler);
	public Creature focus = player;
	
	public static int focusX = 0;
	public static int focusY = 0;

	public Game(String name) {
		saveName = name;
		System.out.println(name);
		frame = new Window(WIDTH, HEIGHT, TITLE, this);
		addKeyListener(input);
		addMouseMotionListener(input);
		addMouseListener(input);
		addMouseWheelListener(input);
		
		gameHandler.setChunks(load(), new File(System.getProperty("user.dir") + "/com/terarria/main/saves/" + saveName).exists());
		gameHandler.addCreature(player);
	}

	public static void main(String[] args) {
		new Game(args[0]);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
	    double tps = 60.0;
	    double ns = 1000000000 / tps;
	    double delta = 0;
	    long timer = System.currentTimeMillis();
	    int frames = 0;
	    
	    while(running) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;
	        
	        while(delta  >= 1) {
	        	tick();
	        	delta--;
	        }
	        if(running) {
	        	render();
		        frames++;
	        }
	        
	        
	        if(System.currentTimeMillis() - timer > 1000) {
	        	timer += 1000;
	        	System.out.println("FPS: " + frames);
	        	frames = 0;
	        }
	    }
	    stop();
	}
	
	private void render() {
		// Render
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(skyColor);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		gameHandler.render(g);

		g.dispose();
		bs.show();
	}

	private void tick() {
		focusX = -focus.x;
		focusY = -focus.y;
		gameHandler.tick();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<Point, HashMap<Point, TYPE>> load() {
		System.out.println("Loading");
		
		try {
			HashMap<Point, HashMap<Point, TYPE>> chunks = null;
			
			if(new File(System.getProperty("user.dir") + "/com/terarria/main/saves/" + saveName).exists()) {
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/com/terarria/main/saves/" + saveName);
		         ObjectInputStream ois = new ObjectInputStream(fis);
		         
		         chunks = (HashMap<Point, HashMap<Point, TYPE>>) ois.readObject();
		         
		         if(loadSave) {
			         player.x = ois.readInt();
			         player.y = ois.readInt();
		         }
		         
		         ois.close();
		         fis.close();
			}
			
			return chunks;
	    } catch(IOException ioe) {
	         ioe.printStackTrace();
	         return null;
	    } catch(ClassNotFoundException c) {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return null;
	    }
		
	}

	public void save() {
		System.out.println("Saving");
		
		try {
               FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/com/terarria/main/saves/" + saveName);
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               HashMap<Point, HashMap<Point, TYPE>> tempMap = getSaveChunks(gameHandler.chunks);
               oos.writeObject(tempMap);
               
               oos.writeInt(player.x);
               oos.writeInt(player.y);
               oos.close();
               fos.close();
               
               System.out.printf("Succesfully saved");
        } catch(IOException ioe) {
               ioe.printStackTrace();
        }
	}

	private HashMap<Point, HashMap<Point, TYPE>> getSaveChunks(Map<Point, Chunk> chunkMap) {

		HashMap<Point, HashMap<Point, TYPE>> savingChunks = new HashMap<Point, HashMap<Point, TYPE>>();
		HashMap<Point, TYPE> temp = new HashMap<Point, TYPE>();
		
		for (Map.Entry<Point, Chunk> entryChunk : chunkMap.entrySet()) {
			temp = new HashMap<Point, TYPE>();
			
			for(int y = 0; y < CHUNK_SIZE; y++) {
				for(int x = 0; x < CHUNK_SIZE; x++) {
					System.out.println("Putting " + entryChunk.getValue().blocks[x][y].type + " into " + x + ", " +  y);
					temp.put(new Point(x, y), entryChunk.getValue().blocks[x][y].type);
				}
			}
			
			savingChunks.put(entryChunk.getKey(), temp);
		}
		return savingChunks;
	}
}
