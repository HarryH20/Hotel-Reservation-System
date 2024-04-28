package org.bearluxury.UI;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import java.awt.*;

public class Style {
    public static Color backgroundColor = new Color(232,223,185,255);
    public static Font defaultFont = new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13);
    public static ImageIcon logoCrest = new ImageIcon("src/main/resources/logo.png");

    /*
        wrap method used for filling space in UI
     */
    public static JLabel wrap(int whitespace) {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < whitespace; ++i) {
            filler.append(" ");
        }
        return new JLabel(String.valueOf(filler));
    }
}
