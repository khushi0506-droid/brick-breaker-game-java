import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int level = 1;

    private int totalBricks;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    private JButton startButton;

    // 🖼️ IMAGE VARIABLE
    private Image gameOverImage;

    public GamePanel(JFrame frame) {

        setLayout(null);

        loadLevel();

        // 🖼️ LOAD IMAGE
        gameOverImage = new ImageIcon("resources/gameover.png").getImage();

        // 🎮 START BUTTON
        startButton = new JButton("START");
        startButton.setBounds(270, 250, 150, 40);
        add(startButton);

        startButton.addActionListener(e -> {
            score = 0;
            level = 1;
            loadLevel();
            resetBall();

            play = true;
            startButton.setVisible(false);
            requestFocusInWindow();
        });

        addKeyListener(this);
        setFocusable(true);

        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        timer = new Timer(delay, this);
        timer.start();
    }

    private void loadLevel() {
        int rows = 3 + level;
        int cols = 7;

        map = new MapGenerator(rows, cols);
        totalBricks = rows * cols;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 🌈 LIGHT BACKGROUND
        g.setColor(new Color(200, 230, 255));
        g.fillRect(0, 0, 700, 600);

        // SCORE + LEVEL
        g.setColor(Color.BLACK);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score: " + score, 520, 30);
        g.drawString("Level: " + level, 50, 30);

        if (!play) {
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Click START to Play", 200, 200);
        }

        // BRICKS
        map.draw((Graphics2D) g);

        // PADDLE
        g.setColor(Color.CYAN);
        g.fillRect(playerX, 550, 100, 8);

        // BALL
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // 🎉 LEVEL COMPLETE
        if (totalBricks <= 0) {
            level++;
            loadLevel();
            resetBall();
            play = false;
            startButton.setVisible(true);
        }

        // ❌ GAME OVER WITH IMAGE
        if (ballPosY > 570) {
            play = false;
            // 🔘 Move button ABOVE image
            startButton.setBounds(270, 80, 150, 40);
            startButton.setText("RESTART");
            startButton.setVisible(true);

            // 🖼️ DRAW IMAGE
            g.drawImage(gameOverImage, 200, 120, 300, 200, this);

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over", 240, 350);

            g.setColor(Color.BLACK);
            g.setFont(new Font("serif", Font.BOLD, 22));
            g.drawString("Final Score: " + score, 260, 390);

            // 🔁 MESSAGE (BOTTOM)
            g.setFont(new Font("serif", Font.BOLD, 18));
            g.drawString("Press ENTER or Click RESTART", 180, 430);

            startButton.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (play) {

            // Paddle collision
            if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            // Brick collision
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {

                    if (map.map[i][j] > 0) {

                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;

                        Rectangle rect = new Rectangle(brickX, brickY,
                                map.brickWidth, map.brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);

                        if (ballRect.intersects(rect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 10;

                            ballYdir = -ballYdir;
                            break A;
                        }
                    }
                }
            }

            // MOVE BALL
            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0 || ballPosX > 670) ballXdir = -ballXdir;
            if (ballPosY < 0) ballYdir = -ballYdir;
        }

        repaint();
    }

    private void resetBall() {
        ballPosX = 120;
        ballPosY = 350;
        ballXdir = -1;
        ballYdir = -2;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerX = Math.min(playerX + 20, 600);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerX = Math.max(playerX - 20, 10);
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}