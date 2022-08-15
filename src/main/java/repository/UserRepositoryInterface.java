package repository;

import domain.User;

import java.util.Map;

public interface UserRepositoryInterface extends CRUDRepository<Long,User> {



    Map<String, Double> getUserStats();









}
