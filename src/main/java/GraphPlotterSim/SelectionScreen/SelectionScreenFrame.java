package GraphPlotterSim.SelectionScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SelectionScreenFrame extends JFrame {

    private JToggleButton[] buttons;
    private JToggleButton[] buttons2;
    private JLabel[] labels;
    private int locationX;
    private int locationY;
    private int locationLabel;

    public SelectionScreenFrame(int buttonCount) {
        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(null);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);

        buttons = new JToggleButton[buttonCount];
        buttons2 = new JToggleButton[buttonCount];
        labels = new JLabel[buttonCount];

        for (int i = 0; i < buttonCount; i++) {
            labels[i] = new JLabel("variable "+(i+1));

            buttons[i] = new JToggleButton("Button X");
            buttons2[i]= new JToggleButton("Button Y");

            buttons[i].setBounds(70+locationX,40+locationY,80,32);
            buttons2[i].setBounds(70+locationX,67+locationY,80,32);
            labels[i].setBounds(78+locationX,18+locationY,80,32);

            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < buttons.length; j++) {
                        if (e.getSource() == buttons[j]) {
                            if (buttons[j].isSelected()) {
                                buttons[j].setForeground(Color.BLUE); // Seçili olduğunda metin rengini mavi yap
                            } else {
                                buttons[j].setForeground(Color.BLACK); // Seçili olmadığında metin rengini siyah yap
                            }if(buttons2[j].isSelected()){
                                buttons2[j].setSelected(false);
                                buttons2[j].setForeground(Color.BLACK);
                            }
                            System.out.println("Button " + (j + 1) + " clicked!");
                            // Burada istediğiniz işlevselliği ekleyebilirsiniz
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
                                buttons2[j].setForeground(Color.BLUE); // Seçili olduğunda metin rengini mavi yap
                            } else {
                                buttons2[j].setForeground(Color.BLACK); // Seçili olmadığında metin rengini siyah yap
                            }
                            if(buttons[j].isSelected()){
                                buttons[j].setSelected(false);
                                buttons[j].setForeground(Color.BLACK);
                            }
                            System.out.println("Button 2" + (j + 1) + " clicked!");
                            // Burada istediğiniz işlevselliği ekleyebilirsiniz
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

       //Bileşenin boyutlarını ve konumunu ayarlayın
        imageLabel.setBounds(140, 50, 200, 200);
        imageLabel2.setBounds(400, 38, 200, 200);
        imageLabel3.setBounds(600, 47, 330, 200);
        imageLabel4.setBounds(845, 70, 359, 150);

        JLabel graphLabel1 = new JLabel("X-Y Graph");
        JLabel graphLabel2 = new JLabel();
        JLabel graphLabel3 = new JLabel("Bar Graph");
        JLabel graphLabel4 = new JLabel("Pie Graph");

        graphLabel2.setText("Scatter Plot Graph and\nRegression Curve");

        graphLabel1.setBounds(212,40,100,30);
        graphLabel2.setBounds(450,20,100,30);

        bottomPanel.add(graphLabel1);
        bottomPanel.add(graphLabel2);

        bottomPanel.add(imageLabel);
        bottomPanel.add(imageLabel2);
        bottomPanel.add(imageLabel3);
        bottomPanel.add(imageLabel4);


        mainPanel.add(upperPanel);
        mainPanel.add(bottomPanel);
        add(mainPanel);
    }
}
