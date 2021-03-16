package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import chessgui.PieceThreat;
import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
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
        return colour + " Bishop (x: " + xtext + " y: " + ytext + ")";
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
                if (Math.abs(opponentPiece.getX() - this.getX()) == 
                        Math.abs(opponentPiece.getY() - this.getY())) {
                    if (opponentPiece.getX() - this.getX() == 
                            opponentPiece.getY() - this.getY()) {
                        if (opponentPiece.getX() > this.getX()) {
                            king_direction = 4;
                        }
                        else {
                            king_direction = 8;
                        }
                    }
                    else {
                        if (opponentPiece.getX() > this.getX()) {
                            king_direction = 2;
                        }
                        else {
                            king_direction = 6;
                        }
                    }
                }
            }
        }
        int[][] directions = {{1, -1, 2}, {1, 1, 4}, {-1, 1, 6}, {-1, -1, 8}};
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
        int[][] directions = {{1, -1}, {1, 1}, {-1, 1}, {-1, -1}};
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
