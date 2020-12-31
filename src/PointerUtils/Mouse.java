package PointerUtils;

import DataUtils.QueueItem;

import java.awt.*;
import java.util.ArrayList;

public class Mouse {

    Robot robot;

    public Mouse() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void moveMouse(ArrayList<QueueItem> items) {
        for(QueueItem item : items) {
            int x, y;
            Point point = (Point) item.getObject();
            x = point.x;
            y = point.y;
            robot.mouseMove(x, y);
        }
    }
}
