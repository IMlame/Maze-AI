import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	
	public static int globalFileCount = 620;
	public static int percentWalls = 30;
	public static int time = 0;
	public static final String TITLE = "MAZE";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	public static int[][] board = new int[20][20];

	public static final int INCWIDTH = WIDTH / board.length;
	public static final int INCHEIGHT = HEIGHT / board.length;

	private boolean running;

	public static int[][] finalPath;

	private void tick() {
		time++;
		if(time == 10) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[y].length; x++) {
					if((int)(Math.random()*100) <= percentWalls) {
						board[y][x] = -1;
					} else {
						board[y][x] = 0;
					}
				}
			}
			board[(int)(Math.random()*board.length)][(int)(Math.random()*board[0].length)] = -2;
			board[(int)(Math.random()*board.length)][(int)(Math.random()*board[0].length)] = -3;
			
			
			printFile(board, "Board");
			finalPath = MazeBacktracker.backtrack(board);
			
			//print to files
			printFile(finalPath, "Answer");
			
			time = 0;
			
			//increase file count
			globalFileCount++;
		}

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (finalPath != null) {
			for (int i = 0; i < finalPath.length; i++) {
				for (int j = 0; j < finalPath[i].length; j++) {
					if (finalPath[i][j] == -1) {
						g.setColor(Color.BLACK);
					} else if (finalPath[i][j] == -3) {
						g.setColor(Color.GREEN);
					} else if (finalPath[i][j] == -2) {
						g.setColor(Color.RED);
					} else if (finalPath[i][j] == 0) {
						g.setColor(Color.WHITE);
					} else {
						g.setColor(Color.yellow);
					}
					g.fillRect(INCWIDTH * j, INCHEIGHT * i, INCWIDTH, INCHEIGHT);
				}
			}
		}
		g.dispose();
		bs.show();
	}

	private void start() {
		new Thread(this, "MazeMain-Thread").start();
		if (!running) {
			running = true;
		}
	}

	private void stop() {
		if (running) {
			running = false;
		}
	}

	public void run() {
		double target = 60.0;
		double nsPerTick = 1000000000.0 / target;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double unprocessed = 0.0;
		int fps = 0;
		int tps = 0;
		boolean canRender = false;

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (unprocessed >= 1) {
				tick();
				unprocessed--;
				tps++;
				canRender = true;
			} else {
				canRender = false;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (canRender) {
				// render
				render();
				fps++;
			}

			if (System.currentTimeMillis() - 1000 > timer) {
				timer += 1000;
				System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
				fps = 0;
				tps = 0;
			}
		}

		System.exit(0);
	}
	
	public static void printFile(int[][] board, String location) {
		try {
			//print board to file
			PrintStream out = new PrintStream(new File("C:/Users/s-lame/OneDrive - Bellevue School District/Eclipse/Maze/" + location + "/" + globalFileCount + ".txt"));
			for(int[] y : board) {
				String num = "";
				for(int x : y) {
					num += "" + x + ",";
				}
				out.println(num.substring(0, num.length()-1));
			}
			out.println("\n");
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Main mazer = new Main();
		JFrame frame = new JFrame(TITLE);
		frame.add(mazer);
		frame.setSize(WIDTH + 5, HEIGHT + 35);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mazer.stop();
				System.exit(0);
			}
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		mazer.start();

	}

}