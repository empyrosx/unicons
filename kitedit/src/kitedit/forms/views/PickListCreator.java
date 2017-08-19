/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JToolBar;

/**
 * Класс для задания значений выпадающего списка
 * @author Admin
 */
public class PickListCreator extends JDialog{

    /**
     * Выпадающий список
     */
    private String pickList;

    /**
     * Модель данных
     */
    DefaultListModel dataModel;

    /**
     * Конструктор
     * @param pickList
     */
    public PickListCreator(String pickList) {

        super();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Редактор выпадающего списка");
        setSize(250, 200);

        dataModel = new DefaultListModel();
        String[] values = pickList.split(",");
        for (int i = 0; i < values.length; i++) {
            String string = values[i];
            dataModel.addElement(string);
        }

        JList list = new JList(dataModel);
        getContentPane().add(BorderLayout.NORTH, createToolBar());
        getContentPane().add(BorderLayout.CENTER, list);

        this.pickList = pickList;
    }

    /**
     * Создание панели инструментов
     * @return
     */
    private JToolBar createToolBar(){

        JToolBar result = new JToolBar(JToolBar.HORIZONTAL);

        // кнопка "Сохранить"
        JButton saveButton = new JButton("Сохранить");

        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                pickList = "";
                for (int i = 0; i < dataModel.getSize(); i++){
                    String value = (String) dataModel.get(i);
                    pickList = pickList + ',' + value;
                }
                
                if (pickList.startsWith(",")){
                    pickList = pickList.substring(1);
                }

                setVisible(false);
            }
        });
        result.add(saveButton);

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                dataModel.addElement("Элемент" + String.valueOf(dataModel.getSize()));
            }
        });
        result.add(addButton);

        return result;
    }

    public String getPickList(){
        return pickList;
    }
}
