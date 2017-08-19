/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation.forms.rules.parsers;

import consolidation.forms.rules.base.BaseRule;

/**
 * ������ ����������� �����������
 * @param <E> ����� ����������� �����������, �������������� ���� ��������
 * @author ��������
 */
public interface RuleParser<E extends BaseRule> {


    /**
     * ������ ������
     * @param rules ����������� ����������� � ��������� ����
     * @return ����� ����������� �����������
     */
    public E[] parseRules(String rules);
}
