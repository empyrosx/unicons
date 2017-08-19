package ru.diman.swing.table;

import ru.diman.swing.table.columns.TableColumnX;
import ru.diman.swing.table.columns.TableColumnModelX;
import ru.diman.swing.table.header.JTableHeaderX;
import ru.diman.swing.table.header.TableHeaderXUI;
import ru.diman.swing.table.header.TableHeaderEvent;
import ru.diman.swing.table.header.TableHeaderListener;
import ru.diman.swing.table.header.ColumnHeaderImpl;
import ru.diman.swing.table.header.ColumnHeader;
import javax.swing.table.JTableHeader;
import java.util.Iterator;
import java.util.Stack;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Сетка данных с поддержкой иерархических заголовков и возможност скрытия колонок
 * @author Admin
 */
public class JTableX extends JTable {

    /**
     * Колоночная модель таблицы (содержит все колонки, в отличие от ColumnModel)
     */
    private TableColumnModelX columnsModel;
    /**
     * Отображать левую панель с картинками для каждой строки
     */
    private boolean showImagesPane;
    /**
     * Контейнер на котором будет расположена данная сетка данных
     */
    private JScrollPane container;
    /**
     * Набор картинок для каждой строки
     */
    private ImagesManager imagesManager;

    /**
     * Дополнительный рисовальщик
     */
    private TableRenderer baseRenderer;

    public TableRenderer getBaseRenderer() {
        return baseRenderer;
    }

    public void setBaseRenderer(TableRenderer baseRenderer) {
        this.baseRenderer = baseRenderer;
    }

    /**
     * Конструктор
     * @param dm модель данных
     * @param columnsModel
     */
    public JTableX(TableModel dm, TableColumnModelX columnsModel) {

        super(dm);

        // устанавливаем колоночную модель
        this.columnsModel = columnsModel;

        setFocusable(true);

        // отключаем выделение строк
        setRowSelectionAllowed(false);

        // отключаем авторассчет ширины колонок
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // создаем модель колонок для отображения
        TableColumnModel cm = new DefaultTableColumnModel();

        setColumnModel(cm);

        // создаем заголовок с поддержкой вложенности
        if (columnsModel != null) {
            setColumnsModelX(columnsModel);
        }

        container = new JScrollPane(this);

        imagesManager = new ImagesManagerImpl();
        imagesManager.addListener(new RowImageListener() {

            @Override
            public void imageChanged(int row) {
                container.getRowHeader().repaint();
            }
        });

        setShowImagesPane(false);

        putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
    }

    public void setColumnsModelX(TableColumnModelX columnsModel) {
        
        TableColumnModel cm = new DefaultTableColumnModel();
        JTableHeaderX header = new JTableHeaderX(cm);

        buildHeader(header);
        createCols();

        setTableHeader(header);
        getTableHeader().setUI(new TableHeaderXUI());

    }

    @Override
    public void setTableHeader(JTableHeader tableHeader) {
        super.setTableHeader(tableHeader);

        // если это заголовок с поддержкой вложенности, то добавляем необходимых
        // слушателей
        if (tableHeader instanceof JTableHeaderX) {
            ((JTableHeaderX) tableHeader).addListener(new TableHeaderListener() {

                @Override
                public void columnChangesVisibility(TableHeaderEvent e) {

                    refresh();
                }

                @Override
                public void columnChangesCollapse(TableHeaderEvent e) {
                    refresh();
                }
            });
        }
    }

    public void buildHeader(JTableHeaderX headerX) {

        Iterator<TableColumnX> columns = columnsModel.getColumns();

        // создаем стек для обработки вложенных элементов
        Stack<ColumnHeader> stack = new Stack<ColumnHeader>();

        // обходим все колонки
        while (columns.hasNext()) {

            // получаем очередную колонку
            TableColumnX tc = columns.next();

            // создаем новый заголовок
            ColumnHeaderImpl header = (ColumnHeaderImpl) tc.getHeader();

            // добавляем его в набор
            headerX.addHeader(header);

            // добавляем в него все элемента из стека, уровень которых больше его на 1
            while (!stack.empty()) {
                ColumnHeader sHeader = stack.peek();

                // если уровень элемент, лежащего в стеке такой же, то просто извлекаем его
                if (sHeader.getLevel() >= header.getLevel()) {
                    stack.pop();
                } else {
                    // если уровень элемента лежащего в стеке меньше чем текущий, значит
                    // текущий является вложенным по отношению к нему
                    if (sHeader.getLevel() == header.getLevel() - 1) {

                        // добавляем
                        ((ColumnHeaderImpl) sHeader).addNestedHeader(header);
                    }
                    break;
                }
            }

            stack.push(header);
        }
    }

    private void createCols() {

        TableColumnModel cm = getColumnModel();
        while (cm.getColumnCount() > 0) {
            cm.removeColumn(cm.getColumn(0));
        }

        for (int i = 0; i < columnsModel.getColumnCount(); i++) {

            TableColumnX tc = columnsModel.getColumn(i);

            // скрытые колонки пропускаем
            if (!tc.getHeader().isVisible()) {
                continue;
            }

            // колонки, которые имеют вложенные колонки и не свернуты пропускаем
            if (tc.getHeader().hasNestedHeaders() && !tc.getHeader().isCollapsed()) {
                continue;
            }

            // добавляем колонку
            addColumn(tc);
        }
    }

    /**
     * Обновление ColumnModel
     */
    public void refresh() {
        createCols();
    }

    /**
     * Колоночная модель
     * @return
     */
    public TableColumnModelX getColumnsModel() {
        return columnsModel;
    }

    /**
     * Получение видимости панели картинок
     * @return
     */
    public boolean getShowImagesPane() {
        return showImagesPane;
    }

    /**
     * Установка видимости панели картинок
     * @param value
     */
    public void setShowImagesPane(boolean value) {

        if (value != showImagesPane) {
            showImagesPane = value;

            if (showImagesPane) {
                JList rowHeader = new DefaultRowHeader(this);
                container.setRowHeaderView(rowHeader);
            } else {
                container.setRowHeader(null);
            }
        }
    }

    /**
     * Получение контейнера на котором располагается данная сетка данных
     * @return
     */
    public JScrollPane getContainer() {
        return container;
    }

    /**
     * Получение набора картинок для данной строки
     * @return
     */
    public ImagesManager getImagesManager() {
        return imagesManager;
    }

    public interface TableRenderer{

        public void renderComponent(JComponent component, int row, int column);
    }
}
