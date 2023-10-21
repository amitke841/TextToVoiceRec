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
    //private boolean endReached = false;
    private Clip clip;

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
        }
        catch (LineUnavailableException e) {
                System.out.println(e);            
        }

        try {
            // Load the audio file
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
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
                        currentLineFile = currentLineFile + 1;
                    } else {
                        //endReached = true;
                        textArea.setForeground(Color.RED);
                        nextButton.setEnabled(false);
                        RecButton.setEnabled(false);
                        StopButton.setEnabled(false);
                        PlayButton.setEnabled(false);
                        textArea.setText("--  End of file reached.  --");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // record button
        RecButton = new JButton("Start Rec");
        RecButton.setBounds(BP + 150, 260, 80, 30);
        RecButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                
                if (clip != null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    clip.close();
                }
                
                try {
                    targetLine.open(audioFormat);
                } catch (LineUnavailableException ex) {
                    System.out.println(ex);
                }
                targetLine.start ();
                
                Thread audioRecorderTnread = new Thread()
                {
                    @Override public void run()
                    {
                        StopButton.setEnabled(true);
                        nextButton.setEnabled(false);
                        RecButton.setEnabled(false);
                        PlayButton.setEnabled(false);
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
        StopButton.setBounds(BP + 230, 260, 80, 30);
        StopButton.setEnabled(false);
        StopButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                targetLine.stop();
                targetLine.close();
                nextButton.setEnabled(true);
                RecButton.setEnabled(true);
                PlayButton.setEnabled(true);
                StopButton.setEnabled(false);
            }
        });


        PlayButton = new JButton("Play");
        PlayButton.setBounds(BP + 310, 260, 80, 30);
        PlayButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                PauseButton.setEnabled(true);
                String audioFilePath ="DATA/recordings/data" + currentLineFile + ".wav";
                
                if (clip != null) {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    clip.close();
                    try {
                        clip.open(AudioSystem.getAudioInputStream(new File(audioFilePath)));
                    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ee) {
                        ee.printStackTrace();
                    }
                    clip.start();
                }                
            }
        });


        // PauseButton = new JButton("Pause");
        // PauseButton.setBounds(BP + 390, 260, 80, 30);
        // PauseButton.setEnabled(false);
        // PauseButton.addActionListener(new ActionListener() {
        //     @Override public void actionPerformed(ActionEvent e) {
        //         try {
        //             clip.stop();
        //             clip.close();
        //         } catch (Exception ex) {
        //             System.out.println(ex);
        //         }
        //     }
        // });


        add(windowTitle);
        add(textArea);
        add(nextButton);
        add(RecButton);
        add(StopButton);
        add(PlayButton);
       //add(PauseButton);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainTest app = new MainTest("DATA/data.txt");
            app.setVisible(true);
        });
    }
}