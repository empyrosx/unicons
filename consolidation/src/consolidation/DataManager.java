/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

/**
 *
 * @author ��������
 */
public interface DataManager {

    /**
     * ��������� �������� �������
     * @param columnName
     * @return
     */
    public Object getValue(String columnName);

    /**
     * ��������� �������� �������
     * @param columnName ��� �������
     * @param value ��������
     */
    public void setValue(String columnName, Object value);

    /**
     * ����������� 
     * @param code ������ �����������
     * @param value �������� ��� �����������
     * @return
     */
    public Object dereference(String code, Object value);
}
