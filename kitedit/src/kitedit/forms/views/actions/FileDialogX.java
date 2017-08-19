/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kitedit.forms.views.actions;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Диман
 */
public class FileDialogX {


    public static String selectFile(Frame frame, String defaultPath, final String fileExtension){

        // создаем файловый диалог
        FileDialog fileDialog = new FileDialog(frame);

        // устанавливаем каталог по умолчанию
        fileDialog.setFile(defaultPath);

        // добавляем фильтр по типу
        fileDialog.setFilenameFilter(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.contains(fileExtension);
            }
        });

        fileDialog.setVisible(true);

        String fileName = fileDialog.getFile();

        if (fileName != null){

            /*
            if (!fileName.contains(fileExtension)){
                fileName = fileName + fileExtension;
            }
             */
            fileName = fileDialog.getDirectory() + fileName;
        }
        return fileName;
    }

} 