package DAGLibrary;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
class BoundBoxTest {

    @Test
    void getTopRightCoord_() {
        Coord2D c1 = new Coord2D(8,9);
        Coord2D c2 = new Coord2D(2,0);
        BoundBox b1 = new BoundBox(c1, c2);

        assertEquals(b1.getTopRightCoord_(), c2);
        assertEquals(b1.getTopRightCoord_(), b1.topRightCoord_);
    }

    @Test
    void getBottomLeftCoord_() {
        Coord2D c1 = new Coord2D(8,9);
        Coord2D c2 = new Coord2D(2,0);
        BoundBox b1 = new BoundBox(c1, c2);

        assertEquals(b1.getBottomLeftCoord_(), c1);
        assertEquals(b1.getBottomLeftCoord_(), b1.bottomLeftCoord_);
    }

    @Test
    void updateBoundBox() throws DAGConstraintException {
        Point point1 = new Point(new Coord2D(2,4));
        Point point2 = new Point((new Coord2D(5.555, -12)));
        Point point3 = new Point(new Coord2D(3,0));

        Origin origin = new Origin(new Coord2D(0,8.6));
        Origin childEmptyOrigin = new Origin(new Coord2D(8,1000));

        HashSet<Point> children1 = new HashSet<Point>();
        children1.add(point1);
        children1.add(point2);
        children1.add(point3);

        children1.add(childEmptyOrigin);
        origin.setChildren_(children1);
        BoundBox boundsCounted = origin.getBounds();

        BoundBox correctAnswer = new BoundBox(new Coord2D(2, -12),new Coord2D(5.555, 4));
        assertEquals(correctAnswer, origin.getBounds());

        Coord2D c = new Coord2D(0,0);
        point1.setPosition(new Coord2D(0,0));
        assertEquals(BoundBox.updateBoundBox(point1), new BoundBox(c, c));
    }

    @Test
    void unite() {
        Point point1 = new Point(new Coord2D(2,4));
        Point point2 = new Point((new Coord2D(5.555, -12)));
        assertEquals(point1.bounds_.unite(point2.bounds_), new BoundBox(new Coord2D(2, -12), new Coord2D(5.555, 4)));

    }


    @Test
    void testEquals() {
        Coord2D coord1 = new Coord2D(2,4);
        Coord2D coord2 = new Coord2D(5.555, -12);

        BoundBox b1 = new BoundBox(coord1, coord2);
        BoundBox b12 = new BoundBox(coord1, coord2);
        assertEquals(b1, b12);
    }

    @Test
    public void testCountBoundBoxInOrigin1() throws DAGConstraintException {
        // Counting BoundBox of the Origin o1 with 3 points as its children

        Point point1 = new Point(new Coord2D(2,4));
        Point point2 = new Point((new Coord2D(5.555, -12)));
        Point point3 = new Point(new Coord2D(3,0));

        Origin origin = new Origin(new Coord2D(0,8.6));

        HashSet<Point> children1 = new HashSet<Point>();
        children1.add(point1);
        children1.add(point2);
        children1.add(point3);

        origin.setChildren_(children1);
        BoundBox boundsCounted = origin.getBounds();

        BoundBox correctAnswer = new BoundBox(new Coord2D(2, -12),new Coord2D(5.555, 4));
        assertEquals(boundsCounted, correctAnswer);
    }

    @Test
    public void testShouldCountBoundBoxInOrigin2() throws DAGConstraintException {
        // Counting bounds of the origin with some inserted origins (just 1 level of insertions).

        Coord2D c1 = new Coord2D(3, 3);
        Coord2D c2 = new Coord2D(0, 0);

        Point p1 = new Point(c1);
        Point p2 = new Point(c2);
        Point p3 = new Point(new Coord2D(4, 0));
        Point p4 = new Point(new Coord2D(7, -100));

        Origin o1 = new Origin(new Coord2D(2, 2));
        Origin o2 = new Origin(new Coord2D(6, 12));
        Origin o3 = new Origin(c2);

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();
        HashSet<Point> children3 = new HashSet<>();

        children1.add(p1);
        children1.add(o2);
        children1.add(o3);
        o1.setChildren_(children1);

        children2.add(p2);
        children2.add(p3);
        o2.setChildren_(children2);

        children3.add(p4);
        o3.setChildren_(children3);

        BoundBox correctAnswer = new BoundBox(new Coord2D(3, -100), new Coord2D(10, 12));
        assertEquals(correctAnswer, o1.getBounds());
    }

    @Test
    public void testCountBoundBoxInOrigin3() throws DAGConstraintException {
        Point point1 = new Point(new Coord2D(2,4));
        Point point2 = new Point((new Coord2D(5.555, -12)));
        Point point3 = new Point(new Coord2D(3,0));

        Origin origin = new Origin(new Coord2D(0,8.6));
        Origin childEmptyOrigin = new Origin(new Coord2D(8,1000));

        HashSet<Point> children1 = new HashSet<Point>();
        children1.add(point1);
        children1.add(point2);
        children1.add(point3);

        children1.add(childEmptyOrigin);
        origin.setChildren_(children1);
        BoundBox boundsCounted = origin.getBounds();

        BoundBox correctAnswer = new BoundBox(new Coord2D(2, -12),new Coord2D(5.555, 4));
        assertEquals(correctAnswer, origin.getBounds());
    }

    @Test
    public void testCountBoundBoxInOrigin4() throws DAGConstraintException {
        // Multiple level of origin insertion (2 levels), normal order of inserting.

        Origin o1 = new Origin(new Coord2D(1000,1000));
        Origin o2 = new Origin(new Coord2D(100, 100));
        Origin o3 = new Origin(new Coord2D(10, 10));

        Point p1 = new Point(new Coord2D(1, 1));

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();
        HashSet<Point> children3 = new HashSet<>();

        children3.add(p1);
        children2.add(o3);
        children1.add(o2);

        o3.setChildren_(children3);
        o2.setChildren_(children2);
        o1.setChildren_(children1);

        assertEquals(new BoundBox(new Coord2D(111, 111), new Coord2D(111, 111)),o1.getBounds());
    }

    @Test
    public void shouldCountBoundBoxCorrectlyInOriginWithChangedChildren() throws DAGConstraintException {
        // Multiple level of origin insertion (2 levels). But here we change the order
        // of insertion.

        Origin o1 = new Origin(new Coord2D(1000,1000));
        Origin o2 = new Origin(new Coord2D(100, 100));
        Origin o3 = new Origin(new Coord2D(10, 10));

        Point p1 = new Point(new Coord2D(1, 1));

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();
        HashSet<Point> children3 = new HashSet<>();

        children3.add(p1);
        children2.add(o3);
        children1.add(o2);

        // Firstly, we add o3 to o2`s children , and add o2 to o1`s children.
        o2.setChildren_(children2);
        o1.setChildren_(children1);
        // And only now we change o3 by adding p1 to its children.
        o3.setChildren_(children3);

        // However, such change of order should not change the answer form the test above.

        assertEquals(new BoundBox(new Coord2D(111, 111), new Coord2D(111, 111)),o1.getBounds());

    }
}