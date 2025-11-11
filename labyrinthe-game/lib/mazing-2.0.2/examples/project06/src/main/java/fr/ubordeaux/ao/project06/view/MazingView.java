package fr.ubordeaux.ao.project06.view;

import fr.ubordeaux.ao.mazing.api.Crusader;
import fr.ubordeaux.ao.mazing.api.IWindowGame;

public class MazingView {

    private static final float SUNDIRX = 0.5f;
    private static final float SUNDIRY = 0.3f;
    private Crusader boss;
    private IWindowGame windowGame;
    private int k;

    public MazingView(IWindowGame windowGame) {
        this.windowGame = windowGame;
        init();
    }

    private void init() {
        setupWindow();
        initializeCharacters();
        windowGame.setVisible(true);

        // Lancer la cinématique de rebonds
        playBounceCinematic();
    }

    private void setupWindow() {
        windowGame.setTileSize(60);
        windowGame.fillArea(72, -30, -30, 0, 60, 60);
    }

    private void initializeCharacters() {
        boss = new Crusader(2f);
        boss.setMode(Crusader.Mode.IDLE);
        boss.setFrameRate(Crusader.Mode.JUMP, 0.5f);
        boss.setFrameRate(Crusader.Mode.RUN, 2f);
        boss.setBeginAnimationTrigger(_ -> {return false;});
        boss.setMidAnimationTrigger(_ -> {return false;});
        boss.setTickAnimationTrigger(_ -> {return false;});
        boss.setEndAnimationTrigger(c -> {
            Crusader crusader = (Crusader) c;
            if (crusader.getCurrentMode() == Crusader.Mode.JUMP) {
                crusader.setMode(Crusader.Mode.RUN);
                k = 2;
            }
            else if (crusader.getCurrentMode() == Crusader.Mode.RUN) {
                if (k-- == 0) {
                    crusader.setMode(Crusader.Mode.WALK);
                                    windowGame.playSound("abstract/woosh");
                    k = 3;
                }
            }
            else if (crusader.getCurrentMode() == Crusader.Mode.WALK) {
                if (k-- == 0) {
                    crusader.setMode(Crusader.Mode.IDLE);
                }
            }
            return false;
        });
        windowGame.add(boss);
    }

    public void playBounceCinematic() {
        float g = 9.80f; // gravité en pixels/s²
        float dt = 0.016f; // ~60fps
        float absorption = 0.98f; // perte d'énergie à chaque rebond

        // Position et vitesse initiales
        float x = -10; // horizontal
        float y = 10; // horizontal
        float z = 5; // hauteur initiale
        float vx = 4f; // vitesse horizontale
        float vy = 0f; // vitesse horizontale y
        float vz = 0; // vitesse verticale initiale

        while (true) {
            // Gravité
            vz -= g * dt; // z augmente vers le haut, donc gravité soustrait

            // Mise à jour de la position verticale
            z += vz * dt;

            // Déplacement horizontal
            x += vx * dt;
            y += vy * dt;

            // Collision avec le sol
            if (z <= 0) {
                windowGame.playSound("abstract/clack");
                z = 0;
                vz = -vz * absorption;
                boss.setMode(Crusader.Mode.JUMP);
            }

            // Mise à jour du boss
            boss.setPosition(x, y, z);

            // Calcul de la position de l'ombre
            float shadowX = x - z * SUNDIRX;
            float shadowY = y - z * SUNDIRY;
            float shadowZ = 0; // toujours au sol

            // Mettre à jour le boss
            boss.setPosition(x, y, z);

            // Mettre à jour l'ombre
            // Tu peux utiliser un autre objet pour l'ombre ou si ton API gère l'ombre du
            // boss :
            boss.setShadowPosition(shadowX, shadowY, shadowZ);

            // Pause pour ~60fps
            try {
                Thread.sleep((long) (dt * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Arrêt de la cinématique si vitesse verticale faible et sur le sol
            if (Math.abs(vz) < 1 && z <= 0.01) {
                break;
            }
        }
    }
}
