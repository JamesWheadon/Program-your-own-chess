package chessgui;

import chessgui.pieces.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class Board extends JComponent {
        
    public int turnCounter = 0;
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

    private final int Square_Width = 65;
    public ArrayList<Piece> White_Pieces;
    public ArrayList<Piece> Black_Pieces;
    public boolean is_whites_turn;
    
    public boolean[][] whiteThreatSquares;
    public boolean[][] blackThreatSquares;
    
    public boolean playerIsBlack;
    public boolean playerIsWhite;
    
    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;
    public Piece Last_Moved_Piece;

    private final int rows = 8;
    private final int cols = 8;
    private Integer[][] BoardGrid;
    private String board_file_path = "images" + File.separator + "board.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";

    public void print2D(boolean mat[][]) 
    { 
        // Loop through all rows 
        for (boolean[] row : mat) {

            // converting each row as string 
            // and then printing in a separate line 
            System.out.println(Arrays.toString(row));
        }
    }

    public boolean[][] addMatrices(boolean mat1[][], boolean mat2[][])
    {
        int matrixRows = mat1.length;
        int matrixColumns = mat1[0].length;
        boolean[][] sum = new boolean[matrixRows][matrixColumns];
        for(int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixColumns; j++) {
                sum[i][j] = mat1[i][j] || mat2[i][j];
            }
        }
        return sum;
    }

    public boolean[][] combineMatrices(boolean mat1[][], boolean mat2[][])
    {
        int matrixRows = mat1.length;
        int matrixColumns = mat1[0].length;
        boolean[][] sum = new boolean[matrixRows][matrixColumns];
        for(int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixColumns; j++) {
                sum[i][j] = mat1[i][j] && mat2[i][j];
            }
        }
        return sum;
    }

    public void initGrid()
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                BoardGrid[i][j] = 0;
            }
        }

        //Image white_piece = loadImage("images/white_pieces/" + piece_name + ".png");
        //Image black_piece = loadImage("images/black_pieces/" + piece_name + ".png");  

        White_Pieces.add(new King(3,0,true,"King.png",this));
        White_Pieces.add(new Queen(4,0,true,"Queen.png",this));
        White_Pieces.add(new Bishop(2,0,true,"Bishop.png",this));
        White_Pieces.add(new Bishop(5,0,true,"Bishop.png",this));
        White_Pieces.add(new Knight(1,0,true,"Knight.png",this));
        White_Pieces.add(new Knight(6,0,true,"Knight.png",this));
        White_Pieces.add(new Rook(0,0,true,"Rook.png",this));
        White_Pieces.add(new Rook(7,0,true,"Rook.png",this));
        White_Pieces.add(new Pawn(0,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(1,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(2,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(3,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(4,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(5,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(6,1,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(7,1,true,"Pawn.png",this));

        Black_Pieces.add(new King(3,7,false,"King.png",this));
        Black_Pieces.add(new Queen(4,7,false,"Queen.png",this));
        Black_Pieces.add(new Bishop(2,7,false,"Bishop.png",this));
        Black_Pieces.add(new Bishop(5,7,false,"Bishop.png",this));
        Black_Pieces.add(new Knight(1,7,false,"Knight.png",this));
        Black_Pieces.add(new Knight(6,7,false,"Knight.png",this));
        Black_Pieces.add(new Rook(0,7,false,"Rook.png",this));
        Black_Pieces.add(new Rook(7,7,false,"Rook.png",this));
        Black_Pieces.add(new Pawn(0,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(1,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(2,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(3,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(4,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(5,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(6,6,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(7,6,false,"Pawn.png",this));
        
        System.out.println("1 or 2 players:");
        Scanner myObj = new Scanner(System.in);
        String players = myObj.nextLine();
        
        String[] acceptableNumbers = {"1", "2"};
        while (!Arrays.asList(acceptableNumbers).contains(players)) {
            System.out.println("1 or 2 players:");
            players = myObj.nextLine();
        }
        if (players.equals("2")) {
            playerIsWhite = true;
            playerIsBlack = true;
        }
        else {
            System.out.println("Choose colour: White or Black");
            String playerColour = myObj.nextLine();
            String[] acceptableInputs = {"White", "Black"};
            while (!Arrays.asList(acceptableInputs).contains(playerColour)) {
                System.out.println("Choose colour: White or Black");
                playerColour = myObj.nextLine();
            }
            if (playerColour.equals("White")) {
                playerIsWhite = true;
                playerIsBlack = false;
            }
            else {
                playerIsWhite = false;
                playerIsBlack = true;
            }
        }
    }

    public Board() {

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();

        initGrid();

        this.setBackground(new Color(37,13,84));
        this.setPreferredSize(new Dimension(520, 520));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);


        
        this.setVisible(true);
        this.requestFocus();
        drawBoard();
        startTurn();
        if (!playerIsWhite) {
            computerTurn();
        }
    }

    private void drawBoard()
    {
        Piece_Graphics.clear();
        Static_Shapes.clear();
        //initGrid();
        
        Image board = loadImage(board_file_path);
        Static_Shapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if (Active_Piece != null)
        {
            Image active_square = loadImage("images" + File.separator + "active_square.png");
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Active_Piece.getX(),Square_Width*Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        for (int i = 0; i < White_Pieces.size(); i++)
        {
            int COL = White_Pieces.get(i).getX();
            int ROW = White_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + White_Pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        for (int i = 0; i < Black_Pieces.size(); i++)
        {
            int COL = Black_Pieces.get(i).getX();
            int ROW = Black_Pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + Black_Pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        this.repaint();
    }

    public Piece getPiece(int x, int y) {
        for (Piece p : White_Pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        for (Piece p : Black_Pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        return null;
    }    

    private void pieceMoved(int Clicked_Column, int Clicked_Row) {
        // if piece is a pawn set has_moved to true
        if (Active_Piece.getClass().equals(Pawn.class))
        {
            Pawn movedPawn = (Pawn)(Active_Piece);
            movedPawn.setHasMoved(true);
            promotePawn(Clicked_Row);
            if (Math.abs(Clicked_Row - Active_Piece.getY()) == 2)
            {
                movedPawn.double_move = true;
            }
            else 
            {
                movedPawn.double_move = false;
            }
            if (Math.abs(Clicked_Row - Active_Piece.getY()) == 1 && 
                    Math.abs(Clicked_Column - Active_Piece.getX()) == 1) 
            {
                if (getPiece(Clicked_Column, Clicked_Row) == null) 
                {
                    Piece takenPawn;
                    if (turnCounter % 2 == 0) {
                        takenPawn = getPiece(Clicked_Column, 
                                Clicked_Row - 1);
                        Black_Pieces.remove(takenPawn);
                    }
                    else 
                    {
                        takenPawn = getPiece(Clicked_Column, 
                                Clicked_Row + 1);
                        White_Pieces.remove(takenPawn);
                    }
                }
            }
        }
        // if piece is a king set has_moved to true
        if (Active_Piece.getClass().equals(King.class))
        {
            King movedKing = (King)(Active_Piece);
            movedKing.setHasMoved(true);
            if (Math.abs(Clicked_Column - Active_Piece.getX()) == 2) 
            {
                if (Clicked_Column < Active_Piece.getX()) 
                {
                    Piece castleRook = getPiece(0, Clicked_Row);
                    castleRook.setX(2);
                }
                else
                {
                    Piece castleRook = getPiece(7, Clicked_Row);
                    castleRook.setX(4);
                }
            }
        }
        // if piece is a rook set has_moved to true
        if (Active_Piece.getClass().equals(Rook.class))
        {
            Rook movedRook = (Rook)(Active_Piece);
            movedRook.setHasMoved(true);
        }
    }

    private void promotePawn(int Clicked_Row) {
        int targetY;
        boolean whiteTurn;
        ArrayList<Piece> colourPieces;
        if (Active_Piece.isWhite()){
            targetY = 7;
            colourPieces = White_Pieces;
            whiteTurn = true;
            
        }
        else {
            targetY = 0;
            colourPieces = Black_Pieces;
            whiteTurn = false;
        }
        // if pawn reaches the final rank upgrade the pawn
        if (Clicked_Row == targetY) {
            System.out.println("Upgrade Pawn: Q, R, B, Kn?");
            Scanner myObj = new Scanner(System.in);
            String pieceUpgrade = myObj.nextLine();
            String[] acceptableInputs = {"Q", "R", "B", "Kn"};
            while (!Arrays.asList(acceptableInputs).contains(pieceUpgrade)) {
                System.out.println("Upgrade Pawn: Q, R, B, Kn?");
                pieceUpgrade = myObj.nextLine();
            }
            if ("Q".equals(pieceUpgrade)) {
                colourPieces.add(new Queen(Active_Piece.getX(),targetY,
                        whiteTurn,"Queen.png",Active_Piece.board));
            }
            if ("R".equals(pieceUpgrade)) {
                colourPieces.add(new Rook(Active_Piece.getX(),targetY,
                        whiteTurn,"Rook.png",Active_Piece.board));
            }
            if ("B".equals(pieceUpgrade)) {
                colourPieces.add(new Bishop(Active_Piece.getX(),targetY,
                        whiteTurn,"Bishop.png",Active_Piece.board));
            }
            if ("Kn".equals(pieceUpgrade)) {
                colourPieces.add(new Knight(Active_Piece.getX(),targetY,
                        whiteTurn,"Knight.png",Active_Piece.board));
            }
            colourPieces.remove(Active_Piece);
        }
    }

    private void startTurn() {
        ArrayList<Piece> playerPieces;
        ArrayList<Piece> opponentPieces;
        ArrayList<PieceThreat> pieceThreats = new ArrayList<>();
        String player;
        String opponent;
        if (turnCounter % 2 == 0) {
            playerPieces = White_Pieces;
            opponentPieces = Black_Pieces;
            player = "White";
            opponent = "Black";
        }
        else {
            opponentPieces = White_Pieces;
            playerPieces = Black_Pieces;
            player = "Black";
            opponent = "White";
        }
        boolean[][] threatMatrix = new boolean[8][8];
        ArrayList<Piece> pinnedPieces = new ArrayList<>();
        boolean[][] checkThreatMatrix = new boolean[8][8];
        boolean kingChecked = false;
        boolean possibleMove = false;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                checkThreatMatrix[i][j] = true;
            }
        }
        for (Piece opponentPiece : opponentPieces) {
            PieceThreat piece = opponentPiece.threatMatrix();
            threatMatrix = addMatrices(threatMatrix, piece.pieceThreats);
            pieceThreats.add(piece);
        }
        if (turnCounter % 2 == 0) {
            blackThreatSquares = threatMatrix;
        }
        else {
            whiteThreatSquares = threatMatrix;
        }
        for (PieceThreat piece : pieceThreats) {
            if (piece.kingPinnedPiece != null) {
                pinnedPieces.add(piece.kingPinnedPiece);
                piece.kingPinnedPiece.possibleMovesMatrix();
                boolean[][] safePinSquares = new boolean[8][8];
                for (int[] safePinSquare : piece.safePinSquares) {
                    safePinSquares[safePinSquare[1]][safePinSquare[0]] = true;
                }
                piece.kingPinnedPiece.possible_moves = combineMatrices(
                        piece.kingPinnedPiece.possible_moves, safePinSquares);
            }
            if (piece.squaresToKing != null) {
                boolean[][] keySquares = new boolean[8][8];
                for (int[] squareToKing : piece.squaresToKing) {
                    keySquares[squareToKing[1]][squareToKing[0]] = true;
                }
                checkThreatMatrix = combineMatrices(checkThreatMatrix, 
                        keySquares);
                kingChecked = true;
            }
        }
        for (Piece playerPiece : playerPieces) {
            if (!pinnedPieces.contains(playerPiece)) {
                playerPiece.possibleMovesMatrix();
            }
            if (kingChecked && !playerPiece.getClass().equals(King.class)) {
                playerPiece.possible_moves = combineMatrices(
                        playerPiece.possible_moves, checkThreatMatrix);
            }
            if (!possibleMove) {
                for(int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (playerPiece.possible_moves[i][j]) {
                            possibleMove = true;
                            break;
                        }
                    }
                }
            }
        }
        if (kingChecked && possibleMove) {
            System.out.println(player + " is in check!");
        }
        else if (!kingChecked && !possibleMove) {
            System.out.println("Stalemate!");
        }
        else if (kingChecked && !possibleMove) {
            System.out.println("Checkmate, " + opponent + " wins!");
        }
    }
    
    private void computerTurn() {
        ArrayList<Piece> Pieces;
        if (turnCounter % 2 == 0) {
            Pieces = White_Pieces;
        }
        else {
            Pieces = Black_Pieces;
        }
        boolean moved = false;
        Piece pieceToMove;
        while (!moved) {
            pieceToMove = Pieces.get((int)(Math.random() * Pieces.size()));
            outerloop:
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (pieceToMove.possible_moves[i][j]) {
                        Active_Piece = pieceToMove;
                        move(j, i);
                        moved = true;
                        break outerloop;
                    }
                }
            }
        }
    }
    
    public void move(int x, int y) {
        Piece pieceOnSquare = getPiece(x, y);
        if (pieceOnSquare != null)
        {
            if (pieceOnSquare.isWhite())
            {
                White_Pieces.remove(pieceOnSquare);
            }
            else
            {
                Black_Pieces.remove(pieceOnSquare);
            }
        }
        pieceMoved(x, y);
        Active_Piece.setX(x);
        Active_Piece.setY(y);
        Last_Moved_Piece = Active_Piece;
        Active_Piece = null;
        turnCounter++;
        startTurn();
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();  
            int Clicked_Row = d_Y / Square_Width;
            int Clicked_Column = d_X / Square_Width;
            if (turnCounter%2 == 1)
            {
                is_whites_turn = false;
            }
            else {
                is_whites_turn = true;
            }
            if ((is_whites_turn && playerIsWhite) || 
                    (!is_whites_turn && playerIsBlack)) {
                Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

                //Active piece is the selected by clicking on the appropriate colour
                // if another piece isnt selected
                if (Active_Piece == null && clicked_piece != null && 
                        ((is_whites_turn && clicked_piece.isWhite()) || 
                        (!is_whites_turn && clicked_piece.isBlack())))
                {
                    Active_Piece = clicked_piece;
                }
                // click the selected piece to deselect it
                else if (Active_Piece != null && Active_Piece.getX() == 
                        Clicked_Column && Active_Piece.getY() == Clicked_Row)
                {
                    Active_Piece = null;
                }
                else if (Active_Piece != null && Active_Piece.possible_moves
                        [Clicked_Row][Clicked_Column] && ((is_whites_turn && 
                        Active_Piece.isWhite()) || (!is_whites_turn && 
                        Active_Piece.isBlack())))
                {
                    if (clicked_piece != null)
                    {
                        if (clicked_piece.isWhite())
                        {
                            White_Pieces.remove(clicked_piece);
                        }
                        else
                        {
                            Black_Pieces.remove(clicked_piece);
                        }
                    }
                    // do move
                    pieceMoved(Clicked_Column, Clicked_Row);
                    Active_Piece.setX(Clicked_Column);
                    Active_Piece.setY(Clicked_Row);
                    Last_Moved_Piece = Active_Piece;
                    Active_Piece = null;
                    turnCounter++;
                    startTurn();
                    if (turnCounter % 2 == 0 && !playerIsWhite) {
                        computerTurn();
                    }
                    else if (turnCounter % 2 == 1 && !playerIsBlack) {
                        computerTurn();
                    }
                }
            }
            drawBoard();
        }

        @Override
        public void mouseDragged(MouseEvent e) {		
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) 
        {
        }
    };

    private void adjustShapePositions(double dx, double dy) {

        Static_Shapes.get(0).adjustPosition(dx, dy);
        this.repaint();

    } 

    private Image loadImage(String imageFile) {
        try {
                return ImageIO.read(new File(imageFile));
        }
        catch (IOException e) {
                return NULL_IMAGE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }

    private void drawShapes(Graphics2D g2) {
        for (DrawingShape shape : Static_Shapes) {
            shape.draw(g2);
        }	
        for (DrawingShape shape : Piece_Graphics) {
            shape.draw(g2);
        }
    }

    private ComponentAdapter componentAdapter = new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {

        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentResized(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }	
    };

    private KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }	
    };

}

interface DrawingShape {
    boolean contains(Graphics2D g2, double x, double y);
    void adjustPosition(double dx, double dy);
    void draw(Graphics2D g2);
}

class DrawingImage implements DrawingShape {

    public Image image;
    public Rectangle2D rect;

    public DrawingImage(Image image, Rectangle2D rect) {
            this.image = image;
            this.rect = rect;
    }

    @Override
    public boolean contains(Graphics2D g2, double x, double y) {
            return rect.contains(x, y);
    }

    @Override
    public void adjustPosition(double dx, double dy) {
            rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());	
    }

    @Override
    public void draw(Graphics2D g2) {
            Rectangle2D bounds = rect.getBounds2D();
            g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
                                            0, 0, image.getWidth(null), image.getHeight(null), null);
    }	
}
