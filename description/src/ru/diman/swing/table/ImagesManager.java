package ru.diman.swing.table;

import java.util.List;
import javax.swing.Icon;

/**
 * Интерфейс для управления набором картинок для каждой записи
 * @author Димитрий
 *
 */
public interface ImagesManager {

	/**
	 * Получение набора картинок для данной строки
	 * @param rowIndex индекс строки
	 * @return
	 */
	public List<Icon> getImages(int rowIndex);

    /**
     * Удаление всех картинок
     */
    public void removeImages();

    /**
     * Удаление всех картинок для данной строки
     * @param rowIndex индекс строки
     */
    public void removeImages(int rowIndex);

    /**
     * Установка набора картинок для данной строки
     * @param rowIndex индекс строки
     * @param images набор картинок
     */
    public void setImages(int rowIndex, List<Icon> images);

    /**
     * Добавление картинки для данной строки
     * @param rowIndex индекс строки
     * @param image картинка
     */
    public void addImage(int rowIndex, Icon image);

    /**
     * Удаление картинки из данной строки
     * @param rowIndex индекс строки
     * @param image картинка
     */
    public void removeImage(int rowIndex, Icon image);

    /**
     * Добавление слушателя
     * @param l слушатель
     */
    public void addListener(RowImageListener l);

    /**
     * Удаление слушателя
     * @param l слушатель
     */
    public void removeListener(RowImageListener l);
}
