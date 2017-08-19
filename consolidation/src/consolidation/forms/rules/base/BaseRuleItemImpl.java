package consolidation.forms.rules.base;


/**
 * Класс, определяющий базовую реализацию элемента контрольного соотношения
 * @author Диман
 * @param <M>
 *
 */
public abstract class BaseRuleItemImpl<M extends Values> implements BaseRuleItem<M> {

	// знак элемента контрольного соотношения
	private RuleSign ruleSign;

	/**
	 * Создание базового элемента контрольного соотношения
	 * @param ruleSign знак элемента контрольного соотношения 
	 */
	protected BaseRuleItemImpl(RuleSign ruleSign){
		this.ruleSign = ruleSign;
	}
	
	/**
	 * Получение знака контрольного соотношения
	 */
	public RuleSign getRuleSign(){
		return ruleSign;
	}

	/**
	 * Получение значение элемента контрольного соотношения
	 * @param values объект для получения значения
	 */
	public abstract Object getValue(M values);
}
