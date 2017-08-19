package consolidation.forms.rules.base;


/**
 * �����, ������������ ������� ���������� �������� ������������ �����������
 * @author �����
 * @param <M>
 *
 */
public abstract class BaseRuleItemImpl<M extends Values> implements BaseRuleItem<M> {

	// ���� �������� ������������ �����������
	private RuleSign ruleSign;

	/**
	 * �������� �������� �������� ������������ �����������
	 * @param ruleSign ���� �������� ������������ ����������� 
	 */
	protected BaseRuleItemImpl(RuleSign ruleSign){
		this.ruleSign = ruleSign;
	}
	
	/**
	 * ��������� ����� ������������ �����������
	 */
	public RuleSign getRuleSign(){
		return ruleSign;
	}

	/**
	 * ��������� �������� �������� ������������ �����������
	 * @param values ������ ��� ��������� ��������
	 */
	public abstract Object getValue(M values);
}
