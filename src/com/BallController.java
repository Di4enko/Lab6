package com;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class BallController extends MouseAdapter {
    private Field field;
    private boolean paused;
    private long pressedTime;
    private long releasedTime;
    private int pressedX;
    private int pressedY;
    private int releasedX;
    private int releasedY;

    public BallController(Field field) {
        super();
        this.field = field;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        pressedTime = new Date().getTime();
        pressedX = e.getX();
        pressedY = e.getY();
        paused = field.isPaused();
        field.pause();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        releasedTime = new Date().getTime();
        releasedX = e.getX();
        releasedY = e.getY();
        pushBall();
        if (!paused) {
            field.resume();
        }
    }

    private BouncingBall findBallToPush(List<BouncingBall> balls) {
        BouncingBall clickedBall = null;
        for(BouncingBall ball: balls) {
            int radius = ball.getRadius();
            int deltaX = Math.abs(pressedX - ball.getX());
            int deltaY = Math.abs(pressedY - ball.getY());
            if(deltaX <= radius && deltaY <= radius) {
                clickedBall = ball;
            }
        }
        return  clickedBall;
    }

    public void pushBall() {
        BouncingBall ball = findBallToPush(field.getBalls());
        if(ball != null) {
            double newSpeedX = (releasedX - pressedX) / (releasedTime - pressedTime) * 5;
            double newSpeedY = (releasedY - pressedY) / (releasedTime - pressedTime) * 5;
            int maxSpeed = ball.getMaxSpeed();
            ball.setSpeedX(newSpeedX <= maxSpeed ? newSpeedX : maxSpeed);
            ball.setSpeedY(newSpeedY <= maxSpeed ? newSpeedY : maxSpeed);
        }
    }
}
