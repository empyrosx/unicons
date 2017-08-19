/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.column.types;

/**
 *
 * @author Димитрий
 */
public enum AutoSize {

    /**
     * Автоматический формат отсутсвует
     */
    AUTO_NONE,

    /**
     * Автоматическое выравнивание по ширине
     */
    AUTO_WIDTH,

    /**
     * Автоматическое выравнивание по высоте
     */
    AUTO_HEIGHT;

    /**
     *
     * @param value
     * @return
     */
    public static AutoSize valueOf(int value){

        switch (value){
            case 1:
                return AUTO_WIDTH;
            case 2:
                return AUTO_HEIGHT;
            default:
                return AUTO_NONE;
        }
    }


}
