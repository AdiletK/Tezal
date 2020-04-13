package kg.tezal.tezal_back;

import kg.tezal.tezal_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TezalBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TezalBackApplication.class, args);
        init();
    }

    public static void init(){
    }

    @Autowired
    private UserRepository userRepository;

//    @PostConstruct
//    private void postConstruct() {
//        User admin = new User(null, null, "adminuser", "admin123", true);
//        userRepository.save(admin, normalUser);
//    }
}

