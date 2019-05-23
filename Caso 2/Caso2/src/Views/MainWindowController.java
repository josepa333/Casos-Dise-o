/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Model.FormatProvider;
import Model.IStrategy;
import Model.PDFTextProcessor;
import Model.Text;
import Model.TextCareTaker;
import Model.TextMemento;
import java.awt.Color;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 *
 * @author jose pablo
 */
public class MainWindowController implements KeyListener, ActionListener, MouseListener{

    private MainWindow view;
    private FormatProvider formatProvider;
    private String path; //FilePath currrently being handled
    private TextCareTaker caretaker;
    private Text generalText;
    private String tmpString;
    private Clipboard c;
    private StringSelection testData;
    private DefaultHighlighter.DefaultHighlightPainter highlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    private ArrayList<String> highlightWords;
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
        view.TextArea.addMouseListener(this);
        view.TextArea.setContentType("text/html");
        view.TextArea.setText("<!DOCTYPE html>" +
            "<html>" +
            "</html>");
        this.formatProvider = new FormatProvider();
        this.path = "";
        this.highlightWords = new ArrayList<>();
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
            jfc.setDialogTitle("Select a document");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt,txtt,xml,csv,json", "txt", "txtt", "xml", "csv", "json");
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
    private void saveFileAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save to file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt,txtt,xml,csv,json", "txt", "txtt", "xml", "csv", "json");
        fileChooser.addChoosableFileFilter(filter);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.path = fileChooser.getSelectedFile().getPath();
            String[] strArray = path.split("\\.");
            String extension =  strArray[strArray.length - 1];
            formatProvider.setFormatStrategy(extension);
            this.formatProvider.processText(view.TextArea.getText(), path);
        }
    }
    
    private void saveFile(){
        if(path == null || path.isEmpty()){
            saveFileAs();
            return;
        }
        String[] strArray = path.split("\\.");
        String extension =  strArray[strArray.length - 1];
        formatProvider.setFormatStrategy(extension);
        this.formatProvider.processText(view.TextArea.getText(), path);
        JOptionPane.showMessageDialog(this.view, "El archivo ha sido guardado");

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
               openFile();
               break;
           case "Save":
               System.out.println("Save");
               saveFile();
               break;
           case "Save as":
               System.out.println("Save as");
               saveFileAs();
               break;
           case "Highlight":
               System.out.println("Highlight");
               testData = new StringSelection( view.TextArea.getSelectedText() );
               c.setContents(testData, testData);
               view.TextArea.replaceSelection("");
               Transferable t1 = c.getContents( null );
               try{
                   if ( t1.isDataFlavorSupported(DataFlavor.stringFlavor) ){
                         Object o = t1.getTransferData( DataFlavor.stringFlavor );
                         String data = (String)t1.getTransferData( DataFlavor.stringFlavor );
                         if(data == null)
                             break;
                         view.TextArea.replaceSelection("<font color=\"red\">"+ data +"</font>");
                         String txtArea = view.TextArea.getText();
                         txtArea = txtArea.replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\"");
                         view.TextArea.setText(txtArea);

                     }
               }
               catch(UnsupportedFlavorException | IOException f){
                   System.out.println(f.getMessage());
               } 
               break;
           case "Undo":
               System.out.println("Undo");
               TextMemento previousMemento = caretaker.getPreviousMemento();
               if(previousMemento == null)
                   JOptionPane.showMessageDialog(view, "You can't undo more");
               else{
                   generalText.restoreMemento(previousMemento);
                   view.TextArea.setText(generalText.getState().replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\""));
               }
               break;
           case "Redo":
               System.out.println("Redo");
               TextMemento nextMemento = caretaker.getNextMemento();
               if(nextMemento == null)
                   JOptionPane.showMessageDialog(view, "You can't redo more");
               else{
                   generalText.restoreMemento(nextMemento);
                   view.TextArea.setText(generalText.getState().replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\""));
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
