package DAGLibrary;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    @Test
    public void testSetChildrenShouldThrowIllegalArgumentExceptionOnAttemptToInsertSpaceInOrigin() {
        Space space = new Space();
        Origin origin = new Origin(new Coord2D(9,9));

        HashSet<Point> set = new HashSet<>();
        set.add(space);
        assertThrows(IllegalArgumentException.class,()->origin.setChildren_(set));
    }

    @Test
    public void testSetChildrenWhenInsertingSpacesInEachOther() {
        Coord2D c1 = new Coord2D(8.18, 99);
        Space s1 = new Space();
        Space s2 = new Space(c1);

        HashSet<Point> children1 = new HashSet<>();
        children1.add(s2);

        assertDoesNotThrow(()->s1.setChildren_(children1));
    }


    @Test
    public void testGetBounds() throws DAGConstraintException {
        Point point1 = new Point(new Coord2D(2,4));
        Point point2 = new Point((new Coord2D(1, 1)));

        Origin origin = new Origin(new Coord2D(10,100));

        HashSet<Point> children1 = new HashSet<Point>();
        children1.add(point1);
        children1.add(point2);

        origin.setChildren_(children1);
        BoundBox boundsCounted = origin.getBounds();

        Space space = new Space(new Coord2D(0,0));
        HashSet<Point> spaceChildren = new HashSet<>();
        spaceChildren.add(origin);
        space.setChildren_(spaceChildren);

        BoundBox correctAnswer = new BoundBox(new Coord2D(11, 101),new Coord2D(12, 104));
        assertEquals(correctAnswer, space.getBounds());
    }

    @Test
    public void testGetBoundsWithMultipleOriginInsertion() throws DAGConstraintException {
        Coord2D c1 = new Coord2D(100,100);
        Coord2D c2 = new Coord2D(10,10);

        Origin o1 = new Origin(c1);
        Origin o2 = new Origin(new Coord2D(-500, -500));
        Origin o3 = new Origin(c2);

        Point p1 = new Point(new Coord2D(9,8));
        Point p2 = new Point(c2);

        Space space = new Space(c1);

        HashSet<Point> children1 = new HashSet<Point>();
        HashSet<Point> children3 = new HashSet<Point>();
        HashSet<Point> spaceChildren = new HashSet<Point>();

        children3.add(p1);
        o3.setChildren_(children3);


        children1.add(p1);
        children1.add(p2);
        children1.add(o2);
        children1.add(o3);
        o1.setChildren_(children1);

        spaceChildren.add(o1);
        spaceChildren.add(p1);
        space.setChildren_(spaceChildren);

        assertEquals(new BoundBox( new Coord2D(9, 8), new Coord2D(119, 118)), space.getBounds());
    }


}