package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        GameXXX.touchedDown(x, y);
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        GameXXX.touchedUp(x, y);
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (float amountX, float amountY) {
        return false;
    }
}

