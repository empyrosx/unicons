package consolidation.forms.model;

import consolidation.forms.model.DataSetModelX.ModelKind;
import java.util.ArrayList;
import java.util.List;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.matrix.DataRow;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;

public class TableInfoModel extends DataSetModel {

    private static final long serialVersionUID = -3558449768116208864L;

    /**
     * Вид модели
     */
    ModelKind modelKind;


    /**
     * Набор ключевых полей
     */
    private List<String> keyColumnNames;

    /**
     * Поле счетчика
     */
    private String incrementColumnName;

    /**
     * конструктор
     * @param ti
     * @param rows 
     */
    public TableInfoModel(TableInfo ti, DataSet rows, ModelKind modelKind) {
        super(ti, rows, null);

        this.modelKind = modelKind;

        // инициализация ключевых полей и поля счетчика
        initialize();
    }


    /**
     * Наличие поля счетчика
     * @return
     */
    private boolean hasIncrementKey(){
        return !incrementColumnName.isEmpty();
    }

    /**
     * Инициализация ключевых полей и поля счетчика
     */
    private void initialize(){

        keyColumnNames = new ArrayList<String>();
        incrementColumnName = "";

        for (int i = 0; i < ti.getCount(); i++){

            Column column = ti.getItem(i);

            // если это ключ, то добавляем в набор
            if (column.getColumnType().equals(ColumnType.KEY)){
                keyColumnNames.add(column.getName());
            }

            // если это счетчик, до устанавливаем его
            if (column.getColumnType().equals(ColumnType.INCREMENT_KEY)){
                incrementColumnName = column.getName();
            }
        }
    }


    /**
     * Загрузка данных в модель
     * @param data данные
     */
    public void loadData(DataSet data){

        // в зависимости от типа модели загружаем по разному
        switch (modelKind){
            case FIXED: {
                fixedLoadData(data);
                break;
            }

            case COMPLEX: {
                mixedLoadData(data);
                break;
            }

            default: {
                lineLoadData(data);
            }
        }
    }

