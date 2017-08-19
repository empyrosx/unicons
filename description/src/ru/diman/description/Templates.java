package ru.diman.description;

/**
 * Интерфейс для управления шаблоном формы ввода
 * @author Диман
 */
public interface Templates {

    /**
     * Получение количества страниц шаблона
     * @return количетво страниц шаблона, 0 если шаблон пустой
     */
    public int getCount();

    /**
     * Получение страницы шаблона формы ввода
     * @param index индекс страницы шаблона
     * @return интерфейс страницы шаблона формы ввода Template если страница с
     * данным индексом существует, IndexOutOfBoundsException в противном случае
     */
    public Template getItem(int index);

    /**
     * Добавление страницы
     * @param template страница
     */
    public void add(Template template);
}
