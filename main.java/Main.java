import javax.swing.*;

public class Main {

    static JFrame frame;

    public static void main(String[] args) {

        frame = new JFrame("Brick Breaker");

        MenuPanel menu = new MenuPanel(frame);

        frame.setBounds(10, 10, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(menu);
        frame.setVisible(true);
    }
}