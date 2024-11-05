package org.example;

import java.applet.Applet;
import java.awt.*;

/**
 * 编译：
 * javac AppApplet.java
 * 各浏览器基本已不再支持
 */
public class _AppApplet extends Applet {
    public void paint(Graphics graphics) {
        graphics.drawString("Hello World",25,25);

    }
}
