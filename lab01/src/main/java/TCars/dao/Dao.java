package TCars.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    public Optional<T> get(Long id);
    public List<T> getAll();
    public void save(T o);
    public void update(T o) throws IllegalArgumentException;
    public void delete(T o);
}
