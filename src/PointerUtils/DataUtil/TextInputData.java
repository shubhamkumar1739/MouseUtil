package PointerUtils.DataUtil;

public class TextInputData {
    public String text;
    public TextInputData(String data) {
        String components[] = data.split(",");
        text = components[0].trim();
    }
}
