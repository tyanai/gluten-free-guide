/* From http://java.sun.com/docs/books/tutorial/index.html */
/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package org.celiac;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import org.celiac.api.GlutenFreeAPI;
import org.celiac.util.Logger;



/**
 * TextInputDemo.java is a 1.4 application that uses these additional files:
 * SpringUtilities.java ...
 */
public class GlutenFreeSimulator extends JPanel implements ActionListener,
		FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2755559720626769948L;

	JTextField smsField;

	boolean smsSet = false;

	Font regularFont, italicFont;

	JLabel smsDisplay;

	final static int GAP = 10;

	public GlutenFreeSimulator() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JPanel leftHalf = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Don't allow us to stretch vertically.
			@Override
			public Dimension getMaximumSize() {
				Dimension pref = getPreferredSize();
				return new Dimension(Integer.MAX_VALUE, pref.height);
			}
		};
		leftHalf.setLayout(new BoxLayout(leftHalf, BoxLayout.PAGE_AXIS));
		leftHalf.add(createEntryFields());
		leftHalf.add(createButtons());

		add(leftHalf);
		add(createSMSDisplay());
	}

	protected JComponent createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JButton button = new JButton("Set SMS");
		button.addActionListener(this);
		panel.add(button);

		button = new JButton("Clear SMS");
		button.addActionListener(this);
		button.setActionCommand("clear");
		panel.add(button);

		// Match the SpringLayout's gap, subtracting 5 to make
		// up for the default gap FlowLayout provides.
		panel
				.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5,
						GAP - 5));
		return panel;
	}

	/**
	 * Called when the user clicks the button or presses Enter in a text field.
	 */
	public void actionPerformed(ActionEvent e) {
		if ("clear".equals(e.getActionCommand())) {
			smsSet = false;
			smsField.setText("");

		} else {
			smsSet = true;
		}
		updateDisplays();
	}

	protected void updateDisplays() {
		smsDisplay.setText(formatSMS());
		if (smsSet) {
			smsDisplay.setFont(regularFont);
		} else {
			smsDisplay.setFont(italicFont);
		}
	}

	protected JComponent createSMSDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		smsDisplay = new JLabel();
		smsDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		regularFont = smsDisplay.getFont().deriveFont(Font.PLAIN, 16.0f);
		italicFont = regularFont.deriveFont(Font.ITALIC);
		updateDisplays();

		// Lay out the panel.
		panel.setBorder(BorderFactory.createEmptyBorder(GAP / 2, // top
				0, // left
				GAP / 2, // bottom
				0)); // right
		panel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
		panel.add(smsDisplay, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(200, 300));

		return panel;
	}

	// Tal Yanai
	protected String formatSMS() {
		if (!smsSet)
			return "No SMS set.";

		String sms = smsField.getText();

		String empty = "";

		if ((sms == null) || empty.equals(sms)) {
			sms = "<em>(no sms specified)</em>";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<html><p align=right>");
		try {
			String theOutput = null;
			
			theOutput = new GlutenFreeAPI().search(sms, "FROM-CELL", true);
			//theOutput = new GlutenFreeAPI().search(sms, "0526199088", true);

			if (theOutput != null) {
				int totalCharacters = theOutput.length();
				theOutput = theOutput.replaceAll("\n", "<br>");
				theOutput = theOutput + "<br><br>Character count: "  + totalCharacters;
			} else {
				theOutput = "No Match Found";
			}
			sb.append(theOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("<br>");
		sb.append(" ");
		sb.append("</p></html>");

		return sb.toString();
	}

	// A convenience method for creating a MaskFormatter.
	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	/**
	 * Called when one of the fields gets the focus so that we can select the
	 * focused field.
	 */
	public void focusGained(FocusEvent e) {
		Component c = e.getComponent();
		if (c instanceof JFormattedTextField) {
			selectItLater(c);
		} else if (c instanceof JTextField) {
			((JTextField) c).selectAll();
		}
	}

	// Workaround for formatted text field focus side effects.
	protected void selectItLater(Component c) {
		if (c instanceof JFormattedTextField) {
			final JFormattedTextField ftf = (JFormattedTextField) c;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ftf.selectAll();
				}
			});
		}
	}

	// Needed for FocusListener interface.
	public void focusLost(FocusEvent e) {
		//TODO: ignore
	} // ignore

	protected JComponent createEntryFields() {
		JPanel panel = new JPanel(new SpringLayout());

		String[] labelStrings = { "SMS Text: " };

		JLabel[] labels = new JLabel[labelStrings.length];
		JComponent[] fields = new JComponent[labelStrings.length];
		int fieldNum = 0;

		// Create the text field and set it up.
		smsField = new JTextField();
		smsField.setColumns(20);
		smsField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		fields[fieldNum++] = smsField;
		/*
		 * cityField = new JTextField(); cityField.setColumns(20);
		 * fields[fieldNum++] = cityField;
		 * 
		 * String[] stateStrings = getStateStrings(); stateSpinner = new
		 * JSpinner(new SpinnerListModel(stateStrings)); fields[fieldNum++] =
		 * stateSpinner;
		 * 
		 * zipField = new JFormattedTextField( createFormatter("#####"));
		 * fields[fieldNum++] = zipField;
		 */
		// Associate label/field pairs, add everything,
		// and lay it out.
		for (int i = 0; i < labelStrings.length; i++) {
			labels[i] = new JLabel(labelStrings[i], SwingConstants.TRAILING);
			labels[i].setLabelFor(fields[i]);
			panel.add(labels[i]);
			panel.add(fields[i]);

			// Add listeners to each field.
			JTextField tf = null;
			if (fields[i] instanceof JSpinner) {
				tf = getTextField((JSpinner) fields[i]);
			} else {
				tf = (JTextField) fields[i];
			}
			tf.addActionListener(this);
			tf.addFocusListener(this);
		}
		SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2, GAP,
				GAP, // init x,y
				GAP, GAP / 2);// xpad, ypad
		return panel;
	}

	public JFormattedTextField getTextField(JSpinner spinner) {
		JComponent editor = spinner.getEditor();
		if (editor instanceof JSpinner.DefaultEditor) {
			return ((JSpinner.DefaultEditor) editor).getTextField();
		} else {
			System.err.println("Unexpected editor type: "
					+ spinner.getEditor().getClass()
					+ " isn't a descendant of DefaultEditor");
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("GlutenFreeSimulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new GlutenFreeSimulator();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws Exception{
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		Logger.info("Starting...");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlutenFreeSimulator.createAndShowGUI();
			}
		});
	}
}

