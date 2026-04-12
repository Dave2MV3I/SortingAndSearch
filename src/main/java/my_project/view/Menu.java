package my_project.view;

import my_project.control.ProgramController;
import my_project.model.AlgorithmType;

import javax.swing.*;


public class Menu {
    private JButton quickButton;
    private JButton selectionButton;
    private JButton bubbleButton;
    private JButton insertionButton;

    private JButton quick2Button;
    private JButton selection2Button;
    private JButton bubble2Button;
    private JButton insertion2Button;

    private JPanel mainPane;

    private JButton linearSearchButton;
    private JButton binarySearchButton;

    public Menu(ProgramController pc) {
        JButton[] buttons = {quickButton, selectionButton, bubbleButton, insertionButton,
                quick2Button, selection2Button, bubble2Button, insertion2Button,
                linearSearchButton, binarySearchButton
        };

        for (JButton btn : buttons) {
            btn.addActionListener(e -> System.out.println(btn.getText()));
            AlgorithmType type = AlgorithmType.valueOf(btn.getText().toUpperCase());
            btn.addActionListener(e -> {pc.startAlgorithm(type);});
        }
    }

    public JPanel getMainPane() {return  mainPane;}

}
