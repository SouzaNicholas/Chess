//NicholasSouza

import javax.swing.ImageIcon;

public class Queen extends Piece{
    
    public Queen(boolean color) {
        super(color);
        
        if(color)
        {
            image = new ImageIcon("wqueen.gif");
        } else {
            image = new ImageIcon("bqueen.gif");
        }//End of color check block
        
        name = "Queen";
        
    }//End of constructor
    
    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        
    if((y1 == y2 && x1 != x2)
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            //Checks to see if any pieces are blocking movement
            //Starts at one so the piece doesn't see itself
            for(int i = 1 ; i < Math.abs(x2 - x1) ; i++)
            {
                
                if(x2 > x1 && Chess.position[x1 + i][y1] != null)
                {
                    return false;
                }//End of right check
                
                if(x2 < x1 && Chess.position[x1 - i][y1] != null)
                {
                    return false;
                }//End of left check
                
            }//End of block check loop
            //If no blocking pieces are found, move is valid
            return true;
        }//End of Horizontal Check
        
        if((y1 != y2 && x1 == x2)
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            //Checks to see if any pieces are blocking movement
            //Starts at one so the piece doesn't see itself
            for(int i = 1 ; i < Math.abs(y2 - y1) ; i++)
            {
                
                if(y2 > y1 && Chess.position[x1][y1 + i] != null)
                {
                    return false;
                }//End of down check
                
                if(y2 < y1 && Chess.position[x1][y1 - i] != null)
                {
                    return false;
                }//End of up check
                
            }//End of block check loop
            //If no blocking pieces are found, move is valid
            return true;
        }//End of Vertical Check
        
        if((Math.abs(y1 - y2) == Math.abs(x1 - x2))
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            
            for(int i = 1 ; i < Math.abs(y1 - y2) ; i++)
            {
                
                if(y1 < y2 && x1 < x2
                        && Chess.position[x1 + i][y1 + i] != null){
                    return false;
                }//End of down-right check
                
                if(y1 > y2 && x1 < x2
                        && Chess.position[x1 + i][y1 - i] != null){
                    return false;
                }//End of up-right check
                
                if(y1 < y2 && x1 > x2
                        && Chess.position[x1 - i][y1 + i] != null){
                    return false;
                }//End of down-left check
                
                if(y1 > y2 && x1 > x2
                        && Chess.position[x1 - i][y1 - i] != null){
                    return false;
                }//End of up-left check
                
            }//End of Diagonal Check
            
            //If piece is not blocked, move is valid
            return true;
            
        }//End of block check
        
        //If no valid move is found, return false
        return false;
        
    }//End of canMove
    
    public boolean canCapture(int x1, int y1, int x2, int y2)
    {
        
        if(y1 == y2 && x1 != x2)
        {
            //Checks to see if any pieces are blocking movement
            //Starts at one so the piece doesn't see itself
            for(int i = 1 ; i < Math.abs(x2 - x1) ; i++)
            {
                
                if(x2 > x1 && Chess.position[x1 + i][y1] != null)
                {
                    return false;
                }//End of right check
                
                if(x2 < x1 && Chess.position[x1 - i][y1] != null)
                {
                    return false;
                }//End of left check
                
            }//End of block check loop
            //If no blocking pieces are found, move is valid
            return true;
        }//End of Horizontal Check
        
        if(y1 != y2 && x1 == x2)
        {
            //Checks to see if any pieces are blocking movement
            //Starts at one so the piece doesn't see itself
            for(int i = 1 ; i < Math.abs(y2 - y1) ; i++)
            {
                
                if(y2 > y1 && Chess.position[x1][y1 + i] != null)
                {
                    return false;
                }//End of down check
                
                if(y2 < y1 && Chess.position[x1][y1 - i] != null)
                {
                    return false;
                }//End of up check
                
            }//End of block check loop
            //If no blocking pieces are found, move is valid
            return true;
        }//End of Vertical Check
        
        if((Math.abs(y1 - y2) == Math.abs(x1 - x2))
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            
            for(int i = 1 ; i < Math.abs(y1 - y2) ; i++)
            {
                
                if(y1 < y2 && x1 < x2
                        && Chess.position[x1 + i][y1 + i] != null){
                    return false;
                }//End of down-right check
                
                if(y1 > y2 && x1 < x2
                        && Chess.position[x1 + i][y1 - i] != null){
                    return false;
                }//End of up-right check
                
                if(y1 < y2 && x1 > x2
                        && Chess.position[x1 - i][y1 + i] != null){
                    return false;
                }//End of down-left check
                
                if(y1 > y2 && x1 > x2
                        && Chess.position[x1 - i][y1 - i] != null){
                    return false;
                }//End of up-left check
                
            }//End of Diagonal Check
            
            //If piece is not blocked, move is valid
            return true;
            
        }//End of block check
        
        //If no valid move is found, return false
        return false;
        
    }//End of canCapture
        
}//End of Queen
