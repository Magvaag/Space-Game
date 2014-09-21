package net.scratch.space.particle;

import net.scratch.space.Particle;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class DirectionalParticle extends Particle {

    public float prev_rotation;
    public float prev_speed;

    public float rotation;
    public float speed;

    public DirectionalParticle(Color color, int maxAge, int x, int y, float prev_rotation, float prev_speed, float rotation, float speed) {
        super(color, maxAge, x, y);

        this.prev_rotation = prev_rotation;
        this.prev_speed = prev_speed/4;

        this.rotation = rotation;
        this.speed = speed;
    }
}
