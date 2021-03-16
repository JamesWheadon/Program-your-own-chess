package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import chessgui.PieceThreat;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
    }
    
    @Override
    public String toString() {
        String xtext = this.getX() + "";
        String ytext = this.getY() + "";
        String colour;
        if (this.isWhite()) {
            colour = "White";
        }
        else {
            colour = "Black";
        }
        return colour + " Knight (x: " + xtext + " y: " + ytext + ")";
    }
    
    @Override
    public void possibleMovesMatrix() {
        Piece pieceOnSquare;
        boolean[][] possibleMoves = new boolean[8][8];
        for (int i = -2; i < 3; i++) {
            if (i != 0) {
                int x = this.getX() + i;
                if (x >= 0 && x < 8) {
                    int dy = 3 - Math.abs(i);
                    int[] possibleY = {this.getY() + dy, this.getY() - dy};
                    for (int y : possibleY) {
                        if (y >= 0 && y < 8) {
                            pieceOnSquare = board.getPiece(x, y);
                            if (pieceOnSquare != null) {
                                if (pieceOnSquare.isWhite() != this.isWhite()) {
                                    possibleMoves[y][x] = true;
                                }
                            }
                            else {
                                possibleMoves[y][x] = true;
                            }
                        }
                    }
                }
            }
        }
        this.possible_moves = possibleMoves;
    }
    
    @Override
    public PieceThreat threatMatrix() {
        boolean[][] threats = new boolean[8][8];
        ArrayList<int[]> squaresToKing = new ArrayList<>();
        for (int i = -2; i < 3; i++) {
            if (i != 0) {
                int x = this.getX() + i;
                if (x >= 0 && x < 8) {
                    int dy = 3 - Math.abs(i);
                    int[] possibleY = {this.getY() + dy, this.getY() - dy};
                    for (int y : possibleY) {
                        if (y >= 0 && y < 8) {
                            threats[y][x] = true;
                            Piece threatenedPiece = 
                                    board.getPiece(x, y);
                            if (threatenedPiece != null) {
                                if (threatenedPiece.getClass().
                                        equals(King.class) && threatenedPiece.
                                                isWhite() != this.isWhite()) {
                                    int [] knightSquare = {this.getX(), 
                                        this.getY()};
                                    squaresToKing.add(knightSquare);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (squaresToKing.isEmpty()) {
            squaresToKing = null;
        }
        PieceThreat Threat = 
                new PieceThreat(threats, null, null, squaresToKing);
        return Threat;
    }
}
