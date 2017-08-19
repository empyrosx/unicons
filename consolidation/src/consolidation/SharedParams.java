/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

/**
 * Интерфейс для работы с ограничениями
 * @author Димитрий
 */
public interface SharedParams {

    /**
     * Получить значение параметра по
     * @param paramName
     * @return
     */
    public Object getParamValue(String paramName);

    /**
     * Установить значение параметра
     * @param paramName
     * @param paramValue
     */
    public void setParamValue(String paramName, Object paramValue);

    /**
     * Получение параметра
     * @param paramName имя параметра
     * @return
     */
    public SharedParam getParam(String paramName);

    /**
     * Получение количества параметров
     * @return
     */
    public int getParamCount();

    /**
     * Добавление параметра
     * @param paramName имя параметра
     * @return
     */
    public SharedParam addParam(String paramName);


    /**
     * Интерфейс одного параметра на панели параметров
     */
    public interface SharedParam {

        /**
         * Получение имени параметра
         * @return
         */
        public String getName();

        /**
         * Получение значения параметра
         * @return
         */
        public Object getValue();

        /**
         * Установка значения параметра
         * @param value новое значение
         */
        public void setValue(Object value);
    }
}
