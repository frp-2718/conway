/* Copyright (c) 2013, frp
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
 * liability, whether in an action of contract, tort or otherwise, arising from,
 * out of or in connection with the software or the use or other dealings in
 * the Software.
 */

package conway.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import conway.model.Cell;
import conway.model.World;

class Board extends JPanel {
    private World w;

    Board(World w) {
        this.w = w;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10*w.getHeight(), 10*w.getWidth());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int y=0; y < w.getWidth(); y++) {
            for (int x=0; x < w.getHeight(); x++) {
                if (w.getCell(y, x).equals(Cell.BORN)) {
                    g.setColor(new Color(102, 102, 102));
                    g.fillOval(x*10, y*10, 10, 10);
                } else if (w.getCell(y, x).equals(Cell.ALIVE)) {
                    g.setColor(Color.black);
                    g.fillOval(x*10, y*10, 10, 10);
                }
            }
        }
    }

    void setWorld(World w) {
        this.w = w;
        repaint();
    }
}

