/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Димитрий
 */
public class PropertyExtractor {

    /**
     *
     * @param clazz
     * @return
     */
    public static List<Property> getProperties(Class<?> clazz){

        // создаем набор свойствы
        List<Property> properties = new ArrayList<Property>();

        Method[] methods = clazz.getMethods();
        Arrays.sort(methods, new MethodComparator());

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            String methodName = method.getName();

            // если это свойство на чтение
            boolean isReadMethod = methodName.startsWith("get")
                    && (method.getParameterTypes().length == 0);

            if (isReadMethod){
                // добавляем свойство в список
                properties.add(new Property(methodName.substring(3), method, null));
            }

            boolean isWriteMethod = methodName.startsWith("set")
                    && (method.getParameterTypes().length == 1);
//                    && (method.getReturnType() == null);

            if (isWriteMethod){

                for (int j = 0; j < properties.size(); j++) {
                    Property p = properties.get(j);

                    String propName = methodName.substring(3);
                    if (p.getColumnName().equals(propName)){
                        p.setSetMethod(method);
                        break;
                    }
                }
            }
        }

        return properties;
    }
} 