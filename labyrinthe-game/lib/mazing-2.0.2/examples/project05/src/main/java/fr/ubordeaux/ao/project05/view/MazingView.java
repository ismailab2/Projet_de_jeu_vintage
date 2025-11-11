package fr.ubordeaux.ao.project05.view;

import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.Spider;
import fr.ubordeaux.ao.mazing.api.Direction;
import fr.ubordeaux.ao.mazing.api.ICharacter;
import fr.ubordeaux.ao.mazing.api.IWindowGame;

import java.awt.geom.Point2D;

import javax.swing.JFrame;

public class MazingView {

    private static final int MAX = 20;
    private static final double MAX_DIST = 12.0;
    private static final int BOSS_INDEX = 1;

    private final IWindowGame windowGame;
    private final double[] vx = new double[MAX * MAX];
    private final double[] vy = new double[MAX * MAX];
    private final double[] px = new double[MAX * MAX];
    private final double[] py = new double[MAX * MAX];
    private final boolean[] death = new boolean[MAX * MAX];

    private Crusader boss;
    private Spider spider;
    private int attack;

    public MazingView(IWindowGame windowGame) {
        this.windowGame = windowGame;
        init();
    }

    // Détermine la direction en fonction de vx et vy
    private Direction getDirection(double vx, double vy) {
        double angle = Math.atan2(vy, vx);
        double pi = Math.PI;

        if (angle >= -pi / 8 && angle < pi / 8) return Direction.EAST;
        if (angle >= pi / 8 && angle < 3 * pi / 8) return Direction.SOUTHEAST;
        if (angle >= 3 * pi / 8 && angle < 5 * pi / 8) return Direction.SOUTH;
        if (angle >= 5 * pi / 8 && angle < 7 * pi / 8) return Direction.SOUTHWEST;
        if (angle >= 7 * pi / 8 || angle < -7 * pi / 8) return Direction.WEST;
        if (angle >= -7 * pi / 8 && angle < -5 * pi / 8) return Direction.NORTHWEST;
        if (angle >= -5 * pi / 8 && angle < -3 * pi / 8) return Direction.NORTH;
        if (angle >= -3 * pi / 8 && angle < -pi / 8) return Direction.NORTHEAST;

        return Direction.SOUTH;
    }

    private void init() {
        setupWindow();
        initializeCharacters();
        windowGame.setVisible(true);
    }

    private void setupWindow() {
        windowGame.setTileSize(60);
    }

    private void initializeCharacters() {
        for (int i = 1; i < MAX * MAX; i++) {
            final int index = i;
            int x = i % MAX;
            int y = i / MAX;

            ICharacter<?> character = createCharacter(i, x, y);
            px[index] = x;
            py[index] = y;

            double speed = assignInitialVelocity(character, index);

            final int startX = x;
            final int startY = y;

            character.setTickAnimationTrigger(_ -> {
                handleAttackReset();
                moveCharacter(character, index, speed, startX, startY);
                return false;
            });

            windowGame.add(character);
        }
    }

    private ICharacter<?> createCharacter(int index, int x, int y) {
        if (index == BOSS_INDEX) {
            return createBoss(x, y);
        } else {
            Spider spider = new Spider();
            spider.setMode(Spider.Mode.WALK);
            spider.setPosition(x, y, 0);
            return spider;
        }
    }

    private Crusader createBoss(int x, int y) {
        Crusader bossCharacter = new Crusader();
        boss = bossCharacter;

        Point2D startPoint = windowGame.getIsoCoordinatesFromScreen(
                ((JFrame) windowGame).getWidth() / 2,
                ((JFrame) windowGame).getHeight() / 2
        );

        boss.setEndAnimationTrigger(_ -> handleBossAttack());

        return bossCharacter;
    }

    private boolean handleBossAttack() {
        if (boss.getCurrentMode() == Crusader.Mode.ATTACK) {
            double angle = Math.random() * 2 * Math.PI;
            vx[BOSS_INDEX] = 0.08 * Math.cos(angle);
            vy[BOSS_INDEX] = 0.08 * Math.sin(angle);
            windowGame.remove(spider);
            attack = 2;
            windowGame.playSound("abstract/ding");
            return false;
        }
        return true;
    }

    private double assignInitialVelocity(ICharacter<?> character, int index) {
        double speed = (character instanceof Crusader) ? 3.0 : Math.random() * 0.03 + 0.03;
        double angle = Math.random() * 2 * Math.PI;
        vx[index] = speed * Math.cos(angle);
        vy[index] = speed * Math.sin(angle);
        character.setDirection(getDirection(vx[index], vy[index]));
        character.setBeginAnimationTrigger(_ -> false);
        character.setMidAnimationTrigger(_ -> false);
        return speed;
    }

    private void handleAttackReset() {
        if (attack == 2) {
            boss.setMode(Crusader.Mode.WALK);
            attack = 0;
        }
    }

    private void moveCharacter(ICharacter<?> character, int index, double speed, int startX, int startY) {
        boolean isWalking = (character instanceof Crusader && ((Crusader) character).getCurrentMode() == Crusader.Mode.WALK)
                || (character instanceof Spider && ((Spider) character).getCurrentMode() == Spider.Mode.WALK);

        if (!isWalking) return;

        if (attack == 0 && index != BOSS_INDEX && !death[index]) {
            double distanceToBoss = Math.hypot(px[BOSS_INDEX] - px[index], py[BOSS_INDEX] - py[index]);
            if (distanceToBoss < 1f) {
                spider = (Spider) character;
                vx[BOSS_INDEX] = vy[BOSS_INDEX] = 0;
                boss.setMode(Crusader.Mode.ATTACK);
                vx[index] = vy[index] = 0;
                death[index] = true;
                attack = 1;
            }
        }

        // Mise à jour de la position
        px[index] += vx[index];
        py[index] += vy[index];

        // Limiter la distance par rapport à l'origine
        double dx = px[index] - startX;
        double dy = py[index] - startY;
        double distance = Math.hypot(dx, dy);
        if (distance > MAX_DIST) {
            vx[index] = -vx[index];
            vy[index] = -vy[index];
        }

        // Changer aléatoirement la direction de temps en temps
        if (Math.random() < 0.001) {
            double angle = Math.random() * 2 * Math.PI;
            double speedMagnitude = Math.hypot(vx[index], vy[index]);
            vx[index] = speedMagnitude * Math.cos(angle);
            vy[index] = speedMagnitude * Math.sin(angle);
        }

        character.setDirection(getDirection(vx[index], vy[index]));
        character.setPosition((float) px[index], (float) py[index], 0);
    }
}