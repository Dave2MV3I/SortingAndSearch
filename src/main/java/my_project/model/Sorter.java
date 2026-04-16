package my_project.model;

import java.util.ArrayList;

public enum Sorter {
    INSERTION{
        @Override
        public ArrayList<SortingStep> sort(int[] array){
            ArrayList<SortingStep> history = new ArrayList<>();

            for (int i = 1; i < array.length; i++){
                int key = array[i];
                history.add(new Marking(i, MarkingType.PERMANENT));
                int j = i-1;
                while (j >= 0 && array[j] > key) {
                    history.add(new Marking(j, MarkingType.COMPARISON));
                    array[j+1] = array[j];
                    history.add(new Swap(j, j+1)); // Nur zum Visualisieren, in Wirklichkeit wird key gemerkt und die anderen kopiert, letztes mit key ersetzt
                    history.add(new Marking(j, MarkingType.DEMARK));
                    j--;
                }
                array[j+1] = key;
                history.add(new Marking(j+1, MarkingType.DEMARK_PERMANENT));
            }

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

        private void quicksort(int rechts, int links){
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
        history.add(new Swap(index1, index2));
    }
}
