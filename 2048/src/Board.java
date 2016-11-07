	import java.awt.*;
	import java.awt.event.*;

	import javax.swing.*;

	@SuppressWarnings("serial")
	public class Board extends JPanel {
		public boolean playing = false; // whether the game is running
		//game board size
		public static final int COURT_WIDTH = 650;
		public static final int COURT_HEIGHT = 650;
		public static final int INTERVAL = 5;
		private Tile[][] tiles = new Tile[4][4];
		boolean moved = false;
		boolean isWin = false;
		boolean isLose = false;
		
		public Board() {
			setBackground(Color.lightGray);
		
			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();	
					if (playing) {
						if (key == KeyEvent.VK_DOWN) {
							down();
						}
						else if (key == KeyEvent.VK_UP) {
							up();
						}
						else if (key == KeyEvent.VK_LEFT) {
							left();
						}
						else if (key == KeyEvent.VK_RIGHT) {
							right();
						}
						//change all merge back to true for next step
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								if (tiles[i][j] != null) {
									tiles[i][j].mergeTrue();
								}
							}
						}
						moved = false;
						checkWin();
						checkLose();
						repaint();
					
					}
					
				}
			});
		}
		//check if there exists a tile with val 2048
		private void checkWin() {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (tiles[i][j]!= null && tiles[i][j].getVal() == 2048) {
						playing = false;
					}
				}
			}
		}
		
		private void checkLose() {
			isLose = false;
			
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					//If any tiles are still null then game is not over
					if (tiles[i][j] == null) {
						return;
					}
					if (tiles[i][j]!= null && j+1 <= 3) {
						if (tiles[i][j + 1] != null) {
							if (tiles[i][j].getVal() == 
									tiles[i][j+1].getVal()) {
								return;
							}
						}
					}
					if (tiles[i][j]!= null && j-1 >= 0) {
						if (tiles[i][j - 1] != null) {
							if (tiles[i][j].getVal() == 
									tiles[i][j-1].getVal()) {
								return;
							}
						}
					}
					if (tiles[i][j]!= null && i+1 <= 3) {
						if (tiles[i+1][j] != null) {
							if (tiles[i][j].getVal() == 
									tiles[i+1][j].getVal()) {
								return;
							}
						}
					}
					if (tiles[i][j]!= null && i-1 >= 0) {
						if (tiles[i-1][j] != null) {
							if (tiles[i][j].getVal() == 
									tiles[i-1][j].getVal()) {
								return;
							}
						}
					}
					
				}
			}
			isLose = true;
			playing = false;
			
		}
		
		//rotate the array clockwise 90 degrees a certain number of times
		//return new tile 2d array
		private Tile[][] rotate (Tile[][] a) {
			Tile[][] ret = new Tile[4][4];

			//1 for left, 2 for up, 3 for right
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						ret[i][j] = a[j][3-i];
					}
				}					
			return ret;
		}
		
		
		//when you press down
		private void down() {			
			//loop through top 3 rows
			for (int j = 2; j >= 0; j--) {
				for (int i = 3; i >= 0; i--) {
					//only check when the tile is occupied
					if (tiles[i][j] != null) {
						int z = 0;
						//you can move any tile a maximum of three times						
						while (j + z <= 2) {
							if (tiles[i][j + z + 1] == null) {
								tiles[i][j+z+1] = tiles[i][j + z];
								tiles[i][j + z] = null;
								moved = true;
								z++;
							}
							else if (tiles[i][j+z].mergable(tiles[i][j+z+1]) && 
									tiles[i][j+z].getCanMerge() && 
									tiles[i][j+z+1].getCanMerge()) {
								tiles[i][j+z+1] = new Tile(tiles[i][j+z].getVal() * 2);
								tiles[i][j+z+1].mergeFalse();
								//tiles[i][j+z].mergeFalse();
								tiles[i][j+z] = null;
								moved = true;
								z++;
							}	
							
							else {
								break;
							}

						}
					}

				}
				
			}
			//add tile if the keyboard press moved any of the existing tiles
			if (moved) {
				addTile();
			}
		}
		

		
		//randomly add a tile to the page
		//90% of the time 2 shows up, 10% a 4 shows up
		private void addTile() {
			int nx, ny;
				nx = (int) (Math.random() * 4);
				ny = (int) (Math.random() * 4);
			
			//set the new tile to be where there is an empty space
			while(tiles[nx][ny] != null) {
				nx = (int) (Math.random() * 4);
				ny = (int) (Math.random() * 4);
			}
			//10% chance
			if ((int) (Math.random() * 10) == 1) {
				tiles[nx][ny] = new Tile(4);
			}
			else {
				tiles[nx][ny] = new Tile(2);
			}
			
		}
		

		
		private void up() {
			int i = 0;
			while (i < 2) {
				tiles = rotate(tiles);
				i++;
			}
			down();
			i = 0;
			while (i < 2) {
				tiles = rotate(tiles);
				i++;
			}		
			}
		
		private void left() {
			int i = 0;
			while (i < 3) {
				tiles = rotate(tiles);
				i++;
			}
			down();
			i = 0;
			while (i < 1) {
				tiles = rotate(tiles);
				i++;
			}		
		}
		
		private void right() {
			int i = 0;
			while (i < 1) {
				tiles = rotate(tiles);
				i++;
			}
			down();
			i = 0;
			while (i < 3) {
				tiles = rotate(tiles);
				i++;
			}		
		}
		
		
		public void reset() {
			playing = true;
			isWin = false;
			isLose = false;
			moved = false;
			
			//clear the array, change to null instead of making new array
			//to save storage
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					tiles[i][j] = null;
				}
			}	
			int x1 = (int) (Math.random() * 4);
			int y1 = (int) (Math.random() * 4);
			int x2 = (int) (Math.random() * 4);
			int y2 = (int) (Math.random() * 4);
			while (y1 == y2 && x1 == x2) {
				y2 = (int) (Math.random() * 4);
				x2 = (int) (Math.random() * 4);
			}
			tiles[x1][y1] = new Tile(2);
			tiles[x2][y2] = new Tile(2);

			
			repaint(); //need to repaint the screen
			requestFocusInWindow();
		}
		

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	        

	        
			//2d array for drawing the background tiles
			g.setColor(new Color (173, 168, 154));
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					g.fillRoundRect(10 + i*160, 10 + j*160, 150, 150, 25, 25);
				}
			}
			
			//2d array for tiles
			//draws the tile if the designated tile exists
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (tiles[i][j] != null) {
						g.setColor(tiles[i][j].getC());
						g.fillRoundRect(10 + i*160, 10 + j*160, 150, 
								150, 25, 25);
						g.setColor(tiles[i][j].getWordC());
						
						int fontSize = tiles[i][j].setFontSize();						
				        g2.setFont(new Font("Arial", Font.BOLD, fontSize));
				        String display = Integer.toString(tiles[i][j].getVal());
						int displayWidth = g.getFontMetrics().
											stringWidth(display);
				        int adjustX = 12 + (145 - displayWidth)/2;
				        int displayHeight = g.getFontMetrics().getHeight();
						int adjustY = 90 + (138 - displayHeight + 
										(displayWidth + 40)/6)/3 - 
										3*(tiles[i][j].getValDigit()*2 - 5);
						
						//drawing the number
				        g2.drawString(display,
				        			adjustX + 160*i,adjustY + 160*j); 

					}
				}
			}
			
			g.setColor(Color.darkGray);
	        g2.setFont(new Font("Arial", Font.BOLD, 80));
			if(isLose) {
		        g.drawString("Game over!", COURT_WIDTH/6, COURT_HEIGHT/2);
		    }
			if(isWin) {
		        g.drawString("You Win!", COURT_WIDTH/4 - 6, COURT_HEIGHT/2);
			}
		}
		

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(COURT_WIDTH, COURT_HEIGHT);
		}
}


