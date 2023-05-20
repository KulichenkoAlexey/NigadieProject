package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Level {
    PlayersBall ball;
    Wall[] wall = new Wall[10];
    BallHole hole;
    public Level(int lv, World world){
        //Создаем границы:
        if (lv == 1){
            // Границы
            createBorders(world);

            // Создаем персонажа:
            ball = new PlayersBall(world, 2, 5, 0.1f);

            // И цeль
            hole = new BallHole(world, 6, 1.475f, 0.1f);
        }
        if (lv == 2){
            System.out.println("lv2");
            createBorders(world);
            wall[4] = new Wall(world, 6.4f, 1, 11.8f, 0.72f);
            wall[5] = new Wall(world, 7.5f, 2.5f, 2, 0.1f);
            hole = new BallHole(world, 7.5f, 2.65f, 0.1f);
            ball = new PlayersBall(world, 2, 5, 0.1f);
        }
        if (lv == 3){
            System.out.println("lv3");
            createBorders(world);
            // Floor
            wall[4] = new Wall(world, 6.4f, 1, 11.8f, 0.72f);
            // walls
            wall[5] = new Wall(world, 5.5f, 2.5f, 2, 0.1f);
            // Platform
            wall[6] = new Wall(world, 7.5f, 4, 2, 0.1f);
            hole = new BallHole(world, 7.5f, 4.65f, 0.1f);
            ball = new PlayersBall(world, 2, 5, 0.1f);
        }
    }

    void createBorders(World world){
        wall[0] = new Wall(world, SCRGame.WIDTHm/2, SCRGame.HEIGHTm, SCRGame.WIDTHm, 0.005f); // Верхняя граница мира;
        wall[1] = new Wall(world, 0, SCRGame.HEIGHTm/2, 0.005f, SCRGame.HEIGHTm); // Левая граница мира;
        wall[2] = new Wall(world, SCRGame.WIDTHm/2, 0, SCRGame.WIDTHm, 0.005f); // Нижняя граница мира;
        wall[3] = new Wall(world, SCRGame.WIDTHm, SCRGame.HEIGHTm/2, 0.005f, SCRGame.HEIGHTm); // Левая граница мира;
        wall[4] = new Wall(world, 6.4f, 1, 11.8f, 0.72f); // Пол для игры;
    }
}
// можно создавать все тела кинематическими, при смене уровня менять их.