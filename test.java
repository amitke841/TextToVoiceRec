import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test extends JFrame {
    private JTextArea textArea;
    private JButton nextButton;
    private BufferedReader reader;
    private String currentLine;

    public test (String fileName) {
        super("Text To Voice");
        Font font = new Font("Arial", Font.PLAIN, 24);
        super.setFont(font);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#383838"));

        JLabel windowTitle = new JLabel("Text To Voice");
        windowTitle.setBounds(10, 10, 100, 20);
        windowTitle.setForeground(Color.WHITE);
        //Font fontTitle = new Font("Arial", Font.PLAIN, 72); // Change the font name and size as needed
        //windowTitle.setFont(fontTitle);

        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(70, 100, 350, 150);

        nextButton = new JButton("Next Line");
        nextButton.setBounds(70, 260, 100, 30);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentLine = reader.readLine();
                    if (currentLine != null) {
                        textArea.setText(currentLine);
                    } else {
                        textArea.setForeground(Color.RED);
                        textArea.setText("--  End of file reached.  --");
                        nextButton.setEnabled(false);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            currentLine = reader.readLine();
            if (currentLine != null) {
                textArea.setText(currentLine);
            } else {
                textArea.setText("File is empty.");
                nextButton.setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(windowTitle);
        add(textArea);
        add(nextButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            test app = new test("DATA/data.txt");
            app.setVisible(true);
        });
    }
}
