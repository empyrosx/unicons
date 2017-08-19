/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import ru.diman.description.TableInfo;
import ru.diman.description.Template;
import ru.diman.xml.XMLBuilder;

/**
 *
 * @author Admin
 */
class XMLTemplateWriter implements DataWriter<Template>{

    /**
     * Строитель
     */
    private XMLBuilder builder;

    /**
     * Конструктор
     * @param pageNode
     */
    public XMLTemplateWriter(XMLBuilder builder) {
        this.builder = builder;
        
    }

    @Override
    public void saveData(Template template) {

        // получаем имя корневого 
        String rootNode = builder.getCurrentNode();

        // имя страницы
        builder.addChild("Options");
        builder.addChild("PageName");
        builder.addAttribute("X", template.getPageName());

        // строковые правила
        builder.addSibling("RulesR");
        builder.addAttribute("X", template.getRowRules());

        // раскладка
        builder.addToParent(rootNode, "TableInfo");
        DataWriter<TableInfo> writer = new XMLTableInfoWriter(builder);
        writer.saveData(template.getTableInfo());
    }
}
