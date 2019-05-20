/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.IStrategy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author jose pablo
 */
public class MainWindowController implements KeyListener, ActionListener, MouseListener{

    private MainWindow view;
    private IStrategy textProcessor;
    
    public MainWindowController() {
        view = new MainWindow();
        view.show();
        view.TextArea.addKeyListener(this);
        view.newFileButton.addActionListener(this);
        view.openFileButton.addActionListener(this);
        view.saveFileButton.addActionListener(this);
        view.SaveAsButton.addActionListener(this);
        view.wordHighlightButton.addActionListener(this);
        view.undoButton.addActionListener(this);
        view.RedoButton.addActionListener(this);
        view.copyButton.addActionListener(this);
        view.cutButton.addActionListener(this);
        view.pasteButton.addActionListener(this);
        view.TextArea.addMouseListener(this);
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (view.TextArea.getSelectedText() != null) { // See if they selected something 
            String s =view.TextArea.getSelectedText();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    public void keyPressed(KeyEvent event){
        System.out.println(  view.TextArea.getText());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
           case "New":
               System.out.println("New");
               break;
           case "Open":
               System.out.println("Open");
               break;
           case "Save":
               System.out.println("Save");
               break;
           case "Save as":
               System.out.println("Save as");
               break;
           case "Highlight":
               System.out.println("Highlight");
               break;
           case "Undo":
               System.out.println("Undo");
               break;
           case "Redo":
               System.out.println("Redo");
               break;
           case "Copy":
               System.out.println("Copy");
               break;
           case "Cut":
               System.out.println("Cut");
               break;
           case "Paste":
               System.out.println("Paste");
               break;
           default:
               System.out.println("Khé");
               break;
       }
    }
}
