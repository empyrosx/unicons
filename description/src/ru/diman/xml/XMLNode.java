/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Admin
 */
public class XMLNode {

    /**
     * Имя данного узла
     */
    private String name;

    /**
     * Атрибуты данного узла
     */
    private List<Attribute> attributes;


    /**
     * Значение данного узла
     */
    private String value;

    /**
     * Дочерние узлы
     */
    private List<XMLNode> children;

    /**
     * Родительский узел
     */
    private XMLNode parent;

    /**
     * Конструктор
     * @param name имя узла
     */
    public XMLNode(String name) {
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
        this.value = "";
    }

    /**
     * Добавление атрибута 
     * @param attribute
     * @param value
     */
    public void addAttribute(String attribute, String value){
        attributes.add(new Attribute(attribute, value));
    }

    /**
     * Установка значения данного узла
     * @param value
     */
    public void addValue(String value){
        this.value = value;
    }

    /**
     * Получение значения данного узла
     * @return
     */
    public String getValue(){
        return value;
    }

    /**
     * Поиск дочернего элемента с заданным именем
     * @param nodeName
     * @return
     */
    public XMLNode selectNode(String nodeName) {

        for (int i = 0; i < children().size(); i++){
            XMLNode child = children().get(i);
            if (child.getName().equals(nodeName)){
                return child;
            }
        }
        return null;
    }

    /**
     * Получение узла в виде строки xml
     * @param level 
     * @return
     */
    public String toString(int level){

        String attrs = "";
        for (int i = 0; i < attributes.size(); i++){
            attrs += " " + attributes.get(i).name + "='" + attributes.get(i).value + "'";
        }

        String tab = "";
        for(int i = 0; i < level*4; i++){
            tab = tab + " ";
        }

        boolean hasData = children().size() > 0 || !value.isEmpty();

        String result = "";
        if (hasData) {
            result = tab + "<" + name + attrs + ">" + "\n";

            Iterator<XMLNode> it = children().iterator();
            while (it.hasNext()) {
                XMLNode child = it.next();
                result += child.toString(level + 1) + "\n";
            }

            if (!value.isEmpty()) {
                result += value + "\n";
            }

            result += tab + "</" + name + ">";
        }
        else {
            result = tab + "<" + name + attrs + "/>";
        }

        return result;
    }

    /**
     * Получение значения атрибута дочернего узла
     * @param path путь к атрибуту (может быть рекурсивным вида Child0/Child/@attr
     * @return
     */
    public String getChildAttribute(String path) {

        if (path.contains("/")){
            // указан рекурсивный путь, получаем первого потомка и вызываем его метод getChildNode

            int slashIndex = path.indexOf("/");
            String childName = path.substring(0, slashIndex);

            XMLNode childNode = selectNode(childName);
            if (childNode != null){
                return childNode.getChildAttribute(path.substring(slashIndex + 1));
            }
        }
        else {
            if (path.startsWith("@")){
                return getAttribute(path.substring(1));
            }
        }

        return "";
    }

    private List<XMLNode> children(){
        if (children == null){
            children = new ArrayList<XMLNode>();
        }
        return children;
    }

    public void add(XMLNode child){
        child.setParent(this);
        children().add(child);
    }

    /**
     * Установка родительского узла
     * @param parent
     */
    private void setParent(XMLNode parent) {
        this.parent = parent;
    }

    /**
     * Получение родительского элемента
     * @return
     */
    public XMLNode getParent(){
        return parent;
    }

    /**
     * Получение имени узла
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Получение количества дочерних элементов
     * @return
     */
    public int getChildCount(){
        return children().size();
    }

    /**
     * Получение дочернего элемента по индексу
     * @param index
     * @return
     */
    public XMLNode getChild(int index){
        return children().get(index);
    }

    /**
     * Получение количества атрибутов данного узла
     * @return
     */
    public int getAtributesCount(){
        return attributes.size();
    }

    /**
     * Получение значение атрибута по наименованию
     * @param name наименование атрибута
     * @return
     */
    public String getAttribute(String name){

        for (int i = 0; i < attributes.size(); i++){
            Attribute attr = attributes.get(i);
            if (attr.getName().equals(name)){
                return attr.getValue();
            }
        }
        return null;
    }

    /**
     * Получение дочернего узла
     * @param path путь к узлу (может быть рекурсивный, то есть Child0/Child1/Child2
     * @return узел XMLNode если узеk существует и null в противном случае
     */
    public XMLNode getChildNode(String path){

        if (path.contains("/")){
            // указан рекурсивный путь, получаем первого потомка и вызываем его метод getChildNode

            int slashIndex = path.indexOf("/");
            String childName = path.substring(0, slashIndex);

            XMLNode childNode = selectNode(childName);
            if (childNode != null){
                return childNode.getChildNode(path.substring(slashIndex + 1));
            }
        }
        else {
            return selectNode(path);
        }

        return null;
    }

    /**
     * Класс для представления атрибута узла
     */
    public class Attribute{

        /**
         * Наименование атрибута
         */
        private String name;

        /**
         * Значение атрибута
         */
        private String value;

        /**
         * Получение наименования атрибута
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Получение значения атрибута
         * @return
         */
        public String getValue() {
            return value;
        }

        /**
         * Конструктор
         * @param name наименование
         * @param value значение
         */
        public Attribute(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
