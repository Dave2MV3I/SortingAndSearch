package my_project.view;

import my_project.control.ProgramController;

import javax.swing.*;

public class Search {
    private JPanel panel1;
    private JSpinner spinner1;

    public JPanel getPanel1(){return panel1;}

    public Search(ProgramController pc){
        spinner1.addChangeListener(e -> {
            pc.searchFor((int)(spinner1.getValue()));
        });
    }
}
