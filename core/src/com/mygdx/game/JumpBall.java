package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class JumpBall {
    JumpBall(World world, float x, float y, float radius){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x, y));
        //remember that its the center of an object, it also is in METERS not pixels!
        Body jumpBall = world.createBody(groundBodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        jumpBall.createFixture(circle, 0.0f);
// Clean up after ourselves
        circle.dispose();

    }
}
