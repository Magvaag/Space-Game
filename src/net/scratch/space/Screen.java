package net.scratch.space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
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

    Image arrow;
    Image cursor;

    int mouseX;
    int mouseY;

    float speed = 0.3F;

    float posX;
    float posY;

    double rotation;

    int pixelSize = 2;

    List<Particle> particleList = new ArrayList<Particle>();

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

        thread.start();

        arrow = new ImageIcon("res/arrow.png").getImage();
        cursor = new ImageIcon("res/cursor.png").getImage();
    }

    public void paintComponent(Graphics g){
        g.clearRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString("Version: " + Main.VERSION, 5, 15);
        g.drawString("by ScratchForFun", 5, 30);

        g.drawString("posX: " + posX, 5, 60);
        g.drawString("posY: " + posY, 5, 75);
        g.drawString("particles: " + particleList.size(), 5, 90);

        for(Particle particle : particleList){
            g.setColor(particle.color);
            g.drawLine(particle.x-(int)posX, particle.y+(int)posY, particle.x-(int)posX, particle.y+(int)posY);
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(rotation, Main.SCREEN_WIDTH/2, Main.SCREEN_HEIGHT/2);
        g2d.drawImage(arrow, Main.SCREEN_WIDTH / 2 - arrow.getWidth(null), Main.SCREEN_HEIGHT / 2 - arrow.getHeight(null), arrow.getWidth(null) * pixelSize, arrow.getHeight(null) * pixelSize, null);
        g2d.dispose();

        g.drawImage(cursor, mouseX, mouseY, cursor.getWidth(null)*pixelSize, cursor.getHeight(null)*pixelSize, null);
    }

    public void update(){
        int deltaX = Main.SCREEN_WIDTH/2 - mouseX;
        int deltaY = Main.SCREEN_HEIGHT/2 - mouseY;

        rotation = -Math.atan2(deltaX, deltaY);

        posX+=Math.sin(rotation)*speed;
        posY+=Math.cos(rotation)*speed;

        particleList.add(new Particle(Color.RED, 500, Main.SCREEN_WIDTH/2+(int)posX, Main.SCREEN_HEIGHT/2-(int)posY));

        if(arrow != null) {
            double particleRotPoint1X = -Math.sin(rotation - Math.toRadians(40)) * arrow.getWidth(null)/2 * pixelSize;
            double particleRotPoint1Y = -Math.cos(rotation - Math.toRadians(40)) * arrow.getHeight(null)/2 * pixelSize;
            double particleRotPoint2X = -Math.sin(rotation + Math.toRadians(40)) * arrow.getWidth(null)/2 * pixelSize;
            double particleRotPoint2Y = -Math.cos(rotation + Math.toRadians(40)) * arrow.getHeight(null)/2 * pixelSize;

            double particleRotPointDeltaX = particleRotPoint2X-particleRotPoint1X;
            double particleRotPointDeltaY = particleRotPoint2Y-particleRotPoint1Y;

            float rnd = random.nextFloat()-0.5F;
            float maxAge = (rnd)*400;
            if(maxAge > 0) maxAge=-maxAge;

            for(int i = 0; i < 3; i++){
                particleList.add(new Particle(new Color(200, 0, 0), 300+(int)(maxAge+200), Main.SCREEN_WIDTH / 2 + (int) ((rnd)*particleRotPointDeltaX+posX), Main.SCREEN_HEIGHT / 2 - (int) ((rnd)*particleRotPointDeltaY+posY)));
            }

            //particleList.add(new Particle(new Color(200, 0, 0), 200, Main.SCREEN_WIDTH / 2 + (int) particleRotPoint2X, Main.SCREEN_HEIGHT / 2 - (int) particleRotPoint2Y));
        }

        List<Particle> removeList = new ArrayList<Particle>();

        for(Particle particle : particleList){
            particle.color = new Color(particle.color.getRed(), particle.color.getGreen(), particle.color.getBlue(), (int)(255-(float)particle.age/(float)particle.maxAge*255));
            particle.age++;
            if(particle.age>=particle.maxAge) removeList.add(particle);
        }

        for(Particle particle : removeList){
            particleList.remove(particle);
        }
    }

    @Override
    public void run() {
        while(true){
            // Update
            update();

            // Render
            repaint();

            // Sleep
            try{
                Thread.sleep(5);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
