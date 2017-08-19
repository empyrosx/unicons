/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

/**
 *
 * @author Admin
 */
public class VisibleConverter implements ValueConverter {

    @Override
    public Object convertInputValue(Object value) {
        String strValue = (String) value;
        if (!strValue.isEmpty()){
            Integer required = Integer.valueOf(strValue);
            if ((required.intValue() & 32) != 0) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Object convertOutputValue(Object value) {
        Boolean visible = (Boolean) value;

        Integer required = 0;
        if (!visible){
            required = 32;
        }

        return required;
    }

}
