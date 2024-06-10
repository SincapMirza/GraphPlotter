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
import java.util.List;

public class GraphXY extends JFrame implements GraphInterface{
    private List<List<Double>> dataLists;
    private int xIndex;
    private int yIndex;
    private ChartPanel chartPanel;

    // Değişken renklerini belirleme
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
        if (dataLists == null || dataLists.isEmpty() || xIndex < 0 || xIndex >= dataLists.size() || yIndex < 0 || yIndex >= dataLists.size()) {
            throw new IndexOutOfBoundsException("Selected axis index is out of bounds.");
        }

        List<Double> xAxisData = dataLists.get(xIndex);
        List<Double> yAxisData = dataLists.get(yIndex);

        if (xAxisData.size() != yAxisData.size()) {
            throw new IllegalArgumentException("X and Y axis data lists must have the same size.");
        }

        XYSeries series = new XYSeries("XY Graph");
        for (int i = 0; i < xAxisData.size(); i++) {
            series.add(xAxisData.get(i), yAxisData.get(i));
        }

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

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Customize the renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, predefinedColors[i % predefinedColors.length]);
            renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        }
        plot.setRenderer(renderer);

        //label axix line renkleri belirleme
        plot.getDomainAxis().setAxisLinePaint(Color.BLACK);
        plot.getRangeAxis().setAxisLinePaint(Color.BLACK);
        plot.getDomainAxis().setTickMarkPaint(Color.BLACK);
        plot.getRangeAxis().setTickMarkPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);

       //max ve min annotasyonu setleme
        addMaxMinAnnotations(plot, xAxisData, yAxisData);

        chartPanel = new ChartPanel(chart);

        getContentPane().add(chartPanel);
    }

    private void addMaxMinAnnotations(XYPlot plot, List<Double> xAxisData, List<Double> yAxisData) {
        //max min bulma
        double maxX = xAxisData.stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).max().orElse(Double.NaN);
        double minX = xAxisData.stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).min().orElse(Double.NaN);
        double maxY = yAxisData.stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).max().orElse(Double.NaN);
        double minY = yAxisData.stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).min().orElse(Double.NaN);


        XYTextAnnotation maxAnnotation = new XYTextAnnotation("Max: (" + maxX + ", " + maxY + ")", maxX, maxY);
        maxAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 12));
        maxAnnotation.setPaint(Color.RED);
        maxAnnotation.setTextAnchor(TextAnchor.BASELINE_RIGHT); // Align top-left

        XYTextAnnotation minAnnotation = new XYTextAnnotation("Min: (" + minX + ", " + minY + ")", minX, minY);
        minAnnotation.setFont(new Font("SansSerif", Font.PLAIN, 12));
        minAnnotation.setPaint(Color.BLUE);
        minAnnotation.setTextAnchor(TextAnchor.BASELINE_LEFT); // Align top-left


        plot.addAnnotation(maxAnnotation);
        plot.addAnnotation(minAnnotation);
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
