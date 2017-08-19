/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento.converters;

import ru.diman.description.column.types.GroupType;

/**
 *
 * @author Admin
 */
public class GroupTypeConverter implements ValueConverter{

    @Override
    public Object convertInputValue(Object value) {
        
        String strValue = (String) value;
        if (!strValue.isEmpty()){
            Integer intValue = Integer.valueOf(strValue);
            GroupType groupType = GroupType.valueOf(intValue);
            return groupType;
        }
        return null;
    }

    @Override
    public Object convertOutputValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
