import jack.company.MapGenerator;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalbricks = 21;

    private Timer time;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator map;

    public Gameplay() {

        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        time = new Timer(delay, this);
        time.start();

    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //Mapa
        map.draw((Graphics2D) g);

        //bordas
//        g.setColor(Color.RED);
//        g.fillRect(0, 0, 3, 592);
//        g.fillRect(0, 0, 692, 3);
//        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        //raquete
        g.setColor(Color.green);
        g.fillRect(playerX, 500, 200, 8);

        //Bola
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        if (totalbricks <= 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Você Ganhou =D", 190, 300);

            g.setColor(Color.BLUE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Você fez " + score + " Pontos", 190, 350);

            g.setColor(Color.ORANGE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Enter para Recomeçar", 190, 400);
        }
        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over :(", 190, 300);

            g.setColor(Color.BLUE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Você fez " + score + " Pontos", 190, 350);

            g.setColor(Color.ORANGE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Enter para Recomeçar", 190, 400);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new Rectangle(playerX, 500, 200, 8))) {
                ballDirY = -ballDirY;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidht + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidht;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValues(0, i, j);
                            totalbricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if (ballPosX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) {
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 490) {
                playerX = 490;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                totalbricks = 31;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    private void moveRight() {
        play = true;
        playerX += 100;
    }

    private void moveLeft() {
        play = true;
        playerX -= 100;
    }

}
