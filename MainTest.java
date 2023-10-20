import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.*;

public class MainTest extends JFrame {
    private JTextArea textArea;
    private JButton nextButton;
    private JButton RecButton;
    private JButton StopButton;
    private JButton PlayButton;
    private JButton PauseButton;
    private BufferedReader reader;
    private String currentLine;
    private AudioFormat audioFormat;
    private SourceDataLine.Info dataInfo;
    private TargetDataLine targetLine; 
    private int currentLineFile = 0;


    public MainTest (String fileName) {
        // open text file with prompts
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // open audio
        try {
            audioFormat = new AudioFormat(44100f,16,1,true,false);
            dataInfo = new SourceDataLine. Info (TargetDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported (dataInfo)){
                System.out.println("Not supported");
            }
        } catch (Exception eb) {
                System.out.println(eb);            
        }

        try {
            targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open(audioFormat);
        }
        catch (LineUnavailableException e) {
                System.out.println(e);            
        }


        // general window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.decode("#383838"));

        // main window
        JLabel windowTitle = new JLabel("Text To Voice");
        windowTitle.setBounds(10, 10, 100, 20);
        windowTitle.setForeground(Color.WHITE);
        //Font fontTitle = new Font("Arial", Font.PLAIN, 72); // Change the font name and size as needed
        //windowTitle.setFont(fontTitle);

        // text area
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(70, 100, 350, 150);
        
        // next button
        int BP = 50;
        nextButton = new JButton("Next Line & Save");
        nextButton.setBounds(BP, 260, 150, 30);
        nextButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
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

  

        // try {
        //     currentLine = reader.readLine();
        //     if (currentLine != null) {
        //         textArea.setText(currentLine);
        //     } else {
        //         textArea.setText("File is empty.");
        //         nextButton.setEnabled(false);
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // record button
        RecButton = new JButton("Start Rec");
        RecButton.setBounds(BP + 160, 260, 80, 30);
        RecButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                currentLineFile = currentLineFile + 1;
                targetLine.start ();
                Thread audioRecorderTnread = new Thread()
                {
                    @Override public void run()
                    {
                        AudioInputStream recordingStream = new AudioInputStream (targetLine);
                        File outputFile = new File ("DATA/recordings/data" + currentLineFile + ".wav");
                        try {
                            AudioSystem.write (recordingStream, AudioFileFormat. Type.WAVE, outputFile);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        System.out.println("Stopped recording");
                    }
                };
                audioRecorderTnread.start();
            }
        });

        // stop button
        StopButton = new JButton("Stop Rec");
        StopButton.setBounds(BP + 250, 260, 80, 30);
        StopButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                targetLine.stop();
                targetLine.close();
            }
        });


        PlayButton = new JButton("Play");
        //PlayButton.setBounds(BP + 80, 260, 80, 30);

        PauseButton = new JButton("Pause");
        //PlayButton.setBounds(BP + 80, 260, 80, 30);

        add(windowTitle);
        add(textArea);
        add(nextButton);
        add(RecButton);
        add(StopButton);
        add(PlayButton);
        add(PauseButton);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainTest app = new MainTest("DATA/data.txt");
            app.setVisible(true);
        });
    }
}
