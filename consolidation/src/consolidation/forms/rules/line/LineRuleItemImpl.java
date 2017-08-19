package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleItemImpl;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * Реализация элемента линейного контрольного соотношения
 * @author Диман
 *
 */
public class LineRuleItemImpl extends BaseRuleItemImpl<LineValues> implements LineRuleItem {

    // номер строки элемента
    private int row;

    /**
     * Создание элемента линейного контрольного соотношения
     * @param row номер строки
     * @param ruleSign знак контрольного соотношения
     */
    public LineRuleItemImpl(int row, RuleSign ruleSign) {
        super(ruleSign);
        this.row = row;
    }

    /**
     * Получение номера строки элемента
     *
     * @return
     */
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public Object getValue(LineValues values) {
        Object objValue = values.getValue(row);
        if (objValue != null) {

            // переводим в дробное
            return (Double) objValue;
        } else {
            return new Double(0.0);
        }
    }
}
