package consolidation.forms.rules.row;

import consolidation.forms.rules.base.BaseRuleImpl;
import consolidation.forms.rules.base.RowValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * Класс для задания связи между колонками формы вида
 * 
 * <pre>Выделяется два типа связи:
 *    1. Сверка показателей
 *       Например:
 *         A = B + C
 *    2. Автоматический расчет
 *       Например:
 *         A := B + C
 *    Правила автоматического расчета используются также при проверке отчета в качестве "Сверка показателей" 
 *   
 * @author Диман
 * 
 * </pre>
 *
 */
public class RowRuleImpl extends BaseRuleImpl<RowRuleItem, RowValues> implements RowRule{

	/**
	 * Создание объекта "Колоночное правило"
	 * @param ruleCaption заголовок контрольного соотношения
	 * @param leftPart элемент левой части контрольного соотношения
	 */
	public RowRuleImpl(String ruleCaption, RowRuleItem leftPart){
		super(ruleCaption, leftPart);
	}

	/**
	 * Создание объекта "Колоночное правило"
	 * @param ruleCaption заголовок контрольного соотношения
	 * @param columnName имя колонки в левой части контрольного соотношения
	 * @param ruleSign знак контрольного соотношения
	 */
	public RowRuleImpl(String ruleCaption, String columnName, RuleSign ruleSign){
		super(ruleCaption, new RowRuleItemImpl(columnName, ruleSign));
	}
	
	/**
	 * Получение имени колонки, стоящей в левой части контрольного соотношения
	 */
	public String getColumnName() {
		
		// получаем элемент левой части контрольного соотношения
		RowRuleItem ruleItem = iterator().next();
		
		// возвращаем имя колонки этого элемента
		return ruleItem.getColumnName();
	}
	
	/**
	 * Добавление элемента строкового контрольного соотношения
	 * @param columnName имя колонки элемента
	 * @param ruleSign знак элемента контрольного соотношения
	 */
	public void add(String columnName, RuleSign ruleSign){
		super.add(new RowRuleItemImpl(columnName, ruleSign));
	}

	/**
	 * Установка значения левой части контрольного соотношения
	 */
	protected void setValue(RowValues values, Double value) {
		// сохраняем значение
		values.setValue(getColumnName(), value);
	}

}
