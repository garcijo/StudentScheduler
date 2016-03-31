package tasksTable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import view.TranslucentGUI;
import data.MySQLConnect;

public class ButtonEditor extends DefaultCellEditor {
	  protected int sel;
	  protected JTable tabla;
	  public JButton button;
	  protected String task;
	  protected String[] taskId;
	  protected int rows;
	  MyTableModel updates;
	  private String label;
	  private String user = TranslucentGUI.user;
	  public boolean isPushed;
	  
	  public JOptionPane updateTable;

	  public ButtonEditor(JCheckBox checkBox) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	//updates.fireTableDataChanged();
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
	    sel = table.getSelectedRow();
	    isPushed = true;
	    return button;
	    
	  }

	  public Object getCellEditorValue() {
	    if (isPushed) {
	    	
	    	
	    	
	    	rows = tabla.getRowCount();
	    	String [] taskInfo = new String[7];
	    	
	    	sel = tabla.getSelectedRow();
	    	
	    	
	    	for (int i=0; i<taskInfo.length; i++){
	    		taskInfo[i] = (String) tabla.getValueAt(sel,i);
	    	}
	    	
	    	
	    	MySQLConnect dal = new MySQLConnect();
  		try {
  			dal.deleteTask(taskInfo[0], user, taskInfo[1], taskInfo[3]);
  			
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
	      // **/
	      // 
  		 //updates.fireTableDataChanged();
	      updateTable.showMessageDialog(button, taskInfo[0] + " was succesfully deleted!");
	      
	      
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
	  
	  public JButton getBtn() {
			// TODO Auto-generated method stub
			return button;
		}
	}