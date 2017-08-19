/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.xml;

/**
 * Класс для облегчения построения xml документов
 * @author Admin
 */
public class XMLBuilder {

    /**
     * корневой элемент
     */
    private XMLNode rootNode;

    /**
     * Текущий элемент
     */
    private XMLNode currentNode;

    /**
     * Конструктор
     * @param rootName имя корневого элемента
     */
    public XMLBuilder(String rootName) {
        this.rootNode = new XMLNode(rootName);
        currentNode = rootNode;
    }

    /**
     * Получение построенного xml
     * @param Level 
     * @return
     */
    public String toXML(){
        return "<?xml version='1.0' encoding='windows-1251'?>" + "\n" + rootNode.toString(0);
    }

    /**
     * Добавление аттрибута
     * @param name имя 
     * @param value значение
     */
    public void addAttribute(String name, String value) {
        currentNode.addAttribute(name, value);
    }

    /**
     * Добавление дочернего элемента
     * @param string
     */
    public void addChild(String childNodeName) {

        XMLNode parentNode = currentNode;
        currentNode = new XMLNode(childNodeName);
        parentNode.add(currentNode);
    }

    /**
     * Добавление узла такого же уровня как текущий
     * @param string
     */
    public void addSibling(String siblingNodeName) {
        addTo(currentNode.getParent(), siblingNodeName);
    }

    /**
     * Добавление узла в узел с именем
     * @param string
     * @param string0
     */
    public void addToParent(String parentNodeName, String childNodeName) {
        XMLNode parentNode = findParentBy(parentNodeName);

        if (parentNode == null){
            throw new RuntimeException("Отсутствует родительский дескриптор : " + parentNodeName);
        }
        addTo(parentNode, childNodeName);
    }

    /**
     * Установка значения
     * @param value значение
     */
    public void addValue(String value) {
        currentNode.addValue(value);
    }

    /**
     * Добавление узла к указанному
     * @param parent
     * @param siblingNodeName
     */
    private void addTo(XMLNode parentNode, String siblingNodeName) {
        currentNode = new XMLNode(siblingNodeName);
        parentNode.add(currentNode);
    }

    private XMLNode findParentBy(String parentName) {
        XMLNode parentNode = currentNode;
        while (parentNode != null){
            if (parentName.equals(parentNode.getName())){
                return parentNode;
            }
            parentNode = parentNode.getParent();
        }
        return null;
    }

    public String getCurrentNode(){
        return currentNode.getName();
    }
}
