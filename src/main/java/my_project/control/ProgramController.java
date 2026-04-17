package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.*;
import my_project.view.Menu;
import my_project.view.Search;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    private int searchedNumber = -1;

    // Datenstrukturen
    private int[] array = new int[n];

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
        randomIntegers();
        visualiser.createElements(array);
        // visualiser.handleAnimation(true,true);
    }

    public void startProgram() {

    }

    public void updateProgram(double dt){
    }

    public void startAlgorithm(AlgorithmType algType){
        if (algType == LINEARSEARCH || algType == BINARYSEARCH) {

            Search searchMenu = new Search(this);
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setPreferredSize(new Dimension(200,200));
            frame.setContentPane(searchMenu.getPanel1());
            frame.pack();
            frame.setVisible(true);

            Searcher searcher = Searcher.valueOf(algType.name().toUpperCase());
            searcher.search(array, searchedNumber);

            // Wenn falsche Zahl, dann letzte nehmen
        }
        else {
            int[] originalArray = Arrays.copyOf(array, array.length);
            sorter = Sorter.valueOf(algType.name().toUpperCase());
            ArrayList<SortingStep> history = sorter.sort(array);
            for (int i : array) System.out.print(i+ " ,");
            counter();

            visualiser.setHistory(history);
            visualiser.setAlgorithm(algType.toString());
            // TEST visualiser.createElements(array);

            for (SortingStep step : history){
                if (step instanceof Swap){
                    System.out.println("Swap");
                } else if (step instanceof Marking) System.out.println("Marking");
            }
        }


    }

    public void randomise(){
        randomIntegers();
        visualiser.createElements(array);
    }

    private void randomIntegers(){
        for (int i = 0; i < array.length; i++)
            array[i] = (int)(3+Math.random()*(range-3));
    }

    public void counter(){
        menu.setCounter(sorter.getSwaps(), sorter.getComps());
    }

    public void autoAnim(boolean auto){
        visualiser.handleAnimation(true,auto);
    }

    public void searchFor(int num){searchedNumber = num;}

    public void changeAutoSpeed(int speed){
        visualiser.setTimerDuration(speed);
    }
}
