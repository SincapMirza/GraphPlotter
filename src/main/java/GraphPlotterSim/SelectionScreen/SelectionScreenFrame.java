package GraphPlotterSim.SelectionScreen;

import GraphPlotterSim.EnterenceScreen.EnterenceScreenFrame;
import GraphPlotterSim.EnterenceScreen.ExcelReading;
import GraphPlotterSim.GraphScreen.GraphScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

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
    private JButton nextPageButton;
    private ArrayList<Double> xData;
    private ArrayList<Double> yData;
    private  JPanel upperPanel;
    private ArrayList<Integer> selectedButtons;
    private List<List<Double>> dataLists;
    private JToggleButton selectAllButtons;
    private String email;
    private JCheckBox checkBox;

    private SelectionScreenFrame() {

        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //ExcelReading.getInstance().readExcelData(EnterenceScreenFrame.getInstance().getFilePath());
       // buttonCount=ExcelReading.getInstance().getColumnCount();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EnterenceScreenFrame.getInstance().setVisible(true);
                dispose();
            }
        });

        EnterenceScreenFrame.getInstance().setVisible(false);

        JPanel mainPanel = new JPanel(new GridLayout(2,1));

        upperPanel = new JPanel();
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



        JLabel infoLabel = new JLabel("*\"SGP & RC\" stands for scatter plot graph and regression curve.");
        infoLabel.setBounds(425,350,500,40);
        font = infoLabel.getFont();
        infoLabel.setFont(font.deriveFont(font.getSize()+2f));

        nextPageButton = new JButton("Show The Graphs");

        nextPageButton.setBounds(532,305,190,40);

        // nextPageButton için ActionListener içinde
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataLists = ExcelReading.getInstance().readDataContent(EnterenceScreenFrame.getInstance().getFilePath(), SelectionScreenFrame.selectionScreenFrame.getSelectedAxisButtons());

                GraphScreenFrame graphScreenFrame = GraphScreenFrame.getInstance();
                graphScreenFrame.updateGraph();
                graphScreenFrame.setVisible(true);

                SelectionScreenFrame.getInstance().setVisible(false);
            }
        });


        JLabel emailLabel = new JLabel("Send e-mail");
        emailLabel.setBounds(1150,310,100,35);

        checkBox = new JCheckBox();
        checkBox.setBounds(1225,310,35,35);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    JDialog emailDialog = new JDialog(SelectionScreenFrame.getSelectionScreenFrame(),"Enter Email", true);
                    emailDialog.setLayout(new FlowLayout());
                    emailDialog.setSize(300, 150);
                    emailDialog.setLocationRelativeTo(SelectionScreenFrame.getSelectionScreenFrame());
                    emailDialog.setResizable(false);

                    JLabel emailPopupLabel = new JLabel("Enter your email:");
                    JTextField emailTextField = new JTextField(20);
                    JButton submitButton = new JButton("Submit");

                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            email = emailTextField.getText();
                            if (isValidEmail(email)) {
                                System.out.println("Email submitted: " + email);
                                emailDialog.dispose();
                            } else {
                                JOptionPane.showMessageDialog(emailDialog, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    emailDialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            if (checkBox.isSelected()) {
                                checkBox.setSelected(false); // CheckBox seçiliyse seçimini kaldır
                            }
                        }
                    });


                    emailDialog.add(emailPopupLabel);
                    emailDialog.add(emailTextField);
                    emailDialog.add(submitButton);

                    emailDialog.setVisible(true);
                }
            }

            private boolean isValidEmail(String email) {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                return email.matches(emailRegex);
            }

        });

        bottomPanel.add(checkBox);
        bottomPanel.add(emailLabel);

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
        // graphButton3 görünürlüğü: counterY 0 olacak ve counterX en az 1 olacak
        graphButton3.setVisible(counterY == 0 && counterX >= 1);

        // graphButton4 görünürlüğü: counterX veya counterY en az 1 olacak
        graphButton4.setVisible(counterX >= 1 || counterY >= 1);

        // graphButton1 görünürlüğü: sadece counterX 1 ve counterY 1 olacak
        graphButton1.setVisible(counterX == 1 && counterY == 1);

        // graphButton2 görünürlüğü: counterY 0 olacak ve counterX en az 1, en fazla 3 olacak
        graphButton2.setVisible(counterY == 0 && counterX >= 1 && counterX <= 3);

        // graphButton1 ve graphButton2 için butonlar seçiliyse işlem yapma
        if (!graphButton1.isVisible() && graphButton1.isSelected()) {
            graphButton1.setSelected(false);
            graphButton1.setForeground(Color.BLACK);
            counterGraph--;
        }
        if (!graphButton2.isVisible() && graphButton2.isSelected()) {
            graphButton2.setSelected(false);
            graphButton2.setForeground(Color.BLACK);
            counterGraph--;
        }

        // counterY 1 değilse ve graphButton3 seçiliyse işlem yapma
        if (counterY != 1 && graphButton3.isSelected()) {
            graphButton3.setSelected(false);
            graphButton3.setForeground(Color.BLACK);
            counterGraph--;
        }
    }


    private void updateGraphsButtonVisibility() {
        // counterGraph 1 veya 1'den büyükse nextPageButton görünür olacak
        boolean isVisible = counterGraph >= 1;
        nextPageButton.setVisible(isVisible);

        // Eğer nextPageButton görünür değilse, metin rengini siyah yap
        if (!isVisible && nextPageButton.isSelected()) {
            nextPageButton.setSelected(false);
            nextPageButton.setForeground(Color.BLACK);
        }
    }



    public static SelectionScreenFrame getInstance() {
        if (selectionScreenFrame == null) {
            synchronized (EnterenceScreenFrame.class) {
                if (selectionScreenFrame == null) {
                    selectionScreenFrame = new SelectionScreenFrame();
                }
            }
        }
        return selectionScreenFrame;
    }

    public ArrayList<Integer> getSelectedAxisButtons() {
        selectedButtons = new ArrayList<>();
        Integer firstSelectedButtonFromButtons2 = null;

        int buttonCount = ExcelReading.getInstance().getColumnCount();
        int j = 0;

        if(ExcelReading.getInstance().isFirstColumnIsString()){
            buttonCount--;
            j++;
        }

        for ( int i = 0; i < buttonCount; i++) {
            if (buttons2[i].isSelected() && buttons2[i] != null) {
                if (firstSelectedButtonFromButtons2 == null) {
                    firstSelectedButtonFromButtons2 = i;
                } else {
                    selectedButtons.add(j);
                }
            } else if (buttons[i].isSelected()) {
                selectedButtons.add(j);
            }
            j++;
        }

        if (firstSelectedButtonFromButtons2 != null) {
            selectedButtons.add(0, firstSelectedButtonFromButtons2);
        }

        return selectedButtons;
    }



    public ArrayList<Double> getxData() {
        return xData;
    }

    public ArrayList<Double> getyData() {
        return yData;
    }

    public void updateButtons() {
        ExcelReading.getInstance().readExcelData(EnterenceScreenFrame.getInstance().getFilePath());
        int buttonCount = ExcelReading.getInstance().getColumnCount();

        graphButton1.setVisible(false);
        graphButton2.setVisible(false);
        graphButton3.setVisible(false);
        graphButton4.setVisible(false);
        nextPageButton.setVisible(false);

        graphButton1.setSelected(false);
        graphButton2.setSelected(false);
        graphButton3.setSelected(false);
        graphButton4.setSelected(false);
        nextPageButton.setSelected(false);

        graphButton1.setForeground(Color.BLACK);
        graphButton2.setForeground(Color.BLACK);
        graphButton3.setForeground(Color.BLACK);
        graphButton4.setForeground(Color.BLACK);

        counterY=0;
        counterX=0;

        // Mevcut butonları temizle
        upperPanel.removeAll();
        upperPanel.revalidate();
        upperPanel.repaint();

        // Yeni butonları oluştur
        buttons = new JToggleButton[buttonCount];
        buttons2 = new JToggleButton[buttonCount];
        labels = new JLabel[buttonCount];

        locationX=0;
        locationY=0;

        if(ExcelReading.getInstance().isFirstColumnIsString()){
            buttonCount--;//buna bi dikkat et hata verebilir
        }

        for (int i = 0; i < buttonCount; i++) {
            labels[i] = new JLabel(ExcelReading.getInstance().getColumnHeaders().get(i));

            buttons[i] = new JToggleButton("X-Axis");
            buttons2[i]= new JToggleButton("Y-Axis");

            buttons[i].setBounds(70+locationX,40+locationY,80,32);
            buttons2[i].setBounds(70+locationX,68+locationY,80,32);
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

            if(i==38){
                System.out.println("sınıra ulaşıldı");
                break;
            }
        }

        selectAllButtons = new JToggleButton("Select All");
        selectAllButtons.setBounds(1150, 320, 80, 40);
        selectAllButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectAllButtons.isSelected()) {
                    selectAllButtons.setForeground(Color.BLUE);
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i] != null && !buttons[i].isSelected()) {
                            buttons[i].setSelected(true);
                            buttons[i].setForeground(Color.BLUE);
                            if (buttons2[i].isSelected()) {
                                buttons2[i].setForeground(Color.BLACK);
                                buttons2[i].setSelected(false);
                                counterY--;
                            }
                            counterX++;
                        }
                    }
                }else if (!selectAllButtons.isSelected()){
                    selectAllButtons.setForeground(Color.BLACK);
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i] != null && buttons[i].isSelected()) {
                            buttons[i].setSelected(false);
                            buttons[i].setForeground(Color.BLACK);
                            if (buttons2[i].isSelected()) {
                                buttons2[i].setForeground(Color.BLACK);
                                buttons2[i].setSelected(false);
                                counterY--;
                            }
                            counterX--;
                        }
                    }
                }
                updateAxisButtonsVisibility();
                updateGraphsButtonVisibility();
            }
        });

        upperPanel.add(selectAllButtons);
        // Paneli yeniden boyutlandır
        upperPanel.revalidate();
        upperPanel.repaint();
    }

    public List<List<Double>> getDataLists() {
        return dataLists;
    }
    public void secondUpdateButtons(){

        for(int i = 0; i < buttons.length; i++){
            if(buttons[i] != null){
            buttons[i].setSelected(false);
            buttons[i].setForeground(Color.BLACK);}
            if(buttons2[i] != null){
                buttons2[i].setSelected(false);
                buttons2[i].setForeground(Color.BLACK);}

        }

        selectAllButtons.setForeground(Color.BLACK);
        selectAllButtons.setSelected(false);

        graphButton1.setVisible(false);
        graphButton2.setVisible(false);
        graphButton3.setVisible(false);
        graphButton4.setVisible(false);
        nextPageButton.setVisible(false);

        graphButton1.setSelected(false);
        graphButton2.setSelected(false);
        graphButton3.setSelected(false);
        graphButton4.setSelected(false);
        nextPageButton.setSelected(false);

        graphButton1.setForeground(Color.BLACK);
        graphButton2.setForeground(Color.BLACK);
        graphButton3.setForeground(Color.BLACK);
        graphButton4.setForeground(Color.BLACK);

        counterY=0;
        counterX=0;
        counterGraph = 0;
    }

    public static SelectionScreenFrame getSelectionScreenFrame() {
        return selectionScreenFrame;
    }

    public JToggleButton getGraphButton1() {
        return graphButton1;
    }

    public JToggleButton getGraphButton2() {
        return graphButton2;
    }

    public JToggleButton getGraphButton3() {
        return graphButton3;
    }

    public JToggleButton getGraphButton4() {
        return graphButton4;
    }

    public String getEmail() {
        return email;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }
}