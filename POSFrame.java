package RealAssignment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class POSFrame extends JFrame {
    private InventoryManager manager;
    private JTextField idF = new JTextField(), qtyF = new JTextField();
    private JLabel priceDisplay = new JLabel("Price: RM0.00"); // 新增：用来显示价格的标签

    public POSFrame(InventoryManager manager) {
        this.manager = manager;
        setTitle("Care4U POS System");
        setSize(400, 350);
        // 我们改成 5 行，给价格显示留个位置
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Product ID:"));
        add(idF);

        add(new JLabel("Quantity:"));
        add(qtyF);


        add(new JLabel("Unit Price:"));
        add(priceDisplay);

        JButton btnPay = new JButton("Process Payment");
        btnPay.addActionListener(new PayListener());

        JButton btnBack = new JButton("Back to Menu");
        btnBack.addActionListener(e -> { new MainMenu(manager); this.dispose(); });

        add(btnPay);
        add(btnBack);


        idF.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                Product p = manager.findProductById(idF.getText());
                if (p != null) {
                    priceDisplay.setText("Price: RM" + p.getPrice());
                } else {
                    priceDisplay.setText("ID Not Found");
                }
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class PayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String id = idF.getText();
                int qty = Integer.parseInt(qtyF.getText());
                Product p = manager.findProductById(id);

                if (p != null && manager.sellProduct(id, qty)) {
                    double total = p.getPrice() * qty;
                    JOptionPane.showMessageDialog(null,
                            "Sold: " + p.getName() + "\nfor total Price: $" + total);
                    idF.setText("");
                    qtyF.setText("");
                    priceDisplay.setText("Price: RM0.00");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Check ID or Stock!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid quantity!");
            }
        }
    }
}