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
    private List<Double> xData;
    private List<Double> yData;
    private ChartPanel chartPanel;

    public GraphXY(String title, List<Double> xData, List<Double> yData) {
        super(title);
        this.xData = xData;
        this.yData = yData;
        initUI();
    }

    private void initUI() {
        XYSeries series = new XYSeries("XY Data");
        for (int i = 0; i < xData.size(); i++) {
            series.add(xData.get(i), yData.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Graph",
                "X-Axis", "Y-Axis", dataset,
                PlotOrientation.VERTICAL,
                false, // Remove legend
                true, // Include tooltips
                false); // Exclude URLs

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE); // Set background to white

        // Set grid line color to gray
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Set line color to black
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0)); // Set shape to black circle
        plot.setRenderer(renderer);

        // Set axis color to black
        plot.getDomainAxis().setAxisLinePaint(Color.BLACK);
        plot.getRangeAxis().setAxisLinePaint(Color.BLACK);
        plot.getDomainAxis().setTickMarkPaint(Color.BLACK);
        plot.getRangeAxis().setTickMarkPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
