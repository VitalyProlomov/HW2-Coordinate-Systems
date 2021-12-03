package DAGLibrary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Coord2DTest {

    @Test
    void getX() {
        Coord2D c1 = new Coord2D(2.5,2);
        assertEquals(c1.getX(), 2.5);
    }

    @Test
    void getY() {
        Coord2D c1 = new Coord2D(2.5,2);
        assertEquals(c1.getY(), 2);
    }

    @Test
    void shift() {
        Coord2D c1 = new Coord2D(2.5,2);
        Coord2D c2 = new Coord2D(0,0);
        c1 = c1.shift(new Coord2D(-2.5,-2));
        assertEquals(c1,c2);
    }

    @Test
    void testEquals() {
        Coord2D c1 = new Coord2D(1, 2);
        Coord2D c2 = new Coord2D(0,9);
        Coord2D c3 = new Coord2D(1,2);

        assertNotEquals(c1,c2);
        assertEquals(c1, c3);
    }
}