package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.*;
import my_project.view.Menu;
import my_project.view.SearchingDialog;

import javax.swing.*;
import java.awt.*;
import KAGO_framework.model.abitur.datenstrukturen.List;
import java.util.Arrays;

import static my_project.model.AlgorithmType.BINARYSEARCH;
import static my_project.model.AlgorithmType.LINEARSEARCH;

public class ProgramController {

    //Attribute
    private final int n = 10;
    private final int range = 100;

    // Referenzen
    private final ViewController viewController;
    private Visualiser visualiser;
    private JFrame frame;
    private Menu menu;
    private Sorter sorter;
    boolean algSelected = false;
    boolean algIsFinished = false;


    // Datenstrukturen
    private int[] array = new int[n];
    private int[] originalArray;

    public ProgramController(ViewController viewController){
        this.viewController = viewController;

        menu = new Menu(this);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));
        frame.setContentPane(menu.getMainPane());
        frame.pack();
        frame.setVisible(true);

        visualiser = new Visualiser();
        viewController.draw(visualiser);
        randomise();
    }

    public void startProgram() {

    }

    public void updateProgram(double dt){
    }

    public void startAutoAnimation(){
        // Algorithm selected?
            if (!algSelected) {
                System.out.println("<<<<<<<<<< PLEASE SELECT ALGORITHM FIRST >>>>>>>>>>");
                return;
            }

        // Already sorted or searched?
            if (!algIsFinished) {
                System.out.println("<<<<<<<<<< PLEASE RANDOMISE FIRST or ALGORITHM ISN'T FINISHED YET (how did you do that?!) >>>>>>>>>>");
                return;
            }

        // Has already animated or is still animaing?
            if (visualiser.isAnimating() || visualiser.hasAnimated()) visualiser.createElements(originalArray);

        visualiser.setAnimating(true);
    }

    public void setAlgorithm(AlgorithmType algType){
        if (algIsFinished){
            System.out.println("<<<<<<<<<< PLEASE RANDOMISE FIRST >>>>>>>>>>");
        } else {
            if (algType == LINEARSEARCH || algType == BINARYSEARCH) {

                if (algType == BINARYSEARCH){
                    Sorter sorter = Sorter.QUICK;
                    sorter.sort(array);
                    visualiser.createElements(array);
                }

                // Übergib 'frame' als Parent, damit der Dialog am Fenster klebt
                SearchingDialog dialog = new SearchingDialog(this.frame);
                dialog.setVisible(true); // Hält hier an, da modal true ist (siehe super(...) in SearchingDialog)

                if (dialog.wasConfirmed()) {
                    int searchedNumber = dialog.getSpinnerValue();
                    Searcher searcher = Searcher.valueOf(algType.name().toUpperCase());
                    originalArray = Arrays.copyOf(array, array.length);
                    visualiser.setHistory(searcher.search(array, searchedNumber));
                    algIsFinished = true;
                }
            }
            else {
                originalArray = Arrays.copyOf(array, array.length);
                sorter = Sorter.valueOf(algType.name().toUpperCase());
                List<SortingStep> history = sorter.sort(array);
                for (int i : array) System.out.print(i+ "; ");
                System.out.println("");
                counter();

                visualiser.setHistory(history);
                algIsFinished = true;
            }
            visualiser.setAlgorithm(algType.toString());
        }
        algSelected = true;
    }

    public void randomise(){
        for (int i = 0; i < array.length; i++)
            array[i] = (int)(3+Math.random()*(range-3));
        visualiser.createElements(array);
        algIsFinished = false;
        visualiser.setAnimating(false);
        resetCounter();
    }

    public void counter(){
        menu.setCounter(sorter.getSwaps(), sorter.getComps(), sorter.getOps());
    }
    public void resetCounter(){
        menu.setCounter(0,0, 0);
    }

    public void changeCooldownDuration(double cooldownDuration){
        visualiser.setCooldownDuration(cooldownDuration);
    }
}
