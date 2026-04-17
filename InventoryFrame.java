package RealAssignment;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryFrame extends JFrame {
    private InventoryManager manager;
    private JTextField idF = new JTextField(), nameF = new JTextField(),
            priceF = new JTextField(), stockF = new JTextField();
    private JTextField searchF = new JTextField();
    private DefaultTableModel model;

    public InventoryFrame(InventoryManager manager) {
        this.manager = manager;
        setTitle("Inventory Management with Search");
        setSize(700, 600);
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("ID:")); inputPanel.add(idF);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameF);
        inputPanel.add(new JLabel("Price:")); inputPanel.add(priceF);
        inputPanel.add(new JLabel("Stock:")); inputPanel.add(stockF);

        JButton btnSave = new JButton("Save Product");
        btnSave.addActionListener(new SaveListener());
        inputPanel.add(new JLabel("")); inputPanel.add(btnSave);


        JPanel centerPanel = new JPanel(new BorderLayout());


        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setBorder(BorderFactory.createTitledBorder("Search Product by Name"));
        searchBar.add(searchF, BorderLayout.CENTER);


        searchF.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        model = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Stock"}, 0);
        JTable table = new JTable(model);

        centerPanel.add(searchBar, BorderLayout.NORTH); // 搜索框在表格上方
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER); // 表格在中间


        JButton btnBack = new JButton("Back to Main Menu");
        btnBack.addActionListener(e -> { new MainMenu(manager); this.dispose(); });


        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(btnBack, BorderLayout.SOUTH);

        refreshTable("");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void refreshTable(String keyword) {
        model.setRowCount(0);
        for (Product p : manager.getInventoryList()) {

            if (keyword.isEmpty() || p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStock()});
            }
        }
    }


    private void filterTable() {
        refreshTable(searchF.getText());
    }

    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                manager.addOrUpdateProduct(new Product(idF.getText(), nameF.getText(),
                        Double.parseDouble(priceF.getText()), Integer.parseInt(stockF.getText())));
                refreshTable(searchF.getText());
                JOptionPane.showMessageDialog(null, "Product Saved!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}