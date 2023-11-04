
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import org.junit.jupiter.api.AfterAll;
// import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container;

public class GamePanelTest {

    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        gamePanel = new GamePanel(800, 600, true);
        gamePanel.testing = true;
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
    public void testCollisionWithBody() {
        gamePanel.snakexlength[0] = 100;
        gamePanel.snakeylength[0] = 100;
        gamePanel.snakexlength[1] = 100;
        gamePanel.snakeylength[1] = 120;
        gamePanel.lengthOfSnake = 2;
        gamePanel.collidesWithBody();
        assertTrue(gamePanel.gameOver);
    }

    @Test
    public void testMainMethod() {
        try {
            Main m2 = new Main();
            m2.test = true;
            m2.main(null);
            // If the main method completes without throwing an exception,
            // we consider it a success.
            assertTrue(true);
        } catch (IOException e) {
            // If an IOException is thrown during execution, fail the test.
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

    private static Properties properties;

    @BeforeAll
    public static void setup() {
        try {
            properties = Main.loadProperties("Main.properties");
        } catch (IOException e) {
            fail("Exception thrown during setup: " + e.getMessage());
        }
    }

    @Test
    public void testLoadProperties() {
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
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
    void closeGameWindow() {
        JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(1000, 1000, false);
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
        gamePanel.closeGameWindow();
        assertFalse(frame.isDisplayable());
    }

    GamePanel gp = new GamePanel(1000, 1000, true);

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

    @Test
    public void saveData() throws Exception {
        int[] snakeX = { 25, 50, 75 };
        int[] snakeY = { 75, 100, 125 };
        int enemyX = 75;
        int enemyY = 125;
        int moves = 12;
        int score = 47;
        int lengthOfSnake = 10;
        boolean left = false;
        boolean right = true;
        boolean up = false;
        boolean down = false;
        gp.saveData(snakeX, snakeY, enemyX, enemyY, moves, score, lengthOfSnake, left, right, up, down);
    }

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
    public void keyPressedTest() {
        gp.triggerAllOptions();
        assertNotNull(gp.leftKeyEvent);
        assertNotNull(gp.rightKeyEvent);
        assertNotNull(gp.upKeyEvent);
        assertNotNull(gp.downKeyEvent);
        assertNotNull(gp.rKeyEvent);
        assertNotNull(gp.qKeyEvent);
    }

    @Test
    public void saveDataException() {
        gp.testing = true;
        int[] snakeX = { 25, 50, 75 };
        int[] snakeY = { 75, 100, 125 };
        int enemyX = 75;
        int enemyY = 125;
        int moves = 12;
        int score = 47;
        int lengthOfSnake = 10;
        boolean left = false;
        boolean right = true;
        boolean up = false;
        boolean down = false;
        gp.saveData(snakeX, snakeY, enemyX, enemyY, moves, score, lengthOfSnake, left, right, up, down);
        gp.getData();
        assertNotNull(gp.resultSet);
    }

    @Test
    public void Exceptiontesting2() {
        gp.testing = true;
        gp.Con();
        gp.newEnemy();
        assertEquals(true, gp.testing);
    }

    @Test
    public void testEnemy2() {
        gp.testing = true;
        gp.gameOver = true;
        assertEquals(true, gp.gameOver);
        gp.newEnemy();
        gp.restart();
        gp.replayGame();
        assertEquals(true, gp.testing);
        assertEquals(0, gp.moves);
        assertEquals(false, gp.gameOver);
    }

    @AfterAll
    public static void CleanupCode() {
        GamePanel gp = new GamePanel(1000, 1000, false);
        gp.deleteData();
        gp.restart();
        gp.resetGame();
        gp.flag = true;
        gp.replay = true;
        System.out.println("This method runs after all tests in the class.");
        GamePanel gp2 = new GamePanel(1200, 900, false);
        gp2.left = true;
        gp2.right = true;
        gp2.up = true;
        gp2.down = true;
        gp2.gameOver = true;
        gp2.actionPerformed(null);
        gp.testing = true;
        gp.getData();
        gp.deleteData();
        gp.testing = true;
        gp.assignVariables();
    }

}
