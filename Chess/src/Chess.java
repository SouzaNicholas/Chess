//NicholasSouza

import javax.swing.JFrame;

public class Chess {
    
    public static final int SQUARESIZE = 44;
    public static final int WINDOWWIDTH = (SQUARESIZE * 8) + 20, WINDOWHEIGHT = (SQUARESIZE * 8) + 50;
    public static Piece[][] position = new Piece[8][8];
    public static BoardComponent board;
    
    public static void main(String args[])
    {
       
        //Initializes the array of pieces.
       initPieces();
       JFrame window = new JFrame();
       
       //Initializes characteristics of the window
       window.setVisible(true);
       window.setSize(WINDOWWIDTH, WINDOWHEIGHT);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setTitle("Chess");
       
       //Creates a board and places it in window
       board = new BoardComponent();
       window.add(board);

       //Creates and attaches a MouseListener and a MouseMotionListener to the window
       BoardMouseListener mouseListener = new BoardMouseListener();
       window.addMouseListener(mouseListener);
       
    }//End of main
    
    public static boolean moveAI()
    {
        //Ensures the AI can actually move
        boolean moveValid = false;
        
        //Stores the most recently captured piece in case an invalid move needs to be reset
        Piece lastCaptured = null;
        
        //Used to determine the value fo each move the computer player could make
        //Also stores where the computer player plans to move
        int AIval = -200;
        int comX1 = -1;
        int comY1 = -1;        
        int comX2 = -1;
        int comY2 = -1;

        for(int x = 0 ; x < Chess.position.length ; x++)
        {
            for(int y = 0 ; y < Chess.position[x].length ; y++)
            {
                for(int x2 = 0 ; x2 < Chess.position.length ; x2++){
                    for(int y2 = 0 ; y2 < Chess.position[x2].length ; y2++){
                        if(Chess.position[x][y] != null
                                && !Chess.position[x][y].color
                                && Chess.position[x][y].canMove(x, y, x2, y2)){
                            int testVal = Chess.evalAI(x, y, x2, y2);
                            if(testVal > AIval)
                            {
                                AIval = testVal;
                                comX1 = x;
                                comY1 = y;        
                                comX2 = x2;
                                comY2 = y2;
                            }//End of better move check
                        }//End of validity check
                    }//End of y2 loop
                }//End of x2 loop
            }//End of y loop
        }//End of x loop

        //Ensures computer player's desired move has been decided
        //Avoids IndexOutOfBoundsException
        if(comX1 >= 0
                && comY1 >= 0
                && comX2 >= 0
                && comY2 >= 0)
        {

            if(Chess.position[comX1][comY1].upgrade){
                Chess.position[comX1][comY1] = new Queen(Chess.position[comX1][comY1].color);}

            if(Chess.position[comX2][comY2] != null){
                lastCaptured = Chess.position[comX2][comY2];}

            moveValid = true;
            
            Chess.position[comX2][comY2] = Chess.position[comX1][comY1];
            Chess.position[comX1][comY1] = null;
            Chess.position[comX2][comY2].hasMoved = true;

            //Checks to see if a king is castling and moves the rook accordingly
            if(Chess.position[comX2][comY2] != null
                    && Chess.position[comX2][comY2].name.equals("King")
                    && Chess.position[comX2][comY2].castle)
            {

                if(comX2 == 6)
                {
                    Chess.position[5][comY2] = Chess.position[7][comY2];
                    Chess.position[7][comY2] = null;
                }//End of right castle movement

                if(comX2 == 1)
                {
                    Chess.position[2][comY2] = Chess.position[0][comY2];
                    Chess.position[0][comY2] = null;
                }//End of right castle movement

                Chess.position[comX2][comY2].castle = false;
            }//End of castle movement
        }//End of validity check

        //Looks to see if a move puts its king in check
        for(int x = 0 ; x < Chess.position.length ; x++)
        {
            for(int y = 0 ; y < Chess.position[x].length ; y++)
            {
                //Ensures any move won't put own king in check
                if(Chess.position[x][y] != null
                    && Chess.position[comX2][comY2] != null
                    && Chess.position[x][y].color == Chess.position[comX2][comY2].color
                    && Chess.position[x][y].name.equals("King")
                    && Chess.position[x][y].inCheck(x, y))
                {
                    Chess.position[comX1][comY1] = Chess.position[comX2][comY2];
                    Chess.position[comX2][comY2] = null;
                    if(lastCaptured != null){
                        Chess.position[comX2][comY2] = lastCaptured;
                    }//Restores the last taken piece
                    moveValid = false;
                    break;
                }//End of in-check test
            }//End of y check
        }//End of x check
        
        return moveValid;
        
    }//End of moveAI
    
