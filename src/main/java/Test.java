import domain.User;
import repository.UserRepository;

public class Test {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        for (User user : userRepository.findAll()) {
            System.out.println(user);
            
        }

    }
}
