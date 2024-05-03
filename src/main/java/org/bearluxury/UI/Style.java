package org.bearluxury.UI;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import java.awt.*;

/**
 * Style class that contains commonly used style attributes and methods
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Style {
    public static Color backgroundColor = new Color(232,223,185,255);
    public static Font defaultFont = new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13);
    public static ImageIcon logoCrest = new ImageIcon("src/main/resources/logo.png");

    /**
     * Used to add whitespaces to a component for wrapping UI components
     *
     * @param whitespace the number of whitespaces to fill
     * @return a JLabel of whitespaces
     */
    public static JLabel wrap(int whitespace) {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < whitespace; ++i) {
            filler.append(" ");
        }
        return new JLabel(String.valueOf(filler));
    }
}
