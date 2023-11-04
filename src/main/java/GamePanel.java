import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	public ImageClass imageClass = new ImageClass();
	public ImageIcon rightmouth = imageClass.rightmouth;
	public ImageIcon leftmouth = imageClass.leftmouth;
	public ImageIcon upmouth = imageClass.upmouth;
	public ImageIcon downmouth = imageClass.downmouth;
	public ImageIcon snakeimage = imageClass.snakeimage;
	public ImageIcon enemy = imageClass.enemy;

	public int[] snakexlength;
	public int[] snakeylength;
	public int lengthOfSnake;

	public SecureRandom random = new SecureRandom();
	public int enemyY;
	public int enemyX;

	public int[] xPos;
	public int[] yPos;

	public boolean left = false;
	public boolean right = true;
	public boolean up = false;
	public boolean down = false;

	public boolean gameOver;

	public int width, height;
	public int numXPositions, numYPositions;

	public int moves;
	public int score;

	public Timer timer;
	public int delay;

	boolean flag = true;
	public int percent;
	public int percent2;
	public int OriginX;
	public int OriginY;
	public int OriginY2;
	public int Rheight;
	public int Sx0;
	public int Sx1;
	public int Sx2;
	public int Sy0;
	public int font1;
	public int font2;
	public int height2;
	public int height3;
	public int w1;
	public int h1;
	public int o1;
	public int o2;

	public ArrayList<GameState> gameStates;
	public int currentStateIndex;

	public boolean replay = true;
	public Connection connection = null;

	public boolean testing;

	public void assignVariables() {
		Properties properties = new Properties();

		try {
			if (testing) {
				throw new IOException("Simulated exception");
			}
			try (InputStream input = GamePanel.class.getClassLoader().getResourceAsStream("GamePanel.properties")) {
				properties.load(input);
			}
		} catch (IOException e) {
			System.out.println("Exception handled");
			return;
		}
		percent = Integer.parseInt(properties.getProperty("percent"));
		percent2 = Integer.parseInt(properties.getProperty("percent2"));
		lengthOfSnake = Integer.parseInt(properties.getProperty("lengthOfSnake"));
		moves = Integer.parseInt(properties.getProperty("moves"));
		score = Integer.parseInt(properties.getProperty("score"));
		delay = Integer.parseInt(properties.getProperty("delay"));
		OriginX = Integer.parseInt(properties.getProperty("OriginX"));
		OriginY = Integer.parseInt(properties.getProperty("OriginY"));
		OriginY2 = Integer.parseInt(properties.getProperty("OriginY2"));
		Rheight = Integer.parseInt(properties.getProperty("Rheight"));
		Sx0 = Integer.parseInt(properties.getProperty("Sx0"));
		Sx1 = Integer.parseInt(properties.getProperty("Sx1"));
		Sx2 = Integer.parseInt(properties.getProperty("Sx2"));
		Sy0 = Integer.parseInt(properties.getProperty("Sy0"));
		font1 = Integer.parseInt(properties.getProperty("font1"));
		font2 = Integer.parseInt(properties.getProperty("font2"));
		height2 = Integer.parseInt(properties.getProperty("height2"));
		height3 = Integer.parseInt(properties.getProperty("height3"));
		w1 = Integer.parseInt(properties.getProperty("w1"));
		h1 = Integer.parseInt(properties.getProperty("h1"));
		url = properties.getProperty("database.url");
		username = properties.getProperty("database.username");
		password = properties.getProperty("database.password");
		o1 = Integer.parseInt(properties.getProperty("o1"));
		o2 = Integer.parseInt(properties.getProperty("o2"));
	}

	String url;
	String username;
	String password;

	public void Con() {
		try {
			if (testing) {
				throw new SQLException("Simulated exception");
			}
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("exception handled");
			return;
		}
	}

	boolean t2test;

	public GamePanel(int windowWidth, int windowHeight, boolean test) {
		gameOver = false;
		assignVariables();
		Con();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		gameStates = new ArrayList<GameState>();
		width = windowWidth;
		height = windowHeight;
		numXPositions = width / percent;
		numYPositions = (height - percent2) / percent;
		snakexlength = new int[numXPositions * numYPositions];
		snakeylength = new int[numXPositions * numYPositions];
		xPos = new int[numXPositions];
		yPos = new int[numYPositions];

		for (int i = 0; i < numXPositions; i++) {
			xPos[i] = percent + i * percent;
		}

		for (int i = 0; i < numYPositions; i++) {
			yPos[i] = percent2 + i * percent;
		}

		newEnemy();

		if (connection != null) {
			timer = new Timer(delay, this);
			timer.start();
		}
		if (test) {
			left = true;
			right = true;
			up = true;
			down = true;
			gameOver = true;
			snakexlength[0] = snakexlength[2];
			snakeylength[0] = snakeylength[2];
			collidesWithBody();
			enemyX = snakexlength[0];
			enemyY = snakeylength[0];
			collidesWithEnemy();
			snakexlength[0] = width;
			snakeylength[0] = width;
			closeGameWindow();
			t2test = true;
			replayGame();
		}
	}

	int t = 0;

	public void newEnemy() {
		if (t > 0) {
			enemyX = xPos[random.nextInt(numXPositions - 2)];
			enemyY = yPos[random.nextInt(numYPositions - 2)];
		}
		for (int i = lengthOfSnake - 1; i >= 0; i--) {
			if ((snakexlength[i] == enemyX && snakeylength[i] == enemyY) || (testing && t < 1)) {
				testing = false;
				t++;
				newEnemy();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.WHITE);
		g.drawRect(percent - 1, OriginY, o1, Rheight);
		g.drawRect(percent - 1, percent2 - 1, o1, o2);
		g.setColor(Color.BLACK);
		g.fillRect(percent, percent2, o1 - 2, o2);

		if (moves == 0) {
			snakexlength[0] = Sx0;
			snakexlength[1] = Sx1;
			snakexlength[2] = Sx2;

			snakeylength[0] = Sy0;
			snakeylength[1] = Sy0;
			snakeylength[2] = Sy0;

			int textX = width / 3;
			int fontSize = width / 32;
			int textY1 = height / 4;
			int textY2 = height / 4 + 40;
			int textY3 = height / 4 + 70;
			int textY4 = height / 4 + 100;
			int textY5 = height / 4 + 130;

			if (!gameOver) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, fontSize));
				g.drawString("Welcome to Snake Game!", textX, textY1);
				g.setFont(new Font("Arial", Font.PLAIN, (int) (fontSize / (2))));
				g.drawString("Use arrow keys to move the snake.", textX, textY2);
				g.drawString("Coincide with Enemy to increase snake's length and score.", textX, textY3);
				g.drawString("Press 'R' to replay the game.", textX, textY4);
				g.drawString("Press 'Q' to quit the game.", textX, textY5);
			}
		}

		if (left) {
			leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if (right) {
			rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if (up) {
			upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}
		if (down) {
			downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		}

		for (int i = 1; i < lengthOfSnake; i++) {
			snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
		}
		enemy.paintIcon(this, g, enemyX, enemyY);

		if (gameOver) {
			int textX = width / 3;
			int fontSize = width / 32;
			int textY1 = height / 4;
			int textY2 = height / 4 + 40;
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, fontSize));
			g.drawString("Game Over", textX, textY1);
			g.setFont(new Font("Arial", Font.PLAIN, font1));
			g.drawString("Space: Delete Replay & Restart | R: View Replay | Q: Quit Game", textX - 100, textY2 + 10);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, font2));
		g.drawString("Score : " + score, (int) (width / 1.2), height2);
		g.drawString("length : " + lengthOfSnake, (int) (width / 1.2), height3);
		g.drawString("press Q to quit game", w1, h1);
		g.dispose();
	}

	public void saveData(int[] snakeX, int[] snakeY, int enemyX, int enemyY, int moves, int score, int lengthOfSnake,
			boolean left, boolean right, boolean up, boolean down) {

		Object[] intArrayX = new Object[snakeX.length];
		Object[] intArrayY = new Object[snakeY.length];

		for (int i = 0; i < snakeX.length; i++) {
			intArrayX[i] = snakeX[i];
			intArrayY[i] = snakeY[i];
		}

		String insertQuery = "INSERT INTO game_states (snakeX, snakeY, enemyX, enemyY, moves, score, lengthOfSnake, isleft, isright, isup, isdown) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);) {
			preparedStatement.setArray(1, connection.createArrayOf("integer", intArrayX));
			preparedStatement.setArray(2, connection.createArrayOf("integer", intArrayY));
			preparedStatement.setInt(3, enemyX);
			preparedStatement.setInt(4, enemyY);
			preparedStatement.setInt(5, moves);
			preparedStatement.setInt(6, score);
			preparedStatement.setInt(7, lengthOfSnake);
			preparedStatement.setBoolean(8, left);
			preparedStatement.setBoolean(9, right);
			preparedStatement.setBoolean(10, up);
			preparedStatement.setBoolean(11, down);
			preparedStatement.executeUpdate();
			if (testing) {
				throw new SQLException("Simulated exception");
			}
		} catch (SQLException e) {
			System.out.println("exception handled");
			return;
		}
	}

	public void deleteData() {
		String deleteQuery = "DELETE FROM game_states";
		try {
			if (testing) {
				throw new SQLException("Simulated exception");
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("exception handled");
			return;
		}
	}

	public ResultSet resultSet;

	public void getData() {
		gameStates.clear();
		String selectQuery = "SELECT * FROM game_states";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Integer[] snakeXArray = (Integer[]) resultSet.getArray("snakeX").getArray();
				Integer[] snakeYArray = (Integer[]) resultSet.getArray("snakeY").getArray();
				snakexlength = new int[snakeXArray.length];
				snakeylength = new int[snakeYArray.length];

				for (int i = 0; i < snakeXArray.length; i++) {
					snakexlength[i] = snakeXArray[i];
					snakeylength[i] = snakeYArray[i];
				}
				enemyX = resultSet.getInt("enemyX");
				enemyY = resultSet.getInt("enemyY");
				moves = resultSet.getInt("moves");
				score = resultSet.getInt("score");
				lengthOfSnake = resultSet.getInt("lengthOfSnake");
				left = resultSet.getBoolean("isleft");
				right = resultSet.getBoolean("isright");
				up = resultSet.getBoolean("isup");
				down = resultSet.getBoolean("isdown");
				gameStates.add(new GameState(snakexlength.clone(), snakeylength.clone(), enemyX, enemyY, moves, score,
						lengthOfSnake, left, right, up, down));
			}
			if (testing) {
				throw new SQLException("Simulated exception");
			}
		} catch (SQLException e) {
			System.out.println("exception handled");
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = lengthOfSnake - 1; i > 0; i--) {
			snakexlength[i] = snakexlength[i - 1];
			snakeylength[i] = snakeylength[i - 1];
		}
		if (left) {
			snakexlength[0] = snakexlength[0] - percent;
		}
		if (right) {
			snakexlength[0] = snakexlength[0] + percent;
		}
		if (up) {
			snakeylength[0] = snakeylength[0] - percent;
		}
		if (down) {
			snakeylength[0] = snakeylength[0] + percent;
		}
		if (snakexlength[0] > width - 2 * percent)
			snakexlength[0] = percent;
		if (snakexlength[0] < percent)
			snakexlength[0] = width - 2 * percent;
		if (snakeylength[0] > height - percent2)
			snakeylength[0] = percent2;
		if (snakeylength[0] < percent2)
			snakeylength[0] = height - percent2;
		collidesWithEnemy();
		collidesWithBody();
		if (!gameOver && flag) {
			gameStates.add(new GameState(snakexlength.clone(), snakeylength.clone(), enemyX, enemyY, moves, score,
					lengthOfSnake, left, right, up, down));
		}
		repaint();
	}

	public void collidesWithBody() {
		for (int i = lengthOfSnake - 1; i > 0; i--) {
			if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
				timer.stop();
				gameOver = true;
			}
		}
	}

	public void collidesWithEnemy() {
		if (snakexlength[0] == enemyX && snakeylength[0] == enemyY) {
			newEnemy();
			lengthOfSnake++;
			score++;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			deleteData();
			restart();
			resetGame();
			flag = true;
			replay = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !right) {
			left = true;
			right = false;
			up = false;
			down = false;
			moves++;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
			left = false;
			right = true;
			up = false;
			down = false;
			moves++;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
			left = false;
			right = false;
			up = true;
			down = false;
			moves++;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
			left = false;
			right = false;
			up = false;
			down = true;
			moves++;
		}

		if (e.getKeyCode() == KeyEvent.VK_R) {
			dataSaveToDB();
			moves++;
			resetGame();
			getData();
			restart();
			replayGame();
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			restart();
			resetGame();
			flag = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			int choice = JOptionPane.showConfirmDialog(this, "Do you want to quit the game?", "Quit Game",
					JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				closeGameWindow();
			}
		}
	}

	// Method to close the game window
	public void closeGameWindow() {
		Container container = this.getTopLevelAncestor();

		if (container instanceof JFrame) {
			JFrame frame = (JFrame) container;
			frame.dispose(); // Close the JFrame
		}
	}

	public void dataSaveToDB() {
		if (replay) {
			for (int i = 0; i < gameStates.size(); i++) {
				GameState state = gameStates.get(i);
				saveData(state.snakeX.clone(), state.snakeY.clone(), state.enemyX, state.enemyY, state.moves,
						state.score, state.lengthOfSnake, state.left, state.right, state.up, state.down);
			}
			replay = false;
		}
		flag = false;
	}

	Timer replayTimer;

	public void replayGame() {
		if ((!gameStates.isEmpty() && !gameOver) || t2test) {

			replayTimer = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (currentStateIndex < gameStates.size() - 1) {
						currentStateIndex++;
						GameState state = gameStates.get(currentStateIndex);
						snakexlength = state.snakeX.clone();
						snakeylength = state.snakeY.clone();
						enemyX = state.enemyX;
						enemyY = state.enemyY;
						moves = state.moves;
						score = state.score;
						lengthOfSnake = state.lengthOfSnake;
						left = state.left;
						right = state.right;
						up = state.up;
						down = state.down;
						repaint();
						collidesWithBody();
						if (gameOver || t2test) {
							((Timer) e.getSource()).stop();
						}
					}
				}
			});
			replayTimer.start();
		}
	}

	public void resetGame() {
		gameStates.clear();
		currentStateIndex = -1;
		gameOver = false;
		flag = true;
		replay = true;
		moves = 0;
		score = 0;
		lengthOfSnake = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();
		repaint();
		newEnemy();
	}

	public void restart() {
		gameOver = false;
		moves = 0;
		score = 0;
		lengthOfSnake = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();
		repaint();
		newEnemy();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	KeyEvent leftKeyEvent;
	KeyEvent rightKeyEvent;
	KeyEvent upKeyEvent;
	KeyEvent downKeyEvent;
	KeyEvent rKeyEvent;
	KeyEvent qKeyEvent;

	public void triggerAllOptions() {
		// Simulate the LEFT key press
		leftKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_LEFT, ' ');
		keyPressed(leftKeyEvent);
		// Simulate the RIGHT key press
		left = false;
		rightKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, ' ');
		keyPressed(rightKeyEvent);
		left = true;
		right = false;
		// Simulate the UP key press
		upKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP,
				' ');
		keyPressed(upKeyEvent);

		// Simulate the DOWN key press
		up = false;
		downKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_DOWN, ' ');
		keyPressed(downKeyEvent);

		// Simulate the LEFT key press
		KeyEvent leftKeyEvent5 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_LEFT, ' ');
		keyPressed(leftKeyEvent5);

		// Simulate the RIGHT key press
		KeyEvent rightKeyEvent5 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, ' ');
		keyPressed(rightKeyEvent5);

		// Simulate the UP key press
		KeyEvent upKeyEvent5 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP,
				' ');
		keyPressed(upKeyEvent5);

		// Simulate the DOWN key press
		KeyEvent downKeyEvent5 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_DOWN, ' ');
		keyPressed(downKeyEvent5);

		// Simulate the LEFT key press
		KeyEvent leftKeyEvent1 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_LEFT, ' ');
		keyPressed(leftKeyEvent1);

		// Simulate the RIGHT key press
		KeyEvent rightKeyEvent1 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, ' ');
		keyPressed(rightKeyEvent1);

		// Simulate the UP key press
		KeyEvent upKeyEvent1 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP,
				' ');
		keyPressed(upKeyEvent1);

		// Simulate the DOWN key press
		KeyEvent downKeyEvent1 = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_DOWN, ' ');
		keyPressed(downKeyEvent1);

		// Simulate the R key press
		rKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R,
				' ');
		keyPressed(rKeyEvent);
		dataSaveToDB();

		// Simulate the S key press
		KeyEvent sKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S,
				' ');
		keyPressed(sKeyEvent);

		// Simulate the SPACE key press
		KeyEvent spaceKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_SPACE, ' ');
		keyPressed(spaceKeyEvent);

		// Simulate the Q key press
		qKeyEvent = new KeyEvent(this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Q,
				' ');
		keyPressed(qKeyEvent);
	}

}
