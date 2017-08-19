package consolidation.forms.rules.base;

/**
 * Набор знаков, используемых в контрольных соотношений
 * @author Диман
 *
 */
public enum RuleSign {
	/**
	 * Знак равенства
	 */
	Equal("="),
	/**
	 * Знак присваивания
	 */
	Confer(":="),
	/**
	 * Плюс
	 */
	Plus("+"),
	/**
	 * Минус
	 */
	Minus("-"),
        /**
         * Больше или равно
         */
        GreatOrEqual(">="),
        /**
         * Меньше или равно
         */
        LessOrEqual("<="),
        /**
         * Больше
         */
        Great(">"),
        /**
         * Меньше
         */
        Less("<");
	
	private String enumValue;
	
	private RuleSign(String enumValue){
		this.enumValue = enumValue;
	}
	
	/**
	 * Получение строкового представления знака контрольного соотношения
	 */
	public String toString() {
		return enumValue;
	}
	
	/**
	 * Создание объекта "Знак контрольного соотношения" по его строковому представлению
	 * @param enumValue строковое представление знака
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