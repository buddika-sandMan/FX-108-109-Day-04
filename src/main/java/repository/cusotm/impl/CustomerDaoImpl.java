package repository.cusotm.impl;

import entity.CustomerEntity;
import org.hibernate.Session;
import repository.cusotm.CustomerDao;
import util.CrudUtil;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        Session session = HibernateUtil.getSesstion();

        session.getTransaction().begin();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();

        return true;
}

    @Override
    public boolean update(CustomerEntity entity) {
        return false;
    }

    @Override
    public boolean delete(CustomerEntity entity) {
        return false;
    }

    @Override
    public List<CustomerEntity> search(CustomerEntity entity) {
        return List.of();
    }

    @Override
    public List<CustomerEntity> searchAll() {
        return List.of();
    }
}
