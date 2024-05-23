package GraphPlotterSim.GraphScreen;

import GraphPlotterSim.EnterenceScreen.ExcelReading;
import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPie extends JFrame {
    private List<List<Double>> dataLists;
    private ChartPanel chartPanel;
    private List<String> labels;

    public GraphPie(String title, List<List<Double>> dataLists) {
        super(title);
        this.dataLists = dataLists;
        if(ExcelReading.getInstance().isFirstColumnIsString()){
        this.labels = ExcelReading.getInstance().getColumnHeaders2();}

        initUI();

    }


    private void initUI() {
        DefaultPieDataset dataset = new DefaultPieDataset();


        if (dataLists.size() == 1 && labels != null) {
            // Iterate through each row and add its value as a separate bar
            List<Double> row = dataLists.get(0); // Get the single list
            for (int j = 0; j < row.size(); j++) {
                Double value = row.get(j);

                if (value != null && !value.isNaN()) {
                    if (j < labels.size()) {
                        String header = labels.get(j);
                        dataset.setValue(header,value);
                    }
                }
            }
        }else if (labels == null && dataLists.size() == 1){
            List<Double> row = dataLists.get(0); // Get the single list
            for (int j = 0; j < row.size(); j++) {
                Double value = row.get(j);
                dataset.setValue("Data " + (j + 1), value);
            }

        }else {
            if(ExcelReading.getInstance().isFirstColumnIsString()){
            ArrayList<Integer> selectedButtons = SelectionScreenFrame.getInstance().getSelectedAxisButtons();
            for(int a = 0; a < selectedButtons.size();a++){
                selectedButtons.set(a,selectedButtons.get(a)-1);
            }
            // Sum up the values of each dataset if there are multiple columns selected
            for (int i = 0; i < dataLists.size(); i++) {
                double sum = dataLists.get(i).stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).sum();
                String header = ExcelReading.getInstance().getColumnHeaders().get(selectedButtons.get(i));
                dataset.setValue(header,sum);
            }}else {
                for (int i = 0; i < dataLists.size() ; i++){
                double sum = dataLists.get(i).stream().filter(d -> !d.isNaN()).mapToDouble(Double::doubleValue).sum();
                String header = ExcelReading.getInstance().getColumnHeaders().get(SelectionScreenFrame.getInstance().getSelectedAxisButtons().get(i));
                dataset.setValue(header,sum);
                }
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
        for (int i = 0; i < dataLists.size(); i++) {
            plot.setSectionPaint("Data " + (i + 1), colors[i % colors.length]);
        }

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})",new DecimalFormat("0"),new DecimalFormat("0.00%")));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(700, 400));
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }
}
