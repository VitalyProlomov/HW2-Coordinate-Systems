package DAGLibrary;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OriginTest {

    @Test
    public void shouldThrowDAGExceptionIfCycleIsSetToChildren1ComplicatedCase1() throws DAGConstraintException {
        Origin o1 = new Origin(new Coord2D(0,0));
        Origin o2 = new Origin(new Coord2D(1,0));
        Origin o3 = new Origin(new Coord2D(8,8.9));

        Point p1 = new Point(new Coord2D(1,2));
        Point p2 = new Point(new Coord2D(0,1));
        Point p3 = new Point(new Coord2D(1,2));

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();
        HashSet<Point> children3 = new HashSet<>();

        children3.add(p1);
        children3.add(p2);
        // o1 is a child of o3.
        children1.add(o1);
        o3.setChildren_(children3);

        children2.add(o3);
        children2.add(p3);
        o2.setChildren_(children2);

        children1.add(p1);
        // o3 is a child of o2 and o2 is child of o1 => o3 is child of o1.
        children1.add(o2);

        assertThrows(DAGConstraintException.class, ()->o1.setChildren_(children1));
    }

    @Test
    public void shouldThrowDAGExceptionIfCycleIsSetToChildren1SimpleCase2() throws DAGConstraintException {
        // the easiest example of cycle.
        Origin o1 = new Origin(new Coord2D(0,0));
        Origin o2 = new Origin(new Coord2D(1,0));

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();

        children1.add(o2);
        children2.add(o1);

        o1.setChildren_(children1);

        assertThrows(DAGConstraintException.class, ()->o1.setChildren_(children2));
    }

    @Test
    public void shouldThrowNullPointerExceptionExceptionOnSettingCycleInChildren() {
        Origin o1 = new Origin(new Coord2D(0,0));

        HashSet<Point> nullSet = null;
        assertThrows(IllegalArgumentException.class,()->o1.setChildren_(nullSet));
    }

    @Test
    public void shouldReturnTrueInSearchingCyclesMethod() throws DAGConstraintException {
        Origin o1 = new Origin(new Coord2D(0,0));
        Origin o2 = new Origin(new Coord2D(1,0));

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();

        children1.add(o2);
        children2.add(o1);

        o1.setChildren_(children1);
    }

    @Test
    void getChildren_() {
        HashSet<Point> children = new HashSet<>();

        Point p1 = new Point(new Coord2D(0,0));
        Point p2 = new Point(new Coord2D(9,18));

        children.add(p1);
        children.add(p2);

        Origin o = new Origin(new Coord2D(8,1));

        assertDoesNotThrow(()->o.setChildren_(children));
        assertEquals(children, o.getChildren_());
    }

    @Test
    void setChildren_() throws DAGConstraintException {
        HashSet<Point> children = new HashSet<>();

        Point p1 = new Point(new Coord2D(0,0));
        Point p2 = new Point(new Coord2D(9,18));

        children.add(p1);
        children.add(p2);

        Origin o = new Origin(new Coord2D(8,1));

        assertDoesNotThrow(()->o.setChildren_(children));
        assertEquals(children, o.getChildren_());
    }

    @Test
    void getBounds() throws DAGConstraintException {
        Coord2D c1 = new Coord2D(8,7);

        Origin o1 = new Origin(c1);
        Point p1 = new Point(c1);

        HashSet<Point> childs = new HashSet<>();
        childs.add(p1);
        o1.setChildren_(childs);

        assertEquals(o1.getBounds(), new BoundBox(c1, c1));
    }


    @Test
    public void shouldCountBoundBoxCorrectlyInOriginExample1() throws DAGConstraintException {
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
    public void shouldCountBoundBoxCorrectlyInOriginExample2() throws DAGConstraintException {
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
    public void shouldThrowDAGExceptionIfCycleIsSetToChildren1SimpleCase1() {
        // the easiest example of cycle.
        Origin o1 = new Origin(new Coord2D(0,0));
        HashSet<Point> children = new HashSet<>();

        children.add(o1);
        assertThrows(DAGConstraintException.class, ()->o1.setChildren_(children));
    }
}