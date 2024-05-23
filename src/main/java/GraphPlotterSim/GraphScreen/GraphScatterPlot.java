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
    private List<List<Double>> dataLists;
    private int selectedIndex;
    private boolean showRegressionLine;
    private ChartPanel chartPanel;

    // Predefined colors for the variable points
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
        this.selectedIndex = selectedIndex;
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
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, predefinedColors[i % predefinedColors.length]);
            renderer.setSeriesShape(i, circle);
        }

        if (showRegressionLine) {
            if (dataLists.size() == 1) {
                // Single-variable regression
                XYSeries regressionSeries = calculateSimpleRegressionLine(dataset.getSeries(selectedIndex));
                dataset.addSeries(regressionSeries);
                renderer.setSeriesLinesVisible(dataset.getSeriesCount() - 1, true);
                renderer.setSeriesShapesVisible(dataset.getSeriesCount() - 1, false);
                renderer.setSeriesPaint(dataset.getSeriesCount() - 1, Color.BLACK);
            } else if (dataLists.size() > 1) {
                // Multivariable regression
                XYSeries regressionSeries = calculateMultivariableRegressionLine(dataset);
                dataset.addSeries(regressionSeries);
                renderer.setSeriesLinesVisible(dataset.getSeriesCount() - 1, true);
                renderer.setSeriesShapesVisible(dataset.getSeriesCount() - 1, false);
                renderer.setSeriesPaint(dataset.getSeriesCount() - 1, Color.BLACK);
            }
        }

        plot.setRenderer(renderer);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(700, 400));

        getContentPane().add(chartPanel);
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

    private XYSeries calculateMultivariableRegressionLine(XYSeriesCollection dataset) {
        int numVariables = dataset.getSeriesCount() - 1;
        int numDataPoints = dataset.getSeries(0).getItemCount();

        // Create matrices for regression calculation
        double[][] xData = new double[numDataPoints][numVariables];
        double[] yData = new double[numDataPoints];

        // Populate matrices with data from dataset
        for (int i = 0; i < numDataPoints; i++) {
            for (int j = 0; j < numVariables; j++) {
                xData[i][j] = dataset.getSeries(j).getY(i).doubleValue();
            }
            yData[i] = dataset.getSeries(numVariables).getY(i).doubleValue();
        }

        // Perform multivariable regression
        double[] coefficients = performMultivariableRegression(xData, yData);

        // Calculate predicted values and create the regression series
        XYSeries regressionSeries = new XYSeries("Multivariable Regression Line");
        for (int i = 0; i < numDataPoints; i++) {
            double predictedY = coefficients[0]; // intercept term
            for (int j = 0; j < numVariables; j++) {
                predictedY += coefficients[j + 1] * xData[i][j];
            }
            regressionSeries.add(i + 1, predictedY);
        }
        return regressionSeries;
    }

    private double[] performMultivariableRegression(double[][] xData, double[] yData) {
        // Perform regression calculation here and return coefficients
        // This method should implement the necessary regression algorithm
        // For simplicity, you can use a library like Apache Commons Math
        // In this example, let's assume we have a placeholder method
        return new double[] {1.0, 0.5, 0.3}; // Placeholder coefficients (intercept, slope1, slope2, ...)
    }

    private XYSeriesCollection createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < dataLists.size(); i++) {
            XYSeries series = new XYSeries("Column " + (i + 1));
            for (int j = 0; j < dataLists.get(i).size(); j++) {
                series.add(j + 1, dataLists.get(i).get(j));
            }
            dataset.addSeries(series);
        }

        return dataset;
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}