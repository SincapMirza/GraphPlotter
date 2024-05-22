package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.EnterenceScreen.ExcelReading;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class GraphPie extends JFrame {
    private List<Double> yData;
    private ChartPanel chartPanel;
    private List<String> labels;

    public GraphPie(String title, List<Double> yData) {
        super(title);
        this.yData = yData;
        if(ExcelReading.getInstance().isFirstColumnIsString()){
        this.labels = ExcelReading.getInstance().getColumnHeaders2();}

        initUI();

    }


    private void initUI() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if(labels!=null){
        for (int i = 0; i < yData.size(); i++) {
            dataset.setValue(labels.get(i), yData.get(i));
            }
        }else {
            for (int i = 0; i < yData.size(); i++) {
                dataset.setValue("data"+(i+1), yData.get(i));
            }
        }



        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart",
                dataset,
                true, // include legend
                true,
                false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.BLACK);

        // Set pie section colors dynamically if needed
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA};
        for (int i = 0; i < yData.size(); i++) {
            plot.setSectionPaint("Data " + (i + 1), colors[i % colors.length]);
        }

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})",new DecimalFormat("0"),new DecimalFormat("0.00%")));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
