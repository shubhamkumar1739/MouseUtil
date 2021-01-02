package PointerUtils;

import DataUtils.QueueItem;
import PointerUtils.DataUtil.MouseClickData;
import PointerUtils.DataUtil.MouseMoveData;

public class UtilItem extends QueueItem {
    public  UtilItem(Object obj, long ts, int type) {
        super(obj, ts, type);
    }
    public UtilItem(String data) {
        String components[] = data.split(",");
        long ts = Long.parseLong(components[0]);
        int type = Integer.parseInt(components[1].trim());

        String remStr = "";
        for(int i = 2; i < components.length - 1; i++) {
            remStr += components[i] + ",";
        }
        remStr += components[components.length - 1];

        Object obj = null;
        if(type == DataInfo.MOUSE_MOVE)
            obj = new MouseMoveData(remStr);
        else if(type == DataInfo.MOUSE_CLICK)
            obj = new MouseClickData(ts);

        setmObject(obj);
        setTimestamp(ts);
        setType(type);
    }
}
