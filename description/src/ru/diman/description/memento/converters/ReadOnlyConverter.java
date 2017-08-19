/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

/**
 *
 * @author Admin
 */
public class ReadOnlyConverter implements ValueConverter{

    @Override
    public Object convertInputValue(Object value) {
        String strValue = (String) value;
        if (!strValue.isEmpty()){
            Integer required = Integer.valueOf(strValue);
            if ((required.intValue() & 1024) != 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    @Override
    public Object convertOutputValue(Object value) {

        Boolean readOnly = (Boolean) value;

        Integer required = 0;
        if (readOnly){
            required = 1024;
        }

        return required;    }


}
