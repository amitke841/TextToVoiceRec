import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            SourceDataLine.Info dataInfo = new SourceDataLine. Info (TargetDataLine. class, audioFormat);
            if (!AudioSystem.isLineSupported (dataInfo))
            {
            System.out.println("Not supported");
            }
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(dataInfo);
            targetLine.open(audioFormat);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}






// JOptionPane. showMessageDialog (null, "Hit ok to start recording"); targetLine.start ();
// Thread audioRecorderTnread = new Thread（）
// eOverride public void run（）
// AudioInputStream recordingStream = new AudioInputStream (targetLine);
// File outputFile = new File ("record.wav");
// AudioSystem. write (recordingStream, AudioFileFormat. Type. WAVE, outputFile);
// catch (IOException ex)
// System. out.printin (ex) ;
// 61
// 59 89 23
// 66
// 67
// 68
// System. out. printin ("Stopped recording");
// };
// Cなしいれ！上ACEし上0E）
// Svgtemout printinle）：