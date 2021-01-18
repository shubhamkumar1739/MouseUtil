package PointerUtils;

import DataUtils.QueueItem;
import PointerUtils.DataUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

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
        if(data.keyItem == KeyboardEvent.BKSP)
            System.out.println("Do backspaces " + data.numPresses + " times");
    }

    public void onTextInput(QueueItem item) {
        TextInputData data = (TextInputData) item.getmObject();
        StringSelection selection = new StringSelection("" +data.text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        System.out.println(data.text);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void onPhysicalKeyAction(QueueItem item) {
        PhysicalKeyActionData data = (PhysicalKeyActionData) item.getmObject();
        if(data.shift == 1) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        robot.keyPress(data.keyCode);
        robot.keyRelease(data.keyCode);

        if(data.shift == 1) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
}
