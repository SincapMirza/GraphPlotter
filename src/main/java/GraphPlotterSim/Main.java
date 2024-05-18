package GraphPlotterSim;
import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;
import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnterenceScreenFrame frame = EnterenceScreenFrame.getInstance();
            SelectionScreenFrame selectionScreenFrame = SelectionScreenFrame.getInstance(20);
            selectionScreenFrame.setVisible(true);
           // frame.setVisible(true);
        });
    }

}
