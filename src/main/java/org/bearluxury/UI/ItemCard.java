package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public class ItemCard extends JPanel {

    public ItemCard(String name, double price) {
        putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;");
        setPreferredSize(new Dimension(250, 100));
    }
}
