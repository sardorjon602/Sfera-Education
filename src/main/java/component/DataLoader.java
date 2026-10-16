package component;

import entity.User;
import entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create") || ddl.equals("create-drop")){
            User admin = User.builder()
                    .phone("998900000000")
                    .password(encoder.encode("admin123"))
                    .role(Role.ROLE_SUPER_ADMIN)
                    .fullName("Admin Admin")
                    .active(true)
                    .telegramId(0L)
                    .telegramUsername(null)
                    .build();

            userRepository.save(admin);
        }
    }
}
