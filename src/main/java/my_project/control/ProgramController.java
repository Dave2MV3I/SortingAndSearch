package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.*;
import my_project.view.Menu;

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

    // Datenstrukturen
    private int[] array = new int[n];

    public ProgramController(ViewController viewController){
        this.viewController = viewController;

        menu = new Menu(this);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,300));
        frame.setContentPane(menu.getMainPane());
        frame.pack();
        frame.setVisible(true);

        visualiser = new Visualiser();
        viewController.draw(visualiser);
        shuffleIntegers();
        visualiser.setAnimElementsWhenShuffling(getShuffeledElements());
    }

    public void startProgram() {

    }

    public void updateProgram(double dt){

    }

    public void startAlgorithm(AlgorithmType algType){
        if (algType == LINEARSEARCH || algType == BINARYSEARCH) {
            Searcher searcher = Searcher.valueOf(algType.name().toUpperCase());
            searcher.search(array);
        }
        else {
            int[] originalArray = Arrays.copyOf(array, array.length);
            Sorter sorter = Sorter.valueOf(algType.name().toUpperCase());
            ArrayList<SortingStep> history = sorter.sort(array);

            Element[] animElements = new Element[array.length];
            for (int i = 0; i < array.length; i++){
                animElements[i] = new Element(originalArray[i]);
            }
            visualiser.prepareAnimation(history, animElements);
            visualiser.setAlgorithm(algType.toString());

            for (SortingStep step : history){
                if (step instanceof Swap){
                    System.out.println("Swap");
                } else if (step instanceof Marking) System.out.println("Marking");
            }
        }


    }

    private void shuffleIntegers(){
        for (int i = 0; i < array.length; i++)
            array[i] = (int)(3+Math.random()*(range-3));
    }

    public Element[] getShuffeledElements(){
        Element[] shuffeledElements = new Element[array.length];
        for (int i = 0; i < array.length; i++){
            shuffeledElements[i] = new Element(array[i]);
        }
        return shuffeledElements;
    }
}
