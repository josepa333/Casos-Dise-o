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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    
    public MainWindowController() {
        caretaker = new TextCareTaker();
        generalText = new Text("");
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
               PDFTextProcessor pdf = new PDFTextProcessor();
               pdf.processText("<texto> Este es un ejemplo de lo crack que soy y solo para demostrarlo <RED>BOOOM, color</RED> mas?, ok <BLUE>BOOM</BLUE> gracias :) </texto>", "Jose");
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
               break;
//            String s =view.TextArea.getSelectedText();

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
