//NicholasSouza

import javax.swing.ImageIcon;

public class King extends Piece{
    
    public King(boolean color) {
        super(color);
        
        if(color)
        {
            image = new ImageIcon("wking.gif");
        } else {
            image = new ImageIcon("bking.gif");
        }//End of color check block
        
        name = "King";
        
    }//End of constructor
    
    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        
        if(!this.hasMoved
                && x2 < 7
                && Chess.position[x2 + 1][y2] != null
                && Chess.position[x2 + 1][y2].name.equals("Rook")
                && Chess.position[x2 + 1][y2].color == this.color
                && !Chess.position[x2 + 1][y2].hasMoved
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            
            for(int i = 1; i < Math.abs(x2 - x1) ;i++)
            {
                if(x1 < 7 && Chess.position[x1 + i][y1] != null){
                    return false;
                }//Returns false if any piece is blocking the castle movement
            }//End of collision check
            
            castle = true;
            
            return true;
        }//End of right Castle condition
        
        if(!this.hasMoved
                && x2 > 0
                && Chess.position[x2 - 1][y2] != null
                && Chess.position[x2 - 1][y2].name.equals("Rook")
                && Chess.position[x2 - 1][y2].color == this.color
                && !Chess.position[x2 - 1][y2].hasMoved
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color))
        {
            
            for(int i = 1; i < Math.abs(x2 - x1) ;i++)
            {
                if(x1 > 0 && Chess.position[x1 - i][y1] != null){
                    return false;
                }//Returns false if any piece is blocking the castle movement
            }//End of collision check
            
            castle = true;
            
            return true;
        }//End of left Castle condition
        
        if((Math.abs(x2 - x1) <= 1)
                && (Math.abs(y2 - y1) <= 1)
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color)){
            return true;
        } else {
            return false;
        }
    }//End of canMove

    public boolean canCapture(int x1, int y1, int x2, int y2) {
        
        if((Math.abs(x2 - x1) <= 1)
                && (Math.abs(y2 - y1) <= 1)
                && (Chess.position[x2][y2] == null
                    || Chess.position[x2][y2].color != this.color)){
            return true;
        } else {
            return false;
        }
    }//End of canCapture
    
    public boolean inCheck(int posX, int posY){
        
        //Loop to ensure King will not be placed in check
        for(int x = 0 ; x < Chess.position.length ; x++){
            for(int y = 0 ; y < Chess.position[x].length ; y++){
                if(Chess.position[x][y] != null
                        && Chess.position[x][y].color != this.color
                        && Chess.position[x][y].canCapture(x, y, posX, posY))
                {
                    return true;
                }//End of threat check
            }//End of y loop
        }//End of x loop
        
        return false;
        
    }//End of inCheck
    
}//End of King
