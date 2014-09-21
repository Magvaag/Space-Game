package net.scratch.space;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class Player {

    public Image image;

    public float x;
    public float y;

    public float speed; //0.3F
    public float rotation;

    public Color color;

    public Player(Image image, Color color, float speed, float x, float y){
        this.image = image;
        this.color = color;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

}
