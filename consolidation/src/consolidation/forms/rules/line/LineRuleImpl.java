package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleImpl;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.base.RuleSign;

public class LineRuleImpl extends BaseRuleImpl<LineRuleItem, LineValues> implements LineRule {

    /**
     * Создание объекта "Линейное правило"
     * @param ruleCaption заголовок контрольного соотношения
     * @param leftPart левая часть контрольного соотношения
     */
    public LineRuleImpl(String ruleCaption, LineRuleItem leftPart) {
        super(ruleCaption, leftPart);
    }

    /**
     * Создание объекта "Линейное правило"
     * @param ruleCaption заголовок контрольного соотношения
     * @param row номер строки левой части контрольного соотношения
     * @param ruleSign знак контрольного соотношения
     */
    public LineRuleImpl(String ruleCaption, int row, RuleSign ruleSign) {
        super(ruleCaption, new LineRuleItemImpl(row, ruleSign));
    }

    /**
     * Номер строки левой части контрольного соотношения
     * @return
     */
    @Override
    public int getRow() {

        // получаем элемент левой части контрольного соотношения
        LineRuleItem ruleItem = (LineRuleItem) getItem(-1);

        // возвращаем имя колонки этого элемента
        return ruleItem.getRow();
    }

    /**
     * Добавление элемента линейного контрольного соотношения
     * @param row номер строки
     * @param ruleSign знак элемента контрольного соотношения
     */
    public void add(int row, RuleSign ruleSign) {
        super.add(new LineRuleItemImpl(row, ruleSign));
    }

    /**
     * Установка значения левой части контрольного соотношения
     * @param values объект доступа к данным
     * @param value значение
     */
    @Override
    protected void setValue(LineValues values, Double value) {
        // сохраняем значение
        values.setValue(getRow(), value);
    }
}
