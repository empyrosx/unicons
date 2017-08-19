/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.lang.reflect.Method;

/**
 *
 * @author Димитрий
 */
public class Property {

        private String columnName;
        private Method getMethod;
        private Method setMethod;

        /**
         *
         * @param columnName
         * @param getMethod
         * @param setMethod
         */
        public Property(String columnName, Method getMethod, Method setMethod) {
            this.columnName = columnName;
            this.getMethod = getMethod;
            this.setMethod = setMethod;
        }

        /**
         *
         * @return
         */
        public String getColumnName() {
            return columnName;
        }

        /**
         *
         * @param columnName
         */
        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        /**
         *
         * @return
         */
        public Method getGetMethod() {
            return getMethod;
        }

        /**
         *
         * @param getMethod
         */
        public void setGetMethod(Method getMethod) {
            this.getMethod = getMethod;
        }

        /**
         *
         * @return
         */
        public Method getSetMethod() {
            return setMethod;
        }

        /**
         *
         * @param setMethod
         */
        public void setSetMethod(Method setMethod) {
            this.setMethod = setMethod;
        }
    } 