package consolidation.forms.rules.base;

/**
 * ����� ������, ������������ � ����������� �����������
 * @author �����
 *
 */
public enum RuleSign {
	/**
	 * ���� ���������
	 */
	Equal("="),
	/**
	 * ���� ������������
	 */
	Confer(":="),
	/**
	 * ����
	 */
	Plus("+"),
	/**
	 * �����
	 */
	Minus("-"),
        /**
         * ������ ��� �����
         */
        GreatOrEqual(">="),
        /**
         * ������ ��� �����
         */
        LessOrEqual("<="),
        /**
         * ������
         */
        Great(">"),
        /**
         * ������
         */
        Less("<");
	
	private String enumValue;
	
	private RuleSign(String enumValue){
		this.enumValue = enumValue;
	}
	
	/**
	 * ��������� ���������� ������������� ����� ������������ �����������
	 */
	public String toString() {
		return enumValue;
	}
	
	/**
	 * �������� ������� "���� ������������ �����������" �� ��� ���������� �������������
	 * @param enumValue ��������� ������������� �����
	 */
    public static RuleSign parse(String enumValue) {

    	if (enumValue.equals("=")){
    		return Equal;
    	}
    	else if (enumValue.equals(":=")){
    		return Confer;
    	}
    	else if (enumValue.equals("+")){
    		return Plus;
    	}
    	else if (enumValue.equals("-")){
    		return Minus;
    	}
    	else if (enumValue.equals(">=")){
    		return GreatOrEqual;
    	}
    	else if (enumValue.equals("<=")){
    		return LessOrEqual;
    	}
    	else if (enumValue.equals(">")){
    		return Great;
    	}
    	else if (enumValue.equals("<")){
    		return Less;
    	}
    	else {
    	    throw new IllegalArgumentException("argument out of range");		
    	}
    } 	
}