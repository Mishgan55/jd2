package repository;

import domain.User;

import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long,User> {

    User findByName(String name, String surname);

    Map<String, Double> getUserStats();









}
