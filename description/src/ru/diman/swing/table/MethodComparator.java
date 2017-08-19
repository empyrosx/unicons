/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 *
 * @author Димитрий
 */
public class MethodComparator implements Comparator<Object> {

    public int compare(Object o1, Object o2) {

        Method m1 = (Method) o1;
        Method m2 = (Method) o2;

        return m1.getName().compareTo(m2.getName());
    }
} 