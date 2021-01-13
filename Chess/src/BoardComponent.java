//NicholasSouza

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class BoardComponent extends JComponent{
    
    public boolean[][] highlights = new boolean[8][8];
    
    public void paintComponent(Graphics g)
    {

        for(int x = 0; x < 8 ; x++)
        {
            for(int y = 0; y < 8 ; y++)
            {

                //Decides whether the tile is light or dark
                if( x % 2 == y % 2)
                {
                    g.setColor(new Color(177,113,24));
                } else {
                    g.setColor(new Color(233,174,95));
                }//End of tile color check

                //Draws the tile
                g.fillRect(x * Chess.SQUARESIZE, y * Chess.SQUARESIZE, Chess.SQUARESIZE, Chess.SQUARESIZE);

            }//End of y loop
        }//End of x loop

        for(int x = 0 ; x < Chess.position.length ; x++){
            for(int y = 0 ; y < Chess.position[x].length ; y++){

                if(highlights[x][y] == true){
                    if( x % 2 == y % 2)
                    {
                        g.setColor(new Color(0,150,150));
                    } else {
                        g.setColor(new Color(50,210,210));
                    }//End of tile color check
                    g.fillRect(x * Chess.SQUARESIZE, y * Chess.SQUARESIZE, Chess.SQUARESIZE, Chess.SQUARESIZE);

                    //Removes the highlight so it's only drawn once
                    highlights[x][y] = false;
                }//End of potential move drawing

                if(Chess.position[x][y] != null)
                {
                    Chess.position[x][y].drawPiece(x, y, g);
                }//End of piece check

            }//End of x loop
        }//End of y loop
        
    }//End of paintComponent
    
}//End of class
