package DAGLibrary;

/**
 * Exception that occurs if user`s attempt to set children leads to the creation of
 * cycles inside the graph (the origin`s children_).
 */
public class DAGConstraintException extends Exception {
    private final String message_;

    public DAGConstraintException() {
        message_ = "Cycles are not allowed in the directed acyclic graph.";
    }

    public DAGConstraintException(String message) {
        message_ = message;
    }
}
