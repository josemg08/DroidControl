package com.josetomas.server.my_iterator.mouse;

import java.awt.*;

public class TouchScreenIterator extends MouseIterator {
    private static final Dimension SERVER_RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenRelationshipX, screenRelationshipY;
    private static Dimension MOBILE_RESOLUTION;

    public TouchScreenIterator(Robot robot) {
        super(robot);
    }

    @Override
    public void moveMouse(int destinationX, int destinationY, int action) {
        //TODO
    }

    public static void setScreenRelationship(Dimension MOBILE_RESOLUTION) {
        screenRelationshipX = (SERVER_RESOLUTION.width / MOBILE_RESOLUTION.width);
        screenRelationshipY = (SERVER_RESOLUTION.height / MOBILE_RESOLUTION.height);
    }

    public void setMobileResolution(int width, int height) {
        MOBILE_RESOLUTION = new Dimension(width, height);
        setScreenRelationship(MOBILE_RESOLUTION);
    }

}
