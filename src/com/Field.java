package com;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field extends JPanel {
    private boolean paused;
    private BallController ballController = new BallController(this);
    private List<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    private Timer repaintTimer = new Timer(10, e -> repaint()) ;

    public Field(){
        setBackground(Color.WHITE);
        repaintTimer.start();
        this.addMouseListener(ballController);
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D canvas = (Graphics2D) graphics;
        for(BouncingBall ball: balls){
            ball.paint(canvas);
        }
    }

    public  void addBall(){
        balls.add(new BouncingBall(this));
    }

    public synchronized void pause(){
        paused = true;
    }

    public synchronized  void resume(){
        paused=false;
        notifyAll();
    }

    public synchronized  void canMove(BouncingBall ball) throws InterruptedException{
        if(paused){
            wait();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public List<BouncingBall> getBalls() {
        return balls;
    }
}
