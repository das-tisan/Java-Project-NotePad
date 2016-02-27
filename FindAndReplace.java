import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class FindAndReplace extends JDialog implements ActionListener{

	boolean foundOne,isReplace;
	JTextField searchText,replaceText;
	JCheckBox cbCase,cbWhole;
	JRadioButton up,down;
	JLabel statusInfo;
	JFrame owner;
	JPanel north,center,south;
	
	public FindAndReplace(JFrame owner,boolean isReplace){
		super(owner,true);	// modality is set to true
		this.isReplace = isReplace;
		north = new JPanel();
		center = new JPanel();
		south = new JPanel();
		
		if(isReplace){
			setTitle("Find And Replace");
		}
		else{
			setTitle("Find");
			setFindPanel(north);
		}
	}
	
	private void setFindPanel(JPanel panel){
		final JButton NEXT = new JButton("Find Next");
		NEXT.setEnabled(false);
		NEXT.addActionListener(this);
		searchText = new JTextField(20);
		searchText.addActionListener(this);
		searchText.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e){
				boolean state = (searchText.getDocument().getLength()>0);
				NEXT.setEnabled(true);
				foundOne = false;
			}
		});
		if(searchText.getText().length()>0){
			NEXT.setEnabled(true);
		}
		panel.add(new JLabel("Find Word: "));
		panel.add(searchText);
		panel.add(NEXT);
		
	}
	
	private void setFindAndReplacePanel(JPanel panel){
		
	}
	
	private int search(String text,String word,int caret){
		boolean found = false;
		int all = text.length();
		int check = word.length();
		if(isSearchDown()){
			int add = 0;
			for(int i=caret+1;i<(all-check);i++){
				String temp = text.substring(i,i+check);
				if(temp.equals(word)){
					if(wholeWordIsSelected()){
						if(checkForWholeWord(check,text,add,caret)){
						
							caret = i;
							found = true;
							break;
						}
					} 
					else{	// not whole word found
						caret = i;
						found = true;
						break;
					}
				}
			}
		}
		else{
			int add = caret;
			for(int i=caret-1;i>=check;i--){
				add--;
				String temp = text.substring(i-check,i);
				if(temp.equals(word)){
					if(wholeWordIsSelected()){
						if(checkForWholeWord(check,text,add,caret)){
						
							caret = i;
							found = true;
							break;
						}
					} 
					else{	// not whole word found
						caret = i;
						found = true;
						break;
					}
				}
			}
		}
		Main.getArea().setCaretPosition(0);
		if(found){
			Main.getArea().requestFocus();
			if(isSearchDown()){
				Main.getArea().select(caret, caret+check);
			}
			else{
				Main.getArea().select(caret-check, caret);
			}
			foundOne = true;
		}
		else{
			
		}
		return -1;
	}
	
	private boolean isSearchDown(){
		return down.isSelected();
	}
	
	private boolean isSearchUp(){
		return up.isSelected();
	}
	
	private boolean wholeWordIsSelected(){
		return cbWhole.isSelected();
	}

	private boolean checkForWholeWord(int check,String text,int add,int caret){
		int offsetLeft = (caret+add)-1;
		int offsetRight = (caret+add)+ check;
		if(offsetLeft<0 || offsetRight>text.length()){
			return true;
		}
		return ((!Character.isLetterOrDigit(text.charAt(offsetLeft))) && (!(Character.isLetterOrDigit(text.charAt(offsetRight)))));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
