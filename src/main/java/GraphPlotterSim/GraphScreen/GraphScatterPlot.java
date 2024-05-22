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
import java.awt.geom.Ellipse2D;
import java.util.List;

public class GraphScatterPlot extends JFrame {
    private List<Double> xData;
    private List<Double> yData;
    private boolean showRegressionLine;
    private ChartPanel chartPanel;

    public GraphScatterPlot(String title, List<Double> xData, List<Double> yData, boolean showRegressionLine) {
        super(title);
        this.xData = xData;
        this.yData = yData;
        this.showRegressionLine = showRegressionLine;
        initUI();
    }

    private void initUI() {
        XYSeries series = new XYSeries("Scatter Data");
        for (int i = 0; i < xData.size(); i++) {
            series.add(xData.get(i), yData.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot",
                "X-Axis", "Y-Axis", dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = chart.getXYPlot();

        // Set background to white
        chart.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);

        // Set data point renderer to use black circles
        XYLineAndShapeRenderer dataRenderer = new XYLineAndShapeRenderer();
        dataRenderer.setSeriesLinesVisible(0, false);
        dataRenderer.setSeriesShapesVisible(0, true);
        dataRenderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
        dataRenderer.setSeriesPaint(0, Color.BLACK);
        plot.setRenderer(dataRenderer);

        if (showRegressionLine) {
            // Calculate regression line
            double[] regressionParams = calculateRegression(xData, yData);
            XYSeries regressionLine = new XYSeries("Regression Line");
            double xMin = xData.stream().min(Double::compare).orElse(0.0);
            double xMax = xData.stream().max(Double::compare).orElse(0.0);
            regressionLine.add(xMin, regressionParams[0] + regressionParams[1] * xMin);
            regressionLine.add(xMax, regressionParams[0] + regressionParams[1] * xMax);
            dataset.addSeries(regressionLine);

            // Set regression line renderer to draw line without points
            XYLineAndShapeRenderer regressionRenderer = new XYLineAndShapeRenderer();
            regressionRenderer.setSeriesLinesVisible(0, true);
            regressionRenderer.setSeriesShapesVisible(0, false);
            regressionRenderer.setSeriesPaint(0, Color.RED); // Set regression line color to red
            plot.setRenderer(1, regressionRenderer); // Use index 1 for regression line renderer
        }

        // Set gridline color to gray
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.getDomainAxis().setAxisLinePaint(Color.BLACK);
        plot.getRangeAxis().setAxisLinePaint(Color.BLACK);
        plot.getDomainAxis().setTickMarkPaint(Color.BLACK);
        plot.getRangeAxis().setTickMarkPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
    }

    private double[] calculateRegression(List<Double> xData, List<Double> yData) {
        int n = xData.size();
        double sumX = xData.stream().mapToDouble(Double::doubleValue).sum();
        double sumY = yData.stream().mapToDouble(Double::doubleValue).sum();
        double sumXY = 0.0;
        double sumXX = 0.0;

        for (int i = 0; i < n; i++) {
            sumXY += xData.get(i) * yData.get(i);
            sumXX += xData.get(i) * xData.get(i);
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return new double[]{intercept, slope};
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
