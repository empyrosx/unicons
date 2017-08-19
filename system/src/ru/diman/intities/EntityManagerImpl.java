/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.intities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.tableinfo.TableInfoImpl;

/**
 * Реализация менеджера сущностей
 * @author Admin
 */
public class EntityManagerImpl implements EntityManager{

    /**
     * Набор сущностей
     */
    private List<Entity> entities;

    /**
     * Набор join-ов для каждой таблицы
     */
    private HashMap<String, ArrayList<Join>> joins;

    /**
     * Конструктор
     * @param entities набор сущностей
     */
    public EntityManagerImpl(Iterator<Entity> entities){

        // загружаем сущности
        this.entities = new ArrayList<Entity>();
        while (entities.hasNext()){
            this.entities.add(entities.next());
        }

        // создаем набор для хранения связок
		this.joins = new HashMap<String, ArrayList<Join>>();

        // инициализируем связки между сущностями
        initLinkedCLMs();
    }


    /**
     * Получение количества сущностей
     * @return
     */
    public int getCount() {
        return entities.size();
    }

    /**
     * Получение сущности по индексу
     * @param index
     * @return
     */
    public Entity getItem(int index) {
        return entities.get(index);
    }

    /**
     * Поиск сущности по имени
     * @param name имя сущности
     * @return
     */
    public Entity findByName(String name){

        // ищем сущность по имени
        for (int i = 0; i < entities.size(); i++) {

            // получаем очередную сущность
			Entity entity = entities.get(i);

            // сраниваем имя
			if (entity.getName().equalsIgnoreCase(name)){
				return entity;
			}
		}
        // если ничего не найдено
		return null;
    }

    /**
     * Инициализация связок между сущностями
     */
	private void initLinkedCLMs(){

		// обходим все CLM
		for (int i = 0; i < entities.size(); i++) {

			// получаем очередную сущность
			Entity entity = entities.get(i);

            // добавляем связки по ней
            addEntityLinks(entity);
		}
	}

    /**
     * Добавление связок для данной сущности
     * @param entity сущность
     */
    private void addEntityLinks(Entity entity){

			// получаем раскладку
			TableInfo ti = entity.getTableInfo();

            // обходим все колонки
            for (Column ci : ti){

                // если внешний ключ не указан, то пропускаем
                if (ci.getForeignKey().isEmpty()){
                    continue;
                }

                // если внешняя таблица не указана, то пропускаем
                if (ci.getForeignTableName().isEmpty()){
                    continue;
                }

                // добавляем прямую связку
                ArrayList<Join> tableJoins = joins.get(ci.getTableName());
                if (tableJoins == null) {
                    tableJoins = new ArrayList<Join>();
                    joins.put(ci.getTableName(), tableJoins);
                }
                tableJoins.add(new Join(ci.getTableName(), ci.getAliasName(), ci.getForeignTableName(), ci.getForeignKey()));

                // добавляем обратную связку
                tableJoins = joins.get(ci.getForeignTableName());
                if (tableJoins == null) {
                    tableJoins = new ArrayList<Join>();
                    joins.put(ci.getForeignTableName(), tableJoins);
                }

                tableJoins.add(new Join(ci.getForeignTableName(), ci.getForeignKey(), ci.getTableName(), ci.getAliasName()));
			}
    }
    
	public String getDataBaseName(String columnIdent){

		// получаем имя CLM
		int pointPos = columnIdent.indexOf(".");

		String clmName;

		if (pointPos >= 0){
			clmName = columnIdent.substring(0, pointPos);
		}
		else{
			clmName = columnIdent.substring(0);
		}

		// находим CLM
		Entity entity = findByName(clmName);

		// получаем раскладку этой CLM
		TableInfo ti = entity.getTableInfo();

		if (pointPos == -1){
			// ошибка
			return "";
		}
		else {

			// получаем имя колонки
			String columnName = columnIdent.substring(columnIdent.indexOf(".") + 1);

			// ищем колонку
			Column ci = ti.FindColumn(columnName);

			return entity.getTableName() + "." + ci.getAliasName();
		}
    }

