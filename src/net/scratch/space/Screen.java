package net.scratch.space;

import net.scratch.space.bullet.Bullet;
import net.scratch.space.bullet.MissileBullet;
import net.scratch.space.bullet.SmallBullet;
import net.scratch.space.obstacle.CometObstacle;
import net.scratch.space.obstacle.LivingObstacle;
import net.scratch.space.obstacle.Obstacle;
import net.scratch.space.particle.DirectionalParticle;
import net.scratch.space.particle.EffectParticle;
import net.scratch.space.particle.StarParticle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Scratch on 9/20/2014.
 */
public class Screen extends JPanel implements Runnable {

    Random random = new Random();
    Thread thread = new Thread(this);

    Player player;

    int mouseX;
    int mouseY;

    int pixelSize = 2;
    boolean mouseDown_1 = false;
    boolean mouseDown_2 = false;
    boolean mouseDown_3 = false;

    boolean DRAW_MAP_LINES = false;

    List<Particle> particleList = new ArrayList<Particle>();
    List<Bullet> bulletList = new ArrayList<Bullet>();
    List<Obstacle> obstacleList = new ArrayList<Obstacle>();

    public Screen(JFrame frame){
        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX=e.getX();
                mouseY=e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX=e.getX();
                mouseY=e.getY();
            }
        });
        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==1)mouseDown_1 = true;
                if(e.getButton()==2)mouseDown_2 = true;
                if(e.getButton()==3)mouseDown_3 = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==1)mouseDown_1 = false;
                if(e.getButton()==2)mouseDown_2 = false;
                if(e.getButton()==3)mouseDown_3 = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        player = new Player(ImageLibrary.arrow, new Color(200, 0, 0), 0.3F, 0, 0);

        thread.start();
    }

    public void paintComponent(Graphics g){
        g.clearRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        drawZero();
        drawString(g, "Version: " + Main.VERSION);
        drawString(g, "by ScratchForFun");
        drawSpace();
        drawString(g, "fps: " + fps);
        drawSpace();
        drawString(g, "player.x: " + player.x);
        drawString(g, "player.y: " + player.y);
        if(obstacleList.size() >= 1){
            drawSpace();
            drawString(g, "obs_player.x: " + obstacleList.get(0).x);
            drawString(g, "obs_player.y: " + obstacleList.get(0).y);
        }
        if(bulletList.size() >= 1){
            drawSpace();
            drawString(g, "blt_player.x: " + bulletList.get(0).x);
            drawString(g, "blt_player.y: " + bulletList.get(0).y);
        }
        drawSpace();
        drawString(g, "particles: " + particleList.size());
        drawString(g, "bullets: " + bulletList.size());
        drawString(g, "obstacle: " + obstacleList.size());
        drawSpace();
        drawString(g, "mouseDown_1: " + mouseDown_1);
        drawString(g, "mouseDown_2: " + mouseDown_2);
        drawString(g, "mouseDown_3: " + mouseDown_3);

        if(DRAW_MAP_LINES) {
            g.setColor(new Color(255, 255, 255, 100));
            int boxSize = 250;
            int amountX = (Main.SCREEN_WIDTH / boxSize) + 1;
            int amountY = (Main.SCREEN_HEIGHT / boxSize) + 1;
            for (int x = 0; x < amountX; x++) {
                g.drawLine(Main.SCREEN_WIDTH / (amountX - 1) * x - (int) (player.x % (Main.SCREEN_WIDTH / (amountX - 1))), 0, Main.SCREEN_WIDTH / (amountX - 1) * x - (int) (player.x % (Main.SCREEN_WIDTH / (amountX - 1))), Main.SCREEN_HEIGHT);
                for (int y = 0; y < amountY; y++) {
                    g.drawLine(0, Main.SCREEN_HEIGHT / (amountY - 1) * y + (int) (player.y % (Main.SCREEN_HEIGHT / (amountY - 1))), Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT / (amountY - 1) * y + (int) (player.y % (Main.SCREEN_HEIGHT / (amountY - 1))));
                }
            }
        }

        for(Particle particle : particleList){
            g.setColor(particle.color);
            if(particle.x-(int)player.x > 0 && particle.x-(int)player.x < Main.SCREEN_WIDTH && particle.y+(int)player.y > 0 && particle.y+(int)player.y < Main.SCREEN_HEIGHT)g.drawLine((int)particle.x-(int)player.x, (int)particle.y+(int)player.y, (int)particle.x-(int)player.x, (int)particle.y+(int)player.y);
        }

        for(Bullet bullet : bulletList){
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(bullet.rotation, bullet.x-player.x, bullet.y+player.y);
            g2d.drawImage(bullet.image, (int)(bullet.x - bullet.image.getWidth(null)-player.x), (int)(bullet.y - bullet.image.getHeight(null)+player.y), bullet.image.getWidth(null) * pixelSize, bullet.image.getHeight(null) * pixelSize, null);
            g2d.dispose();
        }

        for(Obstacle obstacle : obstacleList){
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(obstacle.rotation+Math.toRadians(-45), obstacle.x-player.x, obstacle.y+player.y);
            g2d.drawImage(obstacle.image, (int)(obstacle.x - obstacle.image.getWidth(null)-player.x), (int)(obstacle.y - obstacle.image.getHeight(null)+player.y), obstacle.image.getWidth(null) * pixelSize, obstacle.image.getHeight(null) * pixelSize, null);
            g2d.dispose();
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(player.rotation, Main.SCREEN_WIDTH/2, Main.SCREEN_HEIGHT/2);
        g2d.drawImage(ImageLibrary.arrow, Main.SCREEN_WIDTH / 2 - ImageLibrary.arrow.getWidth(null), Main.SCREEN_HEIGHT / 2 - ImageLibrary.arrow.getHeight(null), ImageLibrary.arrow.getWidth(null) * pixelSize, ImageLibrary.arrow.getHeight(null) * pixelSize, null);
        g2d.dispose();

        g.drawImage(ImageLibrary.cursor, mouseX-ImageLibrary.cursor.getWidth(null)/2, mouseY-ImageLibrary.cursor.getHeight(null), ImageLibrary.cursor.getWidth(null)*pixelSize, ImageLibrary.cursor.getHeight(null)*pixelSize, null);
    }

    int i_space = 1;
    private void drawString(Graphics g, String text){
        g.setColor(Color.WHITE);
        g.drawString(text, 5, i_space*15);

        drawSpace();
    }
    private void drawSpace(){
        i_space++;
    }
    private void drawZero(){
        i_space=1;
    }

    int attack1Cooldown;
    int maxAttack1Cooldown = 45;
    int attack2Cooldown;
    int maxAttack2Cooldown = 120;
    int obstacleSpawnCooldown;
    int maxObstacleSpawnCooldown = 500;
    public void update(){
        movePlayer();

        spawnBullets();
        spawnObstacles();

        updateObstacles();

        updateBullets();

        spawnParticles();
        updateParticles();
    }

    private void movePlayer(){
        int deltaX = Main.SCREEN_WIDTH/2 - mouseX;
        int deltaY = Main.SCREEN_HEIGHT/2 - mouseY;

        player.rotation = (float)-Math.atan2(deltaX, deltaY);

        player.x+=Math.sin(player.rotation)*player.speed;
        player.y+=Math.cos(player.rotation)*player.speed;
    }

    private void spawnBullets(){
        if(attack1Cooldown >= maxAttack1Cooldown){
            if(mouseDown_1){
                attack1Cooldown = 0;
                bulletList.add(new SmallBullet(Main.SCREEN_WIDTH/2+player.x, Main.SCREEN_HEIGHT/2-player.y, player.rotation));
            }
        }else{
            attack1Cooldown++;
        }

        if(attack2Cooldown >= maxAttack2Cooldown){
            if(mouseDown_3){
                attack2Cooldown = 0;
                bulletList.add(new MissileBullet(Main.SCREEN_WIDTH/2+player.x, Main.SCREEN_HEIGHT/2-player.y, player.rotation));
            }
        }else{
            attack2Cooldown++;
        }
    }
    private void spawnObstacles(){
        if(obstacleSpawnCooldown >= maxObstacleSpawnCooldown){
            obstacleSpawnCooldown = 0;
            obstacleList.add(new CometObstacle(-100+player.x, random.nextInt(1080)-player.y, (float)Math.toRadians(90), 0.5F));
        }else{
            obstacleSpawnCooldown++;
        }
    }
    private void spawnParticles(){
        // Stars
        particleList.add(new StarParticle(Color.YELLOW, 700, random.nextInt(Main.SCREEN_WIDTH+400)-200+(int)player.x, random.nextInt(Main.SCREEN_HEIGHT+400)-200-(int)player.y));

        spawnParticlesPlayer(player);
    }
    private void spawnParticlesBullet(Bullet bullet){
        double particleRotPoint1X = -Math.sin(bullet.rotation - Math.toRadians(40)) * bullet.image.getWidth(null)/2 * pixelSize;
        double particleRotPoint1Y = -Math.cos(bullet.rotation - Math.toRadians(40)) * bullet.image.getHeight(null)/2 * pixelSize;
        double particleRotPoint2X = -Math.sin(bullet.rotation + Math.toRadians(40)) * bullet.image.getWidth(null)/2 * pixelSize;
        double particleRotPoint2Y = -Math.cos(bullet.rotation + Math.toRadians(40)) * bullet.image.getHeight(null)/2 * pixelSize;

        double particleRotPointDeltaX = particleRotPoint2X-particleRotPoint1X;
        double particleRotPointDeltaY = particleRotPoint2Y-particleRotPoint1Y;

        float rnd = random.nextFloat()-0.5F;
        float maxAge = (rnd)*400;
        if(maxAge > 0) maxAge=-maxAge;
        Color color = Color.GREEN;
        if(bullet instanceof MissileBullet){
            maxAge = (rnd)*400;
            maxAge+=200;
            color = new Color(160, 60, 60);
        }
        if(bullet instanceof SmallBullet){
            maxAge = (rnd)*100;
            maxAge+=50;
            color = new Color(100, 60, 60);
        }

        for(int i = 0; i < 3; i++){
            particleList.add(new EffectParticle(color, (int)maxAge, (int)bullet.x-(int)(particleRotPointDeltaX*rnd), (int)bullet.y-(int)(particleRotPointDeltaY*rnd)));
        }
    }
    private void spawnParticlesPlayer(Player player){
        particleList.add(new EffectParticle(player.color, 500, Main.SCREEN_WIDTH/2+(int)player.x, Main.SCREEN_HEIGHT/2-(int)player.y));

        double particleRotPoint1X = -Math.sin(player.rotation - Math.toRadians(40)) * ImageLibrary.arrow.getWidth(null)/2 * pixelSize;
        double particleRotPoint1Y = -Math.cos(player.rotation - Math.toRadians(40)) * ImageLibrary.arrow.getHeight(null)/2 * pixelSize;
        double particleRotPoint2X = -Math.sin(player.rotation + Math.toRadians(40)) * ImageLibrary.arrow.getWidth(null)/2 * pixelSize;
        double particleRotPoint2Y = -Math.cos(player.rotation + Math.toRadians(40)) * ImageLibrary.arrow.getHeight(null)/2 * pixelSize;

        double particleRotPointDeltaX = particleRotPoint2X-particleRotPoint1X;
        double particleRotPointDeltaY = particleRotPoint2Y-particleRotPoint1Y;

        float rnd = random.nextFloat()-0.5F;
        float maxAge = (rnd)*400;
        if(maxAge > 0) maxAge=-maxAge;

        for(int i = 0; i < 3; i++){
            particleList.add(new EffectParticle(player.color, 300+(int)(maxAge+200), Main.SCREEN_WIDTH / 2 + (int) ((rnd)*particleRotPointDeltaX+player.x), Main.SCREEN_HEIGHT / 2 - (int) ((rnd)*particleRotPointDeltaY+player.y)));
        }

        //particleList.add(new EffectParticle(new Color(200, 0, 0), 200, Main.SCREEN_WIDTH / 2 + (int) particleRotPoint2X, Main.SCREEN_HEIGHT / 2 - (int) particleRotPoint2Y));
    }
    private void spawnParticlesComet(CometObstacle obstacle){
        double particleRotPoint1X = -Math.sin(obstacle.rotation - Math.toRadians(40)) * ImageLibrary.comet.getWidth(null)/2 * pixelSize;
        double particleRotPoint1Y = -Math.cos(obstacle.rotation - Math.toRadians(40)) * ImageLibrary.comet.getHeight(null)/2 * pixelSize;
        double particleRotPoint2X = -Math.sin(obstacle.rotation + Math.toRadians(40)) * ImageLibrary.comet.getWidth(null)/2 * pixelSize;
        double particleRotPoint2Y = -Math.cos(obstacle.rotation + Math.toRadians(40)) * ImageLibrary.comet.getHeight(null)/2 * pixelSize;

        double particleRotPointDeltaX = particleRotPoint2X-particleRotPoint1X;
        double particleRotPointDeltaY = particleRotPoint2Y-particleRotPoint1Y;

        float rnd = random.nextFloat()-0.5F;
        float maxAge = (rnd)*400;
        if(maxAge > 0) maxAge=-maxAge;
        Color color = Color.ORANGE;

        maxAge+=300;

        for(int i = 0; i < 3; i++){
            particleList.add(new EffectParticle(color, (int)maxAge, (int)obstacle.x-(int)(particleRotPointDeltaX*rnd), (int)obstacle.y-(int)(particleRotPointDeltaY*rnd)));
        }
    }

    private void updateParticles(){
        List<Particle> removeParticleList = new ArrayList<Particle>();

        for(Particle particle : particleList){
            if(particle instanceof EffectParticle) particle.color = new Color(particle.color.getRed(), particle.color.getGreen(), particle.color.getBlue(), (int)(255-(float)particle.age/(float)particle.maxAge*255));
            if(particle instanceof DirectionalParticle){
                DirectionalParticle d_particle = (DirectionalParticle) particle;
                d_particle.x+=Math.sin(d_particle.rotation)*d_particle.speed;
                d_particle.y-=Math.cos(d_particle.rotation)*d_particle.speed;

                d_particle.x+=Math.sin(d_particle.prev_rotation)*d_particle.prev_speed;
                d_particle.y-=Math.cos(d_particle.prev_rotation)*d_particle.prev_speed;
            }

            particle.age++;
            if(particle.age>=particle.maxAge) removeParticleList.add(particle);
        }

        for(Particle particle : removeParticleList){
            particleList.remove(particle);
        }
    }
    private void updateObstacles(){
        List<Obstacle> removeObstacleList = new ArrayList<Obstacle>();

        for(Obstacle obstacle : obstacleList){
            obstacle.x+=Math.sin(obstacle.rotation)*obstacle.speed;
            obstacle.y-=Math.cos(obstacle.rotation)*obstacle.speed;

            if(obstacle instanceof CometObstacle) spawnParticlesComet((CometObstacle)obstacle);
            if(obstacle instanceof LivingObstacle){
                LivingObstacle l_obstacle = (LivingObstacle) obstacle;
                if(l_obstacle.health <= 0)removeObstacleList.add(obstacle);
            }
        }

        for(Obstacle obstacle : removeObstacleList){
            obstacleList.remove(obstacle);
        }
    }
    private void updateBullets(){
        List<Bullet> removeBulletList = new ArrayList<Bullet>();

        for(Bullet bullet : bulletList){
            bullet.x+=Math.sin(bullet.rotation)*bullet.speed;
            bullet.y-=Math.cos(bullet.rotation)*bullet.speed;

            for(Obstacle obstacle : obstacleList){
                boolean hit_width = false;
                boolean hit_height = false;

                // Hit left/right side
                if(bullet.x-bullet.image.getWidth(null)*pixelSize/2 > obstacle.x-obstacle.image.getWidth(null)*pixelSize/2 - bullet.image.getWidth(null)*pixelSize && bullet.x-bullet.image.getWidth(null)*pixelSize/2 < obstacle.x-obstacle.image.getWidth(null)*pixelSize/2 + obstacle.image.getWidth(null)*pixelSize){
                    hit_width = true;
                }

                // Hit top/bottom side
                if(bullet.y-bullet.image.getHeight(null)*pixelSize/2 > obstacle.y-obstacle.image.getHeight(null)*pixelSize/2 - bullet.image.getHeight(null)*pixelSize && bullet.y-bullet.image.getHeight(null)*pixelSize/2 < obstacle.y-obstacle.image.getHeight(null)*pixelSize/2 + obstacle.image.getHeight(null)*pixelSize){
                    hit_height = true;
                }

                // HIT!
                if(hit_width && hit_height){
                    removeBulletList.add(bullet);
                    if(obstacle instanceof LivingObstacle){
                        LivingObstacle l_obstacle = (LivingObstacle) obstacle;
                        l_obstacle.health--;
                    }
                }
            }

            bullet.age++;
            if(bullet.age >= bullet.maxAge) removeBulletList.add(bullet);
            else spawnParticlesBullet(bullet);
        }

        for(Bullet bullet : removeBulletList){
            bulletList.remove(bullet);

            int bulletDivider = 0;

            if(bullet instanceof SmallBullet) bulletDivider = 4;
            if(bullet instanceof MissileBullet) bulletDivider = 2;

            // Add cool effects!
            for (int i = 0; i < 100; i++) {
                float speed = random.nextFloat() / bulletDivider;
                Color color = new Color(1f, random.nextFloat(), 0);

                particleList.add(new DirectionalParticle(color, 50 + random.nextInt(50), (int) bullet.x, (int) bullet.y, bullet.rotation, bullet.speed, (float) Math.toRadians(random.nextInt(360)), speed));
            }
        }
    }

    int fps;
    public void run() {
        long time = System.currentTimeMillis();
        int frames = 0;

        while(true){
            // Update
            update();

            // Render
            repaint();
            frames++;

            // Sleep
            try{
                Thread.sleep(5);
            }catch(Exception e){
                e.printStackTrace();
            }

            if(time+1000 <= System.currentTimeMillis()){
                fps = frames;
                frames = 0;
                time = System.currentTimeMillis();
            }
        }
    }
}
