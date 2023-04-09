package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameXXX extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	public static final float HEIGHTm = 7.2f, WIDTHm = 12.8f;
	Wall[] wall = new Wall[6];
	static PlayersBall ball;
	MyInputProcessor inputProcessor;
	public static float tx, ty;
	public static int jumps = 1, maxJumps = 1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTHm, HEIGHTm);

		//Создаем процессор ивентов:
		inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		// Создаем границы экрана, а также пол для нашего персонажа;
		wall[0] = new Wall(world, WIDTHm/2, HEIGHTm, WIDTHm, 0.005f); // Верхняя граница мира;
		wall[1] = new Wall(world, 0, HEIGHTm/2, 0.005f, HEIGHTm); // Левая граница мира;
		wall[2] = new Wall(world, WIDTHm/2, 0, WIDTHm, 0.005f); // Нижняя граница мира;
		wall[3] = new Wall(world, WIDTHm, HEIGHTm/2, 0.005f, HEIGHTm); // Левая граница мира;
		wall[4] = new Wall(world, 6.4f, 1, 11.8f, 0.72f); // Пол для игры;
		// Создаем персонажа:
		ball = new PlayersBall(world, 2, 7, 0.1f);
	}

	public static void touchedDown(float x, float y){
		tx = x;
		ty = y;
	}

	public static void touchedUp(float x, float y){
		float dx = tx - x;
		float dy = ty - y;
		tx = 0;
		ty = 0;
		// Тут физика закончилась, осталась геометрия и алгебра
		if (jumps > 0){
		float alpha = MathUtils.atan2(-dy, dx);
		float imp = 0.1f;
		ball.body.applyLinearImpulse(imp * MathUtils.cos(alpha), imp * MathUtils.sin(alpha),
				ball.body.getPosition().x, ball.body.getPosition().y, true);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		Texture imgCircleTouch = new Texture("touch_circle.png");
		if (tx != 0 & ty != 0) batch.draw(imgCircleTouch, tx-10, -ty-10 + 720, 20, 20);
		batch.end();
		world.step(1/60f, 6, 2);
		debugRenderer.render(world, camera.combined);
	}


	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
