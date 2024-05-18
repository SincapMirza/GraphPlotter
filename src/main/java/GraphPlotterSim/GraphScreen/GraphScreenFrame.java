package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;
import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.swing.*;

public class GraphScreenFrame extends JFrame {

    private static GraphScreenFrame graphScreenFrame;

    private GraphScreenFrame(){

        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

    }

    public static GraphScreenFrame getInstance(){

        if (graphScreenFrame == null) {
            synchronized (EnterenceScreenFrame.class) {
                if (graphScreenFrame == null) {
                    graphScreenFrame = new GraphScreenFrame();
                }
            }
        }
        return graphScreenFrame;

    }
}
