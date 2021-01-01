package PointerUtils;

import DataUtils.QueueItem;
import PointerUtils.DataUtil.MouseMoveData;

public class UtilItem extends QueueItem {
    public  UtilItem(Object obj, long ts) {
        super(obj, ts);
    }
    public UtilItem(String data, int type) {
        String components[] = data.split(",");
        long ts = Long.parseLong(components[0]);

        String remStr = "";
        for(int i = 1; i < components.length - 1; i++) {
            remStr += components[i] + ",";
        }
        remStr += components[components.length - 1];

        Object obj = null;
        if(type == DataInfo.MOUSE_MOVE_TYPE)
            obj = new MouseMoveData(remStr);

        setmObject(obj);
        setTimestamp(ts);
    }
}
