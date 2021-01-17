package PointerUtils;

import DataUtils.QueueItem;
import PointerUtils.DataUtil.KeyBoardPressData;
import PointerUtils.DataUtil.KeyBoardReleaseData;
import PointerUtils.DataUtil.KeyboardPerformKeyActionData;
import PointerUtils.DataUtil.TextInputData;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Keyboard {
    Robot robot;
    public Keyboard(Robot r) {
        robot = r;
    }
    public void onKeyPressed(QueueItem item) {
        KeyBoardPressData data = (KeyBoardPressData) item.getmObject();
        robot.keyPress(KeyboardPerformKeyActionData.getKeyCode(data.keyType));
    }

    public void onKeyReleased(QueueItem item) {
        KeyBoardReleaseData data = (KeyBoardReleaseData) item.getmObject();
        robot.keyRelease(KeyboardPerformKeyActionData.getKeyCode(data.keyType));
    }

    public void onKeyAction(QueueItem item) {
        KeyboardPerformKeyActionData data = (KeyboardPerformKeyActionData) item.getmObject();
        for(int i = 0; i < data.numPresses; i++) {
            robot.keyPress(KeyboardPerformKeyActionData.getKeyCode(data.keyItem));
            robot.keyRelease(KeyboardPerformKeyActionData.getKeyCode(data.keyItem));
        }
    }

    public void onTextInput(QueueItem item) {
        TextInputData data = (TextInputData) item.getmObject();
        StringSelection selection = new StringSelection("" +data.text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        System.out.println(data.text);

        robot.keyPress(KeyEvent.VK_CONTROL);
        //robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        //robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
