package net.scratch.space.bullet;

import net.scratch.space.ImageLibrary;

import java.awt.*;

/**
 * Created by Scratch on 9/21/2014.
 */
public class MissileBullet extends Bullet {
    public MissileBullet(float x, float y, float rotation) {
        super(ImageLibrary.missile, 2000, x, y, rotation, 1);
    }
}
