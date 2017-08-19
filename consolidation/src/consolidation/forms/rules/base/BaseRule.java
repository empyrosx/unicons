/**
 * 
 */
package consolidation.forms.rules.base;

import java.util.Iterator;


/**
 * Интерфейс базового контрольного соотношения
 * @author Диман
 * @param <M>
 *
 */
public interface BaseRule<E extends BaseRuleItem<M>, M extends Values> {

	/**
	 * Наименование контрольного соотношения
	 */
	public String getCaption();
	
	/**
	 * Получение знака контрольного соотношения
	 */
	public RuleSign getRuleSign();
	
	
	/**
	 * Итератор для обхода элементов контрольного соотношения
	 */
	public Iterator<E> iterator();

	/**
	 * Получение числа элементов правой	 части контрольного соотношения
	 */
	public int getCount();
	
	/**
	 * Получение элемента правой части контрольного соотношения
	 * @param index индекс элемента
	 */
	public E getItem(int index);
	
	/**
	 * Вычисление левой части контрольного соотношения по значениям элементов правой части
	 * @param values объект доступа к данным
	 */
	public void recalculate(M values);

	/**
	 * Проверка выполнения контрольного соотношения
	 * @param values 
	 */
	public boolean check(M values);
}
