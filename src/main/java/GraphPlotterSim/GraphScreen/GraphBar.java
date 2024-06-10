package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.EnterenceScreen.ExcelReading;
import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphBar extends JFrame implements GraphInterface{
    private List<List<Double>> dataLists;
    private ChartPanel chartPanel;

    public GraphBar(String title, List<List<Double>> dataLists) {
        super(title);
        this.dataLists = dataLists;
        initUI();
    }

    private void initUI() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<String> listOfHeaders = ExcelReading.getInstance().getColumnHeaders2();

        if (dataLists.size() == 1) {
            // tek sütun seçildiyse her satırı tek tek dolaşıp değerlerini farklı çubuklara atama
            List<Double> row = dataLists.get(0);
            for (int j = 0; j < row.size(); j++) {
                Double value = row.get(j);

                if (value != null && !value.isNaN()) {
                    if (j < listOfHeaders.size()) {
                        String header = listOfHeaders.get(j);
                        dataset.addValue(value, "category", header);
                    } else {
                        dataset.addValue(value,"category","Data "+(j+1));
                    }
                }
            }
        } else {
            // birden fazla süütun seçili ise sütunları toplama
            if(ExcelReading.getInstance().isFirstColumnIsString()){
                ArrayList<Integer> selectedButtons = SelectionScreenFrame.getInstance().getSelectedAxisButtons();
                for(int a = 0; a < selectedButtons.size();a++){
                    selectedButtons.set(a,selectedButtons.get(a)-1);
                }
                System.out.println(selectedButtons);
            for (int i = 0; i < dataLists.size(); i++) {
                double sum = dataLists.get(i).stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).sum();
                String header = ExcelReading.getInstance().getColumnHeaders().get(selectedButtons.get(i));
                dataset.setValue(sum, "category", header);
            }}else {
                for (int i = 0; i < dataLists.size(); i++) {
                    double sum = dataLists.get(i).stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).sum();
                    String header = ExcelReading.getInstance().getColumnHeaders().get(SelectionScreenFrame.getInstance().getSelectedAxisButtons().get(i));
                    dataset.setValue(sum, "category", header);
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

       plot.getRenderer().setSeriesPaint(0, Color.darkGray);

        chartPanel = new ChartPanel(chart);

        setContentPane(chartPanel);
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}

