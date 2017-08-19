/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit;

import javax.swing.JFrame;
import kitedit.forms.controllers.KitControllerImpl;
import kitedit.forms.views.KitView;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.uniedit.models.TableInfoModel;
import ru.diman.uniedit.models.UniModel;
import ru.diman.uniedit.models.UniModelImpl;
import ru.diman.uniedit.models.UniPageModel;
import ru.diman.uniedit.models.UniPageModelImpl;

/**
 *
 * @author Диман
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        UniModel model = new UniModelImpl(){

            @Override
            @SuppressWarnings("unchecked")
            protected UniPageModel createPageModel(TableInfoModel model, String pageName) {
                return new UniPageModelImpl(model, pageName);
            }

        };
        Controller controller = new KitControllerImpl(model);
        KitView view = new KitView(controller);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }

}
