package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class SCRGame implements Screen {
    public static final float HEIGHTm = 7.2f, WIDTHm = 12.8f;
    private GameXXX gx;

    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cameraWorld;
    Vector3 touch;
    // Создаем все текстуры
    Texture img;

    // Создаём все тела
    BallHole hole;

    MyInputProcessor inputProcessor;

    Level lv;
    PlayersBall ball;
    // Флаги и вспомогательные величины
    int levelN = 0;
    public static float tx, ty;
    public static int jumps = 0, maxJumps = 1;
    private boolean destroyAllBodies = false;
    float timeIter = 1/60f;

    public SCRGame(GameXXX gameXXX){
        gx = gameXXX;
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        cameraWorld = new OrthographicCamera();
        cameraWorld.setToOrtho(false, WIDTHm, HEIGHTm);
        touch = new Vector3();

        //Создаем процессор ивентов:
        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        // Создаем 1 уровень
        newLevel();
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
        if (tx != 0 & ty != 0) gx.batch.draw(imgCircleTouch, tx-.1f, ty-.1f, 0.2f, 0.2f);
        gx.batch.end();

        // Сразу перед world.step:
        clearLevel();

        // Замедление при касании (плавное)
        if (tx + ty != 0 & jumps != 0 & timeIter >= 1/180f) {
            timeIter -= timeIter/10f;
            System.out.println("Touched");
        }
        else if (jumps == 0 | tx + ty == 0){
            timeIter = 1/60f;
        }

        world.step(timeIter, 6, 2);
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

    public void newLevel(){
        levelN ++;
        lv = new Level(levelN, world);
        ball = lv.ball;

    }

    void clearLevel(){
        if (destroyAllBodies){
            {
                Array<Body> bodies = new Array<Body>();
                world.getBodies(bodies);
                for(int i = 0; i < bodies.size; i++)
                {
                    if(!world.isLocked())
                        world.destroyBody(bodies.get(i));
                }
                destroyAllBodies = false;
            }
            newLevel();
        }
    }

    void drawWorld(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++)
        {
            float posX = bodies.get(i).getPosition().x, posY = bodies.get(i).getPosition().x;
            //bodies.get(i).
            if (bodies.get(i).getUserData() == "Player"){
                bodies.get(i).getPosition();
            }
        }
    }

//    Vector2 getSizes(Body body){
//         aabb;
//        b2Transform t;
//        t.SetIdentity();
//        b2Fixture* fixture = nearBody->GetFixtureList();
//        while (fixture != NULL) {
//        const b2Shape *shape = fixture->GetShape();
//        const int childCount = shape->GetChildCount();
//            for (int child = 0; child < childCount; ++child) {
//                b2AABB shapeAABB;
//                shape->ComputeAABB(&shapeAABB, t, child);
//                shapeAABB.lowerBound = shapeAABB.lowerBound;
//                shapeAABB.upperBound = shapeAABB.upperBound;
//                aabb.Combine(shapeAABB);
//            }
//            fixture = fixture->GetNext();
//        }
//
//        CCPoint lowerVertex = aabb.lowerBound;
//        CCPoint heigherVertex = aabb.upperBound;
//        bodyWidth = heigherVertex.x - lowerVertex.x;
//        bodyHeight = heigherVertex.y - lowerVertex.y;
//        return new Vector2(0, 0);
//    }

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


    private class ListenerClass implements ContactListener{

        @Override
        public void beginContact(Contact contact) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();
            System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());

            // Касание "прыжкового" шара
            if(fa.getBody().getUserData() == "JumpBall" | fb.getBody().getUserData() == "JumpBall"){
                jumps = maxJumps; // Прыжки максимальны
                // Теперь делаем импульс равный импульсу при прыжке вверх
                ball.body.setLinearVelocity(new Vector2(0, 0));
                ball.body.applyLinearImpulse(0f, 0.1f,
                        ball.body.getPosition().x, ball.body.getPosition().y, true);
                System.out.println("Jumps are restored");
                // И самое интересное - уничтожаем прыжковый шар (наверное без этого обойдемся :(
                // Игра и так сложная
                //if (fa.getBody().getUserData() == "JumpBall"){
                //    fa.getBody().destroyFixture(fa);
                //}
                //else fb.getBody().destroyFixture(fb);
            }
            // Касание "цели"
            if(fa.getBody().getUserData() == "Hole" | fb.getBody().getUserData() == "Hole"){
                System.out.println("Goal is reached");
                destroyAllBodies = true;
            }

        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();
            System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());

            if(fa.getBody().getType() == BodyDef.BodyType.StaticBody |
                    fa.getBody().getType() == BodyDef.BodyType.KinematicBody){
                jumps = maxJumps;
            }
        }


        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}
