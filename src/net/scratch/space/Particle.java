package net.scratch.space;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class Particle {

    int x;
    int y;

    Color color;

    int age;
    int maxAge;

    public Particle(Color color, int maxAge, int x, int y){
        this.color = color;
        this.maxAge = maxAge;
        this.x = x;
        this.y = y;
    }

}
