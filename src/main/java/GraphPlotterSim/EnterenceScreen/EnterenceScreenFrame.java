package GraphPlotterSim.EnterenceScreen;

import GraphPlotterSim.SelectionScreen.SelectionScreenFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class EnterenceScreenFrame extends JFrame {

    private static EnterenceScreenFrame enterenceScreenFrame;

    private EnterenceScreenFrame() {
        setTitle("Fixed Size Frame");
        setSize(1300, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false); // Pencere boyutunu değiştirmeyi devre dışı bırakır

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
                optionPane.setMessage("How to use?\nFirst, upload your Excel file. Then, select which variables you want to plot in which chart, and your graph is ready.:)");
                optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);

                String title = "How to use";

                JDialog dialog = optionPane.createDialog(null,title);

                dialog.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label2.setForeground(Color.BLUE); // Mouse üzerine gelince yazı rengini mavi yapar
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label2.setForeground(Color.BLACK); // Mouse üzerinden çıkınca yazı rengini tekrar siyah yapar
            }
        });

        JPanel mainPanel = new JPanel(new GridLayout(2,1));

        JPanel innerpanel = new JPanel(new GridLayout(2,1));

        JPanel innerpanel2 = new JPanel(new GridLayout(2,3));

        innerpanel.add(label);
        innerpanel.add(label2);

        mainPanel.add(innerpanel);
        mainPanel.add(innerpanel2);

        // Ana paneli frame'e ekleyin


        JButton fileButton = new JButton("Upload File");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files (*.xls, *.xlsx)", "xls", "xlsx");
                fileChooser.addChoosableFileFilter(filter); // Excel dosyaları için filtre eklendi
                filter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
                fileChooser.addChoosableFileFilter(filter); // CSV dosyaları için filtre eklendi

                int result = fileChooser.showOpenDialog(EnterenceScreenFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    // Dosya türüne göre farklı mesajlar yazdırma
                    String fileType = "";
                    if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
                        fileType = "Excel file: ";
                    } else if (filePath.endsWith(".csv")) {
                        fileType = "CSV file: ";
                    }else {
                        JOptionPane.showMessageDialog(EnterenceScreenFrame.this, "Unsupported file type. Please select an Excel or CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
                       // return; // Hata mesajı gösterildikten sonra metodu sonlandır
                    }
                    System.out.println(fileType + filePath);

                    SelectionScreenFrame frame = SelectionScreenFrame.getInstance(40);
                    frame.setVisible(true);
                }
            }
        });


        fileButton.setPreferredSize(new Dimension(200, 60));
        fileButton.setFont(new Font(Font.DIALOG,Font.PLAIN,16));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fileButton);

        buttonPanel.setBorder(new EmptyBorder(130, 0, 0, 0));

       // getContentPane().add(buttonPanel, BorderLayout.SOUTH);

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
                label3.setForeground(Color.BLUE); // Mouse üzerine gelince yazı rengini mavi yapar
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label3.setForeground(Color.BLACK); // Mouse üzerinden çıkınca yazı rengini tekrar siyah yapar
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
}

