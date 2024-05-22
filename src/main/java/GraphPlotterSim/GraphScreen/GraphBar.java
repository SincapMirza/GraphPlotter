package GraphPlotterSim.GraphScreen;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphBar extends JFrame {
    private List<List<Double>> dataLists;
    private ChartPanel chartPanel;

    public GraphBar(String title, List<List<Double>> dataLists) {
        super(title);
        this.dataLists = dataLists;
        initUI();
    }

    private void initUI() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Iterate through each row and add its value as a separate bar
        for (int i = 0; i < dataLists.size(); i++) {
            List<Double> row = dataLists.get(i);
            for (int j = 0; j < row.size(); j++) {
                Double value = row.get(j);
                if (value != null && !value.isNaN()) {
                    dataset.addValue(value, "Row " + (i + 1), "Value " + (j + 1));
                }
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // Set gridline color to gray
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Set axis color to black
        plot.getDomainAxis().setAxisLinePaint(Color.BLACK);
        plot.getRangeAxis().setAxisLinePaint(Color.BLACK);
        plot.getDomainAxis().setTickMarkPaint(Color.BLACK);
        plot.getRangeAxis().setTickMarkPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}


