package DAGLibrary;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the universal coordinate system.
 * Can have space origins and points as its children, but can not be a child
 * of the origin.
 */
public class Space extends Origin {
    Set<Point> children_;

    public Space() {
        super(new Coord2D(0,0));
    }

    public Space(Coord2D shift) {
        super(shift);
    }

}
