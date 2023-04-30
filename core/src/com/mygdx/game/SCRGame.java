package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class SCRGame implements Screen {
    public static final float HEIGHTm = 7.2f, WIDTHm = 12.8f;
    private GameXXX gx;

    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cameraWorld;
    Vector3 touch;

    Texture img;

    Wall[] wall = new Wall[6];
    static PlayersBall ball;
    MyInputProcessor inputProcessor;
    public static float tx, ty;
    public static int jumps = 1, maxJumps = 1;

    public SCRGame(GameXXX gameXXX){
        gx = gameXXX;
        img = new Texture("badlogic.jpg");
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        cameraWorld = new OrthographicCamera();
        cameraWorld.setToOrtho(false, WIDTHm, HEIGHTm);
        touch = new Vector3();

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

    @Override
    public void show() {
        world.setContactListener(new ListenerClass());
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        cameraWorld.update();
        gx.batch.setProjectionMatrix(cameraWorld.combined);
        gx.batch.begin();
        Texture imgCircleTouch = new Texture("touch_circle.png");
        if (tx != 0 & ty != 0) gx.batch.draw(imgCircleTouch, tx-10, -ty-10 + 720, 20, 20);
        gx.batch.end();

        world.step(1/60f, 6, 2);
        debugRenderer.render(world, cameraWorld.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
    }

    private class MyInputProcessor implements InputProcessor {
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
            touch.set(x, y, 0);
            cameraWorld.unproject(touch);
            tx = touch.x;
            ty = touch.y;
            return false;
        }

        public boolean touchUp (int x, int y, int pointer, int button) {
            touch.set(x, y, 0);
            cameraWorld.unproject(touch);
            float dx = tx - touch.x;
            float dy = ty - touch.y;
            tx = 0;
            ty = 0;
            // Тут физика закончилась, осталась геометрия и алгебра
            if (jumps > 0 & (dx != 0 | dy != 0)){
                float alpha = MathUtils.atan2(dy, dx);
                float imp = 0.1f;
                ball.body.applyLinearImpulse(imp * MathUtils.cos(alpha), imp * MathUtils.sin(alpha),
                        ball.body.getPosition().x, ball.body.getPosition().y, true);
                jumps = 0;
            }
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


        public class ListenerClass implements ContactListener{

        @Override
        public void beginContact(Contact contact) {

        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();
            System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());

            if(fa.getBody().getType() == BodyDef.BodyType.StaticBody){
                jumps = maxJumps;
            }
        }


        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}