    public List<Join> buildJoins(String firstTableName, String secondTableName) {

		List<Join> result = new ArrayList<Join>();

		// набор пройденных таблиц
		List<String> usedTables = new ArrayList<String>();
		usedTables.add(firstTableName);

		// получаем набор Join-ов для первой таблицы
		List<Join> tableJoins = joins.get(firstTableName.toUpperCase());
		if (tableJoins != null){

			// ищем непосредственные Join-ы
			for (int i = 0; i < tableJoins.size(); i++) {

				// получаем очередной Join
				Join join = tableJoins.get(i);

				// если Join сразу дает соединение, то возвращаем его
				if (join.getForeignTableName().equalsIgnoreCase(secondTableName)){
					result.add(join);
					return result;
				}
			}

			// если дошли до сюда, значит непосредственного Join-а нет,
			// пытаемся найти рекурсивно
			for (int i = 0; i < tableJoins.size(); i++) {

				// получаем очередной Join
				Join join = tableJoins.get(i);

				// создаем список новых Join-ов
				List<Join> resultJoins = new ArrayList<Join>();

				// пытаемся подсоединиться
				if (tryJoin(join, join.getForeignTableName(), secondTableName, usedTables, resultJoins)){

					// если успешно, то возвращаем набор необходимых
					// для этого Join-ов
					result.add(join);
					for (int j = 0; j < resultJoins.size(); j++) {
						Join resultJoin = resultJoins.get(j);
						result.add(resultJoin);
					}
					return result;
				}
			}
		}
		return result;
    }

	// попытка найти подключение к таблице TableName через соединение join,
	// tables - набор уже пройденных таблиц
	private boolean tryJoin(Join join, String fromTable, String toTable, List<String> usedTables
			, List<Join> resultJoins){

		// если таблицы совпадают, значит переданный Join и есть искомый
		if (fromTable.equalsIgnoreCase(toTable)){
			resultJoins.add(join);
			return true;
		}

		// получаем набор Join-ов для внешней таблицы и
		List<Join> tableJoins = joins.get(join.getForeignTableName());

		// ищем непосредственные Join-ы
		for (int i = 0; i < tableJoins.size(); i++) {

			// получаем очередной Join
			Join tableJoin = tableJoins.get(i);

			// если Join сразу дает соединение, то возвращаем его
			if (tableJoin.getForeignTableName().equalsIgnoreCase(toTable)){
				resultJoins.add(tableJoin);
				return true;
			}
		}

		// если дошли до сюда, значит непосредственного Join-а нет
		// ищем рекурсивно
		for (int i = 0; i < tableJoins.size(); i++) {

			// получаем очередной Join
			Join foreignJoin = tableJoins.get(i);
			if (!usedTables.contains(foreignJoin.getForeignTableName())){

				// создаем список Join-ов
				List<Join> newJoins = new ArrayList<Join>();

				// если попытка удачно, то добавляем все новые Join в набор
				if (tryJoin(foreignJoin, join.getTableName(), toTable, usedTables, newJoins)){

					for (int j = 0; j < newJoins.size(); j++) {
						Join newJoin = newJoins.get(j);
						resultJoins.add(newJoin);
					}

					return true;
				}
			}
		}

		return false;

	}

    public TableInfo createTableInfo(String columnList) {

        TableInfo result = new TableInfoImpl();

		String[] columns = columnList.split(",");
		for (int i = 0; i < columns.length; i++) {

			// получаем идентификатор колонки
			String columnIdent = columns[i];

			// получаем имя CLM
			int pointPos = columnIdent.indexOf(".");

			String clmName;

			if (pointPos >= 0){
				clmName = columnIdent.substring(0, pointPos);
			}
			else{
				clmName = columnIdent.substring(0);
			}

			// находим CLM
			Entity entity = findByName(clmName);

			// получаем раскладку этой CLM
			TableInfo ti = entity.getTableInfo();

			if (pointPos == -1){
				// добавляем все колонки

				for (int j = 0; j < ti.getCount(); j++) {
					Column ci = ti.getItem(j);
					result.addColumn(ci);
				}
			}
			else {

				// получаем имя колонки
				String columnName = columnIdent.substring(columnIdent.indexOf(".") + 1);

				// ищем колонку
				Column ci = ti.FindColumn(columnName);

				if (ci != null){
					result.addColumn(ci);
				}
			}
		}
		return result;
    }
}
