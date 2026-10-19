package sfera.crmsfera.service;

import sfera.crmsfera.configuration.TrackAction;
import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.entity.Group;
import sfera.crmsfera.entity.Student;
import sfera.crmsfera.entity.User;
import sfera.crmsfera.entity.enums.ActionType;
import sfera.crmsfera.entity.enums.Role;
import sfera.crmsfera.exception.DataNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sfera.crmsfera.repository.GroupRepository;
import sfera.crmsfera.repository.StudentRepository;
import sfera.crmsfera.repository.UserRepository;
import sfera.crmsfera.security.CustomUserDetails;
import sfera.crmsfera.security.JwtService;
import sfera.crmsfera.dto.request.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final NotificationService notificationService;

    public ApiResponse<String> login(String phone, String password) {
        Optional<User> optionalUser = userRepository.findByPhoneAndActiveTrue(phone);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.isActive()){
                return ApiResponse.error("User is not enabled");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ApiResponse.error("Invalid password");
            }

            CustomUserDetails userDetails = CustomUserDetails.fromUser(user);
            String token = jwtService.generateToken(
                    userDetails.getUsername(),
                    userDetails.getRole()
            );

            notificationService.saveNotification(new ReqNotification("Sfera Academy xabarnomasi",
                    "Siz tizimga muvaffaqiyatli kirdingiz!", null, user.getId()));

            return ApiResponse.success(token, userDetails.getRole());
        }

        Optional<Student> optionalStudent = studentRepository.findByPhone(phone);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            if (!student.isActive()){
                return ApiResponse.error("Student is not active");
            }

            if (!passwordEncoder.matches(password, student.getPassword())) {
                return ApiResponse.error("Invalid password");
            }

            CustomUserDetails userDetails = CustomUserDetails.fromStudent(student);
            String token = jwtService.generateToken(
                    userDetails.getUsername(),
                    userDetails.getRole()
            );

            notificationService.saveNotification(new ReqNotification("Sfera Academy xabarnomasi",
                    "Siz tizimga muvaffaqiyatli kirdingiz!", student.getId(), null));
            return ApiResponse.success(token, userDetails.getRole());
        }

        return ApiResponse.error("User topilmadi");
    }


    public ApiResponse<String> saveUser(AuthRegister authRegister, Role role){

        boolean b = userRepository.existsByPhone(authRegister.getPhone());
        if (b){
            return ApiResponse.error("Teacher already exists");
        }

        User teacher = User.builder()
                .phone(authRegister.getPhone())
                .fullName(authRegister.getFullName())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .role(role)
                .active(true)
                .build();
        userRepository.save(teacher);
        return ApiResponse.success(null, "Successfully added user");
    }


    @TrackAction(
            type = ActionType.STUDENT_CREATED,
            description = "Student yaratildi"
    )
    public ApiResponse<String> saveStudent(ReqStudent reqStudent){

        boolean b = studentRepository.existsByPhone(reqStudent.getPhone());

        if (b){
            return ApiResponse.error("User already exists");
        }

        User parent = userRepository.findByPhoneAndRole(reqStudent.getParentPhone(), Role.ROLE_PARENT).orElseThrow(
                () -> new DataNotFoundException("Parent not found")
        );

        Group group = groupRepository.findById(reqStudent.getGroupId()).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );

        Student student = Student.builder()
                .fullName(reqStudent.getFullName())
                .parent(parent)
                .phone(reqStudent.getPhone())
                .password(passwordEncoder.encode(reqStudent.getPassword()))
                .group(group)
                .imgUrl(reqStudent.getImgUrl())
                .build();
        studentRepository.save(student);
        return ApiResponse.success(null, "Successfully saved student");
    }



    public ApiResponse<String> updatePassword(ReqPassword reqPassword){
        Optional<User> optionalUser = userRepository.findByPhoneAndActiveTrue(reqPassword.getPhone());

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(reqPassword.getPassword()));
            userRepository.save(user);

            CustomUserDetails userDetails = CustomUserDetails.fromUser(user);
            String token = jwtService.generateToken(
                    userDetails.getUsername(),
                    userDetails.getRole()
            );

            return ApiResponse.success(token, "Successfully updated password");
        }

        Optional<Student> optionalStudent = studentRepository.findByPhone(reqPassword.getPhone());
        if (optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            student.setPassword(passwordEncoder.encode(reqPassword.getPassword()));
            studentRepository.save(student);
            CustomUserDetails userDetails = CustomUserDetails.fromStudent(student);
            String token = jwtService.generateToken(
                    userDetails.getUsername(),
                    userDetails.getRole()
            );

            return ApiResponse.success(token, "Successfully updated password");
        }
        throw new DataNotFoundException("User not found");

    }


    public ApiResponse<String> registerUser(ReqStudent reqStudent){
//        boolean b = userRepository.existsByPhoneAndActiveTrue(reqStudent.getParentParentPhone() != null ? reqStudent.getParentParentPhone() : reqStudent.getParentPhone()); // yoki mavjud logikangiz

        boolean parentExists = userRepository.existsByPhoneAndActiveTrue(reqStudent.getParentPhone());
        if (!parentExists){

            boolean studentExists = studentRepository.existsByPhone(reqStudent.getPhone());
            if (!studentExists){
                User parent = User.builder()
                        .fullName(reqStudent.getParentName())
                        .role(Role.ROLE_PARENT)
                        .phone(reqStudent.getParentPhone())
                        .password(passwordEncoder.encode(reqStudent.getParentPhone().substring(8,12)))
                        .active(true)
                        .build();
                userRepository.save(parent);

                Group group = groupRepository.findById(reqStudent.getGroupId()).orElseThrow(
                        () -> new DataNotFoundException("Group not found")
                );

                Student student = Student.builder()
                        .fullName(reqStudent.getFullName())
                        .parent(parent)
                        .phone(reqStudent.getPhone())
                        .password(passwordEncoder.encode(reqStudent.getPassword()))
                        .group(group)
                        .imgUrl(reqStudent.getImgUrl())
                        .active(true)
                        .build();
                studentRepository.save(student);
                return ApiResponse.success(null, "Successfully registered user");
            }

            return ApiResponse.error("Student already exists");
        }

        return ApiResponse.error("User already exists");

    }


    public ApiResponse<String> registerFromTelegram(ReqStudentBot req) {
        if (studentRepository.existsByPhone(req.getPhone())) {
            return ApiResponse.error("Student already exists");
        }

        Group group = groupRepository.findById(req.getGroupId()).orElseThrow(
                () -> new DataNotFoundException("Group not found")
        );

        User parent = null;

        // Telefon raqam orqali ota-onani qidiramiz (username tekshiruvi olib tashlandi)
        if (req.getParentPhone() != null && !req.getParentPhone().isBlank()) {
            String p = normalizePhone(req.getParentPhone());
            parent = userRepository.findByPhoneAndActiveTrue(p).orElse(null);
            req.setParentPhone(p);
        }

        // Parent topilmasa -> yaratamiz
        if (parent == null) {
            if (req.getParentPhone() == null || !req.getParentPhone().matches("^998\\d{9}$")) {
                return ApiResponse.error("Parent phone required. Parent contact must be phone number (998...)");
            }
            if (req.getParentName() == null || req.getParentName().isBlank()) {
                return ApiResponse.error("Parent name required");
            }

            if (userRepository.existsByPhoneAndActiveTrue(req.getParentPhone())) {
                return ApiResponse.error("Parent phone already exists");
            }

            parent = User.builder()
                    .fullName(req.getParentName())
                    .role(Role.ROLE_PARENT)
                    .phone(req.getParentPhone())
                    .password(passwordEncoder.encode(last4(req.getParentPhone())))
                    .active(false)
                    .build();

            userRepository.save(parent);
        }

        // Student yaratamiz
        Student student = Student.builder()
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(last4(req.getPhone())))
                .group(group)
                .parent(parent)
                .imgUrl(req.getImgUrl())
                .active(false)
                .build();

        studentRepository.save(student);

        return ApiResponse.success(null, "Successfully registered student");
    }

    private String normalizePhone(String s) {
        if (s == null) return null;
        return s.replace("+", "").replace(" ", "").trim();
    }

    public String last4(String phone) {
        return phone.substring(phone.length() - 4);
    }

    @Transactional
    public ApiResponse<String> linkParentTelegram(String phone,
                                                  Long telegramId,
                                                  String telegramUsername) {

        if (phone == null || phone.isBlank()) {
            return ApiResponse.error("Telefon raqam kiritilmadi");
        }

        Optional<User> optionalParent =
                userRepository.findByPhoneAndRole(
                        phone,
                        Role.ROLE_PARENT
                );

        if (optionalParent.isEmpty()) {
            return ApiResponse.error("Parent topilmadi");
        }

        User parent = optionalParent.get();
        userRepository.save(parent);

        return ApiResponse.success(null, "Muvaffaqiyatli bajarildi");
    }

    public ApiResponse<String> validate(Token token) {
        if (token.getToken() == null || token.getToken().trim().isEmpty()) {
            return ApiResponse.error("Token is required");
        }

        try {
            Claims claims = jwtService.extractAllClaims(token.getToken());

            if (jwtService.isTokenExpired(token.getToken())) {
                return ApiResponse.error("Token expired");
            }

            String phone = claims.getSubject();
            if (phone == null || phone.isEmpty()) {
                return ApiResponse.error("Invalid token");
            }

            if (!userRepository.existsByPhoneAndActiveTrue(phone)) {
                return ApiResponse.error("User not found"); // DataNotFoundException o'rniga ApiResponse.error
            }

            return ApiResponse.success(phone, "Token is valid");

        } catch (ExpiredJwtException e) {
            return ApiResponse.error("Token expired");
        } catch (Exception e) {
            return ApiResponse.error("Invalid token");
        }
    }
}