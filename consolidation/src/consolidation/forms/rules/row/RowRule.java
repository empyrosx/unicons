package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRule;
import consolidation.forms.rules.base.RowValues;

/**
 * Интерфейс cтрокового контрольного соотношения
 * @author Диман
 *
 */
public interface RowRule extends BaseRule<RowRuleItem, RowValues>{
	
	/**
	 * Получение имени колонки, стоящей в левой части контрольного соотношения
	 */
	public String getColumnName();
}
