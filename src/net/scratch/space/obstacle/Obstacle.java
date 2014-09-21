package net.scratch.space.obstacle;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class Obstacle {

    public Image image;

    public float x;
    public float y;
    public float rotation;
    public float speed;

    public Obstacle(Image image, float x, float y, float rotation, float speed){
        this.image = image;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.speed = speed;
    }

}
