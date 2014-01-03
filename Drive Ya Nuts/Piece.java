/* Marjorie Carlson, Jan. 2 2014
 * 
 * A helper class for the Drive Ya Nuts puzzle solver. Gives methods to find the
 * relevant values (e.g. clockwise & counterclockwise neighbors) of a given piece.
 */

import java.util.Arrays;

public class Piece {
    private int[] sides;

    // A piece is constructed by an array of 6 ints, which are the numbers in
    // clockwise order starting from the 1. Thus the first value is always 1.
    // For example, the piece with the numbers 1-6 in clockwise order would
    // be constructed with the argument [1, 2, 3, 4, 5, 6].
    public Piece(int[] inputSides) {
        sides = new int[6];
        System.arraycopy(inputSides, 0, sides, 0, inputSides.length);
    }
    
    public int findValueByPosition(int clockwiseDistanceFromOne) {
        return sides[clockwiseDistanceFromOne];
    }
    
    public int findPositionByValue(int value) {
        for (int i = 0; i < 6; i++) {
            if (sides[i] == value) {
                return i;
            }
        }
        return -1; // if the value doesn't exist
    }
    
    public int findClockwiseNeighborOfValue(int value) {
        int positionOfValue = findPositionByValue(value);
        int positionOfClockwiseNeighbor = (positionOfValue+1)%6;
        return findValueByPosition(positionOfClockwiseNeighbor);
    }
    
    public int findCounterClockwiseNeighborOfValue(int value) {
        int positionOfValue = findPositionByValue(value);
        int positionOfCounterClockwiseNeighbor = (positionOfValue+5)%6;
        return findValueByPosition(positionOfCounterClockwiseNeighbor);
    }
    
    public String toString() {
        return Arrays.toString(sides);
    }    
}
