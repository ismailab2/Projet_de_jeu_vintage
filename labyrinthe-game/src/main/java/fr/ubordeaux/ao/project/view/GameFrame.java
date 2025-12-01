package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.Grid;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.pattern.observable.Observer;
import fr.ubordeaux.ao.project.model.util.Point;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameFrame implements Observer {

    private final Game game;
    private final JFrame frame;
    private final GamePanel panel;

    private static final int TILE_SIZE = 48;

    private BufferedImage imgGround;
    private BufferedImage imgWall;
    private BufferedImage imgBox;
    private BufferedImage imgBoxFixe;
    private BufferedImage imgBomb;
    private BufferedImage imgExplosion;

    private BufferedImage imgPlayer;
    private BufferedImage imgEnemy;

    public GameFrame(Game game) {
        this.game = game;

        loadImages();

        this.frame = new JFrame("boomrang");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new GamePanel();
        this.frame.setContentPane(panel);

        Grid grid = game.getGrid();
        int width  = grid.getxSize() * TILE_SIZE;
        int height = grid.getySize() * TILE_SIZE;
        panel.setPreferredSize(new Dimension(width, height));

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    private void loadImages() {
        imgGround    = loadImage("/user/ground.jpg");
        imgWall      = loadImage("/user/wall.jpg");
        imgBox       = loadImage("/user/box.png");
        imgBoxFixe   = loadImage("/user/box.png");

        imgBomb      = null; // loadImage("/user/bomb.png");
        imgExplosion = null; // loadImage("/user/explosion.png");

        imgPlayer    = loadImage("/Boomrang/player.png");
        imgEnemy     =  loadImage("/Boomrang/enemy.png");
    }

    private BufferedImage loadImage(String path) {
        try {
            var in = getClass().getResourceAsStream(path);
            if (in == null) {
                System.err.println("Ressource introuvable : " + path);
                return null;
            }
            return ImageIO.read(in);
        } catch (IOException e) {
            System.err.println("Erreur de lecture image " + path + " : " + e.getMessage());
            return null;
        }
    }

    public void render() {
        panel.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    //should divide the render function in pair with observer
    //only render the updated player
    @Override
    public void updatePlayer() {
        this.render();
    }

    //only render the updated enemy
    @Override
    public void updateEnemy() {
        this.render();
    }

    //only render the updated bomb
    @Override
    public void updateBomb() {
        this.render();
    }


    //only render the updated explosion
    @Override
    public void updateExplosion() {
        this.render();
    }

    private class GamePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Grid grid = game.getGrid();
            for (int y = 0; y < grid.getySize(); y++) {
                for (int x = 0; x < grid.getxSize(); x++) {
                    Cell cell = grid.getCell(new Point(x, y));
                    CellType type = cell.getCellType();

                    int px = x * TILE_SIZE;
                    int py = y * TILE_SIZE;

                    drawCell(g, type, px, py);
                }
            }

            Player player = game.getPlayer();
            Point pPos = player.getPlayerPosition();
            int playerX = pPos.getX() * TILE_SIZE;
            int playerY = pPos.getY() * TILE_SIZE;

            if (imgPlayer != null) {
                g.drawImage(imgPlayer, playerX, playerY, TILE_SIZE, TILE_SIZE, null);
            } else {
                int margin = TILE_SIZE / 8;
                g.setColor(Color.BLUE);
                g.fillOval(playerX + margin, playerY + margin,
                        TILE_SIZE - 2 * margin, TILE_SIZE - 2 * margin);
            }


            Enemy enemy = game.getEnemy();
            if (enemy != null && enemy.isAlive()) {
                Point ePos = enemy.getPositionEnemy();
                int enemyX = ePos.getX() * TILE_SIZE;
                int enemyY = ePos.getY() * TILE_SIZE;

                if (imgEnemy != null) {
                    g.drawImage(imgEnemy, enemyX, enemyY, TILE_SIZE, TILE_SIZE, null);
                } else {
                    int margin = TILE_SIZE / 8;
                    g.setColor(Color.RED);
                    g.fillOval(enemyX + margin, enemyY + margin,
                            TILE_SIZE - 2 * margin, TILE_SIZE - 2 * margin);
                }
            }
        }

        private void drawCell(Graphics g, CellType type, int px, int py) {
            BufferedImage img = imageFor(type);

            if (img != null) {
                g.drawImage(img, px, py, TILE_SIZE, TILE_SIZE, null);
            } else {
                g.setColor(colorFallback(type));
                g.fillRect(px, py, TILE_SIZE, TILE_SIZE);
            }

            g.setColor(new Color(0, 0, 0, 50));
            g.drawRect(px, py, TILE_SIZE, TILE_SIZE);
        }

        private BufferedImage imageFor(CellType type) {
            return switch (type) {
                case GROUND    -> imgGround;
                case WALL      -> imgWall;
                case BOX       -> imgBox;
                case BOX_FIXE  -> (imgBoxFixe != null ? imgBoxFixe : imgBox);
                case BOMB      -> imgBomb;
                case EXPLOSION -> imgExplosion;
            };
        }

        private Color colorFallback(CellType type) {
            return switch (type) {
                case GROUND    -> new Color(230, 220, 170);
                case WALL      -> new Color(160, 120, 70);
                case BOX       -> new Color(200, 160, 90);
                case BOX_FIXE  -> new Color(100, 80, 60);
                case BOMB      -> Color.RED;
                case EXPLOSION -> Color.ORANGE;
            };
        }
    }
}