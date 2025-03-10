package school.thitracnghiem;

import GUI.MainGUI;
import com.formdev.flatlaf.FlatLightLaf;

public class ThiTracNghiem {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }
}
