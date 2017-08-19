/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

/**
 * ��������� ��� ������ � �������������
 * @author ��������
 */
public interface SharedParams {

    /**
     * �������� �������� ��������� ��
     * @param paramName
     * @return
     */
    public Object getParamValue(String paramName);

    /**
     * ���������� �������� ���������
     * @param paramName
     * @param paramValue
     */
    public void setParamValue(String paramName, Object paramValue);

    /**
     * ��������� ���������
     * @param paramName ��� ���������
     * @return
     */
    public SharedParam getParam(String paramName);

    /**
     * ��������� ���������� ����������
     * @return
     */
    public int getParamCount();

    /**
     * ���������� ���������
     * @param paramName ��� ���������
     * @return
     */
    public SharedParam addParam(String paramName);


    /**
     * ��������� ������ ��������� �� ������ ����������
     */
    public interface SharedParam {

        /**
         * ��������� ����� ���������
         * @return
         */
        public String getName();

        /**
         * ��������� �������� ���������
         * @return
         */
        public Object getValue();

        /**
         * ��������� �������� ���������
         * @param value ����� ��������
         */
        public void setValue(Object value);
    }
}
