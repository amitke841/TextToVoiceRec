package Tests;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;
import java.io.IOException;


public class RecordTest {
    
    public static void main(String[] args) {
        try {
            AudioFormat audioFormat = new AudioFormat(44100f,16,2,true,false);
            SourceDataLine.Info dataInfo = new SourceDataLine. Info (TargetDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported (dataInfo)){
            System.out.println("Not supported");}
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open(audioFormat);
            JOptionPane.showMessageDialog (null, "Hit ok to start recording");
            targetLine.start ();
            Thread audioRecorderTnread = new Thread()
            {
                @Override public void run()
                {
                    AudioInputStream recordingStream = new AudioInputStream (targetLine);
                    File outputFile = new File ("record.wav");
                    try {
                        AudioSystem.write (recordingStream, AudioFileFormat. Type. WAVE, outputFile);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    System.out.println("Stopped recording");
                }
            };

            audioRecorderTnread.start();
            JOptionPane.showMessageDialog (null, "Hit ok to stop recording");
            targetLine.stop();
            targetLine.close();

        } catch (Exception e) {
            System.out.println(e);            
        }
    }

}