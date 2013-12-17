package com.josetomas.server.my_iterator.mouse;

import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;

public class TrackPadIterator extends MouseIterator {
    private int posX, posY, OriginX, OriginY, sensibility;

    public TrackPadIterator(Robot robot) {
        super(robot);
        posX = posY = OriginX = OriginY = 100;
        sensibility = 2;
    }

    //Makes a vector with the last 2 positions received as the actual position
    //and the new ones the destination, the cursor moves in that vector as long as it doesn't
    //take it out of the screen. Action will be 0 for down, 1 for up, 2 for move
    public void moveMouse(int destinationX, int destinationY, int action) {
        if (action == 0) {
            OriginX = destinationX;
            OriginY = destinationY;
            return;
        }

        int deltaX = (destinationX - OriginX) / sensibility;
        int deltaY = (destinationY - OriginY) / sensibility;

        if (getDefaultToolkit().getScreenSize().getWidth() > (posX + deltaX) &&
                (posX + deltaX) > -1) {
            posX += deltaX;
        }
        if (getDefaultToolkit().getScreenSize().getHeight() > (posY + deltaY) &&
                (posY + deltaY) > -1) {
            posY += deltaY;
        }

        robot.mouseMove(posX, posY);
        OriginX = destinationX;
        OriginY = destinationY;
    }

    public void setSensibility(int sensibility) {
        this.sensibility = sensibility;
    }

}
