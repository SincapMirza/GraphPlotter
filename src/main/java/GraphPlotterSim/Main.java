package GraphPlotterSim;
import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;


import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnterenceScreenFrame frame = EnterenceScreenFrame.getInstance();
            frame.setVisible(true);

        });
    }

}
