package com.tangqiang.jframe;
 
import javax.swing.*;
 
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
 
public class ShapedWindow extends JFrame {
    public ShapedWindow() {
        super("Shaped Window");
 
        setUndecorated(true);
 
        setSize(new Dimension(200, 300));
 
        Polygon polygon = new Polygon();
        polygon.addPoint(0, 200);
        polygon.addPoint(100, 0);
        polygon.addPoint(200, 200);
 
        Ellipse2D.Double theCircle = new Ellipse2D.Double(0, 100, 1.0*200, 1.0*200);
 
        GeneralPath path = new GeneralPath();
        path.append(polygon, true);
        path.append(theCircle, true);
 
        setShape(path);
 
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
 
        add(Box.createHorizontalGlue());
        JLabel label = new JLabel("Shaped window");
        label.setForeground(Color.white);
        add(label);
        add(Box.createHorizontalGlue());
 
        getContentPane().setBackground(Color.blue);
 
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
 
                if (ge.getDefaultScreenDevice().isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT)) {
                    new ShapedWindow();
                }
            }
        });
    }
}