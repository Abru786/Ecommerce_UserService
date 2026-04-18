package ms.user_service.service;


import lombok.RequiredArgsConstructor;
import ms.user_service.dto.LoginRequest;
import ms.user_service.dto.RegisterRequest;
import ms.user_service.entity.User;
import ms.user_service.exception.InvalidCredentialsException;
import ms.user_service.exception.UserAlreadyExistsException;
import ms.user_service.repository.UserRepository;
import ms.user_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final  JwtUtil jwtUtil;



    public String register(RegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User already exists");
                });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // simple (no encoding)
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
//
//    public String login(LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
//
//        if (!user.getPassword().equals(request.getPassword())) {
//            throw new InvalidCredentialsException("Invalid email or password");
//        }
//
//        return "Login successful";
//    }
//
//    public String login(String email, String password) {
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getPassword().equals(password)) {
//            throw new RuntimeException("Invalid password");
//        }
//        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
//
//
//        return Map.of(
//                "token", token,
//                "id", user.getId(),
//                "name", user.getName(),
//                "role", user.getRole()
//        );
//    }


    public Object login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // ✅ RETURN FULL RESPONSE
        return Map.of(
                "token", token,
                "id", user.getId(),
                "name", user.getName(),
                "role", user.getRole()
        );
    }




    public List<User> getAllUsers(){
       return  userRepository.findAll();
    }




    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}