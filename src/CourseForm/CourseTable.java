package CourseForm;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import data.MySQLConnect;

public class CourseTable extends JFrame{
	
	static JPanel jp = new JPanel();
	JTable table = new JTable(new CourseTableModel());
	public TableCellEditor check;
	
	public CourseTable () {
		makeTable();
	}
	
	public void makeTable () {
		
		//appearance and table properties
		jp.setVisible(true);
		jp.setSize(300,400);;
		jp.setLayout(new BorderLayout());
		//jp.setBackground(Color.LIGHT_GRAY);
		//table.setBackground(Color.LIGHT_GRAY);

		autoResizeColumns(table);
		
		table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
		table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
		check = table.getColumn("Delete").getCellEditor();
		
		table.setPreferredScrollableViewportSize(new Dimension(500,150));
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		
		
		jp.add(table.getTableHeader(), BorderLayout.PAGE_START);
		jp.add(table, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(table);
		jp.add(new JScrollPane(table));
		
		
	}
	
	public JPanel getCourseTable () {
		return jp;
	}
	

	class CourseTableModel extends AbstractTableModel {
		private String columns [] = {"Course Name", "Days", "Time", "Delete"};
		
		/**
		private Object data [][] = {
				{"COSC 310", "Mon, Tue", "10:30 am", "Delete"}, 
				{"COSC 310", "Mon, Wed, Fri", "10:30 am", "Delete"},
				{"COSC 211", "Tue, Thu", "12:30 pm", "Delete"}
		}; */
		
		
		public Object [][] info () {
			MySQLConnect db = new MySQLConnect ();
			String [][] crse = null;
			try {
				ArrayList<String[]> courses = db.sortCourses();
				int rows = courses.size();
				crse = new String[4][rows];
				String[] temp = new String[4];
				for (int i=0; i<courses.size(); i++) {
					temp = courses.get(i);
					crse[0][i] = temp[0];
					crse[1][i] = temp[2];
					crse[2][i] = temp[3];
					crse[3][i] = "Delete";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
			String[][] pivot = new String[crse[0].length][];
			for (int j = 0; j < crse[0].length;j++)
				pivot[j] = new String[crse.length];
			
			for (int y = 0; y < crse.length; y++)
				for (int z = 0; z < crse[y].length; z++)
					pivot[z][y] = crse[y][z];
			
			return pivot;
		} 
		
		private Object[][] data = info (); 
		
		public CourseTableModel () {
			fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
	
		public String getColumnName(int col) {
			return columns[col];
		}
		
		 public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
	}
	
	
	/**
	 * adjusts the column sizes automatically, depending on the amount of content
	 * in each column.
	 * @param table
	 */
	public void autoResizeColumns (JTable table) {
		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);
		 
		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }
		 
		    tableColumn.setPreferredWidth( preferredWidth );
		}
	}
	
	class ButtonEditor extends DefaultCellEditor {
		  protected JButton button;

		  private String label;
		  protected JTable tabla;
		  private boolean isPushed;
		  protected int rows;
		  protected int selected;
		  public JOptionPane updateTable;
		  
		  public ButtonEditor(JCheckBox checkBox) {
		    super(checkBox);
		    button = new JButton();
		    button.setOpaque(true);
		    button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        fireEditingStopped();
		      }
		    });
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
		    if (isSelected) {
		      button.setForeground(table.getSelectionForeground());
		      button.setBackground(table.getSelectionBackground());
		    } else {
		      button.setForeground(table.getForeground());
		      button.setBackground(table.getBackground());
		    }
		    tabla = table;
		    label = (value == null) ? "" : value.toString();
		    button.setText(label);
		    selected = table.getSelectedRow();
		    isPushed = true;
		    return button;
		  }

		  public Object getCellEditorValue() {
		    if (isPushed) {
		    	rows = tabla.getRowCount();
		    	String [] courseInfo = new String[4];
		    	selected = tabla.getSelectedRow();
		    	
		    	for (int i=0; i<courseInfo.length; i++) {
		    		courseInfo[i] = (String) tabla.getValueAt(selected, i);
		    		
		    	}
		    	
		    	MySQLConnect db = new MySQLConnect();
		    	try {
		    		db.deleteCourse(courseInfo[0], "admin", courseInfo[2], courseInfo[3]);
		    		
		    	} catch (SQLException e) {
		    		e.printStackTrace();
		    	}
		    	updateTable.showMessageDialog(button, courseInfo[0] + " was succesfully deleted!"+courseInfo[2]+ courseInfo[3]);
		    }
		    isPushed = false;
		    return new String(label);
		  }

		  public boolean stopCellEditing() {
		    isPushed = false;
		    return super.stopCellEditing();
		  }

		  protected void fireEditingStopped() {
		    super.fireEditingStopped();
		  }
		}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {

		  public ButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
		}
	
	public static void main  (String [] args) {
		CourseTable ct = new CourseTable();
		JFrame jf = new JFrame ();
		jf.setVisible(true);
		jf.getContentPane().add(jp);
		jf.setSize(450,200);
	}
}