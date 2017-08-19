/*
 * EnumColumnEditor.java
 *
 * Created on 29 Март 2009 г., 16:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.diman.swing.table.editors;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author Димитрий
 */
public class EnumColumnEditor extends DefaultCellEditor{



    /** Creates a new instance of EnumColumnEditor
     * @param enumClass 
     */
    public EnumColumnEditor(Class enumClass) {

        super(new JComboBox());

        JComboBox editor = (JComboBox)getComponent();

        Object[] enums = enumClass.getEnumConstants();
        for (int i = 0; i < enums.length; i++) {
            editor.addItem(enums[i]);
        }

        setClickCountToStart(2);
    }
}
