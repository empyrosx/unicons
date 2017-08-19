package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleItem;
import consolidation.forms.rules.base.LineValues;

/**
 * Интерфейс элемента линейного контрольного соотношения
 * @author Диман
 *
 */
public interface LineRuleItem extends BaseRuleItem<LineValues> {

    /**
     * Получение номера строки элемента линейного контрольного соотношения
     * @return
     */
    public int getRow();
}
