import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontHelper extends JDialog implements ListSelectionListener{

	JPanel panel1, panel2, panel3;
	JLabel fontLabel, sizeLabel, typeLabel, previewLabel;
	JTextField label, fontText, sizeText, typeText;
	JScrollPane fontScroll, typeScroll, sizeScroll;
	JList fontList, typeList, sizeList;
	JButton ok, cancel;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	
	FontHelper(){
		setTitle("Choose Font");
		setSize(300,400);
		setResizable(false);
		gbl = new GridBagLayout();
		setLayout(gbl);
		gbc = new GridBagConstraints();
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		sizeLabel = new JLabel("Sizes: ");
		getContentPane().add(sizeLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		typeLabel = new JLabel("Types: ");
		getContentPane().add(sizeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		fontText = new JTextField("Arial",12);
		getContentPane().add(fontText, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		sizeText = new JTextField("8",4);
		getContentPane().add(sizeText, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		typeText = new JTextField("Regular",6);
		getContentPane().add(typeText, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontList = new JList(fonts);
		fontList.setFixedCellHeight(110);
		fontList.addListSelectionListener(this);
		fontList.setSelectedIndex(0);
		fontScroll = new JScrollPane(fontList);
		getContentPane().add(fontScroll, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		String sizes[] = {"8","10","12","16","18","24","28","36","48","52","72"};
		sizeList = new JList(sizes);
		sizeList.setFixedCellHeight(110);
		sizeScroll = new JScrollPane(sizeList);
		getContentPane().add(sizeScroll, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.WEST;
		String types[] = {"Regular","Bold","Italic","Bold Italic"};
		typeList = new JList(types);
		typeList.setFixedCellWidth(110);
		typeList.addListSelectionListener(this);
		typeList.setSelectedIndex(0);
		typeScroll = new JScrollPane(typeList);
		getContentPane().add(typeScroll,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		previewLabel = new JLabel("Preview Label: ");
		panel1.add(previewLabel);
		getContentPane().add(panel1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		label = new JTextField("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOo...");
		label.setEditable(false);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setFont(new Font("Arial",Font.PLAIN,20));
		panel2.add(label);
		getContentPane().add(panel2, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		panel3.add(ok);
		panel3.add(cancel);
		getContentPane().add(panel3, gbc);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public  Font font(){
		Font font = new Font(String.valueOf(fontList.getSelectedValue()),typeList.getSelectedIndex(),Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
		return font;
	}
	
	public JButton getOk(){
		return ok;
	}
	
	public JButton getCancel(){
		return cancel;
	}
}
