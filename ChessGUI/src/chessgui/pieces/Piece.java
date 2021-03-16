package chessgui.pieces;

import chessgui.Board;
import chessgui.Coord;
import java.util.*;
import chessgui.PieceThreat;

public class Piece {
    private int x;
    private int y;
    final private boolean is_white;
    private String file_path;
    public Board board;
    public boolean[][] possible_moves;
    
    public Piece(int x, int y, boolean is_white, String file_path, Board board)
    {
        this.is_white = is_white;
        this.x = x;
        this.y = y;
        this.file_path = file_path;
        this.board = board;
    }
    
    public String toString() {
        String xtext = this.x + "";
        String ytext = this.y + "";
        return "x: " + xtext + "y: " + ytext;
    }
    
    public String getFilePath()
    {
        return file_path;
    }
    
    public void setFilePath(String path)
    {
        this.file_path = path;
    }
    
    public boolean isWhite()
    {
        return is_white;
    }
    
    public boolean isBlack()
    {
        return !is_white;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void possibleMovesMatrix() {
    }
    
    public PieceThreat threatMatrix() {
        boolean[][] threats = new boolean[8][8];
        Piece pinnedPiece = null;
        ArrayList<int[]> safePinSquares = null;
        ArrayList<int[]> squaresToKing = null;
        PieceThreat PinOrChecked = new PieceThreat(threats, pinnedPiece, 
                safePinSquares, squaresToKing);
        return PinOrChecked;
    }
}
