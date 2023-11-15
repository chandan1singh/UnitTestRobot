import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePanelTest {

    private JFrame testFrame;
    private GamePanel gamePanel;
    private Robot robot;
    private static Properties properties;

    @Before
    public void setUp() throws Exception {
        // Create the JFrame and GamePanel
        testFrame = Main.createGameWindow(800, 600);
        gamePanel = (GamePanel) testFrame.getContentPane().getComponent(0);

        // Create a Robot for simulating key events
        robot = new Robot();
        properties = Main.loadProperties("Main.properties");

    }

    @After
    public void tearDown() {
        // Close the JFrame after each test
        testFrame.dispose();
    }

    @Test
    public void testGamePanelAppearsAndListensToKeys() throws InterruptedException {
        assertNotNull("GamePanel is not null", gamePanel);
        // Wait for the JFrame to become visible
        while (!testFrame.isVisible()) {
            Thread.sleep(100);
        }

        // Simulate arrow key presses
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
        Thread.sleep(1000);
        assertEquals(true, gamePanel.up);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_LEFT);
        Thread.sleep(1000);
        assertEquals(true, gamePanel.left);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        Thread.sleep(1000);
        assertEquals(true, gamePanel.down);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        Thread.sleep(1000);
        assertEquals(true, gamePanel.right);
        robot.keyPress(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_R);
        Thread.sleep(1000);
        assertEquals(true, gamePanel.replay);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_Q);
        robot.keyRelease(KeyEvent.VK_Q);
        Thread.sleep(1000);
    }

    @Test
    public void testCon() {
        gamePanel.Con();
        assertNotNull(gamePanel.connection);
    }

    @Test
    public void testNewEnemy() {
        gamePanel.newEnemy();
        assertTrue(gamePanel.enemyX >= 0 && gamePanel.enemyX < gamePanel.width);
        assertTrue(gamePanel.enemyY >= 0 && gamePanel.enemyY < gamePanel.height);
    }

    @Test
    public void testMainMethod() {
        try {
            Main m2 = new Main();
            m2.main(null);
            assertTrue(true);
        } catch (IOException e) {
            assertTrue(false, "IOException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCollisionWithEnemy() {
        gamePanel.snakexlength[0] = gamePanel.enemyX;
        gamePanel.snakeylength[0] = gamePanel.enemyY;
        int initialScore = gamePanel.score;
        int initialLength = gamePanel.lengthOfSnake;
        gamePanel.collidesWithEnemy();
        assertTrue(initialScore < gamePanel.score);
        assertTrue(initialLength < gamePanel.lengthOfSnake);
    }

    @Test
    public void testChooseScreenSize() {
        int[] dimensions = Main.chooseScreenSize(properties);
        assertNotNull(dimensions);
        assertEquals(2, dimensions.length);
        assertTrue(dimensions[0] > 0);
        assertTrue(dimensions[1] > 0);
    }

    @Test
    public void closeGameWindow() {
        JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(1000, 1000);
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
        gamePanel.closeGameWindow();
        assertFalse(frame.isDisplayable());
    }

    GamePanel gp = new GamePanel(1000, 1000);

    @Test
    public void assignVariablestest() {
        gp.assignVariables();
        assertEquals(25, gp.percent);
        assertEquals(75, gp.percent2);
        assertEquals(3, gp.lengthOfSnake);
        assertEquals(0, gp.moves);
        assertEquals(0, gp.score);
        assertEquals(150, gp.delay);
        assertEquals(24, gp.OriginX);
        assertEquals(10, gp.OriginY);
        assertEquals(74, gp.OriginY2);
        assertEquals(55, gp.Rheight);
        assertEquals(18, gp.font1);
        assertEquals(14, gp.font2);
        assertEquals(30, gp.height2);
        assertEquals(50, gp.height3);
        assertEquals(100, gp.w1);
        assertEquals(40, gp.h1);
        assertEquals(100, gp.Sx0);
        assertEquals(75, gp.Sx1);
        assertEquals(50, gp.Sx2);
        assertEquals(100, gp.Sy0);
    }

    // -------------------------------

    private ArrayList<GameState> gameStates = new ArrayList<>();

    @Test
    public void addToArray() {
        int[] snakexlength = { 25, 50, 75, 100 };
        int[] snakeylength = { 50, 75, 100, 125 };
        int enemyX = 25;
        int enemyY = 50;
        int moves = 4;
        int score = 1;
        int lengthOfSnake = 4;
        boolean left = false;
        boolean right = true;
        boolean up = false;
        boolean down = false;
        gameStates.add(new GameState(snakexlength.clone(), snakeylength.clone(), enemyX, enemyY, moves, score,
                lengthOfSnake, left, right, up, down));
        assertNotNull(gameStates);
    }

    @Test
    public void dataSaveToDB() {
        gp.replay = true;
        assertEquals(true, gp.replay);
        gp.gameStates = gameStates;
        assertEquals(gameStates, gp.gameStates);
        gp.dataSaveToDB();
        gp.getData();
        assertNotNull(gp.resultSet);
    }

    @Test
    public void testEnemy2() {
        gp.gameOver = true;
        assertEquals(true, gp.gameOver);
        gp.newEnemy();
        gp.restart();
        gp.replayGame();
        assertEquals(0, gp.moves);
        assertEquals(false, gp.gameOver);
    }

    @AfterAll
    public static void CleanupCode() {
        GamePanel gp = new GamePanel(1000, 1000);
        gp.deleteData();
        gp.restart();
        gp.resetGame();
        gp.flag = true;
        gp.replay = true;
        gp.assignVariables();
    }

}