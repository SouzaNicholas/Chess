//NicholasSouza

import javax.swing.ImageIcon;

public class Knight extends Piece{
    
    public Knight(boolean color) {
        super(color);
        
        if(color)
        {
            image = new ImageIcon("wknight.gif");
        } else {
            image = new ImageIcon("bknight.gif");
        }//End of color check block
        
        name = "Knight";
        
    }//End of constructor
    
    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        if(((Math.abs(y2 - y1) == 2 && Math.abs(x2 - x1) == 1)
                || (Math.abs(y2 - y1) == 1 && Math.abs(x2 - x1) == 2))
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color)){
            return true;
        } else {
            return false;
        }
    }//End of canMove

    public boolean canCapture(int x1, int y1, int x2, int y2) {
        if(((Math.abs(y2 - y1) == 2 && Math.abs(x2 - x1) == 1)
            || (Math.abs(y2 - y1) == 1 && Math.abs(x2 - x1) == 2))
            && (Chess.position[x2][y2] == null
                || Chess.position[x2][y2].color != this.color)){
            return true;
        } else {
            return false;
        }
    }//End of canCapture
    
}//End of Knight
