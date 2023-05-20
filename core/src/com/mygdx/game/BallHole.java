package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BallHole {
    Body body;

    BallHole(World world, float x, float y, float radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

// Create our body in the world using our body definition
        body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

// to experiment with this
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        body.setUserData("Hole");

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);



        circle.dispose();

    }
}
