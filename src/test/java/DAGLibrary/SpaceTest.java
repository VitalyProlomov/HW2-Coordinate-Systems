package DAGLibrary;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    @Test
    public void shouldThrowIllegalArgumentExceptionOnAttemptToInsertSpaceInOrigin() {
        Space space = new Space();
        Origin origin = new Origin(new Coord2D(9,9));

        HashSet<Point> set = new HashSet<>();
        set.add(space);
        assertThrows(IllegalArgumentException.class,()->origin.setChildren_(set));
    }

    @Test
    public void shouldAllowInsertingSpacesInEachOther() {
        Coord2D c1 = new Coord2D(8.18, 99);
        Space s1 = new Space();
        Space s2 = new Space(c1);

        HashSet<Point> children1 = new HashSet<>();
        children1.add(s2);

        assertDoesNotThrow(()->s1.setChildren_(children1));
    }

    
}