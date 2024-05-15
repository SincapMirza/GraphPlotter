package GraphPlotterSim.SelectionScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionScreenFrame extends JFrame {

    private JToggleButton[] buttons;

    public SelectionScreenFrame(int buttonCount) {
        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 15, 30);
        JPanel flowPanel = new JPanel(flowLayout);
        flowPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel flowPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,15,30));

        buttons = new JToggleButton[buttonCount];

        for (int i = 0; i < buttonCount; i++) {
            buttons[i] = new JToggleButton("Button " + (i + 1));
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < buttons.length; j++) {
                        if (e.getSource() == buttons[j]) {
                            if (buttons[j].isSelected()) {
                                buttons[j].setForeground(Color.BLUE); // Seçili olduğunda metin rengini mavi yap
                            } else {
                                buttons[j].setForeground(Color.BLACK); // Seçili olmadığında metin rengini siyah yap
                            }
                            System.out.println("Button " + (j + 1) + " clicked!");
                            // Burada istediğiniz işlevselliği ekleyebilirsiniz
                            break;
                        }
                    }
                }
            });
            flowPanel.add(buttons[i]);
        }

        JLabel label = new JLabel("vdlmvldsmsldkm");
        flowPanel2.add(label);

        mainPanel.add(flowPanel);
        mainPanel.add(flowPanel2);
        add(mainPanel);
    }
}
