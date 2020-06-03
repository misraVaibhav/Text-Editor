import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.*; 
import java.util.*;


public class FontDialog{
	
	
	private JDialog mDialog;
	private JComboBox<String> mComboBox_1;
	private JComboBox<String> mComboBox_2;
	private JComboBox<String> mComboBox_3;
	private JLabel mLabel;
	private JButton mOk;
	private JButton mCancel;
	private JPanel mPanel_1;
	private JPanel mPanel_2;
	private String mFontName;
	private String mSize;
	private String mStyle;
	private int mStyleInt;
	private String[] mFontNames;
	private String[] mFontStyles;
	private String[] mFontSizes;
	private Dimension mDimension;
	
	
	public FontDialog(){
		
		mDialog = new JDialog(Work.mFrame,"Font"); 
		
		mFontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		mFontSizes = new String[]{
			"10",
			"20",
			"30",
			"40",
			"50",
			"60",
			"70",
			"80",
			"90",
			"100"
		};
		
		mFontStyles = new String[]{
			"Regular",
			"Bold",
			"Italic",
			"Bold Italic"
		};
		
		mComboBox_1 = new JComboBox<String>(mFontNames);
		mComboBox_1.setSelectedItem("Arial");
		mComboBox_2 = new JComboBox<String>(mFontSizes);
		mComboBox_2.setSelectedItem("20");
		mComboBox_3 = new JComboBox<String>(mFontStyles);
		mComboBox_3.setSelectedItem("Regular");
		
		mFontName = "Arial";
		
		mSize = "20";
		
		mStyle = "Regular";
		
		mLabel = new JLabel("AaBbCc");
		mLabel.setFont(new Font(mFontName , mStyleInt , Integer.parseInt(mSize)));
		mOk = new JButton("OK");
		mCancel = new JButton("Cancel");
		mPanel_1 = new JPanel();
		mPanel_2 = new JPanel();
		mPanel_1.add(mComboBox_1);
		
		setListeners();
		
		addComponents();
		
		mDialog.setVisible(true);	
		mDimension = Work.setFrameSize();
		mDialog.setSize(mDimension.width/3,mDimension.height/3);
		mDialog.setLocationRelativeTo(Work.mFrame);
		
	}
	
	
	private void setListeners(){
		mComboBox_1.addItemListener(ee->{
			mFontName = (String)(((JComboBox)ee.getSource()).getSelectedItem());
			mLabel.setFont(new Font(mFontName , mStyleInt , Integer.parseInt(mSize)));
		});
		mComboBox_2.addItemListener(ee->{
			mSize = (String)(((JComboBox)ee.getSource()).getSelectedItem());
			mLabel.setFont(new Font(mFontName , mStyleInt , Integer.parseInt(mSize)));
		});
		mComboBox_3.addItemListener(ee->{
			mStyle = (String)(((JComboBox)ee.getSource()).getSelectedItem());
			if(mStyle.equals("Bold")) mStyleInt = Font.BOLD;
			else if(mStyle.equals("Italic")) mStyleInt = Font.ITALIC;
			else if(mStyle.equals("Regular")) mStyleInt = Font.PLAIN;
			else mStyleInt = Font.BOLD + Font.ITALIC;
			mLabel.setFont(new Font(mFontName , mStyleInt , Integer.parseInt(mSize)));
		});
		mOk.addActionListener(ee->{
			Work.mTextArea.setFont(new Font(mFontName , mStyleInt , Integer.parseInt(mSize)));
		});
		mCancel.addActionListener(ee->{
			mDialog.dispose();
		});
	}
	
	
	private void addComponents(){
		mPanel_1.add(mComboBox_2);
		mPanel_1.add(mComboBox_3);
		mPanel_2.add(mOk);
		mPanel_2.add(mCancel);
		mDialog.add(mPanel_1,BorderLayout.NORTH);
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		mDialog.add(mLabel);
		mDialog.add(mPanel_2,BorderLayout.SOUTH);
	}
}