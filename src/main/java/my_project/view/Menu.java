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
    private JButton quick3Button;

    private JLabel swaps;
    private JLabel comps;
    private JButton randomiseButton;
    private JSlider slider1;
    private JButton playButton;
    private JLabel ops;
    private JLabel all;
    private JButton revertButton;

    public Menu(ProgramController pc) {
        JButton[] buttons = {quickButton, selectionButton, bubbleButton, insertionButton,
                quick2Button, selection2Button, bubble2Button, insertion2Button, quick3Button,
                linearSearchButton, binarySearchButton
        };

        for (JButton btn : buttons) {
            //btn.addActionListener(e -> System.out.println(btn.getText()));
            AlgorithmType type = AlgorithmType.valueOf(btn.getText().toUpperCase());
            btn.addActionListener(e -> {pc.setAlgorithm(type);});
        }

        randomiseButton.addActionListener(e -> {pc.randomise();});

        slider1.addChangeListener(e -> {
            pc.changeCooldownDuration(slider1.getValue());
        });

        playButton.addActionListener(e -> {pc.startAutoAnimation();});

        revertButton.addActionListener( e -> {pc.revert();});
    }

    public JPanel getMainPane() {return  mainPane;}

    public void setCounter(int s, int c, int o){
        swaps.setText("Swaps: " + s);
        comps.setText("Comparisons: " + c);
        ops.setText("Operations: " + o);
        all.setText("All: " + (s*3 + c + o));
    }

}
