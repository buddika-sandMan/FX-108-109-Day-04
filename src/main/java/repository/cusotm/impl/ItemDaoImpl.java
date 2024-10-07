package repository.cusotm.impl;

import entity.ItemEntity;
import repository.cusotm.ItemDao;

import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        return false;
    }

    @Override
    public boolean update(ItemEntity entity) {
        return false;
    }

    @Override
    public boolean delete(ItemEntity entity) {
        return false;
    }

    @Override
    public List<ItemEntity> search(ItemEntity entity) {
        return List.of();
    }

    @Override
    public List<ItemEntity> searchAll() {
        return List.of();
    }
}
