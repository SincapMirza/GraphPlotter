package GraphPlotterSim.GraphScreen;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

class EmailSenderThread extends Thread {
    private String recipient;
    private String subject;
    private String body;
    private List<JPanel> graphPanels;

    public EmailSenderThread(String recipient, String subject, String body, List<JPanel> graphPanels) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.graphPanels = graphPanels;
    }

    @Override
    public void run() {
        sendEmail(recipient, subject, body, graphPanels);
    }

    private void sendEmail(String recipient, String subject, String body, List<JPanel> graphPanels) {

            // E-posta göndermek için gerekli olan bilgiler
            String username = ""; // Gönderici e-posta adresi
            String password = ""; // Gönderici geçiş anahtarı
            String host = "smtp.gmail.com"; // Gmail SMTP sunucusu
            int port = 587; // E-posta sunucusu port numarası

            // E-posta ayarlarını yapılandırma
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);

            // Oturum oluşturma
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                // E-posta mesajını oluşturma
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);

                // MimeMultipart oluşturma
                Multipart multipart = new MimeMultipart();

                // Metin kısmını oluşturma ve e-posta mesajına ekleme
                BodyPart textPart = new MimeBodyPart();
                textPart.setText(body);
                multipart.addBodyPart(textPart);

                // Grafikleri PNG dosyası olarak kaydedip e-posta ekine ekleme
                for (int i = 0; i < graphPanels.size(); i++) {
                    JPanel panel = graphPanels.get(i);
                    String filename = "graph_" + i + ".png";
                    File file = new File(filename);

                    // ChartPanel'i BufferedImage'e dönüştürme
                    ChartPanel chartPanel = (ChartPanel) panel.getComponent(0);
                    JFreeChart chart = chartPanel.getChart();
                    BufferedImage image = chart.createBufferedImage(panel.getWidth(), panel.getHeight());

                    // BufferedImage'i dosyaya kaydetme
                    ImageIO.write(image, "png", file);

                    // PNG dosyasını e-posta ekine ekleme
                    BodyPart imagePart = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    imagePart.setDataHandler(new DataHandler(source));
                    imagePart.setFileName(filename);
                    multipart.addBodyPart(imagePart);
                }

                // MimeMultipart'i e-posta mesajına ekleyerek gönderme
                message.setContent(multipart);

                // E-postayı gönderme
                Transport.send(message);

                // Başarılı mesajı gösterme
                JOptionPane.showMessageDialog(GraphScreenFrame.getInstance(), "The email has been sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Dosyaları temizleme
                for (int i = 0; i < graphPanels.size(); i++) {
                    File file = new File("graph_" + i + ".png");
                    file.delete();
                }
            } catch (MessagingException | IOException e) {
                // Hata mesajını gösterme
                JOptionPane.showMessageDialog(GraphScreenFrame.getInstance(), "An error occurred while sending the email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

