//NicholasSouza

import java.awt.Graphics;
import javax.swing.ImageIcon;

public abstract class Piece {
    
    public ImageIcon image;
    public boolean color;
    public boolean hasMoved = false;
    
    //Used for seeing if a king is in check and castling
    public String name;
    
    //Used to see if a king is castling
    public boolean castle = false;
    
    //Used to track the en passante rule
    //If a pawn moves two tiles past another pawn, 
    //the other pawn can capture behind the first pawn.
    //Variable only used in Pawn. Appears in Piece to dodge NullPointer
    public boolean enPassante = false;
    
    //Tracks whether or not this needs to be upgraded
    //Variable only used in Pawn. Appears in Piece to dodge NullPointer
    public boolean upgrade = false;
    
    public Piece(boolean color)
    {
        this.color = color;
    }//End of constructor
    
    public void drawPiece(int x, int y, Graphics g)
    {
        
        g.drawImage(image.getImage(), x * Chess.SQUARESIZE, y * Chess.SQUARESIZE, null);
        
    }//End of drawPiece
    
    public boolean inCheck(int posX, int posY)
    {
        return false;
    }//End of inCheck
    
    public abstract boolean canMove(int x1, int y1, int x2, int y2);
    
    public abstract boolean canCapture(int x1, int y1, int x2, int y2);
    
}//End of Piece
