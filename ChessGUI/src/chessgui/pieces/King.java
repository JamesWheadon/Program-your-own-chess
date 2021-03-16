package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import chessgui.PieceThreat;
import java.util.ArrayList;

public class King extends Piece {

    private boolean has_moved;

    public King(int x, int y, boolean is_white, String file_path, Board board)
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
        return colour + " King (x: " + xtext + " y: " + ytext + ")";
    }
    
    @Override
    public PieceThreat threatMatrix() {
        boolean[][] threats = new boolean[8][8];
        int i = this.getX() - 1;
        int j = this.getY() - 1;
        for (int a = 0; a < 3; a++) {
            for (int b = 0; b < 3; b++) {
                if (i + a >= 0 && i + a < 8 && j + b >= 0 && j + b < 8) {
                    threats[j + b][i + a] = true;
                }
            }
        }
        PieceThreat Threat = 
                new PieceThreat(threats, null, null, null);
        return Threat;
    }
    
    @Override
    public void possibleMovesMatrix() {
        boolean[][] threatenedSquares;
        if (this.isWhite()) {
            threatenedSquares = board.blackThreatSquares;
        }
        else {
            threatenedSquares = board.whiteThreatSquares;
        }
        Piece pieceOnSquare;
        boolean[][] possibleMoves = new boolean[8][8];
        int i = this.getX();
        int j = this.getY();
        for (int a = -1; a < 2; a++) {
            for (int b = -1; b < 2; b++) {
                if (i + a >= 0 && i + a < 8 && j + b >= 0 && j + b < 8) {
                    pieceOnSquare = board.getPiece(i + a, j + b);
                    if (pieceOnSquare != null) {
                        if (pieceOnSquare.isWhite() != this.isWhite()) {
                            if (threatenedSquares[j + b][i + a] == false) {
                                possibleMoves[j + b][i + a] = true;
                            }
                        }
                    }
                    else {
                        if (threatenedSquares[j + b][i + a] == false) {
                            possibleMoves[j + b][i + a] = true;
                        }
                    }
                }
            }
        }
        if (!has_moved && !threatenedSquares[this.getY()][this.getX()]) {
            int[][] rookXs = {{0, -1, 3}, {7, 1, 4}};
            for (int[] rookX : rookXs) {
                Piece possibleRook = board.getPiece(rookX[0], this.getY());
                if (possibleRook instanceof Rook) {
                    if (!((Rook) possibleRook).has_moved) {
                        boolean clearCells = true;
                        for (int k = 1; k < rookX[2]; k++) {
                            Piece possiblePiece = board.getPiece(this.getX() + 
                                    rookX[1] * k, this.getY());
                            if (possiblePiece != null || 
                                    threatenedSquares[this.getY()][this.getX() + 
                                    rookX[1] * k]) {
                                clearCells = false;
                                break;
                            }
                        }
                        if (clearCells) {
                            possibleMoves[this.getY()]
                                    [this.getX() + rookX[1] * 2] = true;
                        }
                    }
                }
            }
        }
        this.possible_moves = possibleMoves;
    }
}
