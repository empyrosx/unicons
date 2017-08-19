/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author Димитрий
 */
public class MatrixView extends JFrame{

    /**
     * Модель даты рождения
     */
    private DateModel dateModel;

    /**
     * Модель сетки данных
     */
    private TableModel dm;

    /**
     * Метка с датой рождения
     */
    private JLabel birthDateLabel;

    /**
     * Дополнительные цифры даты
     */
    private JLabel additionalDgitsLabel;

    /**
     * Список для выбора дня рождения
     */
    private JComboBox dayComboBox;

    /**
     * Список для выбора месяца рождения
     */
    private JComboBox monthComboBox;

    /**
     * Список для выбора года рождения
     */
    private JComboBox yearComboBox;

    /**
     * Конструктор
     */
    public MatrixView() {

        // модель данных
        dm = createDateModel();

        // строим интерфейс
        buildUI();

        // дата по умолчанию
        setDate(new GregorianCalendar(1983, 07, 24));
    }

    /**
     * Создание модели данных
     * @return
     */
    private TableModel createDateModel(){
        return new DefaultTableModel(3, 3) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Создание сетки данных
     * @param model
     * @return
     */
    private JTable createTable(TableModel model){

        JTable result = new JTable(model);
        result.setBorder(new TitledBorder(""));
        result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        result.setRowSelectionAllowed(false);
        result.setCellSelectionEnabled(false);
        result.setColumnSelectionAllowed(false);

        result.setRowHeight(64);
        result.setTableHeader(null);

        TableCellRenderer r = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int digit = 0;
                if ((column == 0) && (row == 0)) {
                    digit = 1;
                } else if ((column == 0) && (row == 1)) {
                    digit = 2;
                } else if ((column == 0) && (row == 2)) {
                    digit = 3;
                } else if ((column == 1) && (row == 0)) {
                    digit = 4;
                } else if ((column == 1) && (row == 1)) {
                    digit = 5;
                } else if ((column == 1) && (row == 2)) {
                    digit = 6;
                } else if ((column == 2) && (row == 0)) {
                    digit = 7;
                } else if ((column == 2) && (row == 1)) {
                    digit = 8;
                } else if ((column == 2) && (row == 2)) {
                    digit = 9;
                }

                int digitCount = value == null ? 0 : (Integer) value;

                String text = "";
                for (int i = 0; i < digitCount; i++) {
                    text = text + String.valueOf(digit);
                }
                ((JLabel) comp).setText(text);

                return comp;

            }
        };

        result.getColumnModel().getColumn(0).setCellRenderer(r);
        result.getColumnModel().getColumn(1).setCellRenderer(r);
        result.getColumnModel().getColumn(2).setCellRenderer(r);

        return result;
    }

    /**
     * Установка новой даты
     * @param calendar
     */
    private void setDate(Calendar calendar){
        dateModel = new DateModel(calendar);

        dm.setValueAt(dateModel.getDigitCount(1), 0, 0);
        dm.setValueAt(dateModel.getDigitCount(2), 1, 0);
        dm.setValueAt(dateModel.getDigitCount(3), 2, 0);
        dm.setValueAt(dateModel.getDigitCount(4), 0, 1);
        dm.setValueAt(dateModel.getDigitCount(5), 1, 1);
        dm.setValueAt(dateModel.getDigitCount(6), 2, 1);
        dm.setValueAt(dateModel.getDigitCount(7), 0, 2);
        dm.setValueAt(dateModel.getDigitCount(8), 1, 2);
        dm.setValueAt(dateModel.getDigitCount(9), 2, 2);

        if (dateModel != null){
            birthDateLabel.setText(String.format("%d  %d  %d", dateModel.getDay(), dateModel.getMonth(), dateModel.getYear()));
            additionalDgitsLabel.setText(String.format("%d  %d  %d  %d", dateModel.getFirstNumber(), dateModel.getSecondNumber(), dateModel.getThirdNumber(), dateModel.getFourthNumber()));
        }
    }


    /**
     * Построение интерфейса
     */
    private void buildUI(){

        setSize(640, 480);

        // раскладка компонентов
        setLayout(new BorderLayout(0, 0));

        // сетка данных
        JTable table = createTable(dm);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton button = new JButton("Применить");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int year = (Integer) yearComboBox.getSelectedItem();
                int month = ((Month) monthComboBox.getSelectedItem()).getMonth();
                int day = (Integer) dayComboBox.getSelectedItem();

                Calendar c = new GregorianCalendar(year, month, day);
                setDate(c);
            }
        });

        //panel.add(ftf);

        // выпадающий список с днем 
        dayComboBox = new JComboBox();
        for (int i = 0; i <= 31; i++){
            dayComboBox.addItem(Integer.valueOf(i));
        }
        dayComboBox.setSelectedItem(Integer.valueOf(24));

        // выпадающий список с месяцами
        String[] monthNames = new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август" , "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        monthComboBox = new JComboBox();
        for (int i = 0; i < 12; i++){
            monthComboBox.addItem(new Month(i + 1, monthNames[i]));
        }
        monthComboBox.setMaximumRowCount(12);
        monthComboBox.setSelectedIndex(6);


        // выпадающий список с годами
        yearComboBox = new JComboBox();
        for (int i = 1900; i < 2000; i++){
            yearComboBox.addItem(Integer.valueOf(i));
        }
        yearComboBox.setSelectedItem(Integer.valueOf(1983));

        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);

        LayoutManager

        panel.add(button);

        add(panel, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel(new BorderLayout(0, 0));

        birthDateLabel = new JLabel();
        panel2.add(birthDateLabel, BorderLayout.NORTH);
        additionalDgitsLabel = new JLabel();
        panel2.add(additionalDgitsLabel, BorderLayout.SOUTH);

        add(panel2, BorderLayout.EAST);
    }

    /**
     * Класс, представляющий месяц года
     */
    public class Month{

        /**
         * Номер месяца
         */
        private final int month;

        /**
         * Имя месяца
         */
        private final String name;

        /**
         * Конструктор
         * @param month номер месяца
         * @param name имя месяца
         */
        public Month(int month, String name) {
            this.month = month;
            this.name = name;

        }

        /**
         * Получение номера месяца
         * @return
         */
        public int getMonth() {
            return month;
        }

        /**
         * Получение имени месяцаы
         * @return
         */
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
