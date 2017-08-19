/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import kitedit.forms.views.actions.AddPageAction;
import kitedit.forms.views.actions.NewKitAction;
import kitedit.forms.views.actions.OpenKitAction;
import kitedit.forms.views.actions.SaveKitAction;
import ru.diman.editors.uniedit.controllers.Controller;

/**
 *
 * @author Admin
 */
public class ActionManager {


    /**
     * Действие "Создать новый"
     */
    public static String NEW_ACTION = "new";


    /**
     * Действие "Открыть"
     */
    public static String OPEN_ACTION = "open";


    /**
     * Действие "Сохранить"
     */
    public static String SAVE_ACTION = "save";


    /**
     * Действие "Добавить страницу"
     */
    public static String ADD_PAGE_ACTION = "addpage";


    /**
     * Действие "Удалить страницу"
     */
    public static String DELETE_PAGE_ACTION = "deletepage";


    /**
     * Карта действий
     */
    private final Map<String,Action> actionMap;

    /**
     * Контроллер
     */
    private Controller controller;

    /**
     * Конструктор
     * @param controller
     */
    public ActionManager(Controller controller) {

        // устанавливаем контроллер
        this.controller = controller;

        // создаём карту действий
        actionMap = createActionMap();

    }

    /**
     * Создание и заполнение карты действий
     */
    private Map<String,Action> createActionMap(){

        // создаем карту действий
        Map<String,Action> result = new HashMap<String,Action>();

        // Действие "Создать новый"
        result.put(NEW_ACTION, new NewKitAction(controller));

        // Действие "Открыть"
        result.put(OPEN_ACTION, new OpenKitAction(controller));

        // Действие "Сохранить"
        result.put(SAVE_ACTION, new SaveKitAction(controller));

        // Действие "Добавить страницу"
        result.put(ADD_PAGE_ACTION, new AddPageAction(controller));

        // Действие "Удалить страницу"
        //result.put(DELETE_PAGE_ACTION, new DeletePageAction(controller, this));

        return result;
    }

    /**
     * Получение действия по идентификатору
     * @param actionName
     * @return
     */
    public Action getAction(String actionName){
        return actionMap.get(actionName);
    }
}
