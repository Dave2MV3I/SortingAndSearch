package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;
import static my_project.model.MarkingType.*;

// ANMERKUNG: Aufrufe von ops(), von addMark() und von addSwap() wurden größtenteils von Gemini in den Code eingefügt.

public enum Sorter{
    INSERTION{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();
            addMark(0, SORTED, history);

            ops(1);
            ops(2);
            for (int i = 1; i < array.length; i++){
                int key = array[i];
                ops(2);
                addMark(i, PERMANENT, history);

                int j = i-1;
                ops(2);

                ops(1);
                while (j >= 0) {
                    addMark(j, COMPARISON, history);

                    ops(1);
                    if (compare(array[j], key)) {
                        array[j+1] = array[j];
                        ops(2);

                        addSwap(j, j+1, history); // Nur für die Visualisierung, kein tatsächlicher Tausch

                        addMark(j+1, DEMARK, history);
                        j--;
                        ops(2);

                        ops(1);
                    } else {
                        addMark(j, DEMARK, history);
                        break;
                    }
                }
                array[j+1] = key;
                ops(3);

                addMark(j+1, DEMARK_PERMANENT, history);
                addMark(j+1, SORTED, history);

                ops(2);
                ops(2);
            }
            return history;
        }
    },

    SELECTION{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(1);
            ops(3);
            for(int i = 0; i < array.length-1; i++) {
                int index = i;
                ops(1);
                addMark(index, PERMANENT, history);

                ops(2);
                ops(2);
                for (int j = i + 1; j < array.length; j++) {
                    addMark(j, COMPARISON, history);

                    ops(2);
                    if (compare(array[index], array[j])){
                        addMark(index, DEMARK_PERMANENT, history);
                        index = j;
                        ops(1);
                        addMark(index, PERMANENT, history);
                    }
                    addMark(j, DEMARK, history);

                    ops(2);
                    ops(2);
                }

                addMark(index, DEMARK_PERMANENT, history);
                swap(i, index, array, history);
                addMark(i, SORTED, history);

                ops(2);
                ops(3);
            }
            addMark(array.length-1, SORTED, history);
            return history;
        }
    },

    BUBBLE{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            int n = array.length;
            ops(2);
            boolean sorted = false;
            ops(1);

            ops(1);
            while(!sorted){
                sorted = true;
                ops(1);

                ops(1);
                ops(2);
                for (int i = 0; i < n - 1; i++){
                    addMark(i, COMPARISON, history);
                    addMark(i+1, COMPARISON, history);

                    ops(3);
                    if (compare(array[i], array[i+1])){
                        swap(i, i+1, array, history);
                        sorted = false;
                        ops(1);
                    }
                    addMark(i, DEMARK, history);
                    addMark(i+1, DEMARK, history);

                    ops(2);
                    ops(2);
                }

                n--;
                ops(2);
                addMark(n, SORTED, history);

                ops(1);
            }

            ops(1);
            ops(1);
            for (int i = 0; i < n; i++) {
                addMark(i, SORTED, history);
                ops(2);
                ops(1);
            }

            return history;
        }
    },

    QUICK{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(2);
            quicksort(0, array.length - 1, array, history);

            ops(1);
            ops(2);
            for (int i = 0; i < array.length; i++) {
                addMark(i, SORTED, history);
                ops(2);
                ops(2);
            }
            return history;
        }

        private void quicksort(int links, int rechts, int[] array, List<SortingStep> history){
            ops(1);
            if (links < rechts) {
                int l = links;
                int r = rechts - 1;
                int pivot = rechts;
                ops(4);

                addMark(pivot, PERMANENT, history);
                addMark(l, COMPARISON, history);
                addMark(r, COMPARISON, history);

                ops(1);
                while (l <= r) {

                    ops(1);
                    while (l <= r) {
                        ops(2);
                        if (!compare(array[pivot], array[l])) break;
                        addMark(l, DEMARK, history);
                        l++;
                        ops(2);
                        ops(1);
                        if (l <= r) addMark(l, COMPARISON, history);
                        ops(1);
                    }

                    ops(1);
                    while (l <= r) {
                        ops(2);
                        if (!compare(array[r], array[pivot])) break;
                        addMark(r, DEMARK, history);
                        r--;
                        ops(2);
                        ops(1);
                        if (l <= r) addMark(r, COMPARISON, history);
                        ops(1);
                    }

                    ops(1);
                    if (l <= r) {
                        swap(l, r, array, history);
                        addMark(l, DEMARK, history);
                        addMark(r, DEMARK, history);
                        l++;
                        r--;
                        ops(4);
                    }
                    ops(1);
                }

                ops(1);
                if (l <= rechts) addMark(l, DEMARK, history);
                ops(1);
                if (r >= links) addMark(r, DEMARK, history);

                addMark(pivot, DEMARK_PERMANENT, history);
                swap(rechts, l, array, history);
                addMark(l, SORTED, history);

                ops(1);
                quicksort(links, l-1, array, history);
                ops(1);
                quicksort(l+1, rechts, array, history);
            } else {
                ops(1);
                if (links == rechts) {
                    addMark(links, SORTED, history);
                }
            }
        }
    },

    INSERTION2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            addMark(0, SORTED, history);

            ops(1);
            ops(2);
            for (int i = 1; i < array.length; i++) {
                int newIndex = i;
                ops(1);

                ops(1);
                ops(1);
                for (int j = 0; j < i; j++) {
                    addMark(j, COMPARISON, history);

                    ops(2);
                    boolean greater = compare(array[i], array[j]);
                    ops(1);

                    addMark(j, DEMARK, history);

                    ops(1);
                    if (!greater) {
                        newIndex = j;
                        ops(1);
                        break;
                    }

                    ops(2);
                    ops(1);
                }

                int currentElement = array[i];
                ops(2);
                addMark(i, PERMANENT, history);

                ops(2);
                ops(2);
                for (int j = i - 1; j > newIndex - 1; j--) {
                    swap(j, j + 1, array, history);

                    ops(2);
                    ops(2);
                }

                array[newIndex] = currentElement;
                ops(2);
                addMark(newIndex, DEMARK_PERMANENT, history);

                ops(1);
                ops(1);
                for(int k=0; k<=i; k++) {
                    addMark(k, SORTED, history);
                    ops(2);
                    ops(1);
                }

                ops(2);
                ops(2);
            }
            return history;
        }
    },

    SELECTION2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(1);
            ops(3);
            for (int i = 0; i < array.length-1; i++){
                int min = i;
                ops(1);
                addMark(min, PERMANENT, history);

                ops(2);
                ops(2);
                for (int j = i+1; j < array.length; j++){
                    addMark(j, COMPARISON, history);

                    ops(2);
                    if (compare(array[min], array[j])) {
                        addMark(min, DEMARK_PERMANENT, history);
                        min = j;
                        ops(1);
                        addMark(min, PERMANENT, history);
                    }
                    addMark(j, DEMARK, history);

                    ops(2);
                    ops(2);
                }
                addMark(min, DEMARK_PERMANENT, history);
                swap(i, min, array, history);
                addMark(i, SORTED, history);

                ops(2);
                ops(3);
            }
            addMark(array.length-1, SORTED, history);
            return history;
        }
    },

    BUBBLE2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(1);
            ops(2);
            for (int i = 0; i < array.length; i++){
                ops(1);
                ops(4);
                for (int j = 0; j < array.length-1 - i; j++){
                    addMark(j, COMPARISON, history);
                    addMark(j+1, COMPARISON, history);

                    ops(3);
                    if (compare(array[j], array[j+1])) {
                        swap(j, j+1, array, history);
                    }
                    addMark(j, DEMARK, history);
                    addMark(j+1, DEMARK, history);

                    ops(2);
                    ops(4);
                }
                addMark(array.length - 1 - i, SORTED, history);

                ops(2);
                ops(2);
            }
            return history;
        }
    },

    QUICK2{
        @Override
        public List<SortingStep> sort(int[] array){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(2);
            quicksort(0, array.length - 1, array, history);

            ops(1);
            ops(2);
            for(int i = 0; i < array.length; i++) {
                addMark(i, SORTED, history);
                ops(2);
                ops(2);
            }
            return history;
        }

        private void quicksort(int min, int max, int[] array, List<SortingStep> history){
            ops(1);
            if (min >= max) {
                ops(1);
                if (min == max) addMark(min, SORTED, history);
                return;
            }

            int pivot = max;
            ops(1);
            addMark(pivot, PERMANENT, history);

            int lastReplaceable = min;
            ops(1);

            ops(1);
            ops(1);
            for (int i = min; i < max; i++){
                addMark(i, COMPARISON, history);

                ops(3);
                boolean isLessOrEqual = !compare(array[i], array[pivot]);
                ops(1);

                ops(1);
                if (isLessOrEqual){
                    ops(1);
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

                ops(2);
                ops(1);
            }
            addMark(pivot, DEMARK_PERMANENT, history);
            swap(lastReplaceable, pivot, array, history);
            addMark(lastReplaceable, SORTED, history);

            ops(1);
            quicksort(min, lastReplaceable-1, array, history);
            ops(1);
            quicksort(lastReplaceable+1, max, array, history);
        }
    },

    QUICK3{
        private int[] array;
        List<SortingStep> history;

        @Override
        public List<SortingStep> sort(int[] array){
            this.array = array;
            history = new List<>();
            Counter.reset();

            ops(2);
            quicksort(0, array.length - 1);

            ops(1);
            ops(2);
            for (int i = 0; i < array.length; i++) {
                addMark(i, SORTED, history);
                ops(2);
                ops(2);
            }
            return history;
        }

        private void quicksort(int low, int high){
            ops(1);
            if (low >= high) {
                ops(1);
                if (low == high) addMark(low, SORTED, history);
                return;
            }

            int pivot = array[high];
            ops(2);
            addMark(high, PERMANENT, history);

            int lt = low;
            int i = low;
            int gt = high;
            ops(3);

            ops(1);
            while (i <= gt){
                addMark(i, COMPARISON, history);

                ops(1);
                if (compare(pivot, array[i])){
                    addMark(i, DEMARK, history);

                    ops(1);
                    if (lt != i) {
                        swap(lt, i, array, history);
                    }
                    lt++;
                    i++;
                    ops(4);
                } else {
                    ops(1);
                    if (compare(array[i], pivot)){
                        addMark(i, DEMARK, history);

                        ops(1);
                        if (i != gt) {
                            swap(i, gt, array, history);
                        }

                        gt--;
                        ops(2);
                    } else {
                        addMark(i, DEMARK, history);
                        i++;
                        ops(2);
                    }
                }
                ops(1);
            }

            ops(1);
            ops(1);
            for (int k = lt; k <= gt; k++) {
                addMark(k, DEMARK_PERMANENT, history);
                addMark(k, SORTED, history);
                ops(2);
                ops(1);
            }

            ops(1);
            quicksort(low, lt - 1);
            ops(1);
            quicksort(gt + 1, high);
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
        addSwap(index1, index2, history);
    }

    static boolean compare(int a, int b) {
        Counter.addComp();
        return a > b;
    }

    static void ops(int a){
        Counter.addOp(a);
    }

    static void addSwap(int index1, int index2, List<SortingStep> history){
        history.append(new Swap(index1, index2));
        Counter.addSwap();
    }

    static void addMark(int index, MarkingType type, List<SortingStep> history) {
        history.append(new Marking(index, type));
    }
}

