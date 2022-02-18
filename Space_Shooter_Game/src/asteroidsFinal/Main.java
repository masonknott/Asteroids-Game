package asteroidsFinal;

import utilities.JEasyFrame;
import utilities.JEasyFrameFull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static asteroidsFinal.Constants.FPS;
import static asteroidsFinal.Constants.finalDimensions;

public class Main {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static final int INITIAL_LIVES = 5;
    public static final int INITIAL_SAFETY_DURATION = 5000; // millisecs
    boolean shipIsSafe;
    public List<Object> objects;
    Ship playerShip;
    Controls control;
    int score, lives, level, remainingAsteroids;
    View view;
    boolean ended;
    long gameStartTime;
    long startTime;
    boolean resetting;

    public Main(boolean fullScreen) {
        if (fullScreen) finalDimensions();
        view = new View(this);
        objects = new ArrayList<Object>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(new Asteroid());
        }
        control = new Controls();
        playerShip = new Ship(control);
        objects.add(playerShip);
        score = 0;
        remainingAsteroids = N_INITIAL_ASTEROIDS;
        lives = INITIAL_LIVES;
        level = 1;
        ended = false;
        shipIsSafe = true;
        resetting = false;
        JFrame frame = fullScreen ? new JEasyFrameFull(view) : new JEasyFrame(view, "Asteroids 5");
        frame.setResizable(false);
        frame.addKeyListener(control);
    }
    public static void main(String[] args) {
        Main main = new Main(false);
        main.runningLoop();
    }

    public void runningLoop() {
        long DTMS = Math.round(1000 / FPS); // time delay in MS
        gameStartTime = startTime = System.currentTimeMillis();
        while (!ended) {
            long time0 = System.currentTimeMillis(); update(); view.repaint();
            long timeToSleep = time0 + DTMS - System.currentTimeMillis();
            if (timeToSleep < 0)
                System.out.println("Warning: timeToSleep = negative");
            else
                try {Thread.sleep(timeToSleep);
                } catch (Exception e) {
                }


        }
        System.out.println("Your score was " + score);
        System.out.println("Game time " + (int) ((System.currentTimeMillis() - gameStartTime) / 1000));
    }


    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public int getLevel() {
        return level;
    }
    public boolean restart(boolean newlevel) {
        objects.clear();
        if (newlevel) level++;
        else
            lives--;
        if (lives == 0)
            return false;
        for (int i = 0; i < N_INITIAL_ASTEROIDS + (level - 1) * 5; i++) {
            objects.add(new Asteroid());

        }
        remainingAsteroids = N_INITIAL_ASTEROIDS + (level - 1) * 5;
        playerShip.restart();
        objects.add(playerShip);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        shipIsSafe = true;
        startTime = System.currentTimeMillis();
        return true;

    }
    public void update() {
        // suppress collision detection at beginning of game
        if (shipIsSafe) {
            shipIsSafe = System.currentTimeMillis() < startTime + INITIAL_SAFETY_DURATION;
        } else
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    objects.get(i).collisionHandling(objects.get(j));
                }
            }
        ended = true;
        List<Object> alive = new ArrayList<>();
        for (Object o : objects) {
            if (!o.status) {
                o.update();alive.add(o);
                if (o == playerShip) ended = false;
            }
            else if (o== playerShip){
                resetting = true;
                break;
            }
            else updateScore(o);
        }
        if (playerShip.bolt != null) {
            alive.add(playerShip.bolt);
            playerShip.bolt = null;
        }
        synchronized (Main.class) {
            if (remainingAsteroids==0)
                restart(true);
            else if (resetting) {
                ended = !restart(false);
                resetting = false;
            }
            else {
                objects.clear();
                for (Object o : alive) objects.add(o);
            }
        }
    }
    public void updateScore(Object o) {
        if (o.getClass() == Asteroid.class) {
            score += 100;
            remainingAsteroids -= 1;
        }
    }

}

