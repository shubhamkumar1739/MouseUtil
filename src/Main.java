import DataUtils.DataManager;
import DataUtils.QueueItem;
import PointerUtils.Mouse;
import PointerUtils.Point;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Mouse mouse = new Mouse();
        DataManager manager = new DataManager(5, mouse);
        int i = 0;
        while(i < 9) {
            int x = in.nextInt();
            int y = in.nextInt();
            long timestamp = System.currentTimeMillis();
            Point point = new Point(x, y);
            QueueItem item = new QueueItem(point, timestamp);
            manager.onDataReceived(item);
            i++;
        }
        System.out.println("End");
    }
}
