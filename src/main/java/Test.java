import domain.User;
import repository.UserRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        for (User user : userRepository.findAll()) {
            System.out.println(user);
            
        }

        User byId = userRepository.findById(1L);

        System.out.println(byId);

        System.out.println(userRepository.findOne(2L));

//        User user = new User();
//        user.setUserName("Dima");
//        user.setSurname("Avseushkin");
//        user.setBirth(new Timestamp(new Date().getTime()));
//        user.setDeleted(false);
//        user.setCreationDate(new Timestamp(new Date().getTime()));
//        user.setModificationDate(new Timestamp(new Date().getTime()));
//        user.setWeight(82.1);
//
//        userRepository.create(user);
//        System.out.println(user);

       System.out.println(userRepository.findByNameAndSurname("Mikhail","Khorsun"));

        Map<String, Double> userStats = userRepository.getUserStats();

        for (Map.Entry<String, Double> stringDoubleEntry : userStats.entrySet()) {
            System.out.println(stringDoubleEntry);
        }

    }
}
