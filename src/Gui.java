import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Gui {

	JFrame frame = new JFrame();

	public boolean resetter = false;

	Date startDate = new Date();
	Date endDate;

	public int screenHeight = 410;
	public int screenWidth = 654;

	int spacing = 5;

	String vicMes = "N";

	public int mx = -100;
	public int my = -100;

	public int smileyX = 300;
	public int smileyY = 2;

	public int smileyCenterX = smileyX + 20;
	public int smileyCenterY = smileyY + 20;

	public int timeX = 574;
	public int timeY = 5;

	public int vicMesX = 374;
	public int vicMesY = -50;

	public int sec = 0;

	public boolean info = false;

	public boolean happiness = true;

	public boolean victory = false;

	public boolean defeat = false;

	int neighs = 0;

	public boolean prviKlik = false;
	public boolean start = false;

	Random rand = new Random();

	int[][] mines = new int[16][9];
	int[][] neighbours = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];

	public Gui() {

		frame.setTitle("MINESWEEPER");
		frame.setSize(654, 439);
		frame.setState(Frame.NORMAL);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				revealed[i][j] = false;
			}
		}

		Board board = new Board();
		frame.setContentPane(board);

		Move move = new Move();
		frame.addMouseMotionListener(move);

		Click click = new Click();
		frame.addMouseListener(click);

	}

	public class Board extends JPanel {

		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 654, 410);
			g.setColor(Color.GRAY);
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					g.setColor(Color.GRAY);

					/*
					 * if (mines[i][j] == 1) { g.setColor(Color.yellow); }
					 */

					if (revealed[i][j] == true) {
						g.setColor(Color.white);
						if (mines[i][j] == 1 && flagged[i][j] == false) {
							g.setColor(Color.red);
						}
					}

					if (mx >= spacing + i * 40 && mx < spacing + i * 40 + 40 - 2 * spacing
							&& my >= spacing + j * 40 + 40 + 26 && my < spacing + j * 40 + 40 + 26 + 40 - 2 * spacing) {
						g.setColor(Color.lightGray);
					}
					g.fillRect(spacing + i * 40, spacing + j * 40 + 40, 40 - 2 * spacing, 40 - 2 * spacing);
					if (revealed[i][j] == true) {
						g.setColor(Color.black);
						if (mines[i][j] == 0 && neighbours[i][j] != 0) {
							if (neighbours[i][j] == 1) {
								g.setColor(Color.blue);
							} else if (neighbours[i][j] == 2) {
								g.setColor(Color.green);
							} else if (neighbours[i][j] == 3) {
								g.setColor(Color.red);
							} else if (neighbours[i][j] == 4) {
								g.setColor(new Color(0, 0, 128));
							} else if (neighbours[i][j] == 5) {
								g.setColor(new Color(178, 34, 34));
							} else if (neighbours[i][j] == 6) {
								g.setColor(new Color(72, 209, 204));
							} else if (neighbours[i][j] == 7) {
								g.setColor(Color.black);
							} else if (neighbours[i][j] == 8) {
								g.setColor(Color.DARK_GRAY);
							}
							g.setFont(new Font("Tahoma", Font.BOLD, 20));
							g.drawString(Integer.toString(neighbours[i][j]), i * 40 + 14, j * 40 + 40 + 27);
						} else if (mines[i][j] == 1 && flagged[i][j] == false) {
							g.fillRect(i * 40 + 5 + 10, j * 40 + 40 + 5 + 5, 10, 20);
							g.fillRect(i * 40 + 5 + 5, j * 40 + 40 + 5 + 10, 20, 10);
							g.fillRect(i * 40 + 5 + 7, j * 40 + 40 + 5 + 7, 16, 16);
							g.fillRect(i * 40 + 5 + 14, j * 40 + 40 + 5 + 2, 2, 26);
							g.fillRect(i * 40 + 5 + 2, j * 40 + 40 + 5 + 14, 26, 2);

						}
					}

				}
			}

			// smiley painting
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 40, 40);
			g.setColor(Color.black);
			g.fillOval(smileyX + 7, smileyY + 10, 7, 7);
			g.fillOval(smileyX + 25, smileyY + 10, 7, 7);

			if (happiness == true) {
				g.fillRect(smileyX + 12, smileyY + 27, 17, 2);
				g.fillRect(smileyX + 11, smileyY + 25, 2, 2);
				g.fillRect(smileyX + 28, smileyY + 25, 2, 2);
			} else {
				g.fillRect(smileyX + 12, smileyY + 25, 17, 2);
				g.fillRect(smileyX + 11, smileyY + 27, 2, 2);
				g.fillRect(smileyX + 28, smileyY + 27, 2, 2);
			}

			// time counter painting
			g.setColor(Color.black);
			g.fillRect(timeX, timeY, 70, 35);
			if (victory == false && defeat == false && prviKlik == true) {
				sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
			}
			if (sec > 999) {
				sec = 999;
			}
			g.setColor(Color.white);
			if (defeat == true) {
				g.setColor(Color.red);
			}
			if (victory == true) {
				g.setColor(Color.green);
			}
			g.setFont(new Font("Tahoma", Font.PLAIN, 40));
			if (prviKlik == false) {
				g.drawString("000", timeX, timeY + 32);
			} else if (sec < 10) {
				g.drawString("00" + Integer.toString(sec), timeX, timeY + 32);
			} else if (sec < 100) {
				g.drawString("0" + Integer.toString(sec), timeX, timeY + 32);
			} else {
				g.drawString(Integer.toString(sec), timeX, timeY + 32);
			}

			// message painting

			if (victory == true) {
				g.setColor(Color.green);
				vicMes = "YOU WIN";
			} else if (defeat == true) {
				g.setColor(Color.red);
				vicMes = "YOU LOSE";
			}

			if (victory == true || defeat == true) {
				vicMesY = -50 + (int) ((new Date().getTime()) - (endDate.getTime())) / 10;
				if (vicMesY > 33) {
					vicMesY = 33;
				}
				g.setFont(new Font("Tahoma", Font.PLAIN, 35));
				g.drawString(vicMes, vicMesX, vicMesY);
			}

			// flag painting
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					if (flagged[i][j] == true) {
						Polygon p = new Polygon(new int[] { i * 40 + 5 + 17, i * 40 + 5 + 6, i * 40 + 5 + 17 },
								new int[] { j * 40 + 40 + 5 + 1, j * 40 + 40 + 5 + 9, j * 40 + 40 + 5 + 17 }, 3);
						g.setColor(Color.black);
						g.fillRect(i * 40 + 5 + 14, j * 40 + 40 + 5 + 6, 3, 18);
						g.fillRect(i * 40 + 5 + 11, j * 40 + 40 + 5 + 24, 9, 2);
						g.fillRect(i * 40 + 5 + 7, j * 40 + 40 + 5 + 26, 17, 4);
						g.setColor(Color.red);
						g.fillPolygon(p);
					}
				}
			}
			// flagged number
			int totalFlagged = 0;
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 9; j++) {
					if (flagged[i][j] == true) {
						totalFlagged++;
					}
				}
			}
			g.setColor(Color.black);
			g.fillRect(0, timeY, 47, 35);
			if (victory == true) {
				g.setColor(Color.green);
			} else
				g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.PLAIN, 40));
			int number = totalMines() - totalFlagged;
			if (number < 10) {
				g.drawString("0" + Integer.toString(number), 0, timeY + 32);
			} else {
				g.drawString(Integer.toString(number), 0, timeY + 32);
			}

			if (prviKlik == true) {
				start = true;
			}

			// info
			g.setColor(Color.black);
			g.fillRect(150, 2, 40, 40);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", Font.PLAIN, 40));
			g.drawString("?", 160, 37);
		}
	}

	public class Move implements MouseMotionListener {

		@Override
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void checkVictroyStatus() {
		if (start == true) {
			if (defeat == false) {
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 9; j++) {
						if (revealed[i][j] == true && mines[i][j] == 1) {
							defeat = true;
							happiness = false;
							for (int m = 0; m < 16; m++) {
								for (int n = 0; n < 9; n++) {
									if (flagged[m][n] == false) {
										revealed[m][n] = true;
									}
								}
							}
							endDate = new Date();
						}
					}
				}
			}

			if (totalMinesFlagged() >= totalMines() && victory == false) {
				victory = true;
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 9; n++) {
						if (flagged[m][n] == false) {
							revealed[m][n] = true;
						}
					}
				}
				endDate = new Date();
			}
		}
	}

	public int totalMines() {
		int total = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mines[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}

	public int totalMinesFlagged() {
		int total = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mines[i][j] == 1 && flagged[i][j] == true) {
					total++;
				}
			}
		}
		return total;
	}

	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (SwingUtilities.isLeftMouseButton(e)) {
				mx = e.getX();
				my = e.getY();

				if (inInfo() == true) {
					info = true;
				}
				if (inSmiley() == true) {
					resetALL();
				}

				if (prviKlik == false && inBoxX() != -1 && inBoxY() != -1) {
					startDate = new Date();
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 9; j++) {
							mines[i][j] = 1;
						}
					}
					if (inBoxX() != -1 && inBoxY() != -1 && flagged[inBoxX()][inBoxY()] == false) {
						mines[inBoxX()][inBoxY()] = 0;
						if (((inBoxX() - 1) >= 0 && (inBoxX() + 1) <= 15)
								&& ((inBoxY() - 1) >= 0 && (inBoxY() + 1) <= 8)) {
							mines[inBoxX() - 1][inBoxY() - 1] = 0;
							mines[inBoxX() + 1][inBoxY() - 1] = 0;
							mines[inBoxX() - 1][inBoxY() + 1] = 0;
							mines[inBoxX() + 1][inBoxY() + 1] = 0;
							mines[inBoxX() - 1][inBoxY()] = 0;
							mines[inBoxX() + 1][inBoxY()] = 0;
							mines[inBoxX()][inBoxY() - 1] = 0;
							mines[inBoxX()][inBoxY() + 1] = 0;
						} else if ((inBoxY() - 1) < 0) {
							if ((inBoxX() - 1) < 0) {
								mines[inBoxX() + 1][inBoxY() + 1] = 0;
								mines[inBoxX() + 1][inBoxY()] = 0;
								mines[inBoxX()][inBoxY() + 1] = 0;
							} else if ((inBoxX() + 1) > 15) {
								mines[inBoxX() - 1][inBoxY() + 1] = 0;
								mines[inBoxX() - 1][inBoxY()] = 0;
								mines[inBoxX()][inBoxY() + 1] = 0;
							} else {
								mines[inBoxX() - 1][inBoxY() + 1] = 0;
								mines[inBoxX() + 1][inBoxY() + 1] = 0;
								mines[inBoxX() - 1][inBoxY()] = 0;
								mines[inBoxX() + 1][inBoxY()] = 0;
								mines[inBoxX()][inBoxY() + 1] = 0;
							}

						} else if ((inBoxY() + 1) > 8) {
							if ((inBoxX() - 1) < 0) {
								mines[inBoxX()][inBoxY() - 1] = 0;
								mines[inBoxX() + 1][inBoxY() - 1] = 0;
								mines[inBoxX() + 1][inBoxY()] = 0;
							} else if ((inBoxX() + 1) > 15) {
								mines[inBoxX() - 1][inBoxY() - 1] = 0;
								mines[inBoxX() - 1][inBoxY()] = 0;
								mines[inBoxX()][inBoxY() - 1] = 0;

							} else {
								mines[inBoxX() - 1][inBoxY() - 1] = 0;
								mines[inBoxX() + 1][inBoxY() - 1] = 0;
								mines[inBoxX() - 1][inBoxY()] = 0;
								mines[inBoxX() + 1][inBoxY()] = 0;
								mines[inBoxX()][inBoxY() - 1] = 0;
							}

						} else if ((inBoxX() + 1) > 15) {
							mines[inBoxX() - 1][inBoxY() - 1] = 0;
							mines[inBoxX() - 1][inBoxY() + 1] = 0;
							mines[inBoxX() - 1][inBoxY()] = 0;
							mines[inBoxX()][inBoxY() - 1] = 0;
							mines[inBoxX()][inBoxY() + 1] = 0;
						} else if ((inBoxX() - 1) < 0) {
							mines[inBoxX() + 1][inBoxY() - 1] = 0;
							mines[inBoxX() + 1][inBoxY() + 1] = 0;
							mines[inBoxX() + 1][inBoxY()] = 0;
							mines[inBoxX()][inBoxY() - 1] = 0;
							mines[inBoxX()][inBoxY() + 1] = 0;
						}
					}
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 9; j++) {

							if (rand.nextInt(100) < 23 && mines[i][j] == 1) {
								revealed[i][j] = false;
							} else {
								mines[i][j] = 0;
								revealed[i][j] = false;
							}
						}
					}
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 9; j++) {
							neighs = 0;
							for (int m = 0; m < 16; m++) {
								for (int n = 0; n < 9; n++) {
									if (!(m == i && n == j)) {
										if (isN(i, j, m, n) == true) {
											neighs++;
										}
									}
								}
							}
							neighbours[i][j] = neighs;
						}
					}
					if (inSmiley() == false) {
						revealed[inBoxX()][inBoxY()] = true;
					}

					find_empty_cells(inBoxX(), inBoxY());
					prviKlik = true;
					int m = 0;
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 9; j++) {
							if (mines[i][j] == 1) {
								m++;
							}
						}
					}
					System.out.println(m);

				} else {
					if (victory == false && defeat == false) {
						if (inBoxX() != -1 && inBoxY() != -1 && flagged[inBoxX()][inBoxY()] == false) {
							revealed[inBoxX()][inBoxY()] = true;
							if (neighbours[inBoxX()][inBoxY()] == 0) {
								find_empty_cells(inBoxX(), inBoxY());
							}
						}
					}
					if (inSmiley() == true) {
						resetALL();
					}
				}
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				mx = e.getX();
				my = e.getY();
				if (victory == false && defeat == false) {
					if (inBoxX() != -1 && inBoxY() != -1) {
						if (flagged[inBoxX()][inBoxY()] == true) {
							flagged[inBoxX()][inBoxY()] = false;
						} else
							flagged[inBoxX()][inBoxY()] = true;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void resetALL() {

		resetter = true;
		prviKlik = false;
		start = false;
		info = false;

		vicMesY = -50;
		vicMes = "N";

		startDate = new Date();

		happiness = true;
		victory = false;
		defeat = false;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				revealed[i][j] = false;
				flagged[i][j] = false;
				mines[i][j] = 0;
			}
		}
		resetter = false;
	}

	public boolean inSmiley() {
		int dif = (int) Math
				.sqrt(Math.pow(Math.abs(mx - smileyCenterX), 2) + Math.pow(Math.abs(my - smileyCenterY), 2));
		if (dif < 35) {
			return true;
		}
		return false;
	}

	public boolean inInfo() {
		if (mx > 153 && mx < 190 && my > 2 && my < 72) {
			return true;
		}
		return false;
	}

	public int inBoxX() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mx >= spacing + i * 40 && mx < spacing + i * 40 + 40 - 2 * spacing
						&& my >= spacing + j * 40 + 40 + 26 && my < spacing + j * 40 + 40 + 26 + 40 - 2 * spacing) {
					return i;
				}
			}
		}
		return -1;
	}

	public int inBoxY() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 9; j++) {
				if (mx >= spacing + i * 40 && mx < spacing + i * 40 + 40 - 2 * spacing
						&& my >= spacing + j * 40 + 40 + 26 && my < spacing + j * 40 + 40 + 26 + 40 - 2 * spacing) {
					return j;
				}
			}
		}
		return -1;
	}

	public boolean isN(int mX, int mY, int cX, int cY) {
		if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1) {
			return true;
		}
		return false;
	}

	public void find_empty_cells(int j, int i) {

		int current_col = j % 16;
		int cellX;
		int cellY;
		int cellX1 = -1;
		int cellY1 = -1;
		int cellX2 = -1;
		int cellY2 = -1;
		int cellX3 = -1;
		int cellY3 = -1;
		int cellX4 = -1;
		int cellY4 = -1;
		int cellX5 = -1;
		int cellY5 = -1;
		int cellX6 = -1;
		int cellY6 = -1;
		int cellX7 = -1;
		int cellY7 = -1;
		int cellX8 = -1;
		int cellY8 = -1;

		if (current_col > 0) {
			cellY = i - 1;
			cellX = j - 1;
			if (cellX >= 0 && cellY >= 0) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX1 = cellX;
						cellY1 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}

			cellY = i;
			cellX = j - 1;
			if (cellX >= 0 && cellY >= 0) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX2 = cellX;
						cellY2 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}

			cellY = i + 1;
			cellX = j - 1;
			if (cellY < 9) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX3 = cellX;
						cellY3 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}
		}

		cellY = i - 1;
		cellX = j;
		if (cellX >= 0 && cellY >= 0) {
			if (!(mines[cellX][cellY] == 1)) {
				if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false && flagged[cellX][cellY] != true) {
					cellX4 = cellX;
					cellY4 = cellY;
				}
				if (flagged[cellX][cellY] != true) {
					revealed[cellX][cellY] = true;
				}
			}
		}

		cellY = i + 1;
		cellX = j;
		if (cellY < 9) {
			if (!(mines[cellX][cellY] == 1)) {
				if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false && flagged[cellX][cellY] != true) {
					cellX5 = cellX;
					cellY5 = cellY;
				}
				if (flagged[cellX][cellY] != true) {
					revealed[cellX][cellY] = true;
				}
			}
		}

		if (current_col < 15) {
			cellY = i - 1;
			cellX = j + 1;
			if (cellX >= 0 && cellY >= 0) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX6 = cellX;
						cellY6 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}

			cellY = i;
			cellX = j + 1;
			if (cellX >= 0 && cellY >= 0) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX7 = cellX;
						cellY7 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}

			cellY = i + 1;
			cellX = j + 1;
			if (cellY < 9) {
				if (!(mines[cellX][cellY] == 1)) {
					if (neighbours[cellX][cellY] == 0 && revealed[cellX][cellY] == false
							&& flagged[cellX][cellY] != true) {
						cellX8 = cellX;
						cellY8 = cellY;
					}
					if (flagged[cellX][cellY] != true) {
						revealed[cellX][cellY] = true;
					}
				}
			}

		}
		if (cellX1 >= 0 && cellY1 >= 0) {
			find_empty_cells(cellX1, cellY1);
		}
		if (cellX2 >= 0 && cellY2 >= 0) {
			find_empty_cells(cellX2, cellY2);
		}
		if (cellX3 >= 0 && cellY3 >= 0) {
			find_empty_cells(cellX3, cellY3);
		}
		if (cellX4 >= 0 && cellY4 >= 0) {
			find_empty_cells(cellX4, cellY4);
		}
		if (cellX5 >= 0 && cellY5 >= 0) {
			find_empty_cells(cellX5, cellY5);
		}
		if (cellX6 >= 0 && cellY6 >= 0) {
			find_empty_cells(cellX6, cellY6);
		}
		if (cellX7 >= 0 && cellY7 >= 0) {
			find_empty_cells(cellX7, cellY7);
		}
		if (cellX8 >= 0 && cellY8 >= 0) {
			find_empty_cells(cellX8, cellY8);
		}
	}

}