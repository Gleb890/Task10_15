import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Desktop {
    static JFrame frame;
    private JPanel panelMain;
    private JButton addRowButton;
    private JTable field;
    private JButton loadFromFileButton;
    private JButton loadToFileButton;
    private JButton deleteRowButton;
    private JButton getResultButton;
    private JTable Result;

    static int rowsCount = 0;

    public JTable getField() {
        return field;
    }

    public Desktop() {
        setDefaultSettingsToTable(field);
        setDefaultSettingsToTable(Result);

        addRowButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) field.getModel();
                model.addRow(new Object[] {});
                rowsCount++;
            }
        });
        deleteRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rowsCount == 0) return;
                DefaultTableModel model = (DefaultTableModel) field.getModel();
                model.removeRow(rowsCount - 1);
                rowsCount--;
            }
        });
        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = getFileName();
                if (fileName.equals("")) return;
                List<Point> points = Utils.readInfoFromFile(fileName);
                setPointToField(field, points);
            }
        });
        loadToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = getFileName();
                if (fileName.equals("")) return;
                List<Point> points = getPointsFromField();
                try {
                    boolean res = Utils.writeInfoToFile(fileName, points);
                    if (res) JOptionPane.showMessageDialog(frame,
                            "Данные успешно записаны в файл.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        getResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Point> points = getPointsFromField();
                List<Point> res = Utils.getResult(points);

                setPointToField(Result, res);
            }
        });
    }

    private void setDefaultSettingsToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(new String[] {"x1", "x2"});
        model.setRowCount(1);
        model.setColumnCount(2);
    }

    private String getFileName() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath();
        }
        return "";
    }

    private List<Point> getPointsFromField() {
        List<Point> points = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) this.getField().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Point point = new Point(Integer.parseInt((String) model.getValueAt(i, 0)),
                                    Integer.parseInt((String) model.getValueAt(i, 1)));

            points.add(point);
        }
        return points;
    }

    private void setPointToField(JTable table, List<Point> points) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(points.size());
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            model.setValueAt(Integer.toString(point.getX()), i, 0);
            model.setValueAt(Integer.toString(point.getY()), i, 1);
        }
    }

    public static void main(String[] args) {
        frame = new JFrame("Desktop");
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setContentPane(new Desktop().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
