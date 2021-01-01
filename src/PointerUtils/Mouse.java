package PointerUtils;

import DataUtils.QueueItem;
import PointerUtils.DataUtil.MouseMoveData;

import java.awt.*;

public class Mouse {

    Robot robot;

    public Mouse() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void moveMouse(QueueItem item) {
        Point currentPosition = MouseInfo.getPointerInfo().getLocation();
        float xVelocity, yVelocity;
        int scale;
        MouseMoveData point = (MouseMoveData) item.getObject();
        xVelocity = point.xVelocity;
        yVelocity = point.yVelocity;
        scale = point.scale;

        int x = Math.round(currentPosition.x + xVelocity * scale);
        int y = Math.round(currentPosition.y + yVelocity * scale);

        robot.mouseMove(x, y);
    }
}
