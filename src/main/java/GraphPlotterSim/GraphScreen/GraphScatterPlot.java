package GraphPlotterSim.GraphScreen;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class GraphScatterPlot extends JFrame implements GraphInterface{
    private List<List<Double>> dataLists;
    private boolean showRegressionLine;
    private ChartPanel chartPanel;

    // Değişken renkleri
    private final Color[] predefinedColors = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.ORANGE,
            Color.MAGENTA,
            Color.CYAN
    };

    public GraphScatterPlot(String title, List<List<Double>> dataLists, boolean showRegressionLine) {
        super(title);
        this.dataLists = dataLists;
        this.showRegressionLine = showRegressionLine;
        initUI();
    }

    private void initUI() {
        XYSeriesCollection dataset = createDataset();

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Plot",
                "X-Axis", "Y-Axis", dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        Shape circle = new Ellipse2D.Double(-3, -3, 6, 6);
        renderer.setSeriesPaint(0, predefinedColors[(int) (Math.random() * 6)]);
        renderer.setSeriesShape(0, circle);

        if (showRegressionLine) {
            XYSeries regressionSeries = calculateSimpleRegressionLine(dataset.getSeries(0));
            dataset.addSeries(regressionSeries);
            renderer.setSeriesLinesVisible(1, true);
            renderer.setSeriesShapesVisible(1, false);
            renderer.setSeriesPaint(1, Color.BLACK);
        }

        plot.setRenderer(renderer);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        addMaxMinAnnotations(plot, dataset.getSeries(0));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(700, 400));

        getContentPane().add(chartPanel);
    }

    private void addMaxMinAnnotations(XYPlot plot, XYSeries series) {
        // Max ve min noktalarını hesaplama
        double maxX = series.getMaxX();
        double minX = series.getMinX();
        double maxY = series.getMaxY();
        double minY = series.getMinY();

        XYTextAnnotation maxAnnotation = new XYTextAnnotation("Max: (" + maxX + ", " + maxY + ")", maxX, maxY);
        maxAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 12));
        maxAnnotation.setPaint(Color.RED);
        maxAnnotation.setTextAnchor(TextAnchor.BASELINE_RIGHT);

        XYTextAnnotation minAnnotation = new XYTextAnnotation("Min: (" + minX + ", " + minY + ")", minX, minY);
        minAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 12));
        minAnnotation.setPaint(Color.BLUE);
        minAnnotation.setTextAnchor(TextAnchor.BASELINE_LEFT);

        plot.addAnnotation(maxAnnotation);
        plot.addAnnotation(minAnnotation);
    }

    private XYSeries calculateSimpleRegressionLine(XYSeries series) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = series.getItemCount();
        for (int i = 0; i < n; i++) {
            double x = series.getX(i).doubleValue();
            double y = series.getY(i).doubleValue();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        XYSeries regressionSeries = new XYSeries("Simple Regression Line");
        for (int i = 0; i < n; i++) {
            double x = series.getX(i).doubleValue();
            double y = slope * x + intercept;
            regressionSeries.add(x, y);
        }
        return regressionSeries;
    }

    private XYSeriesCollection createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries("Data Points");
        List<Double> yData = dataLists.get(0);
        List<Double> xData = dataLists.get(1);

        int dataSize = Math.min(yData.size(), xData.size());
        for (int i = 0; i < dataSize; i++) {
            series.add(xData.get(i), yData.get(i));
        }

        dataset.addSeries(series);

        return dataset;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
