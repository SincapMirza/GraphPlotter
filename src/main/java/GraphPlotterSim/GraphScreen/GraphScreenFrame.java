package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import java.io.*;
import java.util.*;

public class GraphScreenFrame extends JFrame {

    private static GraphScreenFrame graphScreenFrame;
    private JPanel xyGraphPanel;
    private JPanel pieChartPanel;
    private JPanel scatterPlotPanel;
    private JPanel barChartPanel;
    private List<JPanel> graphPanels;
    private GraphXY xyGraph;
    private GraphScatterPlot scatterPlot;
    private GraphBar barGraph;
    private GraphPie pieChart;

    private GraphScreenFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Graph Plotter");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SelectionScreenFrame.getInstance().secondUpdateButtons();
                SelectionScreenFrame.getInstance().setVisible(true);
                dispose();
            }
        });

        xyGraphPanel = new JPanel();
        pieChartPanel = new JPanel();
        scatterPlotPanel = new JPanel();
        barChartPanel = new JPanel();

        graphPanels = new ArrayList<>();
        graphPanels.add(xyGraphPanel);
        graphPanels.add(pieChartPanel);
        graphPanels.add(scatterPlotPanel);
        graphPanels.add(barChartPanel);
    }

    private void setGridLayout(int rows, int cols) {
        setLayout(new GridLayout(rows, cols, 10, 10));
    }

    public void updateGraph() {
        xyGraphPanel.removeAll();
        pieChartPanel.removeAll();
        scatterPlotPanel.removeAll();
        barChartPanel.removeAll();

        List<JPanel> selectedPanels = new ArrayList<>();

        if (SelectionScreenFrame.getInstance().getGraphButton1().isSelected()) {
            xyGraph = new GraphXY("XY Graph", SelectionScreenFrame.getInstance().getDataLists(), 1, 0);
            xyGraphPanel.add(xyGraph.getChartPanel());
            selectedPanels.add(xyGraphPanel);
        }

        if (SelectionScreenFrame.getInstance().getGraphButton2().isSelected()) {
            scatterPlot = new GraphScatterPlot("Scatter Plot", SelectionScreenFrame.getInstance().getDataLists(), true);
            scatterPlotPanel.add(scatterPlot.getChartPanel());
            selectedPanels.add(scatterPlotPanel);
        }

        if (SelectionScreenFrame.getInstance().getGraphButton3().isSelected()) {
            barGraph = new GraphBar("Bar Graph", SelectionScreenFrame.getInstance().getDataLists());
            barChartPanel.add(barGraph.getChartPanel());
            selectedPanels.add(barChartPanel);
        }

        if (SelectionScreenFrame.getInstance().getGraphButton4().isSelected()) {
            pieChart = new GraphPie("Pie Chart", SelectionScreenFrame.getInstance().getDataLists());
            pieChartPanel.add(pieChart.getChartPanel());
            selectedPanels.add(pieChartPanel);
        }

        getContentPane().removeAll();
        int panelCount = selectedPanels.size();

        if (panelCount == 1) {
            setGridLayout(1, 1);
            setPanelSize(selectedPanels.get(0), 1300, 800);
        } else if (panelCount == 2) {
            setGridLayout(1, 2);
            setPanelSize(selectedPanels.get(0), 740, 800);
            setPanelSize(selectedPanels.get(1), 740, 800);
        } else if (panelCount == 3 || panelCount == 4) {
            setGridLayout(2, 2);
            for (JPanel panel : selectedPanels) {
                setPanelSize(panel, 740, 400);
            }
        }

        for (JPanel panel : selectedPanels) {
            getContentPane().add(panel);
        }

        revalidate();
        repaint();

        if (SelectionScreenFrame.getInstance().getEmail() != null && SelectionScreenFrame.getInstance().getCheckBox().isSelected()) {
            EmailSenderThread emailSenderThread = new EmailSenderThread(SelectionScreenFrame.getInstance().getEmail(), "M&M Graph Plotter plotted graphs", "Your graphs:", selectedPanels);
            emailSenderThread.start();
        }

    }

    private void setPanelSize(JPanel panel, int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setSize(new Dimension(width, height));

        if (panel.getComponentCount() > 0 && panel.getComponent(0) instanceof JPanel) {
            JPanel innerPanel = (JPanel) panel.getComponent(0);
            innerPanel.setPreferredSize(new Dimension(width, height));
            innerPanel.setMaximumSize(new Dimension(width, height));
            innerPanel.setMinimumSize(new Dimension(width, height));
            innerPanel.setSize(new Dimension(width, height));
        }
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
