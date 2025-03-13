package GUI;

import BLL.TestBLL;
import DTO.TestDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TestGUI extends JPanel {
    private TestBLL testBLL = new TestBLL();
    
    private int width, height;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JPanel pnTable;
    
    public TestGUI(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        
        this.pnTable = initPanelTable();
        
        this.setLayout(new BorderLayout());
        this.add(pnTable, BorderLayout.SOUTH);
    }
    
    private void loadTests() {
        tableModel.setRowCount(0);
        ArrayList<TestDTO> tests = testBLL.getAllTests();
        for (TestDTO t : tests) {
            tableModel.addRow(new Object[]{
                t.getId(),
                t.getTestCode(),
                t.getName(),
                t.getTime(),
                t.getLimit(),
                t.getDate(),
            });
        }
    }
    
    public JPanel initPanelTable() {
        JPanel pn_table = new JPanel(new FlowLayout(1, 0, 0));
        pn_table.setPreferredSize(new Dimension(this.width, 300));
        
        tableModel = new DefaultTableModel(new Object[]{
            "ID", "Test Code", "Name", "Time", "Limit", "Date"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 250));
        
        pn_table.add(scrollPane);
        
        return pn_table;
    }

    private void addTest() {
//        String name = txtName.getText();
//        double price = Double.parseDouble(txtPrice.getText());
//        int quantity = Integer.parseInt(txtQuantity.getText());
//
//        productBLL.addProduct(new Product(0, name, price, quantity));
//        loadProducts();
    }

    private void editTest() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            int id = (int) tableModel.getValueAt(selectedRow, 0);
//            String name = txtName.getText();
//            double price = Double.parseDouble(txtPrice.getText());
//            int quantity = Integer.parseInt(txtQuantity.getText());
//
//            productBLL.updateProduct(new Product(id, name, price, quantity));
//            loadProducts();
//        }
    }

    private void deleteTest() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            testBLL.deleteTest(id);
            loadTests();
        }
    }
}
