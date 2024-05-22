package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;
import GraphPlotterSim.EnterenceScreen.ExcelReading;
import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class GraphScreenFrame extends JFrame {

    private static GraphScreenFrame graphScreenFrame;
    private JPanel xyGraphPanel;
    private JPanel pieChartPanel;
    private JPanel scatterPlotPanel;
    JPanel barChartPanel;

    private GraphScreenFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectionScreenFrame.getInstance().setVisible(true); // Singleton SelectionScreenFrame'i görünür yap
                dispose();
            }
        });

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,50));

        //xyGraphPanel = new JPanel(); // Başlangıçta boş bir panel ekleyin
        //xyGraphPanel.setPreferredSize(new Dimension(400, 400));
        pieChartPanel = new JPanel();
        pieChartPanel.setPreferredSize(new Dimension(400,400));
        barChartPanel = new JPanel();
        barChartPanel.setPreferredSize(new Dimension(400,400));
        /*scatterPlotPanel =  new JPanel();
        scatterPlotPanel.setPreferredSize(new Dimension(400,400));

       // mainPanel.add(xyGraphPanel);
        mainPanel.add(scatterPlotPanel);*/
        mainPanel.add(barChartPanel);
        mainPanel.add(pieChartPanel);

        add(mainPanel);
    }

    public void updateGraph() {
        //xyGraphPanel.removeAll(); // Önceki grafiği kaldırın
        pieChartPanel.removeAll();
        barChartPanel.removeAll();
        //scatterPlotPanel.removeAll();

       // GraphXY xyGraph = new GraphXY("XY Graph", SelectionScreenFrame.getInstance().getxData(), SelectionScreenFrame.getInstance().getyData());
        GraphPie pieChart = new GraphPie("Pie Chart", SelectionScreenFrame.getInstance().getDataLists());
        GraphBar barGraph = new GraphBar("bar", SelectionScreenFrame.getInstance().getDataLists());
        /*GraphScatterPlot graphScatterPlot = new GraphScatterPlot("scatter",SelectionScreenFrame.getInstance().getDataLists(),true);

        scatterPlotPanel.add(graphScatterPlot.getChartPanel());
        scatterPlotPanel.revalidate();
        scatterPlotPanel.repaint();*/

        barChartPanel.add(barGraph.getChartPanel());
        barChartPanel.revalidate();
        barChartPanel.repaint();

        pieChartPanel.add(pieChart.getChartPanel());
        pieChartPanel.revalidate();
        pieChartPanel.repaint();

       /* xyGraphPanel.add(xyGraph.getChartPanel()); // Yeni grafiği ekleyin
        xyGraphPanel.revalidate(); // Paneli yeniden doğrulayın
        xyGraphPanel.repaint(); // Paneli yeniden boyayın*/
    }

    public static GraphScreenFrame getInstance() {
        if (graphScreenFrame == null) {
            synchronized (GraphScreenFrame.class) {
                if (graphScreenFrame == null) {
                    graphScreenFrame = new GraphScreenFrame();
                }
            }
        }
        return graphScreenFrame;
    }
}
