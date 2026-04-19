package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

import static my_project.model.MarkingType.*;

public enum Searcher {
    LINEARSEARCH{
        @Override
        public List<SortingStep> search(int[] array, int searchedNumber){
            List<SortingStep> history = new List<>();

            for(int i = 0; i < array.length; i++){
                Sorter.addMark(i, COMPARISON, history);
                if(array[i] == searchedNumber){
                    Sorter.addMark(i, DEMARK, history);
                    Sorter.addMark(i, SORTED, history);
                    return history;
                } else Sorter.addMark(i, DEMARK, history);
            }

            return history;
        }
    },

    BINARYSEARCH{
        @Override
        public List<SortingStep> search(int[] array, int searchedNumber){
            List<SortingStep> history = new List<>();
            binsearch(searchedNumber, 0, array.length-1, array, history);
            return history;
        }

        private void binsearch(int x, int links, int rechts, int[] array, List<SortingStep> history){
            if (links > rechts) return; // Abbruch, wenn Zahl nicht im Array ist

            int mitte = (links+rechts)/2;

            addMark(mitte, COMPARISON, history);

            if(array[mitte] == x){
                addUnmark(mitte, COMPARISON, history);
                addMark(mitte, SORTED, history);
                return;
            } else if(array[mitte] < x){
                addUnmark(mitte, COMPARISON, history);
                binsearch(x, mitte+1, rechts, array, history);
            } else if(array[mitte] > x) {
                addUnmark(mitte, COMPARISON, history);
                binsearch(x, links, mitte-1, array, history);
            }
        }
    };

    public abstract List<SortingStep> search(int[] array, int searchedNumber);

    protected static void addMark(int index, MarkingType type, List<SortingStep> history) {
        history.append(new Marking(index, type));
    }

    protected static void addUnmark(int index, MarkingType type, List<SortingStep> history) {
        if (type == COMPARISON) history.append(new Marking(index, DEMARK));
        if (type == PERMANENT) history.append(new Marking(index, DEMARK_PERMANENT));
    }
}
