/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

import consolidation.SharedParams.SharedParam;

/**
 *
 * @author Димитрий
 */
public class SharedParamImpl implements SharedParam{

    /**
     * Наименование параметра
     */
    private String name;

    /**
     * Значение параметра
     */
    private Object value;
    
    /**
     * Конструктор
     * @param name
     */
    public SharedParamImpl(String name) {
        this.name = name;
        this.value = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }
}
