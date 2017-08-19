/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import kitedit.forms.views.actions.AddCLMColumnsAction;
import kitedit.forms.views.actions.AddPageAction;
import kitedit.forms.views.actions.DeletePageAction;
import kitedit.forms.views.actions.NewKitAction;
import kitedit.forms.views.actions.OpenKitAction;
import kitedit.forms.views.actions.SaveKitAction;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.uniedit.views.UniPageEvent;
import ru.diman.uniedit.views.UniView;
import ru.diman.uniedit.views.listeners.ViewListener;


/**
 *
 * @author Диман
 */
public class KitViewImpl extends JPanel implements UniView, ViewListener{


    /**
	 *
	 */
	private static final long serialVersionUID = 6088251160510374920L;


	/**
     * Контроллер
     */
    private final Controller controller;


    /**
     * Набор страниц
     */
    private JTabbedPane tabbedPane;


    /**
     * Карта действий
     */
    private final Map<String,Action> actionMap;


    /**
     * Менеджер действий
     */
    private ActionManager actionManager;

    /**
     * Действие "Добавить колонки из структурного шаблона"
     */
    private static String ADD_COLUMNS_FROM_CLM_ACTION = "addcolumnsfromclm";


    /**
     * Действие "Добавить колонку"
     */
    private static String ADD_COLUMN = "addcolumn";


    /**
     * Действие "Удалить колонку"
     */
    private static String DELETE_COLUMN = "deletecolumn";


    /**
     * Конструктор
     * @param controller
     */
    public KitViewImpl(Controller controller) {

        // устанавливаем контроллер
        this.controller = controller;

        // добавляем себя в контролер для получения уведомлений об изменении
        controller.addView(this);

        // создаем карту действий
        actionMap = createActionMap();

        actionManager = new ActionManager(controller);
        
        // создаем интерфейс
        createUI();
    }


    /**
     * Создание интерфейса
     */
    private void createUI() {

        // устанавливаем раскладчик
        setLayout(new BorderLayout());


        // создаем набор закладок
        tabbedPane = new JTabbedPane();
        add(BorderLayout.CENTER, tabbedPane);

        // обходим все страницы и добавляем для каждой закладку
        for (int i = 0; i < controller.getCount(); i++) {

            // получаем очередной контроллер
            PageController pageController = controller.getItem(i);

            // создаем страничное представление
            KitPageView view = new KitPageView(pageController);

            // получаем имя страницы
            String pageName = pageController.getPageName();

            // добавляем представление
            tabbedPane.addTab(pageName, view);
        }

        // добавляем всплывающее меню
        tabbedPane.setComponentPopupMenu(createPopupMenu());

        // добавляем панель управления
        add(BorderLayout.NORTH, createToolBar());
    }


    /**
     * Обновление представления
     */
    public void refresh() {

        // запоминаем индекс страницы
        int currentPage = getCurrentPage();

        tabbedPane.removeAll();

        // обходим все страницы и добавляем для каждой закладку
        for (int i = 0; i < controller.getCount(); i++) {

            // получаем очередной контроллер
            PageController pageController = controller.getItem(i);

            // создаем страничное представление
            KitPageView view = new KitPageView(pageController);

            // получаем имя страницы
            String pageName = pageController.getPageName();

            // добавляем представление
            tabbedPane.addTab(pageName, view);
        }

        while (currentPage >= tabbedPane.getTabCount()){
            currentPage--;
        }

        if (currentPage > -1){
            tabbedPane.setSelectedIndex(currentPage);
        }

        // блокировка кнопок

        // удаление страницы доступно только при наличии страниц
        //getAction(DELETE_PAGE_ACTION).setEnabled(controller.getCount() > 0);
    }


    /**
     * Получение текущей страницы
     */
    public int getCurrentPage(){
        return tabbedPane.getSelectedIndex();
    }


    /**
     * Создание и заполнение карты действий
     */
    private Map<String,Action> createActionMap(){

        // создаем карту действий
        Map<String,Action> result = new HashMap<String,Action>();

        // действие "Добавить колонки из CLM"
        result.put(ADD_COLUMNS_FROM_CLM_ACTION, new AddCLMColumnsAction(controller, this));

        return result;
    }


    /**
     * Получение действия по идентификатору
     * @param actionKey идентификатор
     */
    private Action getAction(String actionKey){
        //return actionMap.get(actionKey);
        return actionManager.getAction(actionKey);
    }


    /**
     * Создание всплывающего меню
     */
    private JPopupMenu createPopupMenu(){

        // добавляем всплывающее меню
        JPopupMenu popup = new JPopupMenu();

        // переименовать страницу
        JMenuItem renamePage = new JMenuItem("Переименовать");
        renamePage.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                String pageName = JOptionPane.showInputDialog("Укажите новое имя страницы");

                if (!pageName.isEmpty()){

                    // получаем индекс активной страницы
                    int pageIndex = tabbedPane.getSelectedIndex();

                    // устанавливаем новое имя страницы
                    PageController pageController = controller.getItem(pageIndex);
                    pageController.setPageName(pageName);
                }
            }
        });
        popup.add(renamePage);

        popup.add(new JSeparator());

        // добавление страницы
        //popup.add(getAction(ADD_PAGE_ACTION));
        //popup.add(getAction(DELETE_PAGE_ACTION));

        return popup;
    }


    /**
     * Создание панели кнопок
     */
    private JToolBar createToolBar(){

        // создаем набор кнопок
        JToolBar result = new JToolBar();

        // добавляем действия
        result.add(getAction(ActionManager.NEW_ACTION));
        result.add(getAction(ActionManager.OPEN_ACTION));
        result.add(getAction(ActionManager.SAVE_ACTION));
        result.add(getAction(ActionManager.ADD_PAGE_ACTION));
        result.add(getAction(ActionManager.DELETE_PAGE_ACTION));
        //result.add(getAction(ActionManager.ADD_COLUMNS_FROM_CLM_ACTION));

        return result;
    }

    public void pageAdded(UniPageEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pageRemoved(UniPageEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pageNameChanged(UniPageEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateAll(UniPageEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
