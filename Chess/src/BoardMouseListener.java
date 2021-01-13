//NicholasSouza

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoardMouseListener implements MouseListener{
    
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    
    public void mouseClicked(MouseEvent e)
    {

    }//End of mouseClicked
    public void mousePressed(MouseEvent e)
    {
        //Subtraction used to account for window size offset
        startX = (e.getX() - 10) / Chess.SQUARESIZE;
        startY = (e.getY() - 40) / Chess.SQUARESIZE;
        
        if(Chess.position[startX][startY] != null)
        {
            for(int x = 0 ; x < Chess.position.length ; x++){
                for(int y = 0 ; y < Chess.position[x].length ; y++){
                    
                    //Highlights and tile the selected piece is moved to
                    if(Chess.position[startX][startY].canMove(startX, startY, x, y))
                    {
                        Chess.board.highlights[x][y] = true;
                    }//End of highlight draw
                    
                }//End of y loop
            }//End of x loop
            Chess.board.repaint();
        }//End of null check
        
    }//End of MousePressed
    public void mouseReleased(MouseEvent e)
    {
        //Makes sure the player has actually moved before letting the
        //Computer player make a move.
        boolean playerMoved = false;
        
        //Stores the most recently taken piece in case the player makes an illegal move
        Piece lastCaptured = null;
        
        //Subtraction used to account for window size offset
        endX = (e.getX() - 10) / Chess.SQUARESIZE;
        endY = (e.getY() - 40) / Chess.SQUARESIZE;
        
        //Condition prevents ArrayIndexOutOfBoundsException
        if(endX > 8 || endX < 0 || endY > 8 || endY < 0)
        {
            return;
        }
        
        //Condition prevents moving in place and checks start point has a white piece
        if(!(startX == endX && startY == endY) 
                && Chess.position[startX][startY] != null
                && Chess.position[startX][startY].color
                && Chess.position[startX][startY].canMove(startX, startY, endX, endY))
        {
            
            boolean gameOver = false;
            
            if(Chess.position[endX][endY] != null
                    && Chess.position[endX][endY].name.equals("King"))
            {
                gameOver = true;
                if(Chess.position[endX][endY].color){
                    System.out.println("Black Wins");
                }//End of black check
                if(!Chess.position[endX][endY].color){
                    System.out.println("White Wins");
                }//End of white check
            }//End of game over check
            
            //Marks the moved piece as having moved.
            //Relevant for castling and pawn's double-move
            if(!Chess.position[startX][startY].hasMoved){
                Chess.position[startX][startY].hasMoved = true;}
            
            if(Chess.position[startX][startY].upgrade){
                Chess.position[startX][startY] = new Queen(Chess.position[startX][startY].color);}
                    
            if(Chess.position[endX][endY] != null){
                lastCaptured = Chess.position[endX][endY];}
            
            Chess.position[endX][endY] = Chess.position[startX][startY];
            Chess.position[startX][startY] = null;
            playerMoved = true;
            
            //Looks to see if a move puts its king in check
            for(int x = 0 ; x < Chess.position.length ; x++)
            {
                for(int y = 0 ; y < Chess.position[x].length ; y++)
                {
                    //Ensures any move won't put own king in check
                    if(Chess.position[x][y] != null
                        && Chess.position[endX][endY] != null
                        && Chess.position[x][y].color == Chess.position[endX][endY].color
                        && Chess.position[x][y].name.equals("King")
                        && Chess.position[x][y].inCheck(x, y))
                    {
                        Chess.position[startX][startY] = Chess.position[endX][endY];
                        Chess.position[endX][endY] = null;
                        if(lastCaptured != null){
                            Chess.position[endX][endY] = lastCaptured;
                        }//Restores the last taken piece
                        playerMoved = false;
                        break;
                    }//End of in-check test
                }//End of y check
            }//End of x check
            
            //Checks to see if a king is castling and moves the rook accordingly
            if(Chess.position[endX][endY] != null
                    && Chess.position[endX][endY].name.equals("King")
                    && Chess.position[endX][endY].castle)
            {
                
                if(endX == 6)
                {
                    Chess.position[5][endY] = Chess.position[7][endY];
                    Chess.position[7][endY] = null;
                }//End of right castle movement
                
                if(endX == 1)
                {
                    Chess.position[2][endY] = Chess.position[0][endY];
                    Chess.position[0][endY] = null;
                }//End of right castle movement
                
                Chess.position[endX][endY].castle = false;
            }//End of castle movement
            
            //Checks to see if the player made a legal move.
            //If so, the computer player moves.
            if(playerMoved){
                //Stores how many moves the AI has tried.
                //If the AI's 16 best moves don't work, it will stop trying.
                int moveCount = 0;
                while(!Chess.moveAI())
                {
                    moveCount++;
                    if(moveCount >= 64){break;}
                }//End of AI movement
                
            }//End of playerMoved check
            
            if(gameOver){System.exit(0);}
        
        }//End of validity check
            
        Chess.board.repaint();
        
    }//End of mouseReleased
    public void mouseEntered(MouseEvent e)
    {
        
    }//End of mouseEntered
    public void mouseExited(MouseEvent e)
    {

    }//End of mouseExited
    
}//End of class
