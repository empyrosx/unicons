/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.ui.base;

import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 * Класс болванки
 * @author Admin
 */
public class BaseTemplate extends JPanel{

    /**
     * Конструктор
     */
    public BaseTemplate() {

        super(new BorderLayout(0, 0));
    }

    /**
     * Получение меню
     * @return
     */
    public JMenu getMenu(){
        return null;
    }
}

    

