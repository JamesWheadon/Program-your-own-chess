package chessgui;

import chessgui.Board;
import java.util.*;
import chessgui.pieces.*;

/**
 *
 * @author james
 */
public class PieceThreat {
    public boolean[][] pieceThreats;
    public Piece kingPinnedPiece;
    public ArrayList<int[]> safePinSquares;
    public ArrayList<int[]> squaresToKing;
    
    public PieceThreat(boolean[][] pieceThreats, Piece kingPinnedPiece, 
            ArrayList<int[]> safePinSquares, ArrayList<int[]> squaresToKing)
    {
        this.pieceThreats = pieceThreats;
        this.kingPinnedPiece = kingPinnedPiece;
        this.safePinSquares = safePinSquares;
        this.squaresToKing = squaresToKing;
    }
    
    @Override
    public String toString() {
        if (kingPinnedPiece != null) {
            return this.kingPinnedPiece + "pinned, moving causes checkmate";
        }
        return "";
    }
}
