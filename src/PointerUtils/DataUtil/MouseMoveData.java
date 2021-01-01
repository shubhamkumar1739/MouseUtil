package PointerUtils.DataUtil;

public class MouseMoveData {
    float xVelocity, yVelocity;
    int scale;

    public MouseMoveData(String data) {
        String components[] = data.split(",");
        xVelocity = Float.parseFloat(components[0]);
        yVelocity = Float.parseFloat(components[1]);
        double velocity = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
        System.out.println(velocity);
        scale = Integer.parseInt(components[2].trim());
    }
}
