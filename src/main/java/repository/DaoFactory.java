package repository;

import repository.cusotm.impl.CustomerDaoImpl;
import repository.cusotm.impl.ItemDaoImpl;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T>T getDaoType(DaoType type) {
        switch(type) {
            case CUSTOMER:return (T) new CustomerDaoImpl();
            case ITEM:return (T) new ItemDaoImpl();
        }
        return null;
    }
}
