package net.scratch.space.obstacle;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class LivingObstacle extends Obstacle {

    public int health;

    public LivingObstacle(Image image, int health, float x, float y, float rotation, float speed) {
        super(image, x, y, rotation, speed);

        this.health = health;
    }
}