    //Calculates the most likely scenario three moves out from their next move
    //(Three move metric includes the move being tested)
    //Varaibles for all three moves are stored in here so they can be replaced afterward
    //moveAI is quite jank. Refactor later.
    public static int evalAI(int x, int y, int m1x, int m1y)
    {
        //Used to determine the total value of a move
        int value = 0;
        
        //Stores the starting position of the first white piece hypothetically moved
        int wx = 0;
        int wy = 0;
        
        //Variables to determine worth of first move
        int m1val = -1;
        //Tracks taken pieces to put them back after calaculation
        Piece c1 = null;
        Piece wc1 = null;
        //Tracks where the player is most likely to move
        int wm1val = -1;
        int wm1x = 0;
        int wm1y = 0;
        
        //Variables to determine worth of second move
        int m2val = -1;
        int m2sx = 0;
        int m2sy = 0;
        int m2ex = 0;
        int m2ey = 0;
        //Tracks taken pieces to put them back after calaculation
        Piece c2 = null;
        Piece wc2 = null;
        //Tracks where the player is most likely to move
        int wm2val = -1;
        int wm2sx = 0;
        int wm2sy = 0;
        int wm2ex = 0;
        int wm2ey = 0;
        
        //Variables to determine worth of third move
        int m3val = -1;
        int m3sx = 0;
        int m3sy = 0;
        int m3ex = 0;
        int m3ey = 0;
        //Tracks taken pieces to put them back after calaculation
        Piece c3 = null;
        Piece wc3 = null;
        //Tracks where the player is most likely to move
        int wm3val = -1;
        int wm3sx = 0;
        int wm3sy = 0;
        int wm3ex = 0;
        int wm3ey = 0;

        //Moving to an empty space is a neutral exchange.
        //Prioritized over bad moves
        if(position[m1x][m1y] == null && m1val < 0)
        {
           m1val = 0;
        }//End of null check
        /*If the tile is occupied, the move is 
        evaluated based on that pieces value.
        canMove negates moving onto allied tiles, 
        don't need to check color.*/
        else
        {

            switch(position[m1x][m1y].name)
            {
                //Makes capturing a King the ideal move 100% of the time
                case "King":
                    if(m1val < 100){
                        m1val = 100;
                    }//End of value check
                case "Queen":
                    if(m1val < 10){
                        m1val = 10;
                    }//End of value check
                case "Rook":
                    if(m1val < 8){
                        m1val = 8;
                    }//End of value check
                case "Bishop":
                    if(m1val < 5){
                        m1val = 5;
                    }//End of value check
                case "Knight":
                    if(m1val < 3){
                        m1val = 3;
                    }//End of value check
                case "Pawn":
                    if(m1val < 1){
                        m1val = 1;
                    }//End of value check
            }//End of piece value evaluation
            
        }//End of capture check

        //Moves the pieces to calculate the next move
        //Stores a captured piece in c1 so it can be replaced later
        if(position[m1x][m1y] == null)
        {
            position[m1x][m1y] = position[x][y];
            position[x][y] = null;
        }//End of move 1 (to empty space)
        else
        {
            c1 = position[m1x][m1y];
            position[m1x][m1y] = position[x][y];
            position[x][y] = null;
        }//End of move 1 (to occupied space)
        
        //Adds computer's move 1 value to the total value
        value += m1val;
        
        /*
        CALCULATION OF PLAYER'S MOST LIKELY MOVE 1
        */
        
        //Calculates the most valuable move from the piece's starting position
        for(int x1 = 0 ; x1 < position.length ; x1++){
            for(int y1 = 0 ; y1 < position[x1].length ; y1++){
                if(position[x1][y1] != null
                        && position[x1][y1].color)
                {
                    for(int x2 = 0 ; x2 < position.length ; x2++){
                        for(int y2 = 0 ; y2 < position[x1].length ; y2++){
                            if(position[x1][y1].canMove(x1, y1, x2, y2))
                            {

                                //Moving to an empty space is a neutral exchange.
                                //Prioritized over bad moves
                                if(position[x2][y2] == null && wm1val < 0)
                                {
                                   wx = x1;
                                   wy = y1;
                                   wm1x = x2;
                                   wm1y = y2;
                                   wm1val = 0;
                                }//End of null check
                                /*If the tile is occupied, the move is 
                                evaluated based on that pieces value.
                                canMove negates moving onto allied tiles, 
                                don't need to check color.*/
                                else if(position[x2][y2] != null)
                                {

                                    switch(position[x2][y2].name)
                                    {
                                        //Makes capturing a King the ideal move 100% of the time
                                        case "King":
                                            if(wm1val < 100){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 100;
                                            }//End of value check
                                        case "Queen":
                                            if(wm1val < 10){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 10;
                                            }//End of value check
                                        case "Rook":
                                            if(wm1val < 8){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 8;
                                            }//End of value check
                                        case "Bishop":
                                            if(wm1val < 5){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 5;
                                            }//End of value check
                                        case "Knight":
                                            if(wm1val < 3){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 3;
                                            }//End of value check
                                        case "Pawn":
                                            if(wm1val < 1){
                                                wx = x1;
                                                wy = y1;
                                                wm1x = x2;
                                                wm1y = y2;
                                                wm1val = 1;
                                            }//End of value check
                                    }//End of piece value evaluation

                                }//End of capture check

                            }//End of canMove check
                        }//End of wm1y2 loop
                    }//End of wm1x2 loop
                }//End of color check
            }//End of wm1y1 loop
        }//End of wm1x1 loop

        //Moves the pieces to calculate the next move
        //Stores a captured piece in c1 so it can be replaced later
        if(position[wm1x][wm1y] == null)
        {
            position[wm1x][wm1y] = position[wx][wy];
            position[wx][wy] = null;
        }//End of move 1 (to empty space)
        else
        {
            wc1 = position[wm1x][wm1y];
            position[wm1x][wm1y] = position[wx][wy];
            position[wx][wy] = null;
        }//End of white move 1 (to occupied space)
        
        //Removes the player's move 1 value from the total value
        value -= wm1val;
        
        /*
        CALCULATION OF COMPUTER PLAYER'S BEST MOVE 2
        */
        
        //Calculates the most valuable move from the piece's starting position
        for(int x1 = 0 ; x1 < position.length ; x1++){
            for(int y1 = 0 ; y1 < position[x1].length ; y1++){
                if(position[x1][y1] != null
                        && !position[x1][y1].color)
                {
                    for(int x2 = 0 ; x2 < position.length ; x2++){
                        for(int y2 = 0 ; y2 < position[x2].length ; y2++){

                            if(position[x1][y1].canMove(x1, y1, x2, y2))
                            {

                                //Moving to an empty space is a neutral exchange.
                                //Prioritized over bad moves
                                if(position[x2][y2] == null && m2val < 0)
                                {
                                    m2sx = x1;
                                    m2sy = y1;
                                    m2ex = x2;
                                    m2ey = y2;
                                    m2val = 0;
                                }//End of null check
                                /*If the tile is occupied, the move is 
                                evaluated based on that pieces value.
                                canMove negates moving onto allied tiles, 
                                don't need to check color.*/
                                else if(position[x2][y2] != null)
                                {

                                    switch(position[x2][y2].name)
                                    {
                                        //Makes capturing a King the ideal move 100% of the time
                                        case "King":
                                            if(m2val < 100){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 100;
                                            }//End of value check
                                        case "Queen":
                                            if(m2val < 10){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 10;
                                            }//End of value check
                                        case "Rook":
                                            if(m2val < 8){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 8;
                                            }//End of value check
                                        case "Bishop":
                                            if(m2val < 5){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 5;
                                            }//End of value check
                                        case "Knight":
                                            if(m2val < 3){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 3;
                                            }//End of value check
                                        case "Pawn":
                                            if(m2val < 1){
                                                m2sx = x1;
                                                m2sy = y1;
                                                m2ex = x2;
                                                m2ey = y2;
                                                m2val = 1;
                                            }//End of value check
                                    }//End of piece value evaluation

                                }//End of capture check

                            }//End of canMove check

                        }//End of y2 loop
                    }//End of x2 loop
                }//End of color check
            }//End of y1 loop
        }//End of x1 loop

        //Moves the pieces to calculate the next move
        //Stores a captured piece in c1 so it can be replaced later
        if(position[m2ex][m2ey] == null)
        {
            position[m2ex][m2ey] = position[m2sx][m2sy];
            position[m2sx][m2sy] = null;
        }//End of move 2 (to empty space)
        else
        {
            c2 = position[m2ex][m2ey];
            position[m2ex][m2ey] = position[m2sx][m2sy];
            position[m2sx][m2sy] = null;
        }//End of move 2 (to occupied space)
        
        //Adds computer's move 2 value to the total value
        value += m2val;
        
        /*
        CALCULATION OF PLAYER'S MOST LIKELY MOVE 2
        */
        
        //Calculates the most valuable move from the piece's starting position
        for(int x1 = 0 ; x1 < position.length ; x1++){
            for(int y1 = 0 ; y1 < position[x1].length ; y1++){
                if(position[x1][y1] != null
                        && position[x1][y1].color)
                {
                    for(int x2 = 0 ; x2 < position.length ; x2++){
                        for(int y2 = 0 ; y2 < position[x1].length ; y2++){
                            if(position[x1][y1].canMove(x1, y1, x2, y2))
                            {

                                //Moving to an empty space is a neutral exchange.
                                //Prioritized over bad moves
                                if(position[x2][y2] == null && wm2val < 0)
                                {
                                   wm2sx = x1;
                                   wm2sy = y1;
                                   wm2ex = x2;
                                   wm2ey = y2;
                                   wm2val = 0;
                                }//End of null check
                                /*If the tile is occupied, the move is 
                                evaluated based on that pieces value.
                                canMove negates moving onto allied tiles, 
                                don't need to check color.*/
                                else if(position[x2][y2] != null)
                                {

                                    switch(position[x2][y2].name)
                                    {
                                        //Makes capturing a King the ideal move 100% of the time
                                        case "King":
                                            if(wm2val < 100){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 100;
                                            }//End of value check
                                        case "Queen":
                                            if(wm2val < 10){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 10;
                                            }//End of value check
                                        case "Rook":
                                            if(wm2val < 8){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 8;
                                            }//End of value check
                                        case "Bishop":
                                            if(wm2val < 5){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 5;
                                            }//End of value check
                                        case "Knight":
                                            if(wm2val < 3){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 3;
                                            }//End of value check
                                        case "Pawn":
                                            if(wm2val < 1){
                                                wm2sx = x1;
                                                wm2sy = y1;
                                                wm2ex = x2;
                                                wm2ey = y2;
                                                wm2val = 1;
                                            }//End of value check
                                    }//End of piece value evaluation

                                }//End of capture check

                            }//End of canMove check
                        }//End of y2 loop
                    }//End of x2 loop
                }//End of color check
            }//End of y1 loop
        }//End of x1 loop

        //Moves the pieces to calculate the next move
        //Stores a captured piece in c1 so it can be replaced later
        if(position[wm2ex][wm2ey] == null)
        {
            position[wm2ex][wm2ey] = position[wm2sx][wm2sy];
            position[wm2sx][wm2sy] = null;
        }//End of move 2 (to empty space)
        else
        {
            wc2 = position[wm2ex][wm2ey];
            position[wm2ex][wm2ey] = position[wm2sx][wm2sy];
            position[wm2sx][wm2sy] = null;
        }//End of white move 1 (to occupied space)
        
        //Removes the player's move 2 value from the total value
        value -= wm2val;
        
        /*
        CALCULATION OF COMPUTER PLAYER'S BEST MOVE 3
        */
        
        //Calculates the most valuable move from the piece's starting position
        for(int x1 = 0 ; x1 < position.length ; x1++){
            for(int y1 = 0 ; y1 < position[x1].length ; y1++){
                if(position[x1][y1] != null
                        && !position[x1][y1].color)
                {
                    for(int x2 = 0 ; x2 < position.length ; x2++){
                        for(int y2 = 0 ; y2 < position[x2].length ; y2++){

                            if(position[x1][y1].canMove(x1, y1, x2, y2))
                            {

                                //Moving to an empty space is a neutral exchange.
                                //Prioritized over bad moves
                                if(position[x2][y2] == null && m3val < 0)
                                {
                                    m3sx = x1;
                                    m3sy = y1;
                                    m3ex = x2;
                                    m3ey = y2;
                                    m3val = 0;
                                }//End of null check
                                /*If the tile is occupied, the move is 
                                evaluated based on that pieces value.
                                canMove negates moving onto allied tiles, 
                                don't need to check color.*/
                                else if(position[x2][y2] != null)
                                {

                                    switch(position[x2][y2].name)
                                    {
                                        //Makes capturing a King the ideal move 100% of the time
                                        case "King":
                                            if(m3val < 100){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 100;
                                            }//End of value check
                                        case "Queen":
                                            if(m3val < 10){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 10;
                                            }//End of value check
                                        case "Rook":
                                            if(m3val < 8){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 8;
                                            }//End of value check
                                        case "Bishop":
                                            if(m3val < 5){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 5;
                                            }//End of value check
                                        case "Knight":
                                            if(m3val < 3){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 3;
                                            }//End of value check
                                        case "Pawn":
                                            if(m3val < 1){
                                                m3sx = x1;
                                                m3sy = y1;
                                                m3ex = x2;
                                                m3ey = y2;
                                                m3val = 1;
                                            }//End of value check
                                    }//End of piece value evaluation

                                }//End of capture check

                            }//End of canMove check

                        }//End of y2 loop
                    }//End of x2 loop
                }//End of color check
            }//End of y1 loop
        }//End of x1 loop

        //Moves the pieces to calculate the next move
        //Stores a captured piece in c1 so it can be replaced later
        if(position[m3ex][m3ey] == null)
        {
            position[m3ex][m3ey] = position[m3sx][m3sy];
            position[m3sx][m3sy] = null;
        }//End of move 3 (to empty space)
        else
        {
            c3 = position[m3ex][m3ey];
            position[m3ex][m3ey] = position[m3sx][m3sy];
            position[m3sx][m3sy] = null;
        }//End of move 3 (to occupied space)
        
        //Adds computer's move 3 value to the total value
        value += m3val;
        
        /*
        CALCULATION OF PLAYER'S MOST LIKELY MOVE 3
        */
        
        //Calculates the most valuable move from the piece's starting position
        for(int x1 = 0 ; x1 < position.length ; x1++){
            for(int y1 = 0 ; y1 < position[x1].length ; y1++){
                if(position[x1][y1] != null
                        && position[x1][y1].color)
                {
                    for(int x2 = 0 ; x2 < position.length ; x2++){
                        for(int y2 = 0 ; y2 < position[x1].length ; y2++){
                            if(position[x1][y1].canMove(x1, y1, x2, y2))
                            {

                                //Moving to an empty space is a neutral exchange.
                                //Prioritized over bad moves
                                if(position[x2][y2] == null && wm3val < 0)
                                {
                                   wm3sx = x1;
                                   wm3sy = y1;
                                   wm3ex = x2;
                                   wm3ey = y2;
                                   wm3val = 0;
                                }//End of null check
                                /*If the tile is occupied, the move is 
                                evaluated based on that pieces value.
                                canMove negates moving onto allied tiles, 
                                don't need to check color.*/
                                else if(position[x2][y2] != null)
                                {

                                    switch(position[x2][y2].name)
                                    {
                                        //Makes capturing a King the ideal move 100% of the time
                                        case "King":
                                            if(wm3val < 100){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 100;
                                            }//End of value check
                                        case "Queen":
                                            if(wm3val < 10){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 10;
                                            }//End of value check
                                        case "Rook":
                                            if(wm3val < 8){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 8;
                                            }//End of value check
                                        case "Bishop":
                                            if(wm3val < 5){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 5;
                                            }//End of value check
                                        case "Knight":
                                            if(wm3val < 3){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 3;
                                            }//End of value check
                                        case "Pawn":
                                            if(wm3val < 1){
                                                wm3sx = x1;
                                                wm3sy = y1;
                                                wm3ex = x2;
                                                wm3ey = y2;
                                                wm3val = 1;
                                            }//End of value check
                                    }//End of piece value evaluation

                                }//End of capture check

                            }//End of canMove check
                        }//End of y2 loop
                    }//End of x2 loop
                }//End of color check
            }//End of y1 loop
        }//End of x1 loop

        //Moves the pieces to calculate the next move
        //Stores a captured piece in wc3 so it can be replaced later
        if(position[wm3ex][wm3ey] == null)
        {
            position[wm3ex][wm3ey] = position[wm3sx][wm3sy];
            position[wm3sx][wm3sy] = null;
        }//End of move 3 (to empty space)
        else
        {
            wc3 = position[wm3ex][wm3ey];
            position[wm3ex][wm3ey] = position[wm3sx][wm3sy];
            position[wm3sx][wm3sy] = null;
        }//End of white move 3 (to occupied space)
        
        //Removes the player's move 3 value from the total value
        value -= wm3val;
        
        //Undoes all hypothetical moves
        if(wc3 == null)
        {
            position[wm3sx][wm3sy] = position[wm3ex][wm3ey];
            position[wm3ex][wm3ey] = null;
        } else {
            position[wm3sx][wm3sy] = position[wm3ex][wm3ey];
            position[wm3ex][wm3ey] = wc3;
        }//End of white move 3 reset
        if(c3 == null)
        {
            position[m3sx][m3sy] = position[m3ex][m3ey];
            position[m3ex][m3ey] = null;
        } else {
            position[m3sx][m3sy] = position[m3ex][m3ey];
            position[m3ex][m3ey] = c3;
        }//End of black move 3 reset
        if(wc2 == null)
        {
            position[wm2sx][wm2sy] = position[wm2ex][wm2ey];
            position[wm2ex][wm2ey] = null;
        } else {
            position[wm2sx][wm2sy] = position[wm2ex][wm2ey];
            position[wm2ex][wm2ey] = wc2;
        }//End of white move 2 reset
        if(c2 == null)
        {
            position[m2sx][m2sy] = position[m2ex][m2ey];
            position[m2ex][m2ey] = null;
        } else {
            position[m2sx][m2sy] = position[m2ex][m2ey];
            position[m2ex][m2ey] = c2;
        }//End of black move 2 reset
        if(wc1 == null)
        {
            position[wx][wy] = position[wm1x][wm1y];
            position[wm1x][wm1y] = null;
        } else {
            position[wx][wy] = position[wm1x][wm1y];
            position[wm1x][wm1y] = wc1;
        }//End of white move 1 reset
        if(c1 == null)
        {
            position[x][y] = position[m1x][m1y];
            position[m1x][m1y] = null;
        } else {
            position[x][y] = position[m1x][m1y];
            position[m1x][m1y] = c1;
        }//End of white move 1 reset
        
        return value;
        
    }//End of moveAI
    
    //Defines initial values of position[][] to reduce clutter in main
    private static void initPieces()
    {
        
        //White piece definitions
        position[0][0] = new Rook(true);
        position[1][0] = new Knight(true);
        position[2][0] = new Bishop(true);
        position[3][0] = new Queen(true);
        position[4][0] = new King(true);
        position[5][0] = new Bishop(true);
        position[6][0] = new Knight(true);
        position[7][0] = new Rook(true);
        //White Pawn definitions
        for(int i = 0;i<position[1].length;i++)
        {
            position[i][1] = new Pawn(true);
        }//End of White Pawn definition loop
        
        
        //Black piece definitions
        position[0][7] = new Rook(false);
        position[1][7] = new Knight(false);
        position[2][7] = new Bishop(false);
        position[3][7] = new Queen(false);
        position[4][7] = new King(false);
        position[5][7] = new Bishop(false);
        position[6][7] = new Knight(false);
        position[7][7] = new Rook(false);
        //Black Pawn definitions
        for(int i = 0;i<position[6].length;i++)
        {
            position[i][6] = new Pawn(false);
        }//End of Black Pawn definition loop
        
    }//End of initPieces
    
}//End of class
