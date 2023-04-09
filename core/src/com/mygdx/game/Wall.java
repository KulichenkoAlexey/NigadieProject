package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {
    Wall(World world, float x, float y, float width, float height){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(x, y));
        //remember that its the center of an object, it also is in METERS not pixels!
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(width/2, height/2);
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();
    }
}
