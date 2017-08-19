/**
 * 
 */
package consolidation.forms.rules.base;

import java.util.Iterator;


/**
 * ��������� �������� ������������ �����������
 * @author �����
 * @param <M>
 *
 */
public interface BaseRule<E extends BaseRuleItem<M>, M extends Values> {

	/**
	 * ������������ ������������ �����������
	 */
	public String getCaption();
	
	/**
	 * ��������� ����� ������������ �����������
	 */
	public RuleSign getRuleSign();
	
	
	/**
	 * �������� ��� ������ ��������� ������������ �����������
	 */
	public Iterator<E> iterator();

	/**
	 * ��������� ����� ��������� ������	 ����� ������������ �����������
	 */
	public int getCount();
	
	/**
	 * ��������� �������� ������ ����� ������������ �����������
	 * @param index ������ ��������
	 */
	public E getItem(int index);
	
	/**
	 * ���������� ����� ����� ������������ ����������� �� ��������� ��������� ������ �����
	 * @param values ������ ������� � ������
	 */
	public void recalculate(M values);

	/**
	 * �������� ���������� ������������ �����������
	 * @param values 
	 */
	public boolean check(M values);
}
