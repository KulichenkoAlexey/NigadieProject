package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameXXX extends Game {
	public static final float HEIGHT = 720, WIDTH = 1280;
	SpriteBatch batch;
	OrthographicCamera camera;

	SCRIntro scrIntro;
	SCRGame scrGame;
	
	@Override
    public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        scrIntro = new SCRIntro(this);
        scrGame = new SCRGame(this);
        setScreen(scrGame);
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
