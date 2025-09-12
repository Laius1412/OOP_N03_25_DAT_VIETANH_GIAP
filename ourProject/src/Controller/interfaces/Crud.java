package Controller.interfaces;
import java.util.List;
import java.util.Optional;

public interface Crud<T, ID> {
    T create(T entity);
    Optional<T> readById(ID id);
    List<T> readAll();
    T update(T entity);
    boolean deleteById(ID id);
    boolean delete(T entity);
}
