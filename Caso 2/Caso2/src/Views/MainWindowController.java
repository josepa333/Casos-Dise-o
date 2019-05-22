/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.FormatProvider;
import Model.IStrategy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author jose pablo
 */
public class MainWindowController implements KeyListener, ActionListener, MouseListener{

    private MainWindow view;
    private FormatProvider formatProvider;
    private String path; //FilePath currrently being handled
    
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
        this.formatProvider = new FormatProvider();
        this.path = "";
    }
    public static String readFile(String path, Charset encoding) 
    throws IOException 
    {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }
    private void openFile(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            String str = "";
            jfc.setDialogTitle("Select an image");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt,xml,xsv,json", "txt", "xml","csv", "json");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                this.path = jfc.getSelectedFile().getPath();
                String[] strArray = path.split("\\.");
                String extension =  strArray[strArray.length - 1];
                formatProvider.setFormatStrategy(extension);
                str = this.formatProvider.readFile(readFile(path,Charset.defaultCharset()));
                view.TextArea.setText(str);
            } catch (Exception ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
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
               openFile();
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
