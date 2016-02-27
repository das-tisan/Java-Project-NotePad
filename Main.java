import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public class Main extends JFrame{
	
	static JTextArea mainArea;
	JMenuBar mbar;
	JMenu mnuFile,mnuEdit,mnuFormat,mnuHelp;
	JMenuItem itmNew,itmOpen,itmSave,itmSaveAs,itmExit;
	JMenuItem itmCut,itmCopy,itmPaste;
	JCheckBoxMenuItem wordWrap;
	JMenuItem itmFontColor,itmFind,itmReplace;
	JFileChooser fileChooser;
	String fileName,fileContent;

	// Undo & Redo
	UndoManager undoManager;
	UndoAction undoAction;
	RedoAction redoAction;
	
	Main(){
		initComponent();
		itmSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				save();
			}
		});
		itmSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveAs();
			}
		});
		itmOpen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				open();
			}
		});
		itmNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				open_new();
			}
		});
		itmExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		itmCut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainArea.cut();
			}
		});
		itmCopy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainArea.copy();
			}
		});
		itmPaste.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainArea.paste();
			}
		});
		
		// Word Wrap Style
		wordWrap.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(wordWrap.isSelected()){
					mainArea.setWrapStyleWord(true);
					mainArea.setLineWrap(true);
				}
				else{
					mainArea.setLineWrap(false);
					mainArea.setWrapStyleWord(false);
				}
			}			
		});
		
		itmFontColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Color color = JColorChooser.showDialog(rootPane, "Choose Font Color", Color.BLACK);
				mainArea.setForeground(color);
			}
		});
		
		
		// Undo & Redo
		/*	getDocument():
				Fetches the model associated with the editor. This is primarily for the UI to get at the minimal 
			amount of state required to be a text editor. Subclasses will return the actual type of the model 
			which will typically be something that extends Document.
		*/
		mainArea.getDocument().addUndoableEditListener(new UndoableEditListener(){

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				// TODO Auto-generated method stub
				undoManager.addEdit(e.getEdit());
				undoAction.update();
				redoAction.update();
			}
			
		});
	}
	
	private void initComponent(){
		mainArea = new JTextArea();
		fileChooser = new JFileChooser(".");
		// Text Area added to content panel of JFrame
		getContentPane().add(mainArea);
		getContentPane().add(new JScrollPane(mainArea),BorderLayout.CENTER);
		
		
		setTitle("Untitled Document");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
		
		// Undo
		undoManager = new UndoManager();
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		
		// Menu bar & menu
		mbar = new JMenuBar();
		mnuFile = new JMenu("File");
		mnuEdit = new JMenu("Edit");
		mnuFormat = new JMenu("Format");
		mnuHelp = new JMenu("Help");

		// Add icons to Menu Items
//		ImageIcon newIcon = new ImageIcon(getClass().getResource("/img/new.gif"));
		// Adding icons to menu item not working in linux+openjdk1.7
		// ep02/05/1895:    	itmNew = new JMenuItem("New",newIcon);
		
		// Menu Items
		itmNew = new JMenuItem("New");
		itmOpen = new JMenuItem("Open");
		itmSave = new JMenuItem("Save");
		itmSaveAs = new JMenuItem("Save As");
		itmExit = new JMenuItem("Exit");
		
		itmCut = new JMenuItem("Cut");
		itmCopy = new JMenuItem("Copy");
		itmPaste = new JMenuItem("Paste");
		
		wordWrap = new JCheckBoxMenuItem("Word Wrap");
		itmFontColor = new JMenuItem("Font Color");
		itmFind = new JMenuItem("Find");
		itmReplace = new JMenuItem("Replace");
		
		// Adding Shortcut to menu items
		itmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		itmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		itmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		itmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
		
		itmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		itmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		itmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		
		// Menu Items are added to corresponding menu
		mnuFile.add(itmNew);
		mnuFile.add(itmOpen);
		mnuFile.addSeparator();
		mnuFile.add(itmSave);
		mnuFile.add(itmSaveAs);
		mnuFile.addSeparator();
		mnuFile.add(itmExit);
		
		mnuEdit.add(itmCut);
		mnuEdit.add(itmCopy);
		mnuEdit.add(itmPaste);
		// Undo & Redo
		mnuEdit.addSeparator();	
		mnuEdit.add(undoAction);
		mnuEdit.add(redoAction);
		
		mnuFormat.add(wordWrap);
		mnuFormat.addSeparator();
		mnuFormat.add(itmFontColor);
		mnuFormat.addSeparator();
		mnuFormat.add(itmFind);
		mnuFormat.add(itmReplace);
		
		// Menu added to corresponding Menu Bar
		mbar.add(mnuFile);
		mbar.add(mnuEdit);
		mbar.add(mnuFormat);
		mbar.add(mnuHelp);
		
		// Menu Bar is added to J-Frame Menu-bar
		setJMenuBar(mbar);
	}
	
	private void save(){
		PrintWriter pOut = null;
		int retVal = -1;
		try {
			if(fileName==null){
				saveAs();
			}
			else{
				pOut = new PrintWriter(new FileWriter(fileName));
				String str = mainArea.getText().toString();
				StringTokenizer st = new StringTokenizer(str,System.getProperty("line.separator"));
				while(st.hasMoreElements()){
					pOut.println(st.nextToken());
				}
				// Show as no other way to ensure occured
				JOptionPane.showMessageDialog(rootPane, "File Saved.");
				fileContent = mainArea.getText().toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			pOut.close();
		}
	}
	
	private void saveAs(){
		PrintWriter pOut = null;
		int retVal = -1;
		try{
			retVal = fileChooser.showSaveDialog(this);
			if(retVal == JFileChooser.APPROVE_OPTION)
				pOut = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()));
			String str = mainArea.getText().toString();
			StringTokenizer st = new StringTokenizer(str,System.getProperty("line.separator"));
			while(st.hasMoreElements()){
				pOut.println(st.nextToken());
			}
			// Show as no other way to ensure occured
			JOptionPane.showMessageDialog(rootPane, "File Saved.");
			fileName = fileChooser.getSelectedFile().getName();
			setTitle(fileName); // Change
			fileContent = mainArea.getText().toString();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			pOut.close();
		}
	}
	
	private void open(){
		int retVal = -1;
		FileReader pIn = null;
		try{
			retVal = fileChooser.showSaveDialog(this);
			if(retVal == JFileChooser.APPROVE_OPTION){
				mainArea.setText(null);
				Reader in = new FileReader(fileChooser.getSelectedFile());
				char[] buffer = new char[1000000];
				int nchar;
				while((nchar = in.read(buffer, 0, buffer.length)) != -1){
					mainArea.append(new String(buffer,0,nchar));
				}
				fileName = fileChooser.getSelectedFile().getName();
				setTitle(fileName);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			
		}
	}
	
	private void open_new(){
		if(!mainArea.getText().equals("") && !mainArea.getText().equals(fileContent)){
			if(fileName == null){
				int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save the file?");
				if(option == 0)
					saveAs();
				clear();
			}
			else{
				int option = JOptionPane.showConfirmDialog(rootPane, "Do you want to save?");
				if(option==0){		// YES
					save();
					clear();
				}
				else if(option==2){	// CANCEL
					// No operation
				}
				else{				// NO
					clear();
				}
			}
		}
		else
			clear();
	}
	
	private void clear(){
		mainArea.setText(null);
		setTitle("Untitled File");
		fileName = null;
		fileContent = null;
	}
	
	public static JTextArea getArea(){
		return mainArea;
	}
	
	public static void main(String args[]){
		Main notepad = new Main();
	}
	
	
	
	
	
	
	
	
	
	// Undo Class
	public class UndoAction extends AbstractAction{

		UndoAction(){
			super("Undo");
			setEnabled(false);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try{
				undoManager.undo();
			} catch(CannotUndoException e){
				e.printStackTrace();
			}
			update();
			redoAction.update();
		}
		
		void update(){
			if(undoManager.canUndo()){
				setEnabled(true);
				putValue(Action.NAME,"Undo");
			}
			else{
				setEnabled(false);
				putValue(Action.NAME,"Undo");
			}
		}

	}
	// Redo Class
	public class RedoAction extends AbstractAction{

		RedoAction(){
			super("Redo");
			setEnabled(false);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try{
				undoManager.redo();
			} catch(CannotRedoException e){
				e.printStackTrace();
			}
			update();
			undoAction.update();
		}
		
		void update(){
			if(undoManager.canRedo()){
				setEnabled(true);
				putValue(Action.NAME,"Redo");
			}
			else{
				setEnabled(false);
				putValue(Action.NAME,"Redo");
			}
		}

	}
	/*
	 	An Action can be used to separate functionality and state from a component. 
	 	For example, if you have two or more components that perform the same function, consider using an 
	 	Action object to implement the function. An Action object is an action listener that provides not 
	 	only action-event handling, but also centralized handling of the state of action-event-firing 
	 	components such as tool bar buttons, menu items, common buttons, and text fields. The state that 
	 	an action can handle includes text, icon, mnemonic, enabled, and selected status.
	*/
}
