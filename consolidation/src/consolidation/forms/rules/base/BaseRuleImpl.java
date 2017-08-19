package consolidation.forms.rules.base;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс, определяющий базовую реализацию контрольного соотношения
 * @author Диман
 * @param <E>
 * @param <M>
 *
 */
public abstract class BaseRuleImpl<E extends BaseRuleItem<M>, M extends Values> implements BaseRule<E, M> {

    // наименование контрольного соотношения
    private String ruleCaption;
    // элементы контрольного соотношения
    private ArrayList<E> ruleElements;

    /**
     * Создает базовое правило на основе
     * @param ruleCaption наименование контрольного соотношения
     * @param leftPart базовый элемент левой части контрольного соотношения
     */
    public BaseRuleImpl(String ruleCaption, E leftPart) {

        // устанавливаем наименование
        this.ruleCaption = ruleCaption;

        // создаем набор элементов
        ruleElements = new ArrayList<E>();
        ruleElements.add(leftPart);
    }

    /**
     * Добавление элемента правой части контрольного соотношения
     * @param ruleElement
     */
    public void add(E ruleElement) {
        if (!ruleElements.contains(ruleElement)) {
            ruleElements.add(ruleElement);
        }
    }

    /**
     * Наименование контрольного соотношения
     */
    public String getCaption() {
        return ruleCaption;
    }

    /**
     * Итератор для обхода элементов контрольного соотношения
     */
    public Iterator<E> iterator() {
        return ruleElements.iterator();
    }

    /**
     * Получение числа элементов правой	 части контрольного соотношения
     */
    public int getCount() {
        // нулевой элемент это элемент левой части
        return ruleElements.size() - 1;
    }

    /**
     * Получение элемента правой части контрольного соотношения
     * @param index индекс элемента
     */
    public E getItem(int index) {
        // нулевой элемент это элемент левой части
        return ruleElements.get(index + 1);
    }

    /**
     * Установка значения левой части контрольного соотношения
     * @param values объект доступа к данным
     * @param value значение
     */
    protected abstract void setValue(M values, Double value);

    /**
     * Вычисление левой части контрольного соотношения по значениям элементов правой части
     * @param values объект доступа к данным
     */
    public void recalculate(M values) {

        if (getRuleSign().equals(RuleSign.Confer)) {

            // вычисляем значение правой части контрольного соотношения
            Double rightPartValue = calculateRightPart(values);

            // устанавливаем значение левой части
            setValue(values, rightPartValue);
        }
    }

    /**
     * Проверка выполнения контрольного соотношения
     * @param values
     */
    public boolean check(M values) {

        // получаем значение левой части контрольного соотношения
        Object leftPartValue = getItem(-1).getValue(values);

        // вычисляем значение правой части контрольного соотношения
        Object rightPartValue = calculateRightPart(values);

        // сверяем
        switch (getRuleSign()) {

            default:
                return leftPartValue.equals(rightPartValue);
        }
    }

    /**
     * Вычисление правой части контрольного соотношения
     * @param values
     */
    protected Double calculateRightPart(M values) {

        // получаем итератор для обхода элементов
        Double result = 0.0;

        for (int i = 0; i < getCount(); i++) {

            // получаем текущий элемент
            BaseRuleItem<M> curItem = getItem(i);

            // получаем его значение
            Double curItemValue = (Double)curItem.getValue(values);

            switch (curItem.getRuleSign()) {

                case Plus:
                    result = result + curItemValue;
                    break;

                case Minus:
                    result = result - curItemValue;
                    break;
            }
        }
        return result;
    }

    /**
     * Получение знака контрольного соотношения
     */
    public RuleSign getRuleSign() {

        // получаем элемент левой части контрольного соотношения
        BaseRuleItem<M> ruleItem = getItem(-1);

        // возвращаем имя колонки этого элемента
        return ruleItem.getRuleSign();
    }
}
