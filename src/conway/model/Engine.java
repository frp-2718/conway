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

/**
 * The class <code>Engine</code> represents the usable part of the model,
 * which computes each generation.
 * @author frp
 * @version 1.0
 */
public class Engine {
    private World[] w = new World[2];
    private int genCounter;
    private boolean change = true;

    /**
     * Creates a new Engine by setting up two empty worlds and initializing
     * the generation counter.
     * @param height the height of the world
     * @param width the width of the world
     */
    public Engine(int height, int width, String pattern) {
        this.w[0] = new World(height, width);
        this.w[1] = new World(height, width);
        this.genCounter = 0;
        this.w[0].init(pattern);
    }
    
    /**
     * @return the active world
     */
    public World getCurrent() {
        return w[genCounter%2];
    }
    
    /**
     * @return the current generation number
     */
    public int getGeneration() {
        return this.genCounter;
    }
    
    /**
     * Computes the next state of the world and returns it.
     * @return the next generation
     */
    public World getNext() {
        nextGeneration();
        return getCurrent();
    }
    
    /**
     * Not used.
     * @return the previous state of the world
     */
    public World getPrevious() {
        return w[1-(genCounter%2)];
    }
    
    /**
     * @return false if the current state of the world is identical to the
     * previous state, true else
     */
    public boolean hasChanged() {
        return this.change;
    }

    private void nextGeneration() {
        int current = genCounter % 2;
        int newWorld = 1 - current;
        World grid = w[current];
        int row = grid.getHeight();
        int col = grid.getWidth();
        genCounter++;
        change = false;
        for (int i=0; i < row; i++) {
            for (int j=0; j < col; j++) {
                int sum = grid.sumOfNeighbors(i, j);
                if (sum == 3) {
                    if (grid.getCell(i, j).equals(Cell.DEAD)) {
                        this.change = true;
                        this.w[newWorld].setCell(i, j, Cell.BORN);
                    } else {
                        this.w[newWorld].setCell(i, j, Cell.ALIVE);
                    }
                } else if (sum == 2) {
                    if (grid.getCell(i, j).equals(Cell.BORN))
                        this.w[newWorld].setCell(i, j, Cell.ALIVE);
                    else
                        this.w[newWorld].setCell(i, j, grid.getCell(i, j));
                } else {
                    this.w[newWorld].setCell(i, j, Cell.DEAD);
                    if (grid.getCell(i, j).equals(Cell.BORN) ||
                            grid.getCell(i, j).equals(Cell.ALIVE))
                        this.change = true;
                }
            }
        }
    }
}

