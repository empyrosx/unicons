package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRule;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.line.LineRuleItem;

/**
 * Интерфейс линейного контрольного соотношения
 * @author Диман
 *
 */
public interface LineRule extends BaseRule<LineRuleItem, LineValues>{
    
    /**
     * Получение номера строки, стоящей в левой части контрольного соотношения
     */
    public int getRow();
    
}
