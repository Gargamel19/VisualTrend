package UI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {


    public static final int DEFAULTSIZE = 50;

    public static int rectSize;

    public static JLabel nix = new JLabel();

    public Window() throws HeadlessException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                rectSize = DEFAULTSIZE;


                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new Panels(frame.getWidth(), frame.getHeight(), rectSize));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/4, Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);

            }
        });
    }

    private void eigenschaften() {
        rectSize = DEFAULTSIZE;
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize().width + " : " + Toolkit.getDefaultToolkit().getScreenSize().height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/4, Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);



    }

    private void zuweisen() {

        Panels panels = new Panels(getBounds().height, getBounds().height, rectSize);

        add(nix);



    }


}
