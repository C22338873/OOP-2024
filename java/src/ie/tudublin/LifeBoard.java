package ie.tudublin;

import java.util.Map;

import processing.core.PApplet;

public class LifeBoard {
    
    boolean[][] board;
    boolean[][] next;

    int rows;
    int cols;

    float cellWidth;
    float cellHeight;
    PApplet p;

    public LifeBoard(int rows, int cols, PApplet p)
    {
        this.rows = rows;
        this.cols = cols;
        this.p = p; 
        board = new boolean[rows][cols];
        cellWidth = p.width / (float) cols;
        cellHeight = p.height / (float) rows;
        
    }



    void randomize()
    {
        for(int row = 0 ; row < rows ; row ++)
        {
            for(int col = 0 ; col < cols ; col ++)
            {
                float dice = p.random(1.0f);
                board[row][col] = (dice < 0.5f);                
            }
        }
    }


    public void update()
    {
        // The rules are!
        // If the cell is alive then
        // If it has < 2 alive neighbours, it dies due to lonelyness
        // If it has > 3 alive neighbours, it dies due to overcrouding
        // If it has 2-3 live neighbours, it survives
        // If the cell is dead, then it comes to life 
        // in the next generation if it has exactly 3 
        // live neighbours

        // Write a nested for loop to update the board each frame
        // Read board, write to next
        
        // At the end, swap them like this:
        // boolean[][] temp = board;
        // board = next;
        // next = temp;
        next = new boolean[rows][cols];
        for(int row = 0 ; row < rows ; row ++)
        {
            for(int col = 0 ; col < cols ; col ++)
            {
                int aliveNeighbours = countCells(row, col);
                if (board[row][col]) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        next[row][col] = false;
                    } else {
                        next[row][col] = true;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        next[row][col] = true;
                    }
                }
            }
        }
        boolean[][] temp = board;
        board = next;
        next = temp;
    }

    public void setCell(int row, int col, boolean value)
    {
        if (row > 0 && col > 0 && row < rows && col < cols)
        {
            board[row][col] = value;
        }
    }

    public boolean getCell(int row, int col)
    {
        if (row > 0 && col > 0 && row < rows && col < cols)
        {
            return board[row][col];
        }
        return false;
    }

    public int countCells(int row, int col)
    {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0) && getCell(row + i, col + j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void render()
    {
        p.colorMode(PApplet.HSB, rows, 100, 100);
        for(int row = 0 ; row < rows ; row ++)
        {
            for(int col = 0 ; col < cols ; col ++)
            {
                float x = p.map(col, 0, cols, 0, p.width);
                float y = row * cellHeight;
                p.stroke(0);
                if (board[row][col])
                {
                    p.fill(row, 100, 100);
                }
                else
                {
                    p.noFill();
                }
                p.rect(x, y, cellWidth, cellHeight);
            }
        }
        
    }

    

}
