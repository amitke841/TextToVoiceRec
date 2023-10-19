
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class reintech {

    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
            //AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);

            // Get a target data line
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);

            // Open the target data line
            targetLine.open(format);

            // Start capturing audio
            targetLine.start();

            // Write captured audio data to a file
            File outputFile = new File("recording.wav");
            AudioSystem.write(new AudioInputStream(targetLine), AudioFileFormat.Type.WAVE, outputFile);

            // Record audio for 5 seconds
            Thread.sleep(5000);

            // Stop capturing audio and close resources
            targetLine.stop();
            targetLine.close();
        } catch (LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    