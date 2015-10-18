//******************************************************************************
//
// File:    NimUI.java
// Package: ---
// Unit:    Class NimUI.java
//
// This Java source file is copyright (C) 2015 by Alan Kaminsky. All rights
// reserved. For further information, contact the author, Alan Kaminsky, at
// ark@cs.rit.edu.
//
// This Java source file is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by the Free
// Software Foundation; either version 3 of the License, or (at your option) any
// later version.
//
// This Java source file is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
// details.
//
// You may obtain a copy of the GNU General Public License on the World Wide Web
// at http://www.gnu.org/licenses/gpl.html.
//
//******************************************************************************


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.io.IOException;

/**
 * Class NimUI provides the user interface for the Nim network game.
 *
 * @author  Alan Kaminsky
 * @version 07-Oct-2015
 */
public class NimUI implements ModelListener
{

// Interface for a listener for HeapPanel events.

    private static interface HeapListener
    {
        // Report that markers are to be removed from a heap.
        public void removeObjects
        (int id,          // Heap panel ID
         int numRemoved); // Number of markers to be removed
    }

// Class for a Swing widget displaying a heap of markers.

    private static class HeapPanel
            extends JPanel
    {
        private static final int W = 50;
        private static final int H = 30;
        private static final Color FC = Color.RED;
        private static final Color OC = Color.BLACK;

        private int id;
        private int maxCount;
        private int count;
        private boolean isEnabled;
        private HeapListener listener;


        // Construct a new heap panel.
        public HeapPanel
        (int id,       // Heap panel ID
         int maxCount) // Maximum number of markers
        {
            this.id = id;
            this.maxCount = maxCount;
            this.count = maxCount;
            this.isEnabled = true;
            Dimension dim = new Dimension (W, maxCount*H);
            setMinimumSize (dim);
            setMaximumSize (dim);
            setPreferredSize (dim);
            addMouseListener (new MouseAdapter()
            {
                public void mouseClicked (MouseEvent e)
                {
                    if (isEnabled && listener != null)
                    {
                        int objClicked = maxCount - 1 - e.getY()/H;
                        int numRemoved = count - objClicked;
                        if (numRemoved > 0)
                            listener.removeObjects (id, numRemoved);
                    }
                }
            });
        }

        // Set this heap panel's listener.
        public void setListener
        (HeapListener listener)
        {
            this.listener = listener;
        }

        // Set the number of markers in this heap panel.
        public void setCount
        (int count) // Number of markers
        {
            count = Math.max (0, Math.min (count, maxCount));
            if (this.count != count)
            {
                this.count = count;
                repaint();
            }
        }

        // Enable or disable this heap panel.
        public void setEnabled
        (boolean enabled) // True to enable, false to disable
        {
            if (this.isEnabled != enabled)
            {
                this.isEnabled = enabled;
                repaint();
            }
        }

        // Paint this heap panel.
        protected void paintComponent
        (Graphics g) // Graphics context
        {
            super.paintComponent (g);

            // Clone graphics context.
            Graphics2D g2d = (Graphics2D) g.create();

            // Turn on antialiasing.
            g2d.setRenderingHint
                    (RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

            // For drawing markers.
            Ellipse2D.Double ellipse = new Ellipse2D.Double();
            ellipse.width = W - 2;
            ellipse.height = H - 2;
            ellipse.x = 1;

            // If enabled, draw filled markers.
            if (isEnabled)
            {
                g2d.setColor (FC);
                for (int i = 0; i < count; ++ i)
                {
                    ellipse.y = (maxCount - 1 - i)*H + 1;
                    g2d.fill (ellipse);
                }
            }

            // If disabled, draw outlined markers.
            else
            {
                g2d.setColor (OC);
                for (int i = 0; i < count; ++ i)
                {
                    ellipse.y = (maxCount - 1 - i)*H + 1;
                    g2d.draw (ellipse);
                }
            }
        }


    }

// Hidden data members.

    private static final int NUMHEAPS = 3;
    private static final int NUMOBJECTS = 5;
    private static final int GAP = 10;
    private static final int COL = 10;

    private JFrame frame;
    private HeapPanel[] heapPanel;
    private JTextField myNameField;
    private JTextField theirNameField;
    private JTextField whoWonField;
    private JButton newGameButton;
    private ViewListener viewListener;
    private int myId;
    private String myName;
    private String theirName;
// Hidden constructors.

    /**
     * Construct a new Nim UI.
     */
    private NimUI
    (String name)
    {
        myName = name;
        frame = new JFrame ("Nim -- " + name);
        JPanel panel = new JPanel();
        panel.setLayout (new BoxLayout (panel, BoxLayout.X_AXIS));
        frame.add (panel);
        panel.setBorder (BorderFactory.createEmptyBorder (GAP, GAP, GAP, GAP));

        heapPanel = new HeapPanel [NUMHEAPS];
        for (int h = 0; h < NUMHEAPS; ++ h)
        {
            panel.add (heapPanel[h] = new HeapPanel (h, NUMOBJECTS));
            panel.add (Box.createHorizontalStrut (GAP));
        }

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout (new BoxLayout (fieldPanel, BoxLayout.Y_AXIS));
        panel.add(fieldPanel);

        myNameField = new JTextField (COL);
        myNameField.setEditable (false);
        myNameField.setHorizontalAlignment(JTextField.CENTER);
        myNameField.setAlignmentX(0.5f);
        fieldPanel.add(myNameField);
        fieldPanel.add (Box.createVerticalStrut (GAP));

        theirNameField = new JTextField (COL);
        theirNameField.setEditable (false);
        theirNameField.setHorizontalAlignment(JTextField.CENTER);
        theirNameField.setAlignmentX(0.5f);
        fieldPanel.add(theirNameField);
        fieldPanel.add (Box.createVerticalStrut (GAP));

        whoWonField = new JTextField (COL);
        whoWonField.setEditable (false);
        whoWonField.setHorizontalAlignment(JTextField.CENTER);
        whoWonField.setAlignmentX(0.5f);
        fieldPanel.add(whoWonField);
        fieldPanel.add (Box.createVerticalStrut (GAP));

        newGameButton = new JButton ("New Game");
        newGameButton.setAlignmentX (0.5f);
        newGameButton.setFocusable(false);
        fieldPanel.add(newGameButton);

        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stop();
                System.exit(0);
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });



    }

