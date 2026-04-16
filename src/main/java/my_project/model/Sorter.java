/*
package my_project.model;

import java.util.ArrayList;

import static my_project.model.MarkingType.*;

public enum Sorter {
    INSERTION{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 1; i < array.length; i++){
                int key = array[i];
                //history.add(new Marking(i, MarkingType.PERMANENT));
                addMark(i, PERMANENT, history);
                int j = i-1;
                while (j >= 0 && array[j] > key) {
                    //history.add(new Marking(j, MarkingType.COMPARISON));
                    addMark(j, COMPARISON, history);
                    array[j+1] = array[j];
                    //history.add(new Swap(j, j+1)); // Nur zum Visualisieren, in Wirklichkeit wird key gemerkt und die anderen kopiert, letztes mit key ersetzt
                    //history.add(new Marking(j, MarkingType.DEMARK));
                    addSwap(j, j+1, history);
                    addMark(j, DEMARK, history);
                    j--;
                }
                array[j+1] = key;
                //history.add(new Marking(j+1, MarkingType.DEMARK_PERMANENT));
                addMark(j+1, DEMARK_PERMANENT, history);
            }
            for (int i : array) System.out.println(i);
            return history;
        }
    },

    SELECTION{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            int index;
            for(int i = 0; i < array.length-1; i++) {
                index = i;
                history.add(new Marking(index, MarkingType.COMPARISON));
                for (int j = index + 1; j < array.length; j++) {
                    history.add(new Marking(j, MarkingType.COMPARISON));
                    if (array[index] > array[j]){
                        history.add(new Marking(index, MarkingType.DEMARK));
                        history.add(new Marking(j, MarkingType.DEMARK));
                        history.add(new Marking(j, MarkingType.COMPARISON));
                        index = j;
                    }
                    else {
                        history.add(new Marking(j, MarkingType.DEMARK));
                    }
                }
                history.add(new Marking(index, MarkingType.DEMARK));
                swap(i, index, array, history);
                history.add(new Marking(i, MarkingType.SORTED));
            }
            history.add(new Marking(array.length-1, MarkingType.SORTED));

            return history;
        }
    },

    BUBBLE{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            boolean sorted = false;
            while(!sorted){
                sorted = true;
                for (int i = 0; i < array.length - 1; i++){
                    if (array[i] > array[i+1]){
                        swap(i, i+1, array, history);
                        sorted = false;
                    }
                }
            }

            return history;
        }
    },

    QUICK{
        private int[] array;
        final ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int links, int rechts){
            if (links < rechts) {
                int l = links;
                int r = rechts - 1;
                while (l <= r) {
                    while (l <= r && array[l] < array[rechts]) {
                        l++;
                    }
                    while (l <= r && array[r] > array[rechts]) {
                        r--;
                    }
                    if (l <= r) {
                        swap(l, r, array, history);
                        l++;
                        r--;
                    }
                }
                swap(rechts, l, array, history);
                //array[l] ist sortiert
                quicksort(links, l-1);
                quicksort(l+1, rechts);
            }
        }
    },

    INSERTION2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            // -> This approach is less efficient:
            // -> Here you look for the correct index and move later instead of starting to move immediately
            for (int i = 1; i < array.length; i++) {
                // Find correct index for value behind i (look at all left to i)
                int newIndex = i;
                for (int j = 0; j < i; j++) {
                    if (array[i] > array[j]) continue;
                    else newIndex = j;
                    break;
                }

                // Move value behind i to correct index bz swapping (from left to right)
                int currentElement = array[i];
                for (int j = i - 1; j > newIndex - 1; j--) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                array[newIndex] = currentElement;
            }

            return history;
        }
    },

    SELECTION2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            // -> Äußere Schleife bis length-1, weil letztes Element automatisch sortiert & sonst IndexOutOfBoundsException
            for (int i = 0; i < array.length-1; i++){
                // -> Index des min kann auch i selbst sein, also min = i, nicht i+1
                // -> Innere Schleife jedoch bei i+1 beginnen, sonst redundanter Vergleich von i mit sich selbst
                int min = i;
                for (int j = i+1; j < array.length; j++){
                    if (array[j] < array[min]) min = j;
                }
                swap(i, min, array, history);
            }

            return history;
        }
    },

    BUBBLE2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            // -> Ansatz mit while als äußere Schleife ist besser als mit for, weil der Array auch in weniger Schritten sortiert werden könnte
            for (int i = 0; i < array.length; i++){
                for (int j = 0; j < array.length-1; j++){
                    if (array[j] > array[j+1]) swap(j, j+1, array, history);
                }
            }

            return history;
        }
    },

    QUICK2{
        private int[] array;
        ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int min, int max){
            // -> Letztes Element als Pivot nehmen, dann verliert man nicht seine Position während der swaps
            if (min >= max) return;

            // Partitioning
            int pivot = max;
            int lastReplaceable = min;
            for (int i = min; i < max; i++){
                if (array[i] <= array[pivot]){
                    if (lastReplaceable != i) swap(lastReplaceable, i, array, history);
                    lastReplaceable++;
                }
            }
            swap(lastReplaceable,pivot, array, history);

            // Recursion
            quicksort(min, lastReplaceable-1);
            quicksort(lastReplaceable+1, max);
        }
    },

    QUICK3{
        private int[] array;
        ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            // quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int min, int max){

        }
    };

    public abstract ArrayList<SortingStep> sort(int[] array);

    private static void swap(int index1, int index2, int[] array, ArrayList<SortingStep> history) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void addSwap(int index1, int index2, ArrayList<SortingStep> history){
        history.add(new Swap(index1, index2));
    }

    private static void addMark(int index, MarkingType type, ArrayList<SortingStep> history) {
        history.add(new Marking(index, type));
    }

    private static void addUnmark(int index, MarkingType type, ArrayList<SortingStep> history) {
        // Nutzt den entsprechenden Demark-Typ
        if (type == MarkingType.COMPARISON) history.add(new Marking(index, MarkingType.DEMARK));
        if (type == PERMANENT) history.add(new Marking(index, MarkingType.DEMARK_PERMANENT));
    }
}
*/

