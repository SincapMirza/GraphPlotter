package GraphPlotterSim.SelectionScreen;

import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;
import GraphPlotterSim.GraphScreen.GraphScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SelectionScreenFrame extends JFrame {

    private static SelectionScreenFrame selectionScreenFrame;

    private JToggleButton[] buttons;
    private JToggleButton[] buttons2;
    private JLabel[] labels;
    private int locationX;
    private int locationY;
    private int counterX;
    private int counterY;

    private int counterGraph;

    private JToggleButton graphButton1;
    private JToggleButton graphButton2;
    private JToggleButton graphButton3;
    private JToggleButton graphButton4;
    JButton nextPageButton;

    private SelectionScreenFrame(int buttonCount) {
        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(2,1));

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(null);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/depositphotos_113058906-stock-illustration-statistical-graph-icon-vector-Photoroom.png-Photoroom.png")));
        Image image = imageIcon.getImage().getScaledInstance(200, 210, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));

        ImageIcon imageIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/1000_F_523841307_zEGssQFoDFXsCv1WspKE3MWPe25OmHBC-Photoroom.png-Photoroom.png")));
        Image image2 = imageIcon2.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel2 = new JLabel(new ImageIcon(image2));

        ImageIcon imageIcon3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/png-transparent-bar-chart-iconfinder-icon-bar-graph-icon-angle-building-text-Photoroom.png-Photoroom.png")));
        Image image3 = imageIcon3.getImage().getScaledInstance(359, 170, Image.SCALE_SMOOTH);
        JLabel imageLabel3 = new JLabel(new ImageIcon(image3));

        ImageIcon imageIcon4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Noun_Project_pie_chart_icon_1379121_cc.svg.png")));
        Image image4 = imageIcon4.getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel4 = new JLabel(new ImageIcon(image4));

        // Bileşenin boyutlarını ve konumunu ayarlayın
        imageLabel.setBounds(140, 50, 200, 200);
        imageLabel2.setBounds(400, 38, 200, 200);
        imageLabel3.setBounds(600, 47, 330, 200);
        imageLabel4.setBounds(845, 70, 359, 150);

        JLabel graphLabel1 = new JLabel("X-Y Graph");
        JLabel graphLabel2 = new JLabel("SPG & RC");
        JLabel graphLabel3 = new JLabel("Bar Graph");
        JLabel graphLabel4 = new JLabel("Pie Graph");

        Font font;
        font = graphLabel1.getFont();
        graphLabel1.setFont(font.deriveFont(font.getSize()+4f));

        font = graphLabel2.getFont();
        graphLabel2.setFont(font.deriveFont(font.getSize()+4f));

        font = graphLabel3.getFont();
        graphLabel3.setFont(font.deriveFont(font.getSize()+4f));

        font = graphLabel4.getFont();
        graphLabel4.setFont(font.deriveFont(font.getSize()+4f));

        graphLabel1.setBounds(202,40,100,30);
        graphLabel2.setBounds(453,40,100,30);
        graphLabel3.setBounds(717,40,100,30);
        graphLabel4.setBounds(980,40,100,30);

        graphButton1 = new JToggleButton("Add");
        actionMethod(graphButton1);
        graphButton2 = new JToggleButton("Add");
        actionMethod(graphButton2);
        graphButton3 = new JToggleButton("Add");
        actionMethod(graphButton3);
        graphButton4 = new JToggleButton("Add");
        actionMethod(graphButton4);

        graphButton1.setBounds(200,250,80,35);
        graphButton2.setBounds(450,250,80,35);
        graphButton3.setBounds(720,250,80,35);
        graphButton4.setBounds(980,250,80,35);

        buttons = new JToggleButton[buttonCount];
        buttons2 = new JToggleButton[buttonCount];
        labels = new JLabel[buttonCount];

        for (int i = 0; i < buttonCount; i++) {
            labels[i] = new JLabel("variable "+(i+1));

            buttons[i] = new JToggleButton("X-Axis");
            buttons2[i]= new JToggleButton("Y-Axis");

            buttons[i].setBounds(70+locationX,40+locationY,80,32);
            buttons2[i].setBounds(70+locationX,67+locationY,80,32);
            labels[i].setBounds(78+locationX,18+locationY,80,32);

            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < buttons.length; j++) {
                        if (e.getSource() == buttons[j]) {
                            if (buttons[j].isSelected()) {
                                buttons[j].setForeground(Color.BLUE);
                                counterX++;
                            } else {
                                buttons[j].setForeground(Color.BLACK);
                                counterX--;
                            }
                            if(buttons2[j].isSelected()){
                                buttons2[j].setSelected(false);
                                buttons2[j].setForeground(Color.BLACK);
                                counterY--;
                            }
                            updateAxisButtonsVisibility();
                            updateGraphsButtonVisibility();
                            break;
                        }
                    }
                }
            });

            buttons2[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < buttons2.length; j++) {
                        if (e.getSource() == buttons2[j]) {
                            if (buttons2[j].isSelected()) {
                                buttons2[j].setForeground(Color.BLUE);
                                counterY++;
                            } else {
                                buttons2[j].setForeground(Color.BLACK);
                                counterY--;
                            }
                            if(buttons[j].isSelected()){
                                buttons[j].setSelected(false);
                                buttons[j].setForeground(Color.BLACK);
                                counterX--;
                            }
                            updateAxisButtonsVisibility();
                            updateGraphsButtonVisibility();
                            break;
                        }
                    }
                }
            });


            locationX+=120;
            if((i+1)%10==0){
                locationY+=90;
                locationX=0;
            }

            upperPanel.add(buttons[i]);
            upperPanel.add(buttons2[i]);
            upperPanel.add(labels[i]);

            if(i==39){
                System.out.println("sınıra ulaşıldı");
                break;
            }
        }

        JLabel infoLabel = new JLabel("*\"SGP & RC\" stands for scatter plot graph and regression curve.");
        infoLabel.setBounds(425,350,500,40);
        font = infoLabel.getFont();
        infoLabel.setFont(font.deriveFont(font.getSize()+2f));

        nextPageButton = new JButton("Show The Graphs");

        nextPageButton.setBounds(532,305,190,40);

        nextPageButton.setVisible(false);

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphScreenFrame.getInstance().setVisible(true);
            }
        });

        bottomPanel.add(nextPageButton);

        bottomPanel.add(graphButton1);
        bottomPanel.add(graphButton2);
        bottomPanel.add(graphButton3);
        bottomPanel.add(graphButton4);

        bottomPanel.add(graphLabel1);
        bottomPanel.add(graphLabel2);
        bottomPanel.add(graphLabel3);
        bottomPanel.add(graphLabel4);

        bottomPanel.add(imageLabel);
        bottomPanel.add(imageLabel2);
        bottomPanel.add(imageLabel3);
        bottomPanel.add(imageLabel4);

        bottomPanel.add(infoLabel);

        mainPanel.add(upperPanel);
        mainPanel.add(bottomPanel);
        add(mainPanel);
    }

    public void actionMethod(JToggleButton jToggleButton){
        jToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == jToggleButton){
                    if (jToggleButton.isSelected()) {
                        jToggleButton.setForeground(Color.BLUE);
                        counterGraph++;
                    } else {
                        jToggleButton.setForeground(Color.BLACK);
                        counterGraph--;
                    }
                    updateGraphsButtonVisibility();
                }
            }
        });
    }


    private void updateAxisButtonsVisibility() {
        boolean graphButtonsVisible = (counterX>0 && counterX <= 3) && counterY == 1;
        graphButton1.setVisible(graphButtonsVisible);
        graphButton2.setVisible(graphButtonsVisible);
        graphButton3.setVisible(counterY == 1 && counterX>=1);
        if(!graphButtonsVisible){
            if(graphButton1.isSelected()){
                graphButton1.setSelected(false);
                graphButton1.setForeground(Color.BLACK);
                counterGraph--;
            }
            if(graphButton2.isSelected()){
                graphButton2.setSelected(false);
                graphButton2.setForeground(Color.BLACK);
                counterGraph--;
            }
        }
        if(counterY !=1 ){
            if(graphButton3.isSelected()){
                graphButton3.setSelected(false);
                graphButton3.setForeground(Color.BLACK);
                counterGraph--;
            }
        }
    }

    private void updateGraphsButtonVisibility() {
        // nextPageButton, counterX, counterY ve counterGraph'in durumuna göre görünür veya görünmez olur
        boolean isVisible = counterGraph >= 1 && counterX >= 1 && counterY >= 1;
        nextPageButton.setVisible(isVisible);

        boolean isVisible2 = counterGraph >= 1 && counterX >= 1;

        if(graphButton4.isSelected()){
            nextPageButton.setVisible(isVisible2);
        }


    }


    public static SelectionScreenFrame getInstance(int variable) {
        if (selectionScreenFrame == null) {
            synchronized (EnterenceScreenFrame.class) {
                if (selectionScreenFrame == null) {
                    selectionScreenFrame = new SelectionScreenFrame(variable);
                }
            }
        }
        return selectionScreenFrame;
    }
}
