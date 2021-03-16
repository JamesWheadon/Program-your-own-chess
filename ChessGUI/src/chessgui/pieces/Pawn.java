package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import chessgui.PieceThreat;
import java.util.ArrayList;

public class Pawn extends Piece {

    private boolean has_moved;
    public boolean double_move;
    
    public Pawn(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;
    }
    
    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    
    @Override
    public String toString() {
        String xtext = this.getX() + "";
        String ytext = this.getY() + "";
        String colour = null;
        if (this.isWhite()) {
            colour = "White";
        }
        else {
            colour = "Black";
        }
        return colour + " Pawn (x: " + xtext + " y: " + ytext + ")";
    }
    
    @Override
    public void possibleMovesMatrix() {
        boolean[][] possibleMoves = new boolean[8][8];
        int dy;
        if (this.isWhite()) {
            dy = 1;
        }
        else {
            dy = -1;
        }
        Piece possiblePiece = board.getPiece(this.getX(), this.getY() + dy);
        if (possiblePiece == null) {
            Coord possible = new Coord(this.getX(), this.getY() + dy);
            possibleMoves[this.getY() + dy][this.getX()] = true;
        }
        if (!this.has_moved) {
            Piece twoForwardPiece = board.getPiece(this.getX(), 
                    this.getY() + 2 * dy);
            if (twoForwardPiece == null) {
                possibleMoves[this.getY() + 2 * dy][this.getX()] = true;
            }
        }
        int[] diagonals = {-1, 1};
        for (int diagonal : diagonals) {
            if (this.getX() + diagonal >= 0 && this.getX() + diagonal < 8) {
                Piece diagonalPiece = 
                        board.getPiece(this.getX() + diagonal, 
                                this.getY() + dy);
                if (diagonalPiece != null) {
                    if (diagonalPiece.isWhite() != this.isWhite()) {
                        possibleMoves[this.getY() + dy][this.getX() + diagonal] 
                                = true;
                    }
                }
            }
        }
        if (board.Last_Moved_Piece instanceof Pawn) {
            if (((Pawn) board.Last_Moved_Piece).double_move) {
                if (board.Last_Moved_Piece.getY() == this.getY() && Math.abs(
                        board.Last_Moved_Piece.getX() - this.getX()) == 1) {
                    possibleMoves[this.getY() + dy]
                            [board.Last_Moved_Piece.getX()] = true;
                }
            }
        }
        this.possible_moves = possibleMoves;   
    }
    
    @Override
    public PieceThreat threatMatrix() {
        boolean[][] threats = new boolean[8][8];
        ArrayList<int[]> squaresToKing = new ArrayList<>();
        int dy;
        if (this.isWhite()) {
            dy = 1;
        }
        else {
            dy = -1;
        }
        if (this.getX() > 0) {
            threats[this.getY() + dy][this.getX() - 1] = true;
            Piece threatenedPiece = 
                    board.getPiece(this.getX() - 1, this.getY() + dy);
            if (threatenedPiece != null) {
                if (threatenedPiece.getClass().equals(King.class) && 
                        threatenedPiece.isWhite() != this.isWhite()) {
                    int [] pawnSquare = {this.getX(), this.getY()};
                    squaresToKing.add(pawnSquare);
                }
            }
        }
        if (this.getX() < 7) {
            threats[this.getY() + dy][this.getX() + 1] = true;
            Piece threatenedPiece = 
                    board.getPiece(this.getX() + 1, this.getY() + dy);
            if (threatenedPiece != null) {
                if (threatenedPiece.getClass().equals(King.class) && 
                        threatenedPiece.isWhite() != this.isWhite()) {
                    int [] pawnSquare = {this.getX(), this.getY()};
                    squaresToKing.add(pawnSquare);
                }
            }
        }
        if (squaresToKing.isEmpty()) {
            squaresToKing = null;
        }
        PieceThreat Threat = new PieceThreat(threats, null, null, 
                squaresToKing);
        return Threat;
    }
}
