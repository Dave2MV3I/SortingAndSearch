package my_project.model;

import java.util.ArrayList;

public enum Searcher {
    LINEARSEARCH{
        @Override
        public ArrayList<SortingStep> search(int[] array, int searchedNumber){
            ArrayList<SortingStep> history = new ArrayList<>();

            /*for(int i = 0; i < array.length; i++){
                if(array[i] == searchedNumber){
                    return i;
                }
            }
            return -1;*/

            return history;
        }
    },

    BINARYSEARCH{
        @Override
        public ArrayList<SortingStep> search(int[] array, int searchedNumber){
            ArrayList<SortingStep> history = new ArrayList<>();
            binsearch(searchedNumber, 0, array.length-1, array);
            return history;
        }

        public int binsearch(int x, int links, int rechts, int[] array){
            int mitte = (links+rechts)/2;
            if(array[mitte] == x){
                return mitte;
            }else if(array[mitte] < x){
                binsearch(x,links,mitte-1, array);
            } else if(array[mitte] > x) {
                binsearch(x,mitte+1,rechts, array);
            }
            return -1;
        }
    };

    public abstract ArrayList<SortingStep> search(int[] array, int searchedNumber);

    // TODO Bei searching ein neues GUI erschaffen, mögliche Zahlen angeben und Eigabefeld,
    //  danach search() starten und danach history an Visualiser geben
}
