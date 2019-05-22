/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.IStrategy;
import Model.PDFTextProcessor;
import Model.Text;
import Model.TextCareTaker;
import Model.TextMemento;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jose pablo
 */
public class MainWindowController implements KeyListener, ActionListener{

    private MainWindow view;
    private IStrategy textProcessor;
    private TextCareTaker caretaker;
    private Text generalText;
    private String tmpString;
    private Clipboard c;
    private StringSelection testData;
    
    public MainWindowController() {
        caretaker = new TextCareTaker();
        generalText = new Text("");
        view = new MainWindow();
        view.show();
         c = Toolkit.getDefaultToolkit().getSystemClipboard();
        
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
    }
    
    public void keyPressed(KeyEvent event){
        caretaker.undoWithoutRedo();
        generalText.setState(view.TextArea.getText());
        caretaker.addNewMemento(generalText.createMemento());
        System.out.println(caretaker.getTotalMementos());
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
               TextMemento previousMemento = caretaker.getPreviousMemento();
               if(previousMemento == null)
                   JOptionPane.showMessageDialog(view, "You can't undo more");
               else{
                   generalText.restoreMemento(previousMemento);
                   view.TextArea.setText(generalText.getState());
               }
               break;
           case "Redo":
               System.out.println("Redo");
               TextMemento nextMemento = caretaker.getNextMemento();
               if(nextMemento == null)
                   JOptionPane.showMessageDialog(view, "You can't redo more");
               else{
                   generalText.restoreMemento(nextMemento);
                   view.TextArea.setText(generalText.getState());
               }
               break;
           case "Copy":
               System.out.println("Copy");
               testData = new StringSelection( view.TextArea.getSelectedText() );
               c.setContents(testData, testData);
               break;
           case "Cut":
               System.out.println("Cut");//Donde lo corto? 
               testData = new StringSelection( view.TextArea.getSelectedText() );
               c.setContents(testData, testData);
               view.TextArea.replaceSelection("");
               break;
           case "Paste":
               System.out.println("Paste");//Donde lo pego? 
               Transferable t = c.getContents( null );
               try{
                   if ( t.isDataFlavorSupported(DataFlavor.stringFlavor) ){
                         Object o = t.getTransferData( DataFlavor.stringFlavor );
                         String data = (String)t.getTransferData( DataFlavor.stringFlavor );
                         view.TextArea.replaceSelection( data );
                     }
               }
               catch(UnsupportedFlavorException | IOException f){
                   System.out.println(f.getMessage());
               }
               break;
           default:
               System.out.println("Khé");
               break;
       }
    }
}
