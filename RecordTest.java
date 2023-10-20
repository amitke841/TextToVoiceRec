import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;


public class RecordTest {
    
    public static void main(String[] args) {
        try {
            AudioFormat audioFormat = new AudioFormat(44100f,16,1,true,false);
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