package net.scratch.space.bullet;

import net.scratch.space.ImageLibrary;

/**
 * Created by Scratch on 9/21/2014.
 */
public class SmallBullet extends Bullet {
    public SmallBullet(float x, float y, float rotation) {
        super(ImageLibrary.bullet, 1000, x, y, rotation, 1.5F);//2000
    }
}
