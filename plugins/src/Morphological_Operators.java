//=========================================================================================
//
// Author : Daniel Sage, Biomedical Imaging Group (BIG), http://bigwww.epfl.ch/
//
//=========================================================================================

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Morphological_Operators extends JDialog implements ActionListener {
	
	private String[] listShape = {"Square", "Cross", "Disk", "Shift"};
	private String[] listSize  = {"1", "3", "5", "7", "9", "11", "13", "15", "17", "19", "21", "23", "25", "27", "29", "31"};
	private GridBagLayout 		layout				= new GridBagLayout();
	private GridBagConstraints 	constraint			= new GridBagConstraints();
	private JButton 			bnErosion			= new JButton("Erosion");
	private JButton 			bnDilation			= new JButton("Dilation");
	private JButton 			bnOpening			= new JButton("Open");
	private JButton 			bnClosing			= new JButton("Close");
	private JButton 			bnGradient			= new JButton("Gradient");
	private JButton 			bnTopHat			= new JButton("Top Hat");
	private JButton 			bnBottomHat			= new JButton("Bottom Hat");
	private JButton 			bnMedian			= new JButton("Median");
		
	private JButton 			bnQuit				= new JButton("Quit");
	private JComboBox			cmbES				= new JComboBox(listShape);
	private JComboBox			cmbSize				= new JComboBox(listSize);
	private StructuringElementCanvas	canvas		= new StructuringElementCanvas();

	private JRadioButton		rbGrayLevel			= new JRadioButton("Grayscale image", true);
	private JRadioButton		rbThreshold			= new JRadioButton("Binary image");

	private JLabel lblThreshold = new JLabel("Threshold Value");
	private JTextField txtThreshold = new JTextField("100.0");
	
	public Morphological_Operators() {
		super(new Frame(), "Morphological Operators");
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbGrayLevel);
		group.add(rbThreshold);
		
		// JPanel 
		JPanel pnES = new JPanel(layout);
		pnES.setBorder(BorderFactory.createTitledBorder("Structuring element"));
	 	addComponent(pnES, 0, 0, 1, 1, 3, new JLabel("Shape"));
	 	addComponent(pnES, 0, 1, 1, 1, 3, cmbES);
	 	addComponent(pnES, 1, 0, 1, 1, 3, new JLabel("Size"));
	 	addComponent(pnES, 1, 1, 1, 1, 3, cmbSize);
	 	addComponent(pnES, 0, 2, 1, 3, 3, canvas);
	 	
	 	// JPanel buttons	
		JPanel pnButtons = new JPanel(layout);
		pnButtons.setBorder(BorderFactory.createTitledBorder("Operations"));
		
	 	addComponent(pnButtons, 1, 0, 1, 1, 3, bnErosion);
	 	addComponent(pnButtons, 1, 1, 1, 1, 3, bnDilation);
	 	addComponent(pnButtons, 2, 0, 1, 1, 3, bnOpening);
	 	addComponent(pnButtons, 2, 1, 1, 1, 3, bnClosing);
	 	addComponent(pnButtons, 3, 0, 1, 1, 3, bnTopHat);
	 	addComponent(pnButtons, 3, 1, 1, 1, 3, bnBottomHat);
	 	addComponent(pnButtons, 4, 0, 1, 1, 3, bnGradient);
	 	addComponent(pnButtons, 4, 1, 1, 1, 3, bnMedian);

	 	// JPanel output	
		JPanel pnOutput = new JPanel(layout);
		pnOutput.setBorder(BorderFactory.createTitledBorder("Output"));
	 	addComponent(pnOutput, 0, 0, 1, 1, 3, rbGrayLevel);
	 	addComponent(pnOutput, 1, 0, 1, 1, 3, rbThreshold);
	 	addComponent(pnOutput, 1, 1, 1, 1, 3, lblThreshold);
	 	addComponent(pnOutput, 1, 2, 1, 1, 3, txtThreshold);

		// JPanel main
		JPanel pnMain = new JPanel();
		pnMain.setLayout(layout);
	 	addComponent(pnMain, 0, 0, 2, 1, 3, pnES);
	 	addComponent(pnMain, 2, 0, 2, 1, 3, pnButtons);
	 	addComponent(pnMain, 1, 0, 2, 1, 3, pnOutput);
	 	addComponent(pnMain, 6, 1, 1, 1, 3, bnQuit);

	 	// Add Listeners
		cmbES.addActionListener(this);
		rbGrayLevel.addActionListener(this);
		rbThreshold.addActionListener(this);
		cmbSize.addActionListener(this);
		bnErosion.addActionListener(this);
		bnDilation.addActionListener(this);
		bnOpening.addActionListener(this);
		bnClosing.addActionListener(this);
		bnGradient.addActionListener(this);
		bnTopHat.addActionListener(this);
		bnBottomHat.addActionListener(this);
		bnMedian.addActionListener(this);
		bnQuit.addActionListener(this);
		
		cmbSize.setSelectedIndex(1);
		add(pnMain);
		pack();
		setResizable(true);
		canvas.setSize(new Dimension(140, 140));
		setModal(false);
		GUI.center(this);
		setVisible(true);	
		
		updateInterface();
	}
	
	private void updateInterface() {
		txtThreshold.setEnabled(rbThreshold.isSelected());
		lblThreshold.setEnabled(rbThreshold.isSelected());
	}
	/**
	* Add a component in a panel in the northeast of the cell.
	*/
	private void addComponent(JPanel pn, int row, int col, int width, int height, int space, Component comp) {
	    constraint.gridx = col;
	    constraint.gridy = row;
	    constraint.gridwidth = width;
	    constraint.gridheight = height;
	    constraint.anchor = GridBagConstraints.NORTHWEST;
	    constraint.insets = new Insets(space, space, space, space);
		constraint.weightx = IJ.isMacintosh()?90:100;
		constraint.fill = GridBagConstraints.HORIZONTAL;
	    layout.setConstraints(comp, constraint);
	    pn.add(comp);
	}

	/**
	* Implements the actionPerformed for the ActionListener.
	*/
	public synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == bnQuit) {
			dispose();
			return;
		}
		if (e.getSource() == rbGrayLevel || e.getSource() == rbThreshold) {
			updateInterface();
			return;
		}

		int m = Integer.parseInt((String)cmbSize.getSelectedItem());
		canvas.repaint(cmbES.getSelectedIndex(), m);
		if (e.getSource() instanceof JButton) {
			process((JButton)e.getSource());
		}
	}
	
	/**
	*/
	private void process(JButton button) {
		String operation = button.getText();
		ImagePlus imp = WindowManager.getCurrentImage();
		if (imp == null) {
			IJ.error("no open image.");
			return;
		}
		int type = imp.getType();
		if (type != ImagePlus.GRAY8 && type != ImagePlus.GRAY32 && type != ImagePlus.COLOR_RGB && type!= ImagePlus.GRAY16) {
			IJ.error("Only process the 8-bits, 16-bits, RGB, or 32-bits.");
			return;
		}
		ImageAccess input[] = null;
		if (type == ImagePlus.COLOR_RGB)
			input = ImageAccess.RGB.create(imp);
		else
			input = new ImageAccess[] {new ImageAccess(imp)};
		
		int m = Integer.parseInt((String)cmbSize.getSelectedItem());
		boolean se[][] = canvas.getStructuringElement(m);
		if (se == null) {
			IJ.error("Undefined structuring element.");
			return;
		}
		
		ImageAccess out[] = new ImageAccess[input.length]; 
		for (int z=0; z<input.length; z++) {
			if (operation.equals("Erosion"))
				out[z] = (new CodeMorphological()).erosion(input[z], se);
			else if (operation.equals("Dilation"))
				out[z] = (new CodeMorphological()).dilation(input[z], se);
			else if (operation.equals("Open"))
				out[z] = (new CodeMorphological()).open(input[z], se);
			else if (operation.equals("Close"))
				out[z] = (new CodeMorphological()).close(input[z], se);
			else if (operation.equals("Top Hat"))
				out[z] = (new CodeMorphological()).topHat(input[z], se);
			else if (operation.equals("Bottom Hat"))
				out[z] = (new CodeMorphological()).bottomHat(input[z], se);
			else if (operation.equals("Median"))
				out[z] = (new CodeMorphological()).median(input[z], se);
			else if (operation.equals("Gradient"))
				out[z] = (new CodeMorphological()).gradient(input[z], se);
		}
	
		String title = button.getText();
		String shape = (String)(cmbES.getSelectedItem());
		title += " " + shape.charAt(0) + "-" + cmbSize.getSelectedItem();
		title += " (" + imp.getTitle() + ") ";
		
		if (rbThreshold.isSelected()) {
			double T = Double.parseDouble(txtThreshold.getText());
			for (int z=0; z<input.length; z++) {
				for(int x=0; x<out[z].nx; x++)
				for(int y=0; y<out[z].ny; y++)
					out[z].putPixel(x,  y, out[z].getPixel(x,y) > T ? 255 : 0);
			}
		}
		
		if (out.length == 3)
			new  ImageAccess.Display(true, out[0], out[1], out[2], title);
		else
			new ImageAccess.Display(out, title);
	}
}

