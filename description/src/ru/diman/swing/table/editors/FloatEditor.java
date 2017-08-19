package ru.diman.swing.table.editors;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class FloatEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 5545003407613992615L;
    // редактор
    private JFormattedTextField ftf;
    // формат
    private NumberFormat format;

    /**
     * Конструктор
     */
    public FloatEditor() {
        super(new JFormattedTextField());

        // получаем редактор
        ftf = (JFormattedTextField) getComponent();

        // устанавливаем бордюр
        ftf.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        // создаем формат
        format = NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);

        // устанавливаем формат
        NumberFormatter formatter = new NumberFormatter(format);
        ftf.setFormatterFactory(new DefaultFormatterFactory(formatter));
    }

    ;

    // перекрываем метод для того, чтобы установить начальное значение для ввода
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // устанавливаем значение
        if (value != null) {
            ftf.setText(format.format(value));
        } else {
            ftf.setValue(null);
        }
        return ftf;
    }

    /**
     * Получение значения из редактора
     */
    @Override
    public Object getCellEditorValue() {

        // разбираем текст из редактора, если текст не удовлетворяет формату устанавливаем null
        try {
            Number n = format.parse(ftf.getText());
            return n.doubleValue();
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
