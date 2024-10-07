package repository;

import java.util.List;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(T entity);
    List<T> search(T entity);
    List<T> searchAll();
}
