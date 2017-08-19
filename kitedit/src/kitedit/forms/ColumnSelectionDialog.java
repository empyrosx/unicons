/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kitedit.forms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.ColumnImpl;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.intities.Entity;
import ru.diman.intities.EntityLoader;
import ru.diman.intities.EntityManager;
import ru.diman.intities.EntityManagerImpl;
import ru.diman.swing.table.JTableX;
import ru.diman.swing.table.PropertiesModel;
import ru.diman.swing.table.columns.TableColumnModelX;


/**
 * Форма выбора колонок из CLM шаблонов
 * @author Диман
 */
public class ColumnSelectionDialog {

    // диалог выбора
    final JDialog dialog = new JDialog();

    // выбранные колонки
    TableInfo selectedColumns;

    // модель колонок
    PropertiesModel<Column> model;

    // сетка данных
    JTableX table;

    // список CLM
    JComboBox combo;

    /**
     * Конструктор
     */
    public ColumnSelectionDialog() {

        // создаем интерфейс ввода
        createUI();

        // по умолчанию выделяем первый элемент
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
        }

        KeyboardFocusManager.setCurrentKeyboardFocusManager(new MyFocusManager());
    }

    /**
     * Создание интерфейса ввода
     */
    private void createUI() {

        // создаем форму выбора
        dialog.setTitle("Выбор колонок из структурных шаблонов");
        dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        dialog.setSize(new Dimension(600, 400));
        dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
        JToolBar toolBar = createToolBar(dialog);

        JPanel panel = new JPanel(new BorderLayout(0, 0));

        dialog.getContentPane().add(BorderLayout.NORTH, toolBar);
        dialog.getContentPane().add(BorderLayout.CENTER, panel);

        // создаем выпадающий список со структурыми шаблонами
        combo = createCLMComboBox();
        panel.add(BorderLayout.NORTH, combo);

        // создаем сетку данных с колонками
        table = createTable();
        panel.add(BorderLayout.CENTER, new JScrollPane(table));

        // создаем нижнюю панель с перечнем горячих клавиш
        JPanel keyBindings = new JPanel();
        
        JLabel okLabel = new JLabel("\'Ctrl + Enter\' - кнопка \'ОК\'");
        okLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        keyBindings.add(okLabel);
        
        JLabel cancelLabel = new JLabel("\'Escape\' - кнопка \'Отмена\'");
        cancelLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        keyBindings.add(cancelLabel);
        
        JLabel comboLabel = new JLabel("\'Alt + C\' - выбрать структурный шаблон");
        comboLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        keyBindings.add(comboLabel);
        
        panel.add(BorderLayout.SOUTH, keyBindings);

        table.requestFocusInWindow();
    }

    /**
     * Отображает диалог выбора колонок из CLM шаблонов
     * @return набор колонок в виде раскладки
     */
    public TableInfo selectColumns() {

        dialog.setVisible(true);

        return selectedColumns;
    }

    /**
     * Создание панели кнопок управления
     * @param frame 
     * @return
     */
    private JToolBar createToolBar(final JDialog dialog) {

        // добавляем набор кнопок
        JToolBar toolBar = new JToolBar();

        // кнопка "ОК"
        Action okAction = new AbstractAction("OK") {

            /**
			 * 
			 */
			private static final long serialVersionUID = 3054450508633895729L;

			public void actionPerformed(ActionEvent e) {
                selectedColumns = getSelectedColumns();
                dialog.setVisible(false);
            }
        };
        okAction.putValue(Action.SMALL_ICON, new ImageIcon("d:/Diman/Images/ok.gif"));

        JButton okButton = new JButton(okAction);
        toolBar.add(okButton);


        // кнопка "Отмена"
        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                selectedColumns = null;
            }
        });
        cancelButton.setIcon(new ImageIcon("d:/Diman/Images/cancel2.gif"));
        toolBar.add(cancelButton);

        return toolBar;
    }

    /**
     * Создание выпадающего списка с шаблонами структуры
     * @param panel 
     */
    private JComboBox createCLMComboBox() {

        combo = new JComboBox();


        // получаем менеджер шаблонов структуры
        Iterator<Entity> entities = EntityLoader.loadEntities();
        EntityManager entityManager = new EntityManagerImpl(entities);

        // добавляем каждый шаблон структуры в выпадающий список
        for (int i = 0; i < entityManager.getCount(); i++) {
            Entity entity = entityManager.getItem(i);
            combo.addItem(new CLMItem(entity.getName(), entity.getTableInfo()));
        }

        // добавляем обработчик на выбор шаблона структуры из списка
        combo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // получаем выделенный шаблон структуры
                CLMItem clmItem = (CLMItem) combo.getSelectedItem();
                TableInfo tableInfo = clmItem.getTableInfo();

                model.setRows(tableInfo.iterator());

                // если список закрылся, то переводим фокус на колонки
                if (!combo.isPopupVisible()){
                    table.requestFocusInWindow();
                }
            }
        });

        return combo;
    }

    /**
     * Создание сетки данных
     * @return
     */
    private JTableX createTable() {

        // получаем раскладку текущего структурного шаблона
        //TableInfo tableInfo = getSelectedTableInfo();

        // создаем модель данных
        //model = new TableModelX(ti, new TableInfoValues(tableInfo));
        TableInfo newTable = new TableInfoImpl();
        model = new PropertiesModel<Column>(newTable.iterator(), Column.class);

        // получаем раскладку для отображения
        TableInfo ti = getShowTableInfo();

        // создаем таблицу
        TableColumnModelX columnModel =  new  ColumnModel(model, ti);
        JTableX result = new JTableX(model, columnModel);

        //result.setColumnModel(new ColumnModel(model, ti));

        result.setShowHorizontalLines(true);
        result.setRowSelectionAllowed(true);
        result.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // создаем сетку данных
        return result;
    }

    /**
     * Получение раскладки текущего шаблона структуры из списка
     * @return раскладку шаблона структуры
     */
    private TableInfo getSelectedTableInfo() {
        CLMItem clmItem = (CLMItem) combo.getSelectedItem();
        return clmItem.getTableInfo();
    }

    /**
     * Получение раскладки для отображения свойств колонок шаблона структуры в форме выбора
     * @return раскладку отображения
     */
    private TableInfo getShowTableInfo() {

        // создаем новую раскладку
        TableInfo ti = new TableInfoImpl();

        // добавляем колонку с именем колонки
        Column ci = new ColumnImpl();
        ci.setName("Name");
        ci.setWidth(200);
        ci.setReadOnly(true);
        ti.addColumn(ci);
        return ti;
    }

    /**
     * Получение помеченных колонок для включения в шаблон
     * @return раскладку
     */
    private TableInfo getSelectedColumns() {

        // создаем раскладку
        TableInfo result = new TableInfoImpl();
        
        int[] selectedRows = table.getSelectedRows();
        
        // получаем текущую раскладку структурного шаблона
        TableInfo ti = getSelectedTableInfo();

        int nameColumnIndex = table.convertColumnIndexToModel(0);
        
        // обходим все строки модели
        for (int i = 0; i < selectedRows.length; i++) {

            int row = selectedRows[i];

            // получаем имя отмеченной колонки
            String columnName = (String) model.getValueAt(row, nameColumnIndex);

            // ищем колонку в раскладке структурного шаблона
            Column column = ti.FindColumn(columnName);

            // добавляем колонку в результат
            result.addColumn(column);
        }

        return result;
    }

    /**
     *  Внутренний класс, используемый в качестве элемента выпадающего списка
     *  шаблонов структуры
     */
    class CLMItem {

        // имя шаблона структуры
        private final String name;

        // раскладка структурного шаблона
        private final TableInfo ti;

        /**
         * Конструктор
         * @param name имя шаблона структуры
         * @param ti раскладка шаблона структуры
         */
        public CLMItem(String name, TableInfo ti) {
            this.name = name;
            this.ti = ti;
        }

        @Override
        public String toString() {
            return name;
        }

        /**
         * Получение раскладки шаблона структуры
         * @return раскладку
         */
        TableInfo getTableInfo() {
            return ti;
        }
    }

    /**
     * Специальная обработка клавиатуры
     */
    class MyFocusManager extends DefaultKeyboardFocusManager {

        @Override
        public void processKeyEvent(Component focusedComponent, KeyEvent e) {

            super.processKeyEvent(focusedComponent, e);

            // нажатие ОК по Ctrl + Enter
            if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (e.isControlDown())) {

                selectedColumns = getSelectedColumns();
                dialog.setVisible(false);
                e.consume();
            }

            // нажатие Отмена по Escape
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                selectedColumns = null;
                dialog.setVisible(false);
                e.consume();
            }

            if ((e.getKeyCode() == KeyEvent.VK_C) && (e.isAltDown())) {

                if (!combo.hasFocus()) {
                    combo.requestFocusInWindow();
                }

                if (!combo.isPopupVisible()) {
                    combo.showPopup();
                }
            }
        }
    }
}
