/*
Develop by Jose Gonzalez
2013 - Argentina
*/

package com.josetomas.server.my_iterator.mouse;

import com.josetomas.server.my_iterator.MyIterator;

import java.awt.*;
import java.awt.event.InputEvent;

public abstract class MouseIterator extends MyIterator {

    public MouseIterator(Robot robot) {
        super(robot);
    }

    public void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void rightClick() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }

    public abstract void moveMouse(int destinationX, int destinationY, int action);

}
