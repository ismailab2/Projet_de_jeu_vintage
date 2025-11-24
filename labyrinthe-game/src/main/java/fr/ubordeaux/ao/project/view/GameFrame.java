package fr.ubordeaux.ao.project.view;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameFrame extends JPanel {

    private final Game game;
    private JFrame frame;
    private final int tileSize = 100;

    // Images
    private BufferedImage playerImg;
    private BufferedImage enemyImg;
    private BufferedImage groundImg;
    private BufferedImage wallImg;
    private BufferedImage boxImg;
    private BufferedImage boxFixeImg;
    private BufferedImage explosionImg;
    private BufferedImage bombImg;

    public GameFrame(Game game) {
        this.game = game;
        loadImages();
        setFocusable(true);
    }

    public void createWindow() {
        frame = new JFrame("Boomerang Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(1024, 768);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        requestFocusInWindow();
    }

    public void addController(KeyAdapter keyListener) {
        if (frame != null) frame.addKeyListener(keyListener);
    }

    private void loadImages() {
        try {
            playerImg     = load("/Boomrang/player.png");
            enemyImg      = load("/Boomrang/enemy.png");
            groundImg     = load("/tiles/ground.png");
            wallImg       = load("/tiles/wall.png");
            boxImg        = load("/tiles/box.png");
            boxFixeImg    = load("/tiles/boxFixe.png");
            explosionImg  = load("/tiles/explosion.png");
            bombImg       = load("/tiles/bomb.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage load(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) throw new IOException("Image not found: " + path);
        return ImageIO.read(is);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        drawCharacters(g);
    }

    private void drawMap(Graphics g) {
        int width = game.getGrid().getxSize();
        int height = game.getGrid().getySize();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CellType type = game.getGrid().getCell(new Point(x, y)).getCellType();
                BufferedImage img = switch (type) {
                    case WALL -> wallImg;
                    case BOX -> boxImg;
                    case BOX_FIXE -> boxFixeImg;
                    case BOMB -> bombImg;
                    case EXPLOSION -> explosionImg;
                    case GROUND -> groundImg;
                    default -> groundImg;
                };
                g.drawImage(img, x * tileSize, y * tileSize, tileSize, tileSize, null);
            }
        }
    }

    private void drawCharacters(Graphics g) {
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        Point p = player.getPlayerPosition();
        Point ep = enemy.getPositionEnemy();

        g.drawImage(playerImg, p.getX() * tileSize, p.getY() * tileSize, tileSize, tileSize, null);
        g.drawImage(enemyImg, ep.getX() * tileSize, ep.getY() * tileSize, tileSize, tileSize, null);
    }
}
