/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.controllers;

import ru.diman.editors.uniedit.controllers.PageControllerImpl;
import ru.diman.uniedit.models.UniPageModel;

/**
 * Контроллер для связи модели и представления
 * @author Диман
 */
public class KitPageControllerImpl extends PageControllerImpl{

    /**
     * Конструктор
     * @param model
     */
    public KitPageControllerImpl(UniPageModel model) {

        super(model);
    }
}
