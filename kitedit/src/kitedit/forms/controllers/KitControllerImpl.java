/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.controllers;

import ru.diman.editors.uniedit.controllers.ControllerImpl;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.uniedit.models.UniModel;
import ru.diman.uniedit.models.UniPageModel;

/**
 *
 * @author Диман
 */
public class KitControllerImpl extends ControllerImpl<PageController>{


    /**
     * Конструктор
     * @param model модель
     */
    public KitControllerImpl(UniModel model) {

        super(model);
    }


    @Override
    protected PageController createPageController(UniPageModel pageModel) {
        return new KitPageControllerImpl(pageModel);
    }
}