    /**
     * Загрузка жестко фиксированной формы
     *
     * @param data 
     */
    private void fixedLoadData(DataSet data) {

        // обходим все строки данных
        for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

            // получаем очередную запись
            DataRow dataRow = data.getRow(rowIndex);

            // ищем запись в матрице
            int matrixRow = matrix.indexOf(dataRow);

            if (matrixRow > -1) {

                // копируем все поля, кроме вычислимые (они прописаны в шаблоне и не сохраняются вовне)
                for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

                    // получаем очередную колонку
                    Column ci = ti.getItem(columnIndex);

                    if (ci.getColumnType() != ColumnType.CALC) {

                        // получаем значение колонки
                        Object columnValue = data.getValue(rowIndex, columnIndex);

                        // устанавливаем значение
                        matrix.setValue(matrixRow, columnIndex, columnValue);
                    }
                }
            } else {
                // выводим сообщение о том, что не для строки не нашлось места
                System.out.println("Запись не найдена в шаблоне. Атрибуты записи следующие:");

                for (int i = 0; i < ti.getCount(); i++) {

                    // выводим атрибуты
                    System.out.println(ti.getItem(i).getName() + "=" + dataRow.getValue(i));
                }
            }
        }
    }

    /**
     * Загрузка жестко фиксированной формы
     */
    private void lineLoadData(DataSet data) {

        // обходим все строки данных
        for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

            matrix.addRow(rowIndex);

            // копируем все поля, кроме вычислимые (они прописаны в шаблоне и не сохраняются вовне)
            for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

                // получаем очередную колонку
                Column ci = ti.getItem(columnIndex);

                //if (ci.getColumnType() != ColumnType.CALC){

                // получаем значение колонки
                Object columnValue = data.getValue(rowIndex, columnIndex);

                // устанавливаем значение
                matrix.setValue(rowIndex, columnIndex, columnValue);
                //}
            }
        }

    }

    /**
     * Загрузка жестко фиксированной формы
     */
    private void mixedLoadData(DataSet data) {

        // обходим все строки данных
        for (int i = 0; i < data.getRowCount(); i++) {

            // получаем запись, закачанную из БД
            DataRow dataRow = data.getRow(i);

            // ищем запись
            int row = matrix.indexOf(dataRow);
            if (row > -1) {

                for (int j = 0; j < ti.getCount(); j++) {
                    Column ci = ti.getItem(j);

                    // для фиксированных строк (прописанных в шаблоне)
                    // устанавливаем значения только полей ввода
                    // и только при условии отсутствия в них значений
                    if ((ci.getColumnType() == ColumnType.VALUE)) {

                        // получаем матричное значение
                        Object matrixValue = matrix.getValue(row, j);
                        if (matrixValue == null){
                            Object columnValue = data.getValue(i, j);
                            matrix.setValue(row, j, columnValue);
                        }
                    }
                }
            } else {
                int rowIndex = matrix.getRowCount();
                matrix.addRow(rowIndex);
                for (int j = 0; j < ti.getCount(); j++) {
                    Column ci = ti.getItem(j);

                    if (ci.getColumnType() != ColumnType.CALC) {
                        Object columnValue = data.getValue(i, j);
                        matrix.setValue(rowIndex, j, columnValue);
                    }
                }
            }
        }
    }

    public DataSet getData() {

        DataSet result = new DataRowSet(ti);

        int rowIndex = 0;
        for (int i = 0; i < matrix.getRowCount(); i++) {

            DataRow dataRow = matrix.getRow(i);
            if (1 == 1) {/*(dataRow.hasValues()){*/
                // добавляем новую запись
                result.addRow(result.getRowCount());

                for (int j = 0; j < ti.getCount(); j++) {
                    result.setValue(rowIndex, j, dataRow.getValue(j));
                }
                rowIndex++;
            }

        }

        return result;

    }

    public Class<?> getColumnClass(int columnIndex) {
        Column ci = ti.getItem(columnIndex);

        if (ci.getValueType() == ValueType.INTEGER) {
            return Integer.class;
        } else {
            return String.class;
        }

    }

    /**
     * раскладка
     */
    public TableInfo getTableInfo() {
        return ti;
    }

    /**
     * добавление строки в конец
     */
    public void addRow(int rowBefore) {

        // вставляем запись
        int newRow = matrix.getRowCount();
        matrix.addRow(newRow);

        // если есть поле счетчик, то дополнительно проставляем его
        if (hasIncrementKey()) {
            setIncrementValue(rowBefore, newRow);
        }
        
        fireTableRowsInserted(matrix.getRowCount() - 1, matrix.getRowCount() - 1);
    }

    /**
     * Простановка значений ключевых полей при наличии счетчика
     *
     * Значения всех ключевых полей новой строки, кроме счетчика, автоматом подставляются из строки rowBefore
     * Значение счетчика устанавливается в значение следующее за значением счетчика в строке rowBefore
     * Все строки данной секции со значениями счетчика больше исходного увеличиваются на еденицу
     * @param rowBefore
     * @param newRow
     */
    private void setIncrementValue(int rowBefore, int newRow){

        // обходим все ключевые поля
        for (int i = 0; i < keyColumnNames.size(); i++) {

            String columnName = keyColumnNames.get(i);
            Object keyValue = matrix.getValue(rowBefore, columnName);
            matrix.setValue(newRow, columnName, keyValue);
        }

        // получаем значение счетчика из исходной строки
        Object keyValue = matrix.getValue(rowBefore, incrementColumnName);

        // увеличиваем на единицу
        if (keyValue != null) {
            keyValue = (Integer) keyValue + 1;
        } else {
            keyValue = 1;
        }

        int newIncrementValue = (Integer) keyValue;


        // счетчик строк всех последующих строк в этой секции сдвигаем на 1
        for (int i = 0; i < matrix.getRowCount(); i++) {

            boolean isOurSection = true;

            // если все значения ключей совпадают, то это наша секция
            for (int j = 0; j < keyColumnNames.size(); j++){

                // получаем значение ключа исходной записи
                Object sourceKeyValue = matrix.getValue(rowBefore, keyColumnNames.get(j));

                // получаем значение ключа этой записи
                Object currentKeyValue = matrix.getValue(i, keyColumnNames.get(j));

                if (sourceKeyValue != null){
                    isOurSection = isOurSection && (sourceKeyValue.equals(currentKeyValue));
                }
                else {
                    isOurSection = isOurSection && (currentKeyValue == null);
                }
            }

            if (isOurSection){

                // получаем значение счетчика
                Object value = matrix.getValue(i, incrementColumnName);

                // значение счетчика пустое для только что добавленной записи
                // значение в ней будет установлено в последнюю очередь
                if (value != null) {
                    int currentIncrementValue = (Integer) value;

                    // если значение счетчика в этой записи не меньше нового значения,
                    // то значение счетчика надо передвинуть на единицу
                    if (currentIncrementValue >= newIncrementValue) {
                        matrix.setValue(i, incrementColumnName, currentIncrementValue + 1);
                    }
                } 
            }
        }

        // устанавливаем значение счетчика для новой записи
        matrix.setValue(newRow, incrementColumnName, newIncrementValue);
    }


    /**
     * Возможна ли вставка после данной строки
     */
    public boolean canInsertRow(int row) {

        Column optionsColumn = ti.FindColumn("Опции");
        if (optionsColumn != null) {
            Object value = matrix.getValue(row, "Опции");
            if (value != null) {
                int optionsValue = (Integer) value;
                return ((optionsValue & 4) == 0);
            }
        }
        return true;
    }
}
