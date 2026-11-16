package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeForm extends JFrame {
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField deptField = new JTextField(15);
    private JTextField salaryField = new JTextField(15);
    private DefaultTableModel model =
            new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary"}, 0);
    private JTable table = new JTable(model);
    private EmployeeDAO dao = new EmployeeDAO();

    public EmployeeForm() {
        setTitle("Employee Management");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Employee ID:"));
        form.add(idField);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Department:"));
        form.add(deptField);
        form.add(new JLabel("Salary:"));
        form.add(salaryField);

        JButton submitBtn = new JButton("Submit");
        JButton viewBtn = new JButton("View All Employees");
        form.add(submitBtn);
        form.add(viewBtn);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        submitBtn.addActionListener(e -> addEmployee());
        viewBtn.addActionListener(e -> loadEmployees());
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());

            dao.addEmployee(id, name, dept, salary);
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            idField.setText("");
            nameField.setText("");
            deptField.setText("");
            salaryField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be an integer and Salary a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadEmployees() {
        try {
            model.setRowCount(0);
            List<Object[]> rows = dao.getAllEmployees();
            for (Object[] row : rows) {
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeForm().setVisible(true));
    }
}