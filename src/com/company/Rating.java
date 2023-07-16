package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Rating {

    private JFrame ratingFrame;
    private JPanel ratingPanel;
    private JSlider ratingSlider;
    private JTextField nameTextField;
    private JTextArea feedbackTextArea;
    private JButton submitButton;
    private JLabel rateLabel;

    public Rating() {
        // Create the frame and panel.
        ratingFrame = new JFrame();
        ratingPanel = new JPanel();

        // Set the frame properties.
        ratingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ratingFrame.setContentPane(ratingPanel);
        ratingFrame.pack();
        ratingFrame.setLocationRelativeTo(null);
        ratingFrame.setVisible(true);

        // Create the slider.
        ratingSlider = new JSlider(0, 5, 0);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setMajorTickSpacing(1);

        // Create the text fields.
        nameTextField = new JTextField();
        feedbackTextArea = new JTextArea();

        // Create the button.
        submitButton = new JButton("Submit");

        // Create the label.
        rateLabel = new JLabel("Rating: 0 Star");

        // Add the components to the panel.
        ratingPanel.add(ratingSlider);
        ratingPanel.add(nameTextField);
        ratingPanel.add(feedbackTextArea);
        ratingPanel.add(submitButton);
        ratingPanel.add(rateLabel);

        // Set up the button listener.
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameTextField.getText().equals("") || feedbackTextArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thank You for rating!!.");
                } else {
                    try {
                        String sql = "insert into rate" + "(Name,Rating,Feedback)" + "values (?,?,?)";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern", "root", "root");
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, nameTextField.getText());
                        statement.setString(2, String.valueOf(ratingSlider.getValue()));
                        statement.setString(3, feedbackTextArea.getText());
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "RATED SUCCESSFULLY");
                        nameTextField.setText("");
                        feedbackTextArea.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        // Set up the slider listener.
        ratingSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rateLabel.setText("Rating: " + String.valueOf(ratingSlider.getValue()) + " Star");
            }
        });
    }

    public static void main(String[] args) {
        new Rating();
    }
}
