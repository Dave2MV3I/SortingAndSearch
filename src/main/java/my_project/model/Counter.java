package my_project.model;

public class Counter {
    private static int swaps = 0;
    private static int comps = 0;
    private static int ops = 0;

    public static void addOp(int n) { ops += n; }
    public static void addComp() { comps++; ops++; }
    public static void addSwap() { swaps++; ops += 5; } // Zentraler Wert für Swaps

    public static void reset() {
        swaps = 0;
        comps = 0;
        ops = 0;
    }

    public static int getSwaps() { return swaps; }
    public static int getComps() { return comps; }
    public static int getOps() { return ops; }
}
