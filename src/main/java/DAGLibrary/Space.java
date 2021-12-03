package DAGLibrary;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the universal coordinate system.
 * Can have space origins and points as its children, but can not be a child
 * of the origin.
 */
public class Space extends Origin {

    private Origin spaceOrigin;

    public Space() {
        super(new Coord2D(0,0));
        spaceOrigin = new Origin(new Coord2D(0,0));
    }

    public Space(Coord2D shift) {
        super(shift);
        spaceOrigin = new Origin(shift);
    }

    /**
     * Every time getBounds is called bounds_ is actually updated.
     * At other times it might be incorrect.
     */
    public BoundBox getBounds() {
        return spaceOrigin.getBounds();
    }

    /**
     * overrides setting of children - if a space is added into space, than the addendum is
     * chaned to its originSpcae ield ( so that exception is not get).
     * @param children could be null.
     * @throws DAGConstraintException
     */
    @Override
    public void setChildren_(Set<Point> children) throws DAGConstraintException {
        HashSet<Point> sameChildren = new HashSet<>();
        for (Point p: children) {
            if (p.getClass() == Space.class) {
                sameChildren.add(((Space) p).spaceOrigin);
            } else {
                sameChildren.add(p);
            }
        }
        spaceOrigin.setChildren_(sameChildren);
    }

    /**
     * gets children
     * @return children_ of current space.
     */
    @Override
    public Set<Point> getChildren_() {
        return spaceOrigin.getChildren_();
    }
}
