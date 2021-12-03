package DAGLibrary;

/**
 * Represents physical point that can move in the coordinate system.
 */
public class Point {
    protected Coord2D position_;
    protected BoundBox bounds_;

    public Point(Coord2D coordinates) {
        this.position_ = coordinates;
        bounds_ = new BoundBox(coordinates, coordinates);
    }

    public Coord2D getPosition() {
        return position_;
    }

    /**
     * Used for moving point on the coordinate plane.
     * Also updates the bound_ field of the Point.
     */
    public void setPosition(Coord2D coords) {
        position_ = coords;
        bounds_ = new BoundBox(coords, coords);
    }
}
