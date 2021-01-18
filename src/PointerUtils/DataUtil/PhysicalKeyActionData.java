package PointerUtils.DataUtil;

public class PhysicalKeyActionData {
    public int keyCode, shift;
    public PhysicalKeyActionData(String data) {
        String components[] = data.split(",");
        keyCode = Integer.parseInt(components[0].trim());
        shift = Integer.parseInt(components[1].trim());
    }
}
