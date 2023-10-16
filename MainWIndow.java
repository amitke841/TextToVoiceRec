import javax.swing.*;
//import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWIndow extends JFrame {
    public static void main(String[] args) {

        String InputText = "Some Text";


        // Create a JFrame (window) and set its properties
        JFrame frame = new JFrame("Text To Voice"); // Set the window title
        frame.setSize(1000, 550); // Set the window size (width x height)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window when you click the close button
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setLayout(null); // Set the layout to null
        frame.getContentPane().setBackground(Color.decode("#383838")); // Set the background color to red

        JLabel WindowTitle = new JLabel("Text To Voice");
        //label.setFont(new Font("Arial", Font.BOLD, 16));
        //label.setHorizontalAlignment(JLabel.CENTER);
        WindowTitle.setBounds(10, 10, 100, 20); // Set the position and size of the label
        JPanel InputTextPanel = new JPanel();
        InputTextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        InputTextPanel.setBackground(Color.WHITE);
        InputTextPanel.setBounds(70, 100, 350, 150);
        JLabel InputTextLabel = new JLabel(InputText);
        //InputTextLabel.setBounds(10, 10, 10, 10);

        frame.add(WindowTitle);
        frame.add(InputTextPanel);
        InputTextPanel.add(InputTextLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}
