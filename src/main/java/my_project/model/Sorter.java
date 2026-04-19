package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;
import static my_project.model.MarkingType.*;

public enum Sorter {
    INSERTION{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();
            addMark(0, SORTED, history);

            for (int i = 1; i < array.length; i++){
                int key = array[i];
                addMark(i, PERMANENT, history);

                int j = i-1;
                ops(2);
                while (j >= 0) {
                    addMark(j, COMPARISON, history);

                    if (compare(array[j], key)) {
                        array[j+1] = array[j]; // Im Array Wert als j zwischengespeichert

                        addSwap(j, j+1, history); // Für Visualisierung jedoch ein Tausch
                        swaps++;

                        // Element ist durch den Swap für die Visualisierung auf j+1 gerutscht, Comparison-Markierung wieder entfernen
                        addMark(j+1, DEMARK, history);
                        j--;

                        ops(3);
                    } else {
                        // Element war nicht größer, Comparison-Markierung wieder entfernen
                        addMark(j, DEMARK, history);
                        break;
                    }
                    ops(1);
                }
                ops(1);
                array[j+1] = key;
                ops(2);

                // Key-Element schließlich an Position j+1 -> Permanent-Markierung entfernen, nun sorted
                addMark(j+1, DEMARK_PERMANENT, history);
                addMark(j+1, SORTED, history);
                ops(2);
            }
            ops(2);
            return history;
        }
    },

    SELECTION{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            for(int i = 0; i < array.length-1; i++) {
                int index = i;
                addMark(index, PERMANENT, history);
                ops(1);

                for (int j = i + 1; j < array.length; j++) {
                    addMark(j, COMPARISON, history);
                    if (compare(array[index], array[j])){
                        addMark(index, DEMARK_PERMANENT, history); // Altes Minimum entmarkieren
                        index = j;
                        addMark(index, PERMANENT, history); // Neues Minimum markieren
                        ops(1);
                    }
                    addMark(j, DEMARK, history);
                    ops(2);
                }
                ops(2);
                addMark(index, DEMARK_PERMANENT, history);
                swap(i, index, array, history);
                addMark(i, SORTED, history); // Finale Position Grün
                ops(2);
            }
            ops(2);
            addMark(array.length-1, SORTED, history); // Letztes Element ist automatisch sortiert
            return history;
        }
    },

    BUBBLE{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            int n = array.length;
            boolean sorted = false;
            ops(2);

            while(!sorted){
                sorted = true;
                for (int i = 0; i < n - 1; i++){
                    addMark(i, COMPARISON, history);
                    addMark(i+1, COMPARISON, history);
                    if (compare(array[i], array[i+1])){
                        swap(i, i+1, array, history);
                        sorted = false;
                        ops(1);
                    }
                    ops(1);
                    addMark(i, DEMARK, history);
                    addMark(i+1, DEMARK, history);
                    ops(2);
                }
                ops(2);
                n--;
                addMark(n, SORTED, history); // Element ist nun ganz rechts "aufgestiegen": sorted
                ops(2);
            }
            ops(1);
            // Rest als sortiert markieren
            for (int i = 0; i < n; i++) {
                addMark(i, SORTED, history);
            }

            return history;
        }
    },

    QUICK{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            quicksort(0, array.length - 1, array, history);
            ops(1);
            // Ganz am Ende alles Grün machen
            for (int i = 0; i < array.length; i++) {
                addMark(i, SORTED, history);
            }
            return history;
        }

        private void quicksort(int links, int rechts, int[] array, List<SortingStep> history){
            if (links < rechts) {
                int l = links;
                int r = rechts - 1;
                int pivot = rechts;


                addMark(pivot, PERMANENT, history);
                addMark(l, COMPARISON, history);
                addMark(r, COMPARISON, history);

                while (l <= r) {
                    while (l <= r && compare(array[pivot], array[l])) {
                        addMark(l, DEMARK, history);
                        l++;
                        if (l <= r) addMark(l, COMPARISON, history);
                        ops(2);
                    }

                    while (l <= r && compare(array[r], array[pivot])) {
                        addMark(r, DEMARK, history);
                        r--;
                        if (l <= r) addMark(r, COMPARISON, history);
                        ops(2);
                    }
                    if (l <= r) {
                        swap(l, r, array, history);
                        addMark(l, DEMARK, history);
                        addMark(r, DEMARK, history);
                        l++;
                        r--;
                        ops(2);
                    }
                    ops(4);
                }
                // Hängengebliebene rote Markierungen entfernen
                if (l <= rechts) addMark(l, DEMARK, history);
                if (r >= links) addMark(r, DEMARK, history);

                addMark(pivot, DEMARK_PERMANENT, history);
                swap(rechts, l, array, history);
                addMark(l, SORTED, history); // Pivot ist nun an seiner finalen Stelle "l": Sorted

                quicksort(links, l-1, array, history);
                quicksort(l+1, rechts, array, history);
                ops(6);
            } else if (links == rechts) {
                addMark(links, SORTED, history); // Einzelnes Element ist bereits am Platz
            } ops(1);
        }
    },

    INSERTION2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            // -> This approach is less efficient:
            // -> Here you look for the correct index and move later instead of starting to move immediately

            addMark(0, SORTED, history);
            for (int i = 1; i < array.length; i++) {
                int newIndex = i;
                ops(1);
                for (int j = 0; j < i; j++) {
                    addMark(j, COMPARISON, history);
                    boolean greater = compare(array[i], array[j]);
                    ops(1);
                    addMark(j, DEMARK, history);

                    if (!greater) {
                        newIndex = j;
                        ops(1);
                        break;
                    }
                    ops(1);
                    ops(2);
                }
                ops(2);

                // Move value behind i to correct index bz swapping (from left to right)
                int currentElement = array[i];
                ops(1);
                addMark(i, PERMANENT, history);

                for (int j = i - 1; j > newIndex - 1; j--) {
                    swap(j, j + 1, array, history);
                    ops(2);
                }
                ops(2);
                array[newIndex] = currentElement;
                ops(1);
                addMark(newIndex, DEMARK_PERMANENT, history);

                for(int k=0; k<=i; k++) addMark(k, SORTED, history);
                ops(2);
            }
            ops(2);
            return history;
        }
    },

    SELECTION2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            // -> Schleife nur bis length-1, weil letztes Element automatisch sortiert & sonst IndexOutOfBoundsException
            for (int i = 0; i < array.length-1; i++){
                // -> Index des min kann auch i selbst sein, also min = i, nicht i+1
                // -> Innere Schleife jedoch bei i+1 beginnen, sonst redundanter Vergleich von i mit sich selbst
                int min = i;

                addMark(min, PERMANENT, history);
                for (int j = i+1; j < array.length; j++){
                    addMark(j, COMPARISON, history);
                    if (compare(array[min], array[j])) {
                        addMark(min, DEMARK_PERMANENT, history);
                        min = j;
                        addMark(min, PERMANENT, history);
                        ops(1);
                    }
                    addMark(j, DEMARK, history);
                    ops(3);
                }
                addMark(min, DEMARK_PERMANENT, history);
                swap(i, min, array, history);
                addMark(i, SORTED, history);
                ops(5);
            }
            ops(2);
            addMark(array.length-1, SORTED, history);
            return history;
        }
    },

    BUBBLE2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();

            // -> Ansatz mit while als &auml;u&szlig;ere Schleife ist besser als mit for, weil der Array auch in weniger Schritten sortiert werden k&ouml;nnte
            for (int i = 0; i < array.length; i++){
                for (int j = 0; j < array.length-1 - i; j++){ // Optimerung: -i
                    addMark(j, COMPARISON, history);
                    addMark(j+1, COMPARISON, history);
                    if (compare(array[j], array[j+1])) {
                        swap(j, j+1, array, history);
                    }
                    addMark(j, DEMARK, history);
                    addMark(j+1, DEMARK, history);
                    ops(2);
                }
                addMark(array.length - 1 - i, SORTED, history);
                ops(4);
            }
            ops(2);
            return history;
        }
    },

    QUICK2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            resetCounter();
            quicksort(0, array.length - 1, array, history);
            ops(1);
            for(int i = 0; i < array.length; i++) addMark(i, SORTED, history);
            return history;
        }

        private void quicksort(int min, int max, int[] array, List<SortingStep> history){
            // -> Letztes Element als Pivot nehmen, dann verliert man nicht seine Position während der swaps
            if (min >= max) {
                if (min == max) addMark(min, SORTED, history);
                return;
            }

            int pivot = max;
            addMark(pivot, PERMANENT, history);

            int lastReplaceable = min;
            for (int i = min; i < max; i++){
                addMark(i, COMPARISON, history);
                boolean isLessOrEqual = !compare(array[i], array[pivot]);

                if (isLessOrEqual){
                    if (lastReplaceable != i) {
                        swap(lastReplaceable, i, array, history);
                        addMark(lastReplaceable, DEMARK, history);
                    } else {
                        addMark(i, DEMARK, history);
                    }
                    lastReplaceable++;
                    ops(2);
                } else {
                    addMark(i, DEMARK, history);
                }
                ops(4);
            }
            addMark(pivot, DEMARK_PERMANENT, history);
            swap(lastReplaceable, pivot, array, history);
            addMark(lastReplaceable, SORTED, history); // Pivot an richtiger Stelle

            quicksort(min, lastReplaceable-1, array, history);
            quicksort(lastReplaceable+1, max, array, history);
            ops(7);
        }
    },

    /*QUICK3{
        private int[] array;
        List<SortingStep> history = new List<>();

        @Override
        public List<SortingStep> sort(int[] array){
            resetCounter();
            this.array = array;
            quicksort(0, array.length - 1);
            ops(1);
            return history;
        }

        private void quicksort(int low, int high){
            if (low >= high){ ops(1); return;}
            int pivot = array[high];
            int lt = low;
            int i = low;
            int gt = high;
            while (i <= gt){
                if (compare(pivot, array[i])){
                    swap(lt, i, array, history);
                    lt++;
                    i++;
                    ops(1);
                } else if (compare(array[i], pivot)){
                    swap(i, gt, array, history);
                    gt--;
                } else {
                    i++;
                }
                ops(2);
            }
            quicksort(low, lt - 1);
            quicksort(gt + 1, high);
        }
    };*/

    QUICK3{
        private int[] array;
        List<SortingStep> history;

        @Override
        public List<SortingStep> sort(int[] array){
            this.array = array;
            history = new List<>();

            quicksort(0, array.length - 1);
            ops(1);

            for (int i = 0; i < array.length; i++) {
                addMark(i, SORTED, history);
            }
            return history;
        }

        private void quicksort(int low, int high){
            // Abbruchbedingung
            if (low >= high) {
                if (low == high) addMark(low, SORTED, history);
                ops(1);
                return;
            }

            int pivot = array[high];
            addMark(high, PERMANENT, history); // Das gewählte Pivot markieren

            int lt = low;
            int i = low;
            int gt = high;

            while (i <= gt){
                addMark(i, COMPARISON, history); // Aktuelles Element betrachten

                if (compare(pivot, array[i])){ // array[i] < pivot
                    addMark(i, DEMARK, history);

                    if (lt != i) { // Nur swappen, wenn es nicht dasselbe Element ist
                        swap(lt, i, array, history);
                    }
                    lt++;
                    i++;
                    ops(3);
                } else if (compare(array[i], pivot)){ // array[i] > pivot
                    addMark(i, DEMARK, history);

                    if (i != gt) {
                        swap(i, gt, array, history);
                    }

                    gt--;
                    ops(2);
                } else {
                    // Element ist gleich dem Pivot
                    addMark(i, DEMARK, history);
                    i++;
                    ops(1);
                }
                ops(1);
            }

            // Die Elemente zwischen lt und gt sind nun alle exakt gleich dem Pivot.
            // Sie haben ihre finale Position erreicht!
            for (int k = lt; k <= gt; k++) {
                addMark(k, DEMARK_PERMANENT, history); // Entfernt das Permanent
                addMark(k, SORTED, history);           // Setzt den Mittelteil auf Grün
            }

            quicksort(low, lt - 1);
            quicksort(gt + 1, high);
            ops(7);
        }
    };

    private static int swaps = 0;
    private static int comps = 0;
    private static int ops = 0;

    public abstract List<SortingStep> sort(int[] array);

    private static void swap(int index1, int index2, int[] array, List<SortingStep> history) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
        history.append(new Swap(index1, index2));
        swaps++;
    }

    static boolean compare(int a, int b) {
        comps++;
        return a > b;
    }

    static void ops(int a){
        ops +=a;
    }

    public void resetCounter(){
        swaps=0;
        comps=0;
        ops =0;
    }

    static void addSwap(int index1, int index2, List<SortingStep> history){
        history.append(new Swap(index1, index2));
    }

    static void addMark(int index, MarkingType type, List<SortingStep> history) {
        history.append(new Marking(index, type));
    }

    public int getSwaps() { return swaps; }
    public int getComps() { return comps; }
    public int getOps() { return ops; }
}
