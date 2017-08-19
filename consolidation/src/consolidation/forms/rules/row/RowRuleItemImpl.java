package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleItemImpl;
import consolidation.forms.rules.base.RowValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * Реализация элемента строкового контрольного соотношения
 * @author Диман
 *
 */
public class RowRuleItemImpl extends BaseRuleItemImpl<RowValues> implements RowRuleItem{

	// имя колонки 
	private String columnName;

	/**
	 * Создание элемента строкового контрольного соотношения
	 * @param columnName имя колонки элемента 
	 * @param ruleSign знак элемента
	 */
	public RowRuleItemImpl(String columnName, RuleSign ruleSign) {
		super(ruleSign);
		this.columnName = columnName;
	}

	/**
	 * Получение имени колонки элемента контрольного соотношения
	 */
	public String getColumnName(){
		return columnName;
	}

	public Object getValue(RowValues values) {
		Object objValue = values.getValue(columnName);
		if (objValue != null){
			
			// переводим в дробное
			return objValue;
		}
		else {
			return new Double(0.0);
		}
	}

}
