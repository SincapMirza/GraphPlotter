package GraphPlotterSim.GraphScreen;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphXY extends JFrame {
    private List<List<Double>> dataLists;
    private int xIndex;
    private int yIndex;
    private ChartPanel chartPanel;

    // Predefined colors for the variable lines
    private final Color[] predefinedColors = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.ORANGE,
            Color.MAGENTA,
            Color.CYAN
    };

    public GraphXY(String title, List<List<Double>> dataLists, int xIndex, int yIndex) {
        super(title);
        this.dataLists = dataLists;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        initUI();
    }

    private void initUI() {
        // Verify that the dataLists and indexes are valid
        if (dataLists == null || dataLists.isEmpty() || xIndex < 0 || xIndex >= dataLists.size() || yIndex < 0 || yIndex >= dataLists.size()) {
            throw new IndexOutOfBoundsException("Selected axis index is out of bounds.");
        }

        // Retrieve the X and Y axis data
        List<Double> xAxisData = dataLists.get(xIndex);
        List<Double> yAxisData = dataLists.get(yIndex);

        // Verify that the X and Y axis data lists have the same size
        if (xAxisData.size() != yAxisData.size()) {
            throw new IllegalArgumentException("X and Y axis data lists must have the same size.");
        }

        // Create the XYSeries with the data
        XYSeries series = new XYSeries("XY Graph");
        for (int i = 0; i < xAxisData.size(); i++) {
            series.add(xAxisData.get(i), yAxisData.get(i));
        }

        // Create the dataset and the chart
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Graph",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize the plot appearance
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE); // Set background to white
        plot.setDomainGridlinePaint(Color.GRAY); // Set grid line color to gray
        plot.setRangeGridlinePaint(Color.GRAY);

        // Customize the renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, predefinedColors[i % predefinedColors.length]); // Use predefined colors
            renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0)); // Set shape to black circle
        }
        plot.setRenderer(renderer);

        // Set axis colors and labels
        plot.getDomainAxis().setAxisLinePaint(Color.BLACK);
        plot.getRangeAxis().setAxisLinePaint(Color.BLACK);
        plot.getDomainAxis().setTickMarkPaint(Color.BLACK);
        plot.getRangeAxis().setTickMarkPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);

        // Create and add the chart panel
        chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(700, 400));
        getContentPane().add(chartPanel);
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
