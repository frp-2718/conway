/* Copyright (c) 2013, 2014 frp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * The Software is provided "as is", without warranty of any kind, express or
 * implied, including but not limited to the warranties of merchantability,
 * fitness for a particular purpose and noninfringement. In no event shall the
 * authors or copyright holders be liable for any claim, damages or other
 * liability, whether in an action of contract, tort or otherwise, arising
 * from, out of or in connection with the software or the use or other dealings
 * in the Software.
 */

package conway.model;

import java.util.Random;

/**
 * The class <code>World</code> represents the board where the cells of the
 * game of life develop.
 * <p>The board is <strong>not</strong> toric.</p>
 * @author frp
 * @version 1.2
 */
public class World {
    private Cell[][] world;
    
    World(int height, int width) {
        world = new Cell[height][width];
        for (int i=0; i < height; i++) {
            for (int j=0; j < width; j++) {
                world[i][j] = Cell.DEAD;
            }
        }
    }

    /**
     * @return a cell state : DEAD, ALIVE or BORN
     */
    public Cell getCell(int row, int col) {
        return this.world[row][col];
    }

    /**
     * @return the height of the world, i.e. the number of lines
     */
    public int getHeight() {
        return this.world.length;
    }

    /**
     * @return the width of the world, i.e. the number of columns
     */
    public int getWidth() {
        return this.world[0].length;
    }

    void init(String config) {
        if (this.getHeight() < 30 || this.getWidth() < 30) {
            randomGen();
        }
        if (config.equals("clown")) {
            this.setCell(25, 25, Cell.ALIVE);
            this.setCell(25, 26, Cell.ALIVE);
            this.setCell(25, 24, Cell.ALIVE);
            this.setCell(26, 24, Cell.ALIVE);
            this.setCell(26, 26, Cell.ALIVE);
            this.setCell(27, 24, Cell.ALIVE);
            this.setCell(27, 26, Cell.ALIVE);
        } else if (config.equals("fixed")) {
            this.setCell(25, 25, Cell.ALIVE);
            this.setCell(25, 26, Cell.ALIVE);
            this.setCell(24, 25, Cell.ALIVE);
            this.setCell(24, 26, Cell.ALIVE);
        } else {
            randomGen();
        }
    }

    void setCell(int row, int col, Cell c) {
        this.world[row][col] = c;
    }

    int sumOfNeighbors(int row, int col) {
        int sum = 0;
        for (int i=row-1; i <= row+1; i++) {
            for (int j=col-1; j <= col+1; j++) {
                if (isInside(i, j) && (i != row || j != col)) {
                    sum += this.getCell(i, j).getValue();
                }
            }
        }
        return sum;
    }

    private boolean isInside(int row, int col) {
        return (row >= 0 && row < this.getHeight()) &&
               (col >= 0 && col < this.getWidth());
    }

    private void randomGen() {
        Random r = new Random();
        for (int i=0; i < this.getHeight(); i++) {
            for (int j=0; j < this.getWidth(); j++) {
                if (r.nextInt(2) == 1)
                    this.setCell(i, j, Cell.ALIVE);
            }
        }
    }
}

