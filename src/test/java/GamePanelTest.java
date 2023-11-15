import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class GamePanelTest {

    private FrameFixture frameFixture;

    @Before
    public void setUp() {
        // Create the JFrame and GamePanel
        JFrame frame = createGameWindow(800, 600);
        frameFixture = new FrameFixture(frame);
        frameFixture.show();
    }

    @After
    public void tearDown() {
        // Close the JFrame after each test
        frameFixture.cleanUp();
    }

    @Test
    public void testGamePanelAppearsAndListensToKeys() {

        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('L'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('R'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('U'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('D'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode(' '));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('R'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('S'));
        frameFixture.pressAndReleaseKey(KeyPressInfo.keyCode('Q'));
        Pause.pause(1000);
    }

    public static JFrame createGameWindow(int windowWidth, int windowHeight) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10, 10, windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel(windowWidth, windowHeight);
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        frame.setVisible(true);
        return frame; // Return the JFrame.
    }
}
