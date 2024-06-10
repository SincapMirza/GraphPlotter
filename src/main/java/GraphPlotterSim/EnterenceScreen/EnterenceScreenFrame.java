package GraphPlotterSim.EnterenceScreen;

import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class EnterenceScreenFrame extends JFrame  {

    private String filePath;

    private static EnterenceScreenFrame enterenceScreenFrame;

    private EnterenceScreenFrame() {
        setTitle("Graph Plotter");
        setSize(1300, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // "Welcome To Graph Plotter" yazısı
        JLabel label = new JLabel("Welcome To Graph Plotter");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setBorder(new EmptyBorder(90, 0, 0, 0));
        Font font = label.getFont();
        label.setFont(font.deriveFont(font.getSize() + 35f));

        // "How to use" yazısı
        JLabel label2 = new JLabel("How to use");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBorder(new EmptyBorder(0, 0, 70, 0));
        font=label2.getFont();
        label2.setFont(font.deriveFont(font.getSize()+7f));

        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane optionPane = new JOptionPane();
                optionPane.setMessage("How to Use:\n" +
                        "Database Upload:\n" +
                        "Click the \"Upload File\" button to select an Excel file. Ensure that your Excel-based database is properly prepared.\n\n" +
                        "Creating the Database Correctly:\n" +
                        "For the program to function correctly, the first row of your database must consist of headers (Optionally, the first column can also consist of headers).\n" +
                        "Values other than headers should be numerical and should not contain letters or symbols.\n\n" +
                        "Selecting Variables:\n" +
                        "If you have completed the database operations correctly, your variables will appear.\n" +
                        "Your variables are extracted from the headers in the rows (Headers in the first column are not selected as variables and will be used as titles only in pie and column charts if only one variable is selected.).\n" +
                        "By selecting from these variables, you can create your graphs.\n\n" +
                        "Things to Consider When Selecting Variables:\n" +
                        "If you select one x-axis and one y-axis variable, you can create X-Y and Scatter Plot graphs.\n" +
                        "If you select one or more x variables, you can create Pie and Bar charts.\n\n" +
                        "Creating an X-Y Graph:\n" +
                        "When creating an X-Y graph, the variable selected in the y-axis button will be passed to the y-axis, and the variable selected in the x-axis button will be passed to the x-axis.\n\n" +
                        "Creating a Scatter Plot Graph:\n" +
                        "When creating a Scatter Plot graph, the variable selected in the y-axis button will be passed to the y-axis, and the variable selected in the x-axis button will be passed to the x-axis.\n\n" +
                        "Creating Pie and Bar Charts:\n" +
                        "If you are creating Pie and Bar charts and select an x button variable, it will fetch your headers from the first column and create your graphs by separating them row by row.\n" +
                        "If more than one x button variable is selected, the values under the columns will be summed up, and the headers from the first row will be shown as titles.\n\n" +
                        "Sending e-mail:\n" +
                        "When you select the Send E-mail checkbox at the bottom right corner of the screen where you select your variables, you will encounter an email entry screen.\n" +
                        "If you enter your email here, your graphs will be sent to you as PNG images via email.");
                optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);

                String title = "How to use";

                JDialog dialog = optionPane.createDialog(null,title);

                dialog.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label2.setForeground(Color.BLUE); // Mouse üzerine gelince yazı rengini mavi yap
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label2.setForeground(Color.BLACK); // Mouse üzerinden çıkınca yazı rengini tekrar siyah yap
            }
        });

        JPanel mainPanel = new JPanel(new GridLayout(2,1));

        JPanel innerpanel = new JPanel(new GridLayout(2,1));

        JPanel innerpanel2 = new JPanel(new GridLayout(2,3));

        innerpanel.add(label);
        innerpanel.add(label2);

        mainPanel.add(innerpanel);
        mainPanel.add(innerpanel2);

        JButton fileButton = new JButton("Upload File");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files (*.xls, *.xlsx)", "xls", "xlsx");
                fileChooser.addChoosableFileFilter(filter); // Excel dosyaları için filtre ekleme

                int result = fileChooser.showOpenDialog(EnterenceScreenFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    // Dosya türüne göre farklı mesajlar yazdırma
                    String fileType = "";
                    if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
                        fileType = "Excel file: ";
                    }else {
                        JOptionPane.showMessageDialog(EnterenceScreenFrame.this, "Unsupported file type. Please select an Excel file.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Hata mesajı gösterildikten sonra metodu sonlandırma
                    }
                    System.out.println(fileType + filePath);

                    SelectionScreenFrame frame = SelectionScreenFrame.getInstance();
                    frame.setVisible(true);

                    frame.updateButtons();
                }
            }
        });



        fileButton.setPreferredSize(new Dimension(200, 60));
        fileButton.setFont(new Font(Font.DIALOG,Font.PLAIN,16));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fileButton);

        buttonPanel.setBorder(new EmptyBorder(130, 0, 0, 0));

        innerpanel2.add(buttonPanel);

        JLabel label3 = new JLabel("Contributors");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setBorder(new EmptyBorder(130,0,0,0));
        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane optionPane = new JOptionPane();
                optionPane.setMessage("İsa Mirza Sincap\n\nMirza Şakiroğlu\n\nAli Baran Işık\n\nHamza Memiş\n\nMustafa Araz");
                optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);

                String title = "Contributors";

                JDialog dialog = optionPane.createDialog(null,title);

                dialog.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label3.setForeground(Color.BLUE); // Mouse üzerine gelince yazı rengini mavi yapma
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label3.setForeground(Color.BLACK); // Mouse üzerinden çıkınca yazı rengini tekrar siyah yapma
            }
        });

        innerpanel2.add(label3);


        getContentPane().add(mainPanel);
    }

    public static EnterenceScreenFrame getInstance() {
        if (enterenceScreenFrame == null) {
            synchronized (EnterenceScreenFrame.class) {
                if (enterenceScreenFrame == null) {
                    enterenceScreenFrame = new EnterenceScreenFrame();
                }
            }
        }
        return enterenceScreenFrame;
    }
    public String getFilePath(){
        if(filePath != null){
            return filePath;
        }else {
            return null;
        }
    }
}

