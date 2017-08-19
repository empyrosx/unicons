/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import kitedit.forms.views.actions.NewKitAction;
import kitedit.forms.views.actions.OpenKitAction;
import ru.diman.editors.uniedit.controllers.Controller;
import ru.diman.editors.uniedit.controllers.PageController;
import ru.diman.uniedit.views.UniPageEvent;
import ru.diman.uniedit.views.UniView;

/**
 *
 * @author Admin
 */
public class KitView extends JFrame implements UniView{

    /**
     * Контроллер
     */
    private Controller controller;

    /**
     * Набор страниц
     */
    private JTabbedPane pages;

    /**
     * Набор открытых окон
     */
    private List<KitPageView> views;

    /**
     * Конструктор
     * @param controller
     */
    public KitView(Controller controller) {

        setLayout(new BorderLayout());

        // устанавливаем размер по умолчанию
        setSize(new Dimension(800, 600));

        // устанавливаем заголовок формы
        setTitle("Редактор шаблонов маппинга");

        // устанавливаем контроллер
        this.controller = controller;

        this.views = new ArrayList<KitPageView>();

        this.pages = new JTabbedPane(JTabbedPane.TOP);
        add(BorderLayout.CENTER, pages);

        // добавляем себя для получения уведомлений
        controller.addView(this);

        // создаем меню
        setJMenuBar(createMenuBar());

        // добавляем панель инструментов
        add(BorderLayout.NORTH, createToolBar());
    }

    public void pageAdded(UniPageEvent e) {

        // получаем очередной контроллер
        PageController pageController = controller.getItem(e.getPageIndex());

        KitPageView pageView = new KitPageView(pageController);
        pageView.setVisible(true);

        views.add(pageView);
        //desktop.add(pageView);
        pages.addTab(pageController.getPageName(), pageView);
    }

    public void pageRemoved(UniPageEvent e) {

        // получаем удаляемое представление
        KitPageView view = views.get(e.getPageIndex());

        // удаляем страницу
        pages.removeTabAt(e.getPageIndex());

        // удаляем окно из списка
        views.remove(view);

    }

    public void pageNameChanged(UniPageEvent e) {

        // получаем индекс страницы
        int pageIndex = e.getPageIndex();

        // обновляем имя страницы
        pages.setTitleAt(pageIndex, controller.getItem(pageIndex).getPageName());

    }

    public void updateAll(UniPageEvent e) {
        
    }

    /**
     * Создание меню
     * @return
     */
    private JMenuBar createMenuBar(){

        JMenuBar result = new JMenuBar();

        // операции с файлами
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_A);

        // создать новый
        fileMenu.add(new NewKitAction(controller));

        // открыть файл...
        fileMenu.add(new OpenKitAction(controller));

        /*
        // разделитель
        fileMenu.add(new JSeparator());

        // сохранить
        fileMenu.add(new SaveFileAction(controller, this));

        // сохранить как
        fileMenu.add(new SaveFileAsAction(controller, this));


        // разделитель
        fileMenu.add(new JSeparator());

        // выход
        fileMenu.add(new AbstractAction("Выход") {

            @Override
            public void actionPerformed(ActionEvent e) {
                CLMView.this.setVisible(false);
                CLMView.this.dispose();
            }
        });

         */
        result.add(fileMenu);

        return result;
    }

    /**
     * Создание панели инструментов
     * @return
     */
    private JToolBar createToolBar(){

        JToolBar result = new JToolBar();

        // фиксируем панель сверху
        result.setFloatable(false);

        JButton newFileButton = new JButton(new NewKitAction(controller));
        newFileButton.setFocusable(false);
        result.add(newFileButton);

        
        JButton openFileButton = new JButton(new OpenKitAction(controller));
        openFileButton.setFocusable(false);
        result.add(openFileButton);

        /*
        JButton saveFileButton = new JButton(new SaveFileAction(controller, this));
        saveFileButton.setFocusable(false);
        result.add(saveFileButton);

        JButton saveFileAsButton = new JButton(new SaveFileAsAction(controller, this));
        saveFileAsButton.setFocusable(false);
        result.add(saveFileAsButton);

         */
        return result;
    }
}
