package my_project.model;

public enum MarkingType {
    DEMARK,   // Element gets unmarked
    DEMARK_PERMANENT, // Element looses its permanent highlight
    PERMANENT,  // Element is marked permanently until not changed
    COMPARISON, // Element is marked for a single comparison
    SORTED      // Element is already sorted or in the sorted area (marked permanently)
}
