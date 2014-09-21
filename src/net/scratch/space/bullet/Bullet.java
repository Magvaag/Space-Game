package net.scratch.space.bullet;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class Bullet {

    public float x;
    public float y;
    public float speed;
    public float rotation;

    public int age;
    public int maxAge;

    public Image image;

    public Bullet(Image image, int maxAge, float x, float y, float rotation, float speed){
        this.image = image;
        this.maxAge = maxAge;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.speed = speed;
    }

}
