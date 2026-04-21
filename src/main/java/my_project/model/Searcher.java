package my_project.model;

import KAGO_framework.model.abitur.datenstrukturen.List;

import static my_project.model.MarkingType.*;

public enum Searcher {
    LINEARSEARCH{
        @Override
        public List<SortingStep> search(int[] array, int searchedNumber){
            List<SortingStep> history = new List<>();
            Counter.reset();

            ops(1);
            ops(2);
            for(int i = 0; i < array.length; i++){
                Sorter.addMark(i, COMPARISON, history);

                ops(2);
                if(array[i] == searchedNumber){
                    Sorter.addMark(i, DEMARK, history);
                    Sorter.addMark(i, SORTED, history);
                    ops(1);
                    Counter.addComp();
                    return history;
                } else Sorter.addMark(i, DEMARK, history);

                ops(4);
            }

            ops(1);
            return history;
        }
    },

    BINARYSEARCH{
        @Override
        public List<SortingStep> search(int[] array, int searchedNumber){
            List<SortingStep> history = new List<>();
            Counter.reset();
            ops(3);
            binsearch(searchedNumber, 0, array.length-1, array, history);
            ops(1);
            return history;
        }

        private void binsearch(int x, int links, int rechts, int[] array, List<SortingStep> history){
            ops(1);
            if (links > rechts) {
                ops(1);
                Counter.addComp();
                return;
            }

            ops(3);
            int mitte = (links+rechts)/2;

            addMark(mitte, COMPARISON, history);

            ops(2);
            if(array[mitte] == x){
                addMark(mitte, DEMARK, history);
                addMark(mitte, SORTED, history);
                ops(1);
                Counter.addComp();
            } else {
                ops(2);
                if(array[mitte] < x){
                    addMark(mitte, DEMARK, history);
                    ops(2);
                    Counter.addComp();
                    binsearch(x, mitte+1, rechts, array, history);
                } else {
                    ops(2);
                    if(array[mitte] > x) {
                        Counter.addComp();
                        addMark(mitte, DEMARK, history);
                        ops(2);
                        binsearch(x, links, mitte-1, array, history);
                    }
                }
            }
        }
    };

    public abstract List<SortingStep> search(int[] array, int searchedNumber);

    static void ops(int a){
        Counter.addOp(a);
    }

    static void addMark(int index, MarkingType type, List<SortingStep> history) {
        history.append(new Marking(index, type));
    }
}