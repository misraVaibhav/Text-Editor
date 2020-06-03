import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.filechooser.*;
import java.io.*; 
import java.util.*; 


class Work{
	static JFrame mFrame;
	private JMenuBar mMenuBar;
	private JMenu mFileMenu;
	private JMenu mEditMenu;
	private JMenu mZoomItem;
	private JMenu mViewMenu;
	private JMenu mFormatMenu;
	private JMenu mAboutMenu;
	private JMenuItem mNew;
	private JMenuItem mOpen;
	private JMenuItem mSave;
	private JMenuItem mSaveAs;
	private JMenuItem mExit;
	private JMenuItem mCut;
	private JMenuItem mCopy;
	private JMenuItem mPaste;
	private JMenuItem mZoomIn;
	private JMenuItem mZoomOut;
	private JMenuItem mFont;
	static JTextArea mTextArea;
	private JScrollPane mScrollPane;
	private JDialog mDialog;
	private int mFontSize; 
	private Dimension mDimension;
	private boolean mSaved;
	private String mTempText;
	
	
	public Work(){
		mFrame =  new JFrame("Untitled - Notepad");
		
		mSaved = false;
		
		mFrame.setJMenuBar(addMenuBar());
		
		mFrame.add(addTextArea());
		
		mTempText = "";
		
		mDimension = setFrameSize();
		mFrame.setBounds(mDimension.width/4,mDimension.height/4,mDimension.width/2,mDimension.height/2);
		
		setListeners();
		
		setMnenomics();
		
		setAccelerators();
		
		mFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(mSaved == true){
					System.exit(0);
				}
				else{
					int mChoice = JOptionPane.showConfirmDialog(mFrame,"Do you want to save the current file?");
					if(mChoice == JOptionPane.YES_OPTION){
						saveTheFile();
						System.exit(0);
					}
					else if(mChoice == JOptionPane.NO_OPTION){
						System.exit(0);
					}
				}
			}
		});
		
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mFrame.setVisible(true);
			
		mFrame.setResizable(true);
	}
	
	static Dimension setFrameSize(){
		Toolkit mToolkit = Toolkit.getDefaultToolkit();
		return mToolkit.getScreenSize();
	}
	
	private JMenuBar addMenuBar(){
		mMenuBar = new JMenuBar();
		mFileMenu = new JMenu("File");
		mEditMenu = new JMenu("Edit");
		mFormatMenu = new JMenu("Format");
		mViewMenu = new JMenu("View");
		mAboutMenu = new JMenu("About");
		
		mNew = new JMenuItem("New");
		mOpen = new JMenuItem("Open");
		mSave = new JMenuItem("Save");
		mExit = new JMenuItem("Exit");
		mSaveAs = new JMenuItem("Save As");
		mFileMenu.add(mNew);
		mFileMenu.add(mOpen);
		mFileMenu.add(mSave);
		mFileMenu.add(mSaveAs);
		mFileMenu.add(mExit);
		
		mCut = new JMenuItem(new DefaultEditorKit.CutAction());
		mCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
		mPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
		mCut.setText("Cut");
		mCopy.setText("Copy");
		mPaste.setText("Paste");
		mEditMenu.add(mCut);
		mEditMenu.add(mCopy);
		mEditMenu.add(mPaste);
		
		mZoomItem = new JMenu("Zoom");
		mZoomIn = new JMenuItem("Zoom In");
		mZoomOut = new JMenuItem("Zoom Out");
		mZoomItem.add(mZoomIn);
		mZoomItem.add(mZoomOut);
		mViewMenu.add(mZoomItem);		
		
		mFont = new JMenuItem("Font");
		mFormatMenu.add(mFont);
		
		mMenuBar.add(mFileMenu);
		mMenuBar.add(mEditMenu);
		mMenuBar.add(mFormatMenu);
		mMenuBar.add(mViewMenu);
		mMenuBar.add(mAboutMenu);
		return mMenuBar;
	}
	
	private JScrollPane addTextArea(){
		mTextArea = new JTextArea();
		mFontSize = 20;
		mTextArea.setFont(new Font("Arial" , Font.PLAIN , mFontSize));
		mScrollPane = new JScrollPane(mTextArea);
		return mScrollPane;
	}
	
	private void setMnenomics(){
		mFileMenu.setMnemonic(KeyEvent.VK_F);
		mEditMenu.setMnemonic(KeyEvent.VK_E);
		mFormatMenu.setMnemonic(KeyEvent.VK_O);
		mViewMenu.setMnemonic(KeyEvent.VK_V);
		mAboutMenu.setMnemonic(KeyEvent.VK_A);
	}
	
	private void setAccelerators(){
		mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		mOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
		mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
		mSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK));
		mCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_DOWN_MASK));
		mCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK));
		mPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_DOWN_MASK));
		mZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,KeyEvent.CTRL_DOWN_MASK));
		mZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,KeyEvent.CTRL_DOWN_MASK));
	}
	
	private void setListeners(){
		mNew.addActionListener(e->{
			if(mSaved == true){
				mFrame.setTitle("Untitled - Notepad");
				mTextArea.setText("");
			}
			else{
				int mChoice = JOptionPane.showConfirmDialog(mFrame,"Do you want to save the current file?");
				if(mChoice == JOptionPane.YES_OPTION){
					saveTheFile();
					mFrame.setTitle("Untitled - Notepad");
					mTextArea.setText("");
				}
				else if(mChoice == JOptionPane.NO_OPTION){
					mFrame.setTitle("Untitled - Notepad");
					mTextArea.setText("");
				}
			}
			mSaved = false;
		});
		mOpen.addActionListener(e->{
			if(mSaved == true){
				openAFile();
			}
			else{
				int mChoice = JOptionPane.showConfirmDialog(mFrame,"Do you want to save the current file?");
				if(mChoice == JOptionPane.YES_OPTION){
					saveTheFile();
					openAFile();
				}
				else if(mChoice == JOptionPane.NO_OPTION){
					mFrame.setTitle("Untitled - Notepad");
					mTextArea.setText("");
					openAFile();
				}
			}
			mSaved = false;
		});
		mSave.addActionListener(e->saveTheFile());
		mSaveAs.addActionListener(e->{
			saveAsTheFile();
		});
		mExit.addActionListener(e->{
			if(mSaved == true){
				System.exit(0);
			}
			else{
				int mChoice = JOptionPane.showConfirmDialog(mFrame,"Do you want to save the current file?");
				if(mChoice == JOptionPane.YES_OPTION){
					saveTheFile();
					System.exit(0);
				}
				else if(mChoice == JOptionPane.NO_OPTION){
					System.exit(0);
				}
			}
			
		});
		mFont.addActionListener(e->{
			new FontDialog();
		});
		mZoomIn.addActionListener(e->mTextArea.setFont(new Font("sansserif",Font.PLAIN,mFontSize += 2)));
		mZoomOut.addActionListener(e->mTextArea.setFont(new Font("sansserif",Font.PLAIN,mFontSize -= 2)));
	}
	
	private void saveTheFile(){
		if(mSaved == true && mTempText.equals(mTextArea.getText())) return;
		JFileChooser mFileChooser = new JFileChooser();
		mFileChooser.setSelectedFile(new File("*.txt"));
		mFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Document","txt"));
		mFileChooser.setAcceptAllFileFilterUsed(true);
		int mChoice = mFileChooser.showSaveDialog(mFrame);
		if(mChoice == JFileChooser.APPROVE_OPTION){
			try{
				String mFileName = mFileChooser.getSelectedFile().toString();
				if(!mFileName.endsWith(".txt")){
					mFileName += ".txt";
				}
				String mTitleName = mFileChooser.getSelectedFile().getName();
				if(!mTitleName.endsWith(".txt")){
					mTitleName += ".txt";
				}
				mFrame.setTitle(mTitleName + " - Notepad");
				BufferedWriter mBufferedWriter = new BufferedWriter(new FileWriter(mFileName));
				mBufferedWriter.write(mTextArea.getText()); 
				mBufferedWriter.close();
			}
			catch(Exception mException){
				System.out.println(mException.getMessage());				
			}
		}
		mSaved = true;
		mTempText = mTextArea.getText();
	}
	
	private void saveAsTheFile(){
		JFileChooser mFileChooser = new JFileChooser();
		mFileChooser.setDialogTitle("Save As");
		mFileChooser.setSelectedFile(new File((mFrame.getTitle()).substring(0,(mFrame.getTitle()).length()-10)));
		mFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Document","txt"));
		mFileChooser.setAcceptAllFileFilterUsed(true);
		int mChoice = mFileChooser.showSaveDialog(mFrame);
		if(mChoice == JFileChooser.APPROVE_OPTION){
			try{
				String mFileName = mFileChooser.getSelectedFile().toString();
				if(!mFileName.endsWith(".txt")){
					mFileName += ".txt";
				}
				String mTitleName = mFileChooser.getSelectedFile().getName();
				if(!mTitleName.endsWith(".txt")){
					mTitleName += ".txt";
				}
				mFrame.setTitle(mTitleName  + " - Notepad");
				BufferedWriter mBufferedWriter = new BufferedWriter(new FileWriter(mFileName));
				mBufferedWriter.write(mTextArea.getText()); 
				mBufferedWriter.close();
			}
			catch(Exception mException){
				System.out.println(mException.getMessage());				
			}
		}
		mSaved = true;
	}
	
	private void openAFile(){
		JFileChooser mFileChooser = new JFileChooser();
		mFileChooser.setSelectedFile(new File("*.txt"));
		mFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Document","txt"));
		mFileChooser.setAcceptAllFileFilterUsed(true);
		int mChoice = mFileChooser.showOpenDialog(mFrame);
		if(mChoice == JFileChooser.APPROVE_OPTION){
			try{
				String mFileName = mFileChooser.getSelectedFile().getName();
				if(!mFileName.endsWith(".txt")){
					mFileName += ".txt";
				}
				mFrame.setTitle(mFileName + " - Notepad");
				mTextArea.setText("");
				Scanner mScanner = new Scanner(new FileReader(mFileChooser.getSelectedFile().getPath()));
				while(mScanner.hasNext()){
					mTextArea.append(mScanner.nextLine() + "\n");
				}
			}
			catch(Exception mException){
				System.out.println(mException.getMessage());
			}
		}
	}
	
	
}


public class Notepad{
	public static void main(String[] args){
		EventQueue.invokeLater(()->new Work());
	}
}