/**
 * A 1.4 file that provides utility methods for creating form- or grid-style
 * layouts with SpringLayout. These utilities are used by several programs, such
 * as SpringBox and SpringCompactGrid.
 */
class SpringUtilities {
	/**
	 * A debugging utility that prints to stdout the component's minimum,
	 * preferred, and maximum sizes.
	 */
	public static void printSizes(Component c) {
		System.out.println("minimumSize = " + c.getMinimumSize());
		System.out.println("preferredSize = " + c.getPreferredSize());
		System.out.println("maximumSize = " + c.getMaximumSize());
	}

	/**
	 * Aligns the first <code>rows</code> * <code>cols</code> components of
	 * <code>parent</code> in a grid. Each component is as big as the maximum
	 * preferred width and height of the components. The parent is made just big
	 * enough to fit them all.
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 * @param initialX
	 *            x location to start the grid at
	 * @param initialY
	 *            y location to start the grid at
	 * @param xPad
	 *            x padding between cells
	 * @param yPad
	 *            y padding between cells
	 */
	public static void makeGrid(Container parent, int rows, int cols,
			int initialX, int initialY, int xPad, int yPad) {
		SpringLayout layout;
		try {
			layout = (SpringLayout) parent.getLayout();
		} catch (ClassCastException exc) {
			System.err
					.println("The first argument to makeGrid must use SpringLayout.");
			return;
		}

		Spring xPadSpring = Spring.constant(xPad);
		Spring yPadSpring = Spring.constant(yPad);
		Spring initialXSpring = Spring.constant(initialX);
		Spring initialYSpring = Spring.constant(initialY);
		int max = rows * cols;

		// Calculate Springs that are the max of the width/height so that all
		// cells have the same size.
		Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0))
				.getWidth();
		Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0))
				.getWidth();
		for (int i = 1; i < max; i++) {
			SpringLayout.Constraints cons = layout.getConstraints(parent
					.getComponent(i));

			maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
			maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
		}

		// Apply the new width/height Spring. This forces all the
		// components to have the same size.
		for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons = layout.getConstraints(parent
					.getComponent(i));

			cons.setWidth(maxWidthSpring);
			cons.setHeight(maxHeightSpring);
		}

		// Then adjust the x/y constraints of all the cells so that they
		// are aligned in a grid.
		SpringLayout.Constraints lastCons = layout.getConstraints(parent
				.getComponent(0));
		SpringLayout.Constraints lastRowCons = lastCons;
		for (int i = 0; i < max; i++) {
			SpringLayout.Constraints cons = layout.getConstraints(parent
					.getComponent(i));
			if (i % cols == 0) { // start of new row
				lastRowCons = lastCons;
				cons.setX(initialXSpring);
			} else { // x position depends on previous component
				cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
						xPadSpring));
			}

			if (i / cols == 0) { // first row
				cons.setY(initialYSpring);
			} else { // y position depends on previous row
				cons.setY(Spring.sum(lastRowCons
						.getConstraint(SpringLayout.SOUTH), yPadSpring));
			}
			lastCons = cons;
		}

		// Set the parent's size.
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, Spring.sum(Spring
				.constant(yPad), lastCons.getConstraint(SpringLayout.SOUTH)));
		pCons.setConstraint(SpringLayout.EAST, Spring.sum(
				Spring.constant(xPad), lastCons
						.getConstraint(SpringLayout.EAST)));
	}

	/* Used by makeCompactGrid. */
	private static SpringLayout.Constraints getConstraintsForCell(int row,
			int col, Container parent, int cols) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}

	/**
	 * Aligns the first <code>rows</code> * <code>cols</code> components of
	 * <code>parent</code> in a grid. Each component in a column is as wide as
	 * the maximum preferred width of the components in that column; height is
	 * similarly determined for each row. The parent is made just big enough to
	 * fit them all.
	 * 
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 * @param initialX
	 *            x location to start the grid at
	 * @param initialY
	 *            y location to start the grid at
	 * @param xPad
	 *            x padding between cells
	 * @param yPad
	 *            y padding between cells
	 */
	public static void makeCompactGrid(Container parent, int rows, int cols,
			int initialX, int initialY, int xPad, int yPad) {
		SpringLayout layout;
		try {
			layout = (SpringLayout) parent.getLayout();
		} catch (ClassCastException exc) {
			System.err
					.println("The first argument to makeCompactGrid must use SpringLayout.");
			return;
		}

		// Align all cells in each column and make them the same width.
		Spring x = Spring.constant(initialX);
		for (int c = 0; c < cols; c++) {
			Spring width = Spring.constant(0);
			for (int r = 0; r < rows; r++) {
				width = Spring.max(width, getConstraintsForCell(r, c, parent,
						cols).getWidth());
			}
			for (int r = 0; r < rows; r++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r,
						c, parent, cols);
				constraints.setX(x);
				constraints.setWidth(width);
			}
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
		}

		// Align all cells in each row and make them the same height.
		Spring y = Spring.constant(initialY);
		for (int r = 0; r < rows; r++) {
			Spring height = Spring.constant(0);
			for (int c = 0; c < cols; c++) {
				height = Spring.max(height, getConstraintsForCell(r, c, parent,
						cols).getHeight());
			}
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r,
						c, parent, cols);
				constraints.setY(y);
				constraints.setHeight(height);
			}
			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
		}

		// Set the parent's size.
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}
}