// Exported operations.

    public synchronized void setViewListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    /**
     * An object holding a reference to a Nim UI.
     */
    private static class UIRef
    {
        public NimUI ui;
    }

    /**
     * Construct a new Nim UI.
     */
    public static NimUI create
    (String name)
    {
        final UIRef ref = new UIRef();
        onSwingThreadDo (new Runnable()
        {
            public void run()
            {
                ref.ui = new NimUI (name);
            }
        });
        return ref.ui;
    }

    /**
     * Sent to a client when a game session has been joined
     * @param id - id of the player
     * @throws IOException
     */
    public void id(int id)throws IOException{
        setId(id);
    }

    /**
     * set the name and id to a player
     * @param id - id of the player
     * @param name - name of the player
     * @throws IOException
     */
    public void name(int id, String name)throws IOException{
        if (!isMe(id)) theirName = name;
    }

    /**
     * reports the score of a player
     * @param id - id of the player
     * @param score - score of the player
     * @throws IOException
     */
    public void score(int id, int score)throws IOException{
        System.out.println(id+" "+score);
        onSwingThreadDo (new Runnable() {
            public void run() {
                if (isMe(id)) {
                    myNameField.setText(myName+" = "+score);
                }else {
                    theirNameField.setText(theirName + " = " + score);
                }
            }
        });
    }

    /**
     * number of markers in one of the heaps
     * @param heap - heap to the markers
     * @param markers - number of markers in heap
     * @throws IOException
     */
    public void heap(int heap, int markers)throws IOException{
        onSwingThreadDo (new Runnable() {
            public void run() {
                heapPanel[heap].setCount(markers);
            }
        });
    }

    /**
     * what clients turn it is.
     * @param i - id of the client
     * @throws IOException
     */
    public void turn(int i)throws IOException{
        onSwingThreadDo (new Runnable() {
            public void run() {
                if(isMe(i)){
                    for(int i = 0; i<heapPanel.length;++i)heapPanel[i].setEnabled(true);
                }else{
                    for(int i = 0; i<heapPanel.length;++i)heapPanel[i].setEnabled(false);
                }
            }
        });
    }

    /**
     * report a client win
     * @param i - id of the client
     * @throws IOException
     */
    public void win(int i)throws IOException{
        onSwingThreadDo (new Runnable() {
            public void run() {
                if(isMe(i)){
                    whoWonField.setText(myName+" wins!");
                }else{
                    whoWonField.setText(theirName+" wins!");
                }
            }
        });
    }

    /**
     *
     * @throws IOException
     */
    public void quit()throws IOException{
        onSwingThreadDo (new Runnable() {
            public void run() {
                System.exit(0);
            }
        });
    }


    public void join(String name){
        try {
            viewListener.join(name);
        }catch(IOException ioe){
            ioError();
        }
    }

// Hidden operations.
    private boolean isMe(int id){
        return this.myId == id;
    }
    private void setId(int id){
        this.myId = id;
    }
    private void stop(){
        try{
            viewListener.quit();
            System.exit(0);
        }catch(IOException ioe){
            ioError();
        }
    }

    private void newGame() {
        try {
            viewListener.newGame();
        } catch (IOException ioe) {
            ioError();
        }
    }




    /**
     * Execute the given runnable object on the Swing thread.
     */
    private static void onSwingThreadDo
    (Runnable task)
    {
        try
        {
            SwingUtilities.invokeAndWait(task);
        }
        catch (Throwable exc)
        {
            exc.printStackTrace (System.err);
            System.exit(1);
        }
    }
    private void ioError(){
        JOptionPane.showMessageDialog(frame
                ,"IO error when sending message to server"
                ,"IO Error"
                ,JOptionPane.ERROR_MESSAGE);
        System.exit (0);
    }

}
