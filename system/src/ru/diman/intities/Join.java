package ru.diman.intities;

/**
 * Класс, представляющий собой связку между двумя таблицами
 * @author Димитрий
 *
 */
public class Join {

    private String tableName;
    private String keyFieldName;
    private String foreignTableName;
    private String foreignKey;

    public Join(String tableName, String keyFieldName, String foreignTableName, String foreignKey) {
        this.tableName = tableName;
        this.keyFieldName = keyFieldName;
        this.foreignTableName = foreignTableName;
        this.foreignKey = foreignKey;
    }

    public String getTableName() {
        return tableName;
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public String getForeignTableName() {
        return foreignTableName;
    }

    public String getForeignKey() {
			return foreignKey;
		}
	}
 