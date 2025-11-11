package fr.ubordeaux.ao.project00;

import java.awt.Color;
import java.util.Random;

import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.mazing.api.Spider;
import fr.ubordeaux.ao.mazing.api.Villager;
import fr.ubordeaux.ao.mazing.api.WindowGame;

public class Main {
    public static void main(String[] args) {

        IWindowGame windowGame = new WindowGame();
        windowGame.fillCheckerboardBackground(-100, -100, 200, 200);

        Crusader crusader = new Crusader();
        crusader.setScale(1f);
        crusader.setOffset(1f, +1f);
        crusader.setPosition(0, 0, 0);
        windowGame.add(72, 0, 0, 0);
        windowGame.add(crusader);

        for (int mode = 0; mode < Crusader.Mode.values().length; mode++) {
            crusader = new Crusader();
            if (mode == 0){                    
                crusader.setScale(3f);
            }

            crusader.setPosition(2, mode - 4, 0);
            windowGame.add(72, 2, mode - 4, 0);
            crusader.setMode(Crusader.Mode.values()[mode]);
            crusader.setEndAnimationTrigger(character -> {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000); // attendre 2 secondes
                        ((Crusader) character).resetAnimation();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
                return false;
            });
            windowGame.add(crusader);
        }

        for (int y = 0; y < 4; y++) {
            crusader = new Crusader();
            crusader.setScale(3f);
            crusader.setPosition(4, y, 0);
            windowGame.add(72, 4, y, 0);
            Random rand = new Random();
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            crusader.setColor(color);
            windowGame.add(crusader);
        }

        for (int mode = 0; mode < Spider.Mode.values().length; mode++) {
            Spider spider = new Spider();
            spider.setPosition(6, mode + 3, 0);
            spider.setMode(Spider.Mode.values()[mode]);
            spider.setEndAnimationTrigger(character -> {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000); // attendre 2 secondes
                        ((Spider) character).resetAnimation();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
                return false;
            });
            windowGame.add(72, 6, mode + 3, 0);
            windowGame.add(spider);
        }

        for (int mode = 0; mode < Villager.Mode.values().length; mode++) {
            Villager villager = new Villager();
            villager.setPosition(8, mode, 0);
            windowGame.add(72, 8, mode, 0);
            villager.setScale(2f);
            villager.setMode(Villager.Mode.values()[mode]);
            windowGame.add(villager);
        }

        windowGame.setVisible(true);

    }

}
