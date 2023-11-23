import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuApp();
            }
        });
    }
}