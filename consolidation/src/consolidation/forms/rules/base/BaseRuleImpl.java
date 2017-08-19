package consolidation.forms.rules.base;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * �����, ������������ ������� ���������� ������������ �����������
 * @author �����
 * @param <E>
 * @param <M>
 *
 */
public abstract class BaseRuleImpl<E extends BaseRuleItem<M>, M extends Values> implements BaseRule<E, M> {

    // ������������ ������������ �����������
    private String ruleCaption;
    // �������� ������������ �����������
    private ArrayList<E> ruleElements;

    /**
     * ������� ������� ������� �� ������
     * @param ruleCaption ������������ ������������ �����������
     * @param leftPart ������� ������� ����� ����� ������������ �����������
     */
    public BaseRuleImpl(String ruleCaption, E leftPart) {

        // ������������� ������������
        this.ruleCaption = ruleCaption;

        // ������� ����� ���������
        ruleElements = new ArrayList<E>();
        ruleElements.add(leftPart);
    }

    /**
     * ���������� �������� ������ ����� ������������ �����������
     * @param ruleElement
     */
    public void add(E ruleElement) {
        if (!ruleElements.contains(ruleElement)) {
            ruleElements.add(ruleElement);
        }
    }

    /**
     * ������������ ������������ �����������
     */
    public String getCaption() {
        return ruleCaption;
    }

    /**
     * �������� ��� ������ ��������� ������������ �����������
     */
    public Iterator<E> iterator() {
        return ruleElements.iterator();
    }

    /**
     * ��������� ����� ��������� ������	 ����� ������������ �����������
     */
    public int getCount() {
        // ������� ������� ��� ������� ����� �����
        return ruleElements.size() - 1;
    }

    /**
     * ��������� �������� ������ ����� ������������ �����������
     * @param index ������ ��������
     */
    public E getItem(int index) {
        // ������� ������� ��� ������� ����� �����
        return ruleElements.get(index + 1);
    }

    /**
     * ��������� �������� ����� ����� ������������ �����������
     * @param values ������ ������� � ������
     * @param value ��������
     */
    protected abstract void setValue(M values, Double value);

    /**
     * ���������� ����� ����� ������������ ����������� �� ��������� ��������� ������ �����
     * @param values ������ ������� � ������
     */
    public void recalculate(M values) {

        if (getRuleSign().equals(RuleSign.Confer)) {

            // ��������� �������� ������ ����� ������������ �����������
            Double rightPartValue = calculateRightPart(values);

            // ������������� �������� ����� �����
            setValue(values, rightPartValue);
        }
    }

    /**
     * �������� ���������� ������������ �����������
     * @param values
     */
    public boolean check(M values) {

        // �������� �������� ����� ����� ������������ �����������
        Object leftPartValue = getItem(-1).getValue(values);

        // ��������� �������� ������ ����� ������������ �����������
        Object rightPartValue = calculateRightPart(values);

        // �������
        switch (getRuleSign()) {

            default:
                return leftPartValue.equals(rightPartValue);
        }
    }

    /**
     * ���������� ������ ����� ������������ �����������
     * @param values
     */
    protected Double calculateRightPart(M values) {

        // �������� �������� ��� ������ ���������
        Double result = 0.0;

        for (int i = 0; i < getCount(); i++) {

            // �������� ������� �������
            BaseRuleItem<M> curItem = getItem(i);

            // �������� ��� ��������
            Double curItemValue = (Double)curItem.getValue(values);

            switch (curItem.getRuleSign()) {

                case Plus:
                    result = result + curItemValue;
                    break;

                case Minus:
                    result = result - curItemValue;
                    break;
            }
        }
        return result;
    }

    /**
     * ��������� ����� ������������ �����������
     */
    public RuleSign getRuleSign() {

        // �������� ������� ����� ����� ������������ �����������
        BaseRuleItem<M> ruleItem = getItem(-1);

        // ���������� ��� ������� ����� ��������
        return ruleItem.getRuleSign();
    }
}
