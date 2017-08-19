/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.system;

import ru.diman.database.QueryManagerXImpl;
import java.util.Iterator;
import ru.diman.intities.Entity;
import ru.diman.intities.EntityLoader;
import ru.diman.intities.EntityManager;
import ru.diman.intities.EntityManagerImpl;
import ru.diman.system.handbooks.HandBookFactoryImpl;
import ru.diman.system.handbooks.HandBookFactory;
import ru.diman.database.QueryManagerX;

/**
 *
 * @author Admin
 */
public class Registry {

    /**
     * Менеджер запросов к БД
     */
    private static QueryManagerX queryManager;


    /**
     * Фабрика справочников
     */
    private static HandBookFactory handbookFactory;

    /**
     * Менеджер сущностей предметной области
     */
    private static EntityManager entityManager;



    /**
     * Получение менеджера запросов к БД
     * @return
     */
    public static QueryManagerX getQueryManager(){

        if (queryManager == null){
            queryManager = new QueryManagerXImpl();
        }

        return queryManager;
    }

    /**
     * Получение фабрики справочников
     * @return 
     */
    public static HandBookFactory getHandBookFactory(){

        if (handbookFactory == null){
            handbookFactory = new HandBookFactoryImpl();
        }

        return handbookFactory;
    }

    /**
     * Получение менеджера сущностей предметной области
     * @return 
     */
    public static EntityManager getEntityManager(){

        if (entityManager == null){
            EntityLoader entityLoader = new EntityLoader();
            Iterator<Entity> entities = entityLoader.loadEntities();
            entityManager = new EntityManagerImpl(entities);
        }

        return entityManager;

    }

}
