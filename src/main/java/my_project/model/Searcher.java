package my_project.model;

import java.util.ArrayList;

public enum Searcher {
    LINEARSEARCH{
        @Override
        public ArrayList<SortingStep> search(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            return history;
        }
    },

    BINARYSEARCH{
        @Override
        public ArrayList<SortingStep> search(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            return history;
        }
    };

    public abstract ArrayList<SortingStep> search(int[] array);

    // TODO Bei searching ein neues GUI erschaffen, mögliche Zahlen angeben und Eigabefeld,
    //  danach search() starten und danach history an Visualiser geben
}
