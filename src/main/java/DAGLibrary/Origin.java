package DAGLibrary;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the class of the coordinate plain, which is represented by its
 * starting point and its position referring to some other origin or space.
 */
public class Origin extends Point{
    protected Set<Point> children_;

    public Origin(Coord2D shift) {
        // Property bounds_ will be initialized incorrectly, but we
        // do not care about it, because it is only reachable in get
        // method, where bounds_ will be updated specifically -
        // using the concept of lazy initializng.
        super(shift);
        children_ = new HashSet<>();
    }

    /**
     * basic getter for children_ field.
     * @return children_
     */
    public Set<Point> getChildren_() {
        return children_;
    }

    /**
     * Setter of children. Children could be null.
     * @param children could be null.
     */
    public void setChildren_(Set<Point> children) throws DAGConstraintException {
        if (children == null) {
            throw new IllegalArgumentException("Null can not be set as a children");
        }
        if (areThereCyclesInChildren(children)) {
            throw new DAGConstraintException(
                    "The Set contains cycles, it can not be set as children of current origin.");
        }
        for (Point point: children) {
            if (point.getClass() == Space.class && this.getClass() == Origin.class) {
                throw new IllegalArgumentException("Space can not be a child of an origin");
            }
        }
        this.children_ = children;
    }


    /**
     * This method checks if there will be cycles in the Set that
     * is given if it will be set as children. To check that we only need
     * to check if current Origin is not going to be in the cycle itself (since other
     * origins in the Set that user gives us can have children that ALREADY undergone the same
     * method when their children were set. And since the only way to change Origin`s children
     * is through the setChildren(), we may be sure that the way to have cycles after setting
     * children is to have the origin we are currently working with inside the given Set<Points>.
     * * @return true if the cycle is found, false if not.
     */
    private boolean areThereCyclesInChildren(Set<Point> points) {
        for (Point point: points) {
            if (point.getClass() == Origin.class) {
                if (point == this) {
                    return true;
                }
                if ( ((Origin)point).getChildren_().size() == 0 ) {
                    if (this.areThereCyclesInChildren(((Origin) point).getChildren_())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Every time getBounds is called bounds_ is actually updated.
     * At other times it might be incorrect.
     */
    public BoundBox getBounds() {
        if (children_.size() == 0) {
            return null;
        }

        bounds_ = BoundBox.updateBoundBox(this);
        return bounds_;
    }


}
