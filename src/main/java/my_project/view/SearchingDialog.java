package my_project.view;

import javax.swing.*;
import java.awt.*;

public class SearchingDialog extends JDialog{
    private JPanel panel1;
    private JSpinner spinner1;
    private JButton confirmButton;
    private boolean confirmed = false;

    public SearchingDialog(JFrame parent) {
        super(parent, "Searching Menu", true); // 'true' macht ihn modal (blockiert alles andere)
        setContentPane(panel1);
        setPreferredSize(new Dimension(200,200));
        pack();

        // Button-Logik
        confirmButton.addActionListener(e -> {
            confirmed = true;
            dispose(); // Schließt den Dialog
        });
    }

    public int getSpinnerValue() {
        return (int) spinner1.getValue();
    }

    public boolean wasConfirmed() {
        return confirmed;
    }
}
