package net.scratch.space.obstacle;

import net.scratch.space.ImageLibrary;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class CometObstacle extends LivingObstacle {
    public CometObstacle(float x, float y, float rotation, float speed) {
        super(ImageLibrary.comet, 5, x, y, rotation, speed);
    }
}
