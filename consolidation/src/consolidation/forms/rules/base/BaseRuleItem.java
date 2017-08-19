package consolidation.forms.rules.base;


/**
 * Интерфейс, определяющий базовый элемент контрольного соотношения
 *   
 * @author Диман
 * @param <M> интерфейс доступа к данным для расчета значения
 *
 */
public interface BaseRuleItem<M extends Values> {
	
	/**
	 * Получение знака контрольного соотношения
	 */	
	public RuleSign getRuleSign();
	
	/**
	 * Получение значение элемента контрольного соотношения
	 * @param values объект для получения значения
	 */
	public Object getValue(M values);
}
