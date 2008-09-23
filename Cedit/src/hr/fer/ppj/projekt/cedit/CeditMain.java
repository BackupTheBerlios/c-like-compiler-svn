package hr.fer.ppj.projekt.cedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;



public class CeditMain extends JFrame {
	
	private static final long serialVersionUID = 922174647836529096L;
	
	
	/**
	 * Glavni program.
	 * @param args argumenti komandne linije
	 */
	public static void main(String[] args) {
	    try {
		    // Set System L&F
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

	    }
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		

		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CeditMain().setVisible(true);
			}
		});
	}
	
	
	/**
	 * Mapa koja cuva sve akcije.
	 */
	private Map<String, Action> allActions;

	/**
	 * Tekst editor komponenta.
	 */
	private JTextArea editor = new JTextArea();
	
	/**
	 * Konstruktor
	 */
	public CeditMain() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(100, 50);
		setSize((int)screenSize.getWidth()-200, (int)screenSize.getHeight()-100);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Cedit");
		
		initGUI();
	}
	
	/**
	 * Stvaranje korisnickog sucelja.
	 */
	private void initGUI() {
		
		createActions();
		
		createMenus();
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(200, 400));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 8));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(400, 150));
		JScrollPane textArea = new JScrollPane(this.editor);
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JPanel mainWindow = new JPanel();
		
		mainWindow.setLayout(new BorderLayout());
		mainWindow.add(bottomPanel, BorderLayout.SOUTH);
		mainWindow.add(textArea, BorderLayout.CENTER);
		mainWindow.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainWindow, BorderLayout.CENTER);
		this.getContentPane().add(rightPanel, BorderLayout.EAST);

		rightPanel.setLayout(new BorderLayout());
		JTree fileTree = new JTree();
		fileTree.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		JScrollPane scrollFileTree = new JScrollPane(fileTree);
		rightPanel.add(scrollFileTree, BorderLayout.CENTER);
		JPanel currentStep = new JPanel();
		currentStep.setLayout(new BoxLayout(currentStep, BoxLayout.Y_AXIS));
		currentStep.add(new JLabel("Leksicka analiza..."));
		currentStep.add(new JLabel("neka analiza..."));
		currentStep.add(new JLabel("neka analiza..."));
		rightPanel.add(currentStep, BorderLayout.SOUTH);
		
		bottomPanel.setLayout(new BorderLayout());
		JTextArea messageText = new JTextArea();
		messageText.setEditable(false);
		messageText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		bottomPanel.add(messageText, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout());
		buttons.add(new JButton("Compile"));
		buttons.add(new JButton("Button 2"));
		buttons.add(new JButton("Button 3"));
		buttonPanel.add(buttons, BorderLayout.EAST);
		bottomPanel.add(buttonPanel, BorderLayout.NORTH);
	}


	/**
	 * Stvaranje izbornika.
	 */
	private void createMenus() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		menuFile.add(new JMenuItem(allActions.get("Open")));
		menuFile.add(new JMenuItem(allActions.get("Save")));
		menuFile.addSeparator();
		menuFile.add(new JMenuItem(allActions.get("Exit")));
		menuBar.add(menuFile);
		
		JMenu menuEdit = new JMenu("Edit");
		menuEdit.add(new JMenuItem(allActions.get("Copy")));
		menuEdit.add(new JMenuItem(allActions.get("Cut")));
		menuEdit.add(new JMenuItem(allActions.get("Paste")));
		menuBar.add(menuEdit);
		
		JMenu menuTools = new JMenu("Tools");
		menuBar.add(menuTools);
		
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		this.setJMenuBar(menuBar);
	}
	
	
	
	/**
	 * Stvaranje svih akcija programa
	 */
	@SuppressWarnings("serial")
	private void createActions() {
		allActions = new HashMap<String, Action>();
		
		Action fileOpen = new AbstractAction("Open") {
			public void actionPerformed(ActionEvent event) {
				actionOpen();
			}
		};
		fileOpen.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
		fileOpen.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		allActions.put("Open", fileOpen);
		
		Action fileSave = new AbstractAction("Save") {
			public void actionPerformed(ActionEvent event) {
				actionSave();
			}
		};
		fileSave.putValue(Action.SHORT_DESCRIPTION, "Saves current file");
		fileSave.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
		fileSave.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		allActions.put("Save", fileSave);

		Action fileExit = new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent event) {
				actionExit();
			}
		};
		fileExit.putValue(Action.SHORT_DESCRIPTION, "Exit application");
		fileExit.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_X));
		fileExit.putValue(Action.ACCELERATOR_KEY, 
			KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		allActions.put("Exit", fileExit);

		Action editCopy = new AbstractAction("Copy") {
			public void actionPerformed(ActionEvent event) {
				actionCopy();
			}
		};
		editCopy.putValue(Action.SHORT_DESCRIPTION, "Copy to clipboard");
		allActions.put("Copy", editCopy);
		
		Action editCut = new AbstractAction("Cut") {
			public void actionPerformed(ActionEvent event) {
				actionCut();
			}
		};
		editCut.putValue(Action.SHORT_DESCRIPTION, "Cut to clipboard");
		allActions.put("Cut", editCut);

		Action editPaste = new AbstractAction("Paste") {
			public void actionPerformed(ActionEvent event) {
				actionPaste();
			}
		};
		editPaste.putValue(Action.SHORT_DESCRIPTION, "Paste from clipboard");
		allActions.put("Paste", editPaste);
	}
	
	
	/**
	 * Akcija: Open. 
	 * 
	 * Otvaranje datoteke u editoru.
	 */
	protected void actionOpen() {
		JFileChooser jfc = new JFileChooser();
		if(JFileChooser.APPROVE_OPTION != jfc.showOpenDialog(this)) {
			return;
		}
		openFile(jfc.getSelectedFile().getAbsolutePath());
	}

	/**
	 * Akcija: Save. 
	 * 
	 * Snimanje datoteke (nije implementirano do kraja!).
	 */
	protected void actionSave() {
		int result = JOptionPane.showConfirmDialog(
				this, "Jeste li sigurni da zelite snimiti datoteku?");
		if(result==JOptionPane.YES_OPTION) {
			// Ubaciti kod za snimanje na ovom mjestu
			JOptionPane.showMessageDialog(this, "Snimljeno", "Poruka", 
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Nije snimljeno", "Poruka", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Akcija: Exit.
	 * 
	 * Izlazak iz programa.
	 */
	protected void actionExit() {
		System.exit(0);
	}
	
	/**
	 * Akcija: Cut.
	 * 
	 * Brisanje oznacenog teksta i pohrana u Clipboard.
	 */
	protected void actionCut() {
		editor.cut();
	}
	
	/**
	 * Akcija: Copy.
	 * 
	 * Kopiranje oznacenog teksta u Clipboard.
	 */
	protected void actionCopy() {
		editor.copy();
	}
	
	/**
	 * Akcija: Paste.
	 * 
	 * Citanje teksta iz Clipboarda i ubacivanje u editor.
	 */
	protected void actionPaste() {
		editor.paste();
	}
	
	
	/**
	 * Pomocna metoda za ucitavanje datoteke u editor.
	 * @param ime puno ime datoteke
	 */
	private void openFile(String ime) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(ime));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this, 
					"Datoteka "+ime+" se ne moze otvoriti.", 
					"Greska", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		StringBuilder sb = new StringBuilder(10000);
		try {
			while(true) {
				String line = reader.readLine();
				if(line==null) break;
				sb.append(line).append('\n');
			}
			editor.setText(sb.toString());
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(
					this, 
					"Dogodila se je greska prilikom citanja datoteke "+ime+".", 
					"Greska", 
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try { reader.close(); } catch(Exception ignorable) {}
		}
	}
	
	
	
	

}
