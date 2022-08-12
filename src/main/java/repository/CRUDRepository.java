package repository;

import domain.User;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<K,T> {

     int DEFAULT_LIMIT=10;

     int DEFAULT_OFFSET=0;
    T findById(K id);

    T findByNameAndSurname(String name, String surname);


    Optional<T> findOne(K id);

    List<T> findAll();

    List<T> findAll(int limit,int offset);

    T create(T object);

    T update(T object);

    K delete(K id);
}
