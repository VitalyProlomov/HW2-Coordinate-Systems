package DAGLibrary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getPosition() {
        Coord2D c1 = new Coord2D(2,3);
        Point p1 = new Point(c1);
        assertEquals(p1.getPosition(), c1);
    }

    @Test
    void setPosition() {
        Coord2D c1 = new Coord2D(2,3);
        Coord2D c2 = new Coord2D(9, -9);
        Point p1 = new Point(c1);
        assertDoesNotThrow(()->p1.setPosition(c2));
        assertEquals(p1.position_, c2);
    }


}