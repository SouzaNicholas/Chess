//NicholasSouza

import javax.swing.ImageIcon;

public class Bishop extends Piece{
    
    public Bishop(boolean color) {
        super(color);
        
        if(color)
        {
            image = new ImageIcon("wbishop.gif");
        } else {
            image = new ImageIcon("bbishop.gif");
        }//End of color check block
        
        name = "Bishop";
        
    }//End of constructor
    
    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        
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
                
            }//End of block check loop
            
            //If piece is not blocked, move is valid
            return true;
            
        }//End of block check
        
        //If no valid move is found, return false
        return false;
            
    }//End of canMove

    public boolean canCapture(int x1, int y1, int x2, int y2) {
        
        if(Math.abs(y1 - y2) == Math.abs(x1 - x2))
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
                
            }//End of block check loop
            
            //If piece is not blocked, move is valid
            return true;
            
        }//End of block check
        
        //If no valid move is found, return false
        return false;
            
    }//End of canCapture
        
}//End of Bishop
