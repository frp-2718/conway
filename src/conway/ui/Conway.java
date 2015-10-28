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
 * liability, whether in an action of contract, tort or otherwise, arising from,
 * out of or in connection with the software or the use or other dealings in
 * the Software.
 */

package conway.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import conway.model.Engine;

// ...
public class Conway {
    private Engine engine; 
    private JFrame window;
    private JPanel panel;
    private Board board;
    private JButton startButton;
    private JLabel counter;
    private JLabel status;
    private int delay = 500;
    ActionListener animation;
    Timer t;
    private boolean paused = true;
    private boolean stable = false;
    
    public Conway() {
        engine = new Engine(50, 50, "random");
        window = new JFrame("Conway's game of life");
        panel = new JPanel();
        board = new Board(engine.getCurrent());
        startButton = new JButton("Start");
        counter = new JLabel("0");
        status = new JLabel("Paused");
        arrange();
        activate();
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        pause();
    }

    private void activate() {
        animation = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (engine.hasChanged()) {
                    board.setWorld(engine.getNext());
                    counter.setText("" + engine.getGeneration());
                } else {
                    setStable();
                }
            }
        };
        t = new Timer(delay, animation);
        t.setInitialDelay(0);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused) {
                    runLife();
                } else if (stable) {
                    engine = new Engine(50, 50, "random");
                    board.setWorld(engine.getCurrent());
                    counter.setText("" + 0);
                    stable = false;
                    pause();
                } else {
                    pause();
                }
            }
        });
    }

    private void arrange() {
        window.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(1, 3));
        status.setHorizontalAlignment(JLabel.CENTER);
        counter.setHorizontalAlignment(JLabel.CENTER);
        panel.add(status);
        panel.add(startButton);
        panel.add(counter);
        window.add(board, BorderLayout.CENTER);
        window.add(panel, BorderLayout.SOUTH);
        window.pack();
    }

    private void runLife() {
        paused = false;
        status.setForeground(Color.green.darker());
        status.setText("Running");
        startButton.setText("Pause");
        t.start();
    }

    private void pause() {
        paused = true;
        t.stop();
        status.setForeground(Color.orange.darker());
        status.setText("Paused");
        startButton.setText("Start");
    }

    private void setStable() {
        stable = true;
        status.setForeground(Color.red.darker());
        status.setText("Stable");
        startButton.setText("Restart");
        t.stop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Conway();
            }
        });
    }
}

