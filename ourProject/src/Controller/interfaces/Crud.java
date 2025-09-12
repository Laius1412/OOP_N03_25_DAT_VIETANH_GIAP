package Controller.interfaces;

import java.util.ArrayList;

public interface Crud<T, ID> {
        void create(T entity);
        boolean update(ID id, T newData);
        boolean delete(ID id);
        T findById(ID id);
        ArrayList<T> findAll();
}


