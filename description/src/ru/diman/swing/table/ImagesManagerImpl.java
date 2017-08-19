/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.swing.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;

/**
 * Реализация менеджера картинок для каждой записи для сетки данных
 * @author Admin
 */
public class ImagesManagerImpl implements ImagesManager{

    /**
     * Набор картинок для каждой записи
     */
    private Map<Integer,List<Icon>> recordImages;

    /**
     * Слушатели
     */
    private List<RowImageListener> listeners;

    /**
     * Конструктор
     */
    public ImagesManagerImpl() {
        this.recordImages = new HashMap<Integer, List<Icon>>();
        this.listeners = new ArrayList<RowImageListener>();
    }

    @Override
    public List<Icon> getImages(int rowIndex) {
        return recordImages.get(rowIndex);
    }

    @Override
    public void removeImages() {
        recordImages.clear();
        
        // уведомляем слушателей
        notifyListeners();
    }

    @Override
    public void removeImages(int rowIndex) {
        recordImages.remove(rowIndex);

        // уведомляем слушателей
        notifyListeners();
    }

    @Override
    public void setImages(int rowIndex, List<Icon> images) {
        recordImages.put(rowIndex, images);

        // уведомляем слушателей
        notifyListeners();
    }

    @Override
    public void addImage(int rowIndex, Icon image) {
        List<Icon> images = recordImages.get(rowIndex);
        if (images == null){
            images = new ArrayList<Icon>();
            recordImages.put(rowIndex, images);
        }
        images.add(image);

        // уведомляем слушателей
        notifyListeners();
    }

    @Override
    public void removeImage(int rowIndex, Icon image) {
        List<Icon> images = recordImages.get(rowIndex);
        if (images != null){
            images.remove(image);
        }

        // уведомляем слушателей
        notifyListeners();
    }

    @Override
    public void addListener(RowImageListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(RowImageListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(){
        for (int i = 0; i < listeners.size(); i++){
            RowImageListener l = listeners.get(i);
            l.imageChanged(-1);
        }
    }
}
