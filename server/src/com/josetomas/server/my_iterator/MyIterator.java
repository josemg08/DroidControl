package com.josetomas.server.my_iterator;

import java.awt.*;

public abstract class MyIterator {
    protected Robot robot;

    public MyIterator(Robot robot) {
        this.robot = robot;
    }

    public void scroll(int scrollX, int scrollY) {
        if (scrollX == 0) {
            robot.mouseWheel(scrollY);
        } else if (scrollX > 0) {
            robot.keyPress(39);
            robot.keyRelease(39);
        } else {
            robot.keyPress(37);
            robot.keyRelease(37);
        }
    }

    public void zoom(int zoom) {
        robot.keyPress(17);
        robot.mouseWheel(zoom);
        robot.keyRelease(17);
    }

}
