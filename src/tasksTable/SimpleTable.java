package tasksTable;


import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import tasksTable.MyTableModel;
import data.MySQLConnect;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimpleTable extends JPanel {
	private JPanel panel;
	public JTable table;
	public ButtonEditor buttonEditor;
	public JScrollPane scrollPane;
	public JButton btn;


    public SimpleTable() {
    	
        super(new GridLayout(1,0));
        buttonEditor = new ButtonEditor(new JCheckBox());
        panel = new JPanel(new BorderLayout());
        table = new JTable(new MyTableModel());
        table.removeColumn(table.getColumnModel().getColumn(7));
        table.removeColumn(table.getColumnModel().getColumn(6));
        table.removeColumn(table.getColumnModel().getColumn(5));
        table.removeColumn(table.getColumnModel().getColumn(4));
        table.removeColumn(table.getColumnModel().getColumn(3));
        table.removeColumn(table.getColumnModel().getColumn(2));
        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        
        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        panel.add(scrollPane);
        
        
        btn = buttonEditor.button;
		ActionListener[] lis = btn.getActionListeners();
		btn.removeActionListener(lis[0]);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				panel.remove(scrollPane);
				//pnl4.revalidate();
				table = new JTable(new MyTableModel());
		        table.removeColumn(table.getColumnModel().getColumn(7));
		        table.removeColumn(table.getColumnModel().getColumn(6));
		        table.removeColumn(table.getColumnModel().getColumn(5));
		        table.removeColumn(table.getColumnModel().getColumn(4));
		        table.removeColumn(table.getColumnModel().getColumn(3));
		        table.removeColumn(table.getColumnModel().getColumn(2));  
		        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
		        table.setFillsViewportHeight(true);
		        table.setAutoCreateRowSorter(true);
		        scrollPane = new JScrollPane(table);
				panel.add(scrollPane);
				panel.revalidate();
				panel.setVisible(true);
			}
		});
		btn.addActionListener(lis[0]);
		
    }
    
    public JPanel getTable(){
		return this.panel;
	}

    public JTable getUpdateTable(){
		return table;
	}


    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableSortDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableModel newContentPane = new TableModel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}




