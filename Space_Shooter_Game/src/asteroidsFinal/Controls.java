package asteroidsFinal;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Controls extends KeyAdapter implements Controller {
    Action input;
    public Controls(){
        input = new Action();
    }
    @Override
    public Action action() {
        return input;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {case KeyEvent.VK_UP: input.thrust = 1;
            break;
            case KeyEvent.VK_LEFT: input.turn = -1;
                break;
            case KeyEvent.VK_RIGHT: input.turn = 1;
                break;
            case KeyEvent.VK_SPACE: input.shoot = true;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP: input.thrust = 0;
                break;
            case KeyEvent.VK_LEFT: input.turn = 0;
                break;
            case KeyEvent.VK_RIGHT: input.turn = 0;
                break;
            case KeyEvent.VK_SPACE: input.shoot = false;
                break;
        }
    }
}

