package PointerUtils.DataUtil;

public class TextInputData {
    public String text;
    public TextInputData(String data) {
        text = data.substring(1, data.length() - 1);
    }
}
