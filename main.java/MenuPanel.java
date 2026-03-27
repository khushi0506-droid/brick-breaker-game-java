import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private Image menuImage;

    public MenuPanel(JFrame frame) {
        setLayout(null);
        setBackground(new Color(240, 128, 128)); // 🌈 light background

        // 🖼️ LOAD IMAGE - Ensure resources/menu.png exists
        try {
            menuImage = new ImageIcon("resources/menu.png").getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // 🎮 TITLE - Simplified styling, painted within paintComponent
        JLabel title = new JLabel("BRICK BREAKER");
        title.setBounds(170, 50, 500, 60);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40)); // Changed "Futuristic" to "Arial" for stability
        add(title);

        // 💬 MESSAGE
        JLabel msg = new JLabel("Want to play Brick Breaker?");
        msg.setBounds(200, 400, 350, 30);
        msg.setForeground(Color.DARK_GRAY);
        msg.setFont(new Font("Arial", Font.BOLD, 18));
        add(msg);

        // 🔘 START BUTTON
        JButton startButton = new JButton("START GAME");
        startButton.setBounds(250, 430, 200, 40);
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        add(startButton);

        // ▶️ BUTTON ACTION
        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            // Assuming GamePanel exists
            GamePanel gamePanel = new GamePanel(frame);
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });
    }

    // 🎨 DRAW IMAGE AND CUSTOM TEXT
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (menuImage != null) {
            g.drawImage(menuImage, 150, 100, 400, 300, this);
        }

        // --- Add any custom drawing/shadows here if needed ---
    }
}
