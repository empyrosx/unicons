package ru.diman.database;

import java.util.ArrayList;
import java.util.List;
import ru.diman.description.column.types.ValueType;
import ru.diman.database.QueryManager.Params;

/**
 * Класс, представляющий собой реализацию интерфейса Params
 * @author Димитрий
 *
 */
public class ParamsImpl implements Params{

	// набор параметров
	private List<Param> params;

	public ParamsImpl() {
		this.params = new ArrayList<Param>();
	}

	/**
	 * Получение количества параметров
	 */
	public int getCount() {
		return params.size();
	}

	/**
	 * Получение имени параметра по индексу
	 * @param paramIndex индекс параметра
	 */
	public String getName(int paramIndex) {
		return params.get(paramIndex).getName();
	}

	/**
	 * Получение типа параметра по индексу
	 * @param paramIndex индекс параметра
	 */
	public ValueType getType(int paramIndex) {
		return params.get(paramIndex).getType();
	}

	public void add(String paramName, ValueType paramType){
		params.add(new Param(paramName, paramType));
	}


	/**
	 * Класс, представляющий собой описание SQL параметра
	 * @author Димитрий
	 *
	 */
	class Param{

		// имя параметра
		private String name;

		// тип параметра
		private ValueType type;

		/**
		 * Конструктор
		 * @param name имя параметра
		 * @param type тип параметра
		 */
		public Param(String name, ValueType type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Получение имени параметра
		 */
		String getName(){
			return name;
		}

		/**
		 * Получение типа параметра
		 */
		ValueType getType(){
			return type;
		}
	}
}
