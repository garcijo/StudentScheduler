package tasksTable;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import view.TranslucentGUI;
import data.MySQLConnect;

public class MyTableModel extends AbstractTableModel {
	private boolean DEBUG = false;
	private String user = TranslucentGUI.user;
	
    private String[] columnNames = {"Name",
                                    "Due Date",
                                    "Description",
                                    "Course",
                                    "Type",
                                    "Priority",
                                    "Delete",
                                    "Id"};
    
    
    
    public Object[][] info() {
    		MySQLConnect dal = new MySQLConnect();
    		String[][] tsk = null;
    		try {
    			ArrayList<String[]> tasks = dal.sortTasksAlphabetic(user);
    			int rows = tasks.size();
    			tsk = new String[8][rows];
    			String[] temp = new String[8];
    			for (int i = 0; i < tasks.size(); i++) {
    				temp = tasks.get(i);
    				tsk[0][i] = temp[1];
    				tsk[1][i] = temp[3];
    				tsk[2][i] = temp[4];
    				tsk[3][i] = temp[5];
    				tsk[4][i] = temp[6];
    				tsk[5][i] = temp[7];
    				tsk[6][i] = "Delete";
    				tsk[7][i] = temp[0];
    				
    				//System.out.println(tsk);
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		String[][] pivot = new String[tsk[0].length][];
    	    for (int u = 0; u < tsk[0].length; u++)
    	        pivot[u] = new String[tsk.length];

    	    for (int y = 0; y < tsk.length; y++)
    	        for (int col = 0; col < tsk[y].length; col++)
    	            pivot[col][y] = tsk[y][col];
    	    return pivot;
    }
    
    private Object[][] data = info();
    

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        data[row][col] = value;
        // Normally, one should call fireTableCellUpdated() when 
        // a value is changed.  However, doing so in this demo
        // causes a problem with TableSorter.  The tableChanged()
        // call on TableSorter that results from calling
        // fireTableCellUpdated() causes the indices to be regenerated
        // when they shouldn't be.  Ideally, TableSorter should be
        // given a more intelligent tableChanged() implementation,
        // and then the following line can be uncommented.
        //fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}