package my_project.model;

import java.util.ArrayList;

import static my_project.model.MarkingType.*;

public enum Sorter {
    INSERTION{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 1; i < array.length; i++){
                int key = array[i];
                addMark(i, PERMANENT, history);
                int j = i-1;
                while (j >= 0 && array[j] > key) {
                    addMark(j, COMPARISON, history);
                    array[j+1] = array[j];
                    addSwap(j, j+1, history);
                    addUnmark(j, COMPARISON, history);
                    j--;
                }
                array[j+1] = key;
                addUnmark(j+1, PERMANENT, history);
            }
            for (int i : array) System.out.println(i);
            return history;
        }
    },

    SELECTION{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            int index;
            for(int i = 0; i < array.length-1; i++) {
                index = i;
                addMark(index, COMPARISON, history);
                for (int j = index + 1; j < array.length; j++) {
                    addMark(j, COMPARISON, history);
                    if (array[index] > array[j]){
                        addUnmark(index, COMPARISON, history);
                        addUnmark(j, COMPARISON, history);
                        addMark(j, COMPARISON, history);
                        index = j;
                    }
                    else {
                        addUnmark(j, COMPARISON, history);
                    }
                }
                addUnmark(index, COMPARISON, history);
                swap(i, index, array, history);
                addSwap(i, index, history);
                addMark(i, SORTED, history);
            }
            addMark(array.length-1, SORTED, history);

            return history;
        }
    },

    BUBBLE{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            boolean sorted = false;
            while(!sorted){
                sorted = true;
                for (int i = 0; i < array.length - 1; i++){
                    addMark(i, COMPARISON, history);
                    addMark(i+1, COMPARISON, history);

                    if (array[i] > array[i+1]){
                        swap(i, i+1, array, history);
                        addSwap(i, i+1, history);
                        sorted = false;
                    }

                    addUnmark(i, COMPARISON, history);
                    addUnmark(i+1, COMPARISON, history);
                }
            }

            return history;
        }
    },

    QUICK{
        private int[] array;
        final ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int links, int rechts){
            if (links < rechts) {
                int l = links;
                int r = rechts - 1;

                addMark(rechts, PERMANENT, history); // Pivot markieren

                while (l <= r) {
                    while (l <= r && array[l] < array[rechts]) {
                        addMark(l, COMPARISON, history);
                        addUnmark(l, COMPARISON, history);
                        l++;
                    }
                    while (l <= r && array[r] > array[rechts]) {
                        addMark(r, COMPARISON, history);
                        addUnmark(r, COMPARISON, history);
                        r--;
                    }
                    if (l <= r) {
                        swap(l, r, array, history);
                        addSwap(l, r, history);
                        l++;
                        r--;
                    }
                }
                swap(rechts, l, array, history);
                addSwap(rechts, l, history);
                addUnmark(rechts, PERMANENT, history); // Pivot demarkieren

                //array[l] ist sortiert
                quicksort(links, l-1);
                quicksort(l+1, rechts);
            }
        }
    },

    INSERTION2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 1; i < array.length; i++) {
                addMark(i, PERMANENT, history);

                int newIndex = i;
                for (int j = 0; j < i; j++) {
                    addMark(j, COMPARISON, history);
                    if (array[i] > array[j]) {
                        addUnmark(j, COMPARISON, history);
                        continue;
                    } else {
                        newIndex = j;
                        addUnmark(j, COMPARISON, history);
                        break;
                    }
                }

                int currentElement = array[i];
                for (int j = i - 1; j > newIndex - 1; j--) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    addSwap(j, j+1, history); // Visueller Swap für die Verschiebung
                }
                array[newIndex] = currentElement;

                addUnmark(i, PERMANENT, history); // Das Start-Element de-markieren
            }

            return history;
        }
    },

    SELECTION2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 0; i < array.length-1; i++){
                int min = i;
                addMark(min, PERMANENT, history);

                for (int j = i+1; j < array.length; j++){
                    addMark(j, COMPARISON, history);
                    if (array[j] < array[min]) {
                        addUnmark(min, PERMANENT, history);
                        min = j;
                        addMark(min, PERMANENT, history);
                    }
                    addUnmark(j, COMPARISON, history);
                }

                swap(i, min, array, history);
                addSwap(i, min, history);
                addUnmark(min, PERMANENT, history);
            }

            return history;
        }
    },

    BUBBLE2{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 0; i < array.length; i++){
                for (int j = 0; j < array.length-1; j++){
                    addMark(j, COMPARISON, history);
                    addMark(j+1, COMPARISON, history);

                    if (array[j] > array[j+1]) {
                        swap(j, j+1, array, history);
                        addSwap(j, j+1, history);
                    }

                    addUnmark(j, COMPARISON, history);
                    addUnmark(j+1, COMPARISON, history);
                }
            }

            return history;
        }
    },

    QUICK2{
        private int[] array;
        ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int min, int max){
            if (min >= max) return;

            // Partitioning
            int pivot = max;
            addMark(pivot, PERMANENT, history);

            int lastReplaceable = min;
            for (int i = min; i < max; i++){
                addMark(i, COMPARISON, history);
                if (array[i] <= array[pivot]){
                    if (lastReplaceable != i) {
                        swap(lastReplaceable, i, array, history);
                        addSwap(lastReplaceable, i, history);
                    }
                    lastReplaceable++;
                }
                addUnmark(i, COMPARISON, history);
            }
            swap(lastReplaceable, pivot, array, history);
            addSwap(lastReplaceable, pivot, history);
            addUnmark(pivot, PERMANENT, history);

            // Recursion
            quicksort(min, lastReplaceable-1);
            quicksort(lastReplaceable+1, max);
        }
    },

    QUICK3{
        private int[] array;
        ArrayList<SortingStep> history = new ArrayList<>();

        @Override
        public ArrayList<SortingStep> sort(int[] array){
            this.array = array;
            // quicksort(0, array.length - 1);
            return history;
        }

        private void quicksort(int min, int max){

        }
    };

    public abstract ArrayList<SortingStep> sort(int[] array);

    private static void swap(int index1, int index2, int[] array, ArrayList<SortingStep> history) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void addSwap(int index1, int index2, ArrayList<SortingStep> history){
        history.add(new Swap(index1, index2));
    }

    private static void addMark(int index, MarkingType type, ArrayList<SortingStep> history) {
        history.add(new Marking(index, type));
    }

    private static void addUnmark(int index, MarkingType type, ArrayList<SortingStep> history) {
        if (type == MarkingType.COMPARISON) history.add(new Marking(index, MarkingType.DEMARK));
        if (type == PERMANENT) history.add(new Marking(index, MarkingType.DEMARK_PERMANENT));
    }
}
