/**
 * 
 */
package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleItem;
import consolidation.forms.rules.base.RowValues;

/**
 * Интерфейс элемента строкового контрольного соотношения
 * @author Диман
 *
 */
public interface RowRuleItem extends BaseRuleItem<RowValues>{

	/**
	 * Получение имени колонки элемента контрольного соотношения
	 */
	public String getColumnName();
	
}
