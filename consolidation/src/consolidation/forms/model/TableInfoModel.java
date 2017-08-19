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
     * ��� ������
     */
    ModelKind modelKind;


    /**
     * ����� �������� �����
     */
    private List<String> keyColumnNames;

    /**
     * ���� ��������
     */
    private String incrementColumnName;

    /**
     * �����������
     * @param ti
     * @param rows 
     */
    public TableInfoModel(TableInfo ti, DataSet rows, ModelKind modelKind) {
        super(ti, rows, null);

        this.modelKind = modelKind;

        // ������������� �������� ����� � ���� ��������
        initialize();
    }


    /**
     * ������� ���� ��������
     * @return
     */
    private boolean hasIncrementKey(){
        return !incrementColumnName.isEmpty();
    }

    /**
     * ������������� �������� ����� � ���� ��������
     */
    private void initialize(){

        keyColumnNames = new ArrayList<String>();
        incrementColumnName = "";

        for (int i = 0; i < ti.getCount(); i++){

            Column column = ti.getItem(i);

            // ���� ��� ����, �� ��������� � �����
            if (column.getColumnType().equals(ColumnType.KEY)){
                keyColumnNames.add(column.getName());
            }

            // ���� ��� �������, �� ������������� ���
            if (column.getColumnType().equals(ColumnType.INCREMENT_KEY)){
                incrementColumnName = column.getName();
            }
        }
    }


    /**
     * �������� ������ � ������
     * @param data ������
     */
    public void loadData(DataSet data){

        // � ����������� �� ���� ������ ��������� �� �������
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
     * �������� ������ ������������� �����
     *
     * @param data 
     */
    private void fixedLoadData(DataSet data) {

        // ������� ��� ������ ������
        for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

            // �������� ��������� ������
            DataRow dataRow = data.getRow(rowIndex);

            // ���� ������ � �������
            int matrixRow = matrix.indexOf(dataRow);

            if (matrixRow > -1) {

                // �������� ��� ����, ����� ���������� (��� ��������� � ������� � �� ����������� �����)
                for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

                    // �������� ��������� �������
                    Column ci = ti.getItem(columnIndex);

                    if (ci.getColumnType() != ColumnType.CALC) {

                        // �������� �������� �������
                        Object columnValue = data.getValue(rowIndex, columnIndex);

                        // ������������� ��������
                        matrix.setValue(matrixRow, columnIndex, columnValue);
                    }
                }
            } else {
                // ������� ��������� � ���, ��� �� ��� ������ �� ������� �����
                System.out.println("������ �� ������� � �������. �������� ������ ���������:");

                for (int i = 0; i < ti.getCount(); i++) {

                    // ������� ��������
                    System.out.println(ti.getItem(i).getName() + "=" + dataRow.getValue(i));
                }
            }
        }
    }

    /**
     * �������� ������ ������������� �����
     */
    private void lineLoadData(DataSet data) {

        // ������� ��� ������ ������
        for (int rowIndex = 0; rowIndex < data.getRowCount(); rowIndex++) {

            matrix.addRow(rowIndex);

            // �������� ��� ����, ����� ���������� (��� ��������� � ������� � �� ����������� �����)
            for (int columnIndex = 0; columnIndex < ti.getCount(); columnIndex++) {

                // �������� ��������� �������
                Column ci = ti.getItem(columnIndex);

                //if (ci.getColumnType() != ColumnType.CALC){

                // �������� �������� �������
                Object columnValue = data.getValue(rowIndex, columnIndex);

                // ������������� ��������
                matrix.setValue(rowIndex, columnIndex, columnValue);
                //}
            }
        }

    }

    /**
     * �������� ������ ������������� �����
     */
    private void mixedLoadData(DataSet data) {

        // ������� ��� ������ ������
        for (int i = 0; i < data.getRowCount(); i++) {

            // �������� ������, ���������� �� ��
            DataRow dataRow = data.getRow(i);

            // ���� ������
            int row = matrix.indexOf(dataRow);
            if (row > -1) {

                for (int j = 0; j < ti.getCount(); j++) {
                    Column ci = ti.getItem(j);

                    // ��� ������������� ����� (����������� � �������)
                    // ������������� �������� ������ ����� �����
                    // � ������ ��� ������� ���������� � ��� ��������
                    if ((ci.getColumnType() == ColumnType.VALUE)) {

                        // �������� ��������� ��������
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
                // ��������� ����� ������
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
     * ���������
     */
    public TableInfo getTableInfo() {
        return ti;
    }

    /**
     * ���������� ������ � �����
     */
    public void addRow(int rowBefore) {

        // ��������� ������
        int newRow = matrix.getRowCount();
        matrix.addRow(newRow);

        // ���� ���� ���� �������, �� ������������� ����������� ���
        if (hasIncrementKey()) {
            setIncrementValue(rowBefore, newRow);
        }
        
        fireTableRowsInserted(matrix.getRowCount() - 1, matrix.getRowCount() - 1);
    }

    /**
     * ����������� �������� �������� ����� ��� ������� ��������
     *
     * �������� ���� �������� ����� ����� ������, ����� ��������, ��������� ������������� �� ������ rowBefore
     * �������� �������� ��������������� � �������� ��������� �� ��������� �������� � ������ rowBefore
     * ��� ������ ������ ������ �� ���������� �������� ������ ��������� ������������� �� �������
     * @param rowBefore
     * @param newRow
     */
    private void setIncrementValue(int rowBefore, int newRow){

        // ������� ��� �������� ����
        for (int i = 0; i < keyColumnNames.size(); i++) {

            String columnName = keyColumnNames.get(i);
            Object keyValue = matrix.getValue(rowBefore, columnName);
            matrix.setValue(newRow, columnName, keyValue);
        }

        // �������� �������� �������� �� �������� ������
        Object keyValue = matrix.getValue(rowBefore, incrementColumnName);

        // ����������� �� �������
        if (keyValue != null) {
            keyValue = (Integer) keyValue + 1;
        } else {
            keyValue = 1;
        }

        int newIncrementValue = (Integer) keyValue;


        // ������� ����� ���� ����������� ����� � ���� ������ �������� �� 1
        for (int i = 0; i < matrix.getRowCount(); i++) {

            boolean isOurSection = true;

            // ���� ��� �������� ������ ���������, �� ��� ���� ������
            for (int j = 0; j < keyColumnNames.size(); j++){

                // �������� �������� ����� �������� ������
                Object sourceKeyValue = matrix.getValue(rowBefore, keyColumnNames.get(j));

                // �������� �������� ����� ���� ������
                Object currentKeyValue = matrix.getValue(i, keyColumnNames.get(j));

                if (sourceKeyValue != null){
                    isOurSection = isOurSection && (sourceKeyValue.equals(currentKeyValue));
                }
                else {
                    isOurSection = isOurSection && (currentKeyValue == null);
                }
            }

            if (isOurSection){

                // �������� �������� ��������
                Object value = matrix.getValue(i, incrementColumnName);

                // �������� �������� ������ ��� ������ ��� ����������� ������
                // �������� � ��� ����� ����������� � ��������� �������
                if (value != null) {
                    int currentIncrementValue = (Integer) value;

                    // ���� �������� �������� � ���� ������ �� ������ ������ ��������,
                    // �� �������� �������� ���� ����������� �� �������
                    if (currentIncrementValue >= newIncrementValue) {
                        matrix.setValue(i, incrementColumnName, currentIncrementValue + 1);
                    }
                } 
            }
        }

        // ������������� �������� �������� ��� ����� ������
        matrix.setValue(newRow, incrementColumnName, newIncrementValue);
    }


    /**
     * �������� �� ������� ����� ������ ������
     */
    public boolean canInsertRow(int row) {

        Column optionsColumn = ti.FindColumn("�����");
        if (optionsColumn != null) {
            Object value = matrix.getValue(row, "�����");
            if (value != null) {
                int optionsValue = (Integer) value;
                return ((optionsValue & 4) == 0);
            }
        }
        return true;
    }
}
