
import java.awt.Container;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
// import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.awt.Container;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Container;

// import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container;

public class GamePanelTest {
    GamePanel gp = new GamePanel(1000, 1000, true);

    @Test
    void closeGameWindow() {
        JFrame frame = new JFrame("Game Frame");
        GamePanel gamePanel = new GamePanel(1000, 1000, false);
        frame.add(gamePanel);
        gamePanel.closeGameWindow();
    }

    @Test
    public void MaintTest() {
        try {
            Main.test = true;
            Main.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public void testEnemy() {
        gp.gameOver = true;
        assertEquals(true,gp.gameOver);
        gp.newEnemy();
        gp.restart();
        assertEquals(0,gp.moves);
        gp.replayGame();
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

    @Test
    public void getingData() throws Exception {
        gp.getData();
    }

    @Test
    public void MaintTest2() {
        try {
            Main m2 = new Main();
            Main.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MaintTest3() {
        try {
            Main.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void collidesWithBodyTest() {
        gp.collidesWithBody();
    }

    @Test
    public void collidesWithEnemyTest() {
        gp.collidesWithEnemy();
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
    }

    @Test
    public void dataSaveToDB() {
        gp.replay = true;
        gp.gameStates = gameStates;
        gp.dataSaveToDB();
    }

    @Test
    public void newEnemyTest() {
        gp.newEnemy();
    }

    @Test
    public void keyPressedTest() {
        gp.triggerAllOptions();
    }

    @Test
    public void actionPerformedMethod() {
        GamePanel gp2 = new GamePanel(1200, 900, false);
        gp2.left = true;
        gp2.right = true;
        gp2.up = true;
        gp2.down = true;
        gp2.gameOver = true;
        gp2.actionPerformed(null);
    }

    @Test
    public void testCloseGameWindow() {
        gp.closeGameWindow();
    }

    @Test
    public void Exceptiontesting() {
        gp.testing = true;
        gp.assignVariables();
    }

    @Test
    public void GetiingData() {
        gp.testing = true;
        gp.getData();
        gp.deleteData();
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
    }

    @Test
    public void Exceptiontesting2() {
        gp.testing = true;
        gp.Con();
    }

    @Test
    public void newEnemyExceptionTesting() {
        gp.testing = true;
        gp.newEnemy();
    }

    @Test
    public void testEnemy2() {
        gp.testing = true;
        gp.gameOver = true;
        gp.newEnemy();
        gp.restart();
        gp.replayGame();
    }

    @AfterAll
    public static void afterAllTests() {
        GamePanel gp = new GamePanel(1000, 1000, false);
        gp.deleteData();
        gp.restart();
        gp.resetGame();
        gp.flag = true;
        gp.replay = true;
        System.out.println("This method runs after all tests in the class.");
    }


}