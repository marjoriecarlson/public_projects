/* Marjorie Carlson, Jan. 2 2014.
 * 
 * A program to solve "Drive Ya Nuts," a silly game by Milton Bradley. Seven hexagonal pieces must be
 * placed, with one piece in the middle and the other six surrounding it. Each piece's sides are
 * numbered with the numerals 1-6, but with the numbers in varying orders. The pieces must be placed so
 * that all contiguous sides match in number, e.g., one piece's side labeled "1" adjoins its neighbor's
 * side labeled "1." The (solved) puzzle is viewable here:
 * http://www.hasbro.com/common/instruct/DriveYaNuts.PDF.
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Puzzle {
	public static Piece[] pieces = new Piece[]{
		new Piece(new int[] {1, 2, 3, 4, 5, 6}),
		new Piece(new int[] {1, 6, 5, 4, 3, 2}),
		new Piece(new int[] {1, 4, 3, 6, 5, 2}),
		new Piece(new int[] {1, 6, 2, 4, 5, 3}),
		new Piece(new int[] {1, 4, 6, 2, 3, 5}),
		new Piece(new int[] {1, 6, 5, 3, 2, 4}),
		new Piece(new int[] {1, 6, 4, 2, 5, 3})};
	
	// Returns an array of the pieces that could legally be placed next, given an arraylist
	// of currently placed pieces. Pieces are placed in the following order: the zeroth element is the
	// hub; the next piece is placed so that it lines up with the "1" of the hub pieces, and all following
	// pieces are placed in clockwise order from that piece.
	public ArrayList<Piece> findPotentialPieces(ArrayList<Piece> piecesSoFar) {
		int numberOfPieces = piecesSoFar.size();
		ArrayList<Piece> potentialPieces = new ArrayList<Piece>();
		
		switch (numberOfPieces) {
		case 0: // if no pieces are placed, all pieces are potential
			for (int i = 0; i < 7; i++) {
				potentialPieces.add(pieces[i]);
			}
			break;
			
		case 1: // if only the hub is placed, all other pieces are potential
			for (int i = 0; i < 7; i++) {
				if (!piecesSoFar.contains(pieces[i])) {
					potentialPieces.add(pieces[i]);
				}	
			}
			break;
		
		default: // Finds pieces that match both the next open spot on the hub and the open spot on
			 // the previously placed piece. If this is the seventh and final piece, the piece
			 // must also match the other open spot on the first edge piece placed.

			int hubValue = piecesSoFar.get(0).findValueByPosition(numberOfPieces - 1);
			int hubValueOfPreviousPiece = piecesSoFar.get(0).findValueByPosition(numberOfPieces - 2);
			int prevPieceValue = piecesSoFar.get(numberOfPieces - 1).findCounterClockwiseNeighborOfValue(hubValueOfPreviousPiece);
			int firstPieceValue = piecesSoFar.get(1).findClockwiseNeighborOfValue(1);
						
			for (int i = 0; i < 7; i++) {
				if (!piecesSoFar.contains(pieces[i]) &&
					pieces[i].findClockwiseNeighborOfValue(hubValue) == prevPieceValue &&
					(numberOfPieces < 6 ||
					 pieces[i].findCounterClockwiseNeighborOfValue(hubValue) == firstPieceValue)) {
					potentialPieces.add(pieces[i]);
				}
			}
		}
		return potentialPieces;
	}

	
	// Recursively solves the puzzle. Takes an arraylist of the pieces already placed. If all are placed,
	// it outputs the solution; otherwise, it iterates through each potential piece that COULD legally be
	// placed next, add it to pieces already placed, and recurse on the new arraylist.
	public void placePieces(ArrayList<Piece> piecesSoFar){
		if (piecesSoFar.size() == 7) {
			System.out.println(piecesSoFar.toString());
		}
		else {
			ArrayList<Piece> potentialPieces = findPotentialPieces(piecesSoFar);
			for (Piece p : potentialPieces) {
					ArrayList<Piece> clone = (ArrayList<Piece>) piecesSoFar.clone();
					clone.add(p);
					placePieces(clone);
			}
		}
	}
	
	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle();
		puzzle.placePieces(new ArrayList<Piece>());
		
		// Output: [[1, 6, 2, 4, 5, 3], [1, 4, 6, 2, 3, 5], [1, 6, 5, 3, 2, 4],
		// [1, 4, 3, 6, 5, 2], [1, 2, 3, 4, 5, 6], [1, 6, 4, 2, 5, 3], [1, 6, 5, 4, 3, 2]]
	}
}