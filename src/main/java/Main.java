import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Main {

    public static void main(String[] args) throws IOException {
        Properties properties;
        properties = loadProperties("Main.properties");
        int[] dimensions = chooseScreenSize(properties);
        createGameWindow(dimensions[0], dimensions[1]);
    }

    public static Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream input = Main.class.getClassLoader().getResourceAsStream(fileName);
                properties.load(input);
        return properties;
    }

    public static int[] chooseScreenSize(Properties properties) {
        int windowWidth = Integer.parseInt(properties.getProperty("windowWidth"));
        int windowHeight = Integer.parseInt(properties.getProperty("windowHeight"));
        return new int[] { windowWidth, windowHeight };
    }

    public static boolean test = false;
    public static void createGameWindow(int windowWidth, int windowHeight) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10, 10, windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel(windowWidth, windowHeight, test);
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        frame.setVisible(true);
    }
}