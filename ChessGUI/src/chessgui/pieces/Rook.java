package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import chessgui.PieceThreat;
import java.util.ArrayList;

public class Rook extends Piece {
    
    public boolean one_from_king;
    // integer defines direction of threat or potential threat from piece
    // 0 is not in line with king
    // 1 is same x, king on lower y
    // 2 is diagonal, king on higher x and lower y
    // 3 is same y, king on higher x
    // 4 is diagonal, king on higher x and y
    // 5 is same x, king on higher y
    // 6 is diagonal, king on lower x and higher y
    // 7 is same y, king on lower x
    // 8 is diagonal, king on lower x and y
    public int king_direction;
    protected boolean has_moved;

    public Rook(int x, int y, boolean is_white, String file_path, Board board)
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
        return colour + " Rook (x: " + xtext + " y: " + ytext + ")";
    }
    
    @Override
    public PieceThreat threatMatrix() {
        boolean[][] threats = new boolean[8][8];
        ArrayList<int[]> squaresToKing = new ArrayList<>();
        ArrayList<int[]> safePinSquares = new ArrayList<>();
        ArrayList<Piece> opponentPieces;
        Piece pieceOnSquare;
        Piece kingPinned = null;
        int king_direction = 0;
        if (this.isWhite()) {
            opponentPieces = board.Black_Pieces;
        }
        else {
            opponentPieces = board.White_Pieces;
        }
        for (Piece opponentPiece : opponentPieces) {
            if (opponentPiece.getClass().equals(King.class)) {
                if (opponentPiece.getX() == this.getX() || 
                        opponentPiece.getY() == this.getY()) {
                    if (opponentPiece.getX() == this.getX()) {
                        if (opponentPiece.getY() > this.getY()) {
                            king_direction = 5;
                        }
                        else {
                            king_direction = 1;
                        }
                    }
                    else {
                        if (opponentPiece.getX() > this.getX()) {
                            king_direction = 3;
                        }
                        else {
                            king_direction = 7;
                        }
                    }
                }
            }
        }
        int[][] directions = {{0, -1, 1}, {1, 0, 3}, {0, 1, 5}, {-1, 0, 7}};
        for (int[] direction : directions) {
            int i = 1;
            while (this.getX() + i * direction[0] < 8 && 
                    this.getX() + i * direction[0] >= 0 && 
                    this.getY() + i * direction[1] < 8 && 
                    this.getY() + i * direction[1] >= 0) {
                threats[this.getY() + i * direction[1]]
                        [this.getX() + i * direction[0]] = true;
                pieceOnSquare = board.getPiece(this.getX() + 
                        i * direction[0], this.getY() + i * direction[1]);
                i++;
                if (pieceOnSquare != null) {
                    if (pieceOnSquare.getClass().equals(King.class) && 
                            pieceOnSquare.isWhite() != this.isWhite()) {
                        for (int j = 0; j < i; j++) {
                            int[] square = {this.getX() + j * direction[0], 
                            this.getY() + j * direction[1]};
                            squaresToKing.add(square);
                        }
                        if (this.getX() + i * direction[0] < 8 && 
                                this.getX() + i * direction[0] >= 0 && 
                                this.getY() + i * direction[1] < 8 && 
                                this.getY() + i * direction[1] >= 0) {
                            threats[this.getY() + i * direction[1]]
                                    [this.getX() + i * direction[0]] = true;
                        }
                    }
                    if (this.isWhite() != pieceOnSquare.isWhite() && 
                            king_direction == direction[2]) {
                        int k = i;
                        while (this.getX() + k * direction[0] < 8 && 
                                this.getY() + k * direction[1] >= 0) {
                            Piece pieceBeyond = board.getPiece(this.getX() + 
                                    k * direction[0], 
                                    this.getY() + k * direction[1]);
                            k++;
                            if (pieceBeyond != null) {
                                if (pieceBeyond.getClass().equals(King.class)) {
                                    kingPinned = pieceOnSquare;
                                    for (int j = 0; j < i; j++) {
                                        int[] square = {this.getX() + j * direction[0], 
                                        this.getY() + j * direction[1]};
                                        safePinSquares.add(square);
                                    }
                                }
                                else {
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        if (squaresToKing.isEmpty()) {
            squaresToKing = null;
        }
        if (safePinSquares.isEmpty()) {
            safePinSquares = null;
        }
        PieceThreat Threat = 
                new PieceThreat(threats, kingPinned, safePinSquares, 
                        squaresToKing);
        return Threat;
    }
    
    @Override
    public void possibleMovesMatrix() {
        Piece pieceOnSquare;
        boolean[][] possibleMoves = new boolean[8][8];
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        for (int[] direction : directions) {
            int i = 1;
            while (this.getX() + i * direction[0] < 8 && 
                    this.getX() + i * direction[0] >= 0 && 
                    this.getY() + i * direction[1] < 8 && 
                    this.getY() + i * direction[1] >= 0) {
                pieceOnSquare = board.getPiece(this.getX() + 
                        i * direction[0], this.getY() + i * direction[1]);
                if (pieceOnSquare == null) {
                    possibleMoves[this.getY() + i * direction[1]]
                            [this.getX() + i * direction[0]] = true;
                }
                else if (pieceOnSquare.isWhite() != this.isWhite()) {
                    possibleMoves[this.getY() + i * direction[1]]
                            [this.getX() + i * direction[0]] = true;
                    break;
                }
                else {
                    break;
                }
                i++;
            }
        }
        this.possible_moves = possibleMoves;
    }
}
