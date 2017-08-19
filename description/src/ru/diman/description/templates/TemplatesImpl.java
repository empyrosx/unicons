package ru.diman.description.templates;

import java.util.ArrayList;
import ru.diman.description.Template;
import ru.diman.description.Templates;

/**
 * Реализация интерфейса Templates
 * @author Диман
 */
public class TemplatesImpl implements Templates{

    /**
     * Набор страниц шаблона формы ввода
     */
    private ArrayList<Template> pages;

    /**
     * Конструктор
     */
    public TemplatesImpl() {
        pages = new ArrayList<Template>();
    }

    /**
     * Получение количества страниц шаблона
     * @return количество страниц шаблона
     */
    @Override
    public int getCount(){
        return pages.size();
    }

    /**
     * Получение страницы шаблона формы ввода
     * @param index индекс страницы шаблона
     * @return интерфейс страницы шаблона формы ввода Template если страница с
     * данным индексом существует, IndexOutOfBoundsException в противном случае
     */
    @Override
    public Template getItem(int index){
        return pages.get(index);
    }

    /**
     * Добавление страницы
     * @param template страница
     */
    @Override
    public void add(Template template){
        pages.add(template);
    }
}
