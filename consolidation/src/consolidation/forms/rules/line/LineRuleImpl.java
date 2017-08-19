package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleImpl;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.base.RuleSign;

public class LineRuleImpl extends BaseRuleImpl<LineRuleItem, LineValues> implements LineRule {

    /**
     * �������� ������� "�������� �������"
     * @param ruleCaption ��������� ������������ �����������
     * @param leftPart ����� ����� ������������ �����������
     */
    public LineRuleImpl(String ruleCaption, LineRuleItem leftPart) {
        super(ruleCaption, leftPart);
    }

    /**
     * �������� ������� "�������� �������"
     * @param ruleCaption ��������� ������������ �����������
     * @param row ����� ������ ����� ����� ������������ �����������
     * @param ruleSign ���� ������������ �����������
     */
    public LineRuleImpl(String ruleCaption, int row, RuleSign ruleSign) {
        super(ruleCaption, new LineRuleItemImpl(row, ruleSign));
    }

    /**
     * ����� ������ ����� ����� ������������ �����������
     * @return
     */
    @Override
    public int getRow() {

        // �������� ������� ����� ����� ������������ �����������
        LineRuleItem ruleItem = (LineRuleItem) getItem(-1);

        // ���������� ��� ������� ����� ��������
        return ruleItem.getRow();
    }

    /**
     * ���������� �������� ��������� ������������ �����������
     * @param row ����� ������
     * @param ruleSign ���� �������� ������������ �����������
     */
    public void add(int row, RuleSign ruleSign) {
        super.add(new LineRuleItemImpl(row, ruleSign));
    }

    /**
     * ��������� �������� ����� ����� ������������ �����������
     * @param values ������ ������� � ������
     * @param value ��������
     */
    @Override
    protected void setValue(LineValues values, Double value) {
        // ��������� ��������
        values.setValue(getRow(), value);
    }
}
