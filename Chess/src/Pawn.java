//NicholasSouza

import javax.swing.ImageIcon;

public class Pawn extends Piece{
    
    public Pawn(boolean color) {
        super(color);
        
        if(color)
        {
            image = new ImageIcon("wpawn.gif");
        } else {
            image = new ImageIcon("bpawn.gif");
        }//End of color check block
        
        name = "Pawn";
        
    }//End of constructor
    
    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        
        if(!hasMoved 
                && Chess.position[x2][y2] == null
                && ((color && ((x1 == x2 && (y2 - y1) == 2)))
                    || (!color &&  ((x1 == x2 && (y1 - y2) == 2)))))
        {
            enPassante = true;
            return true;
        }//End of two space move check
        
        //Check uses color to limit movement direction
        if(((color 
                && (x1 == x2 && (y2 - y1) == 1))
            || (!color
                 &&  (x1 == x2 && (y1 - y2) == 1)))
            && Chess.position[x2][y2] == null)
        {

            //Checks to see if an upgrade is necessary only if the move is valid
            if(color && y2 > 7){
                upgrade = true;
            }//End of upgrade check
            if(!color && y2 < 0){
                upgrade = true;
            }//End of upgrade check
            
            //Prevents en passante after single move.
            if(enPassante){enPassante = false;}
            
            return true;
        }
        
        if(this.canCapture(x1, y1, x2, y2)
                    && (Chess.position[x2][y2] != null 
                    && Chess.position[x2][y2].color != this.color))
        {
            
            //Checks to see if an upgrade is necessary only if the move is valid
            if(color && y2 == 7){
                upgrade = true;
            }//End of upgrade check
            if(!color && y2 == 0){
                upgrade = true;
            }//End of upgrade check
            
            return true;
        }//End of capture check
        
        //If nothing else works, the piece cannot move
        return false;
        
    }//End of canMove

    public boolean canCapture(int x1, int y1, int x2, int y2) {
        //Checks use color to limit movement direction
        
        if((color && ((x2 == x1 + 1 && (y2 - y1) == 1))
                || ((x2 == x1 - 1 && (y2 - y1) == 1)))
            || (!color
                &&  ((x2 == x1 + 1 && (y1 - y2) == 1)
                || (x2 == x1 - 1 && (y1 - y2) == 1))))
        {
            //Prevents en passante after single move.
            if(enPassante){enPassante = false;}
            return true;
        }//End of primary capture check

        if((color 
                && ((x2 == x1 + 1 && (y2 - y1) == 1)
                    || (x2 == x1 - 1 && (y2 - y1) == 1))
                && Chess.position[x2][y2 - 1] != null
                && Chess.position[x2][y2 - 1].enPassante
                && Chess.position[x2][y2 - 1].color != this.color))
        {
            Chess.position[x2][y2 + 1] = null;
            return true;
        }//White en passante check
        
        if((!color
                && ((x2 == x1 + 1 && (y1 - y2) == 1)
                    || (x2 == x1 - 1 && (y1 - y2) == 1))
                && Chess.position[x2][y2 - 1] != null
                && Chess.position[x2][y2 - 1].enPassante
                && Chess.position[x2][y2 - 1].color != this.color))
        {
            Chess.position[x2][y2 - 1] = null;
            return true;
        }//Black en passante check
        
        //If nothing else works, return false
        return false;
            
    }//End of canCapture
    
}//End of Pawn
