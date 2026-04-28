package ms.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ms.user_service.dto.LoginRequest;
import ms.user_service.dto.RegisterRequest;
import ms.user_service.entity.User;
import ms.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Register request received for email: {}", request.getEmail());
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        log.info("Login attempt for email: {}", req.getEmail());
        return ResponseEntity.ok(
                userService.login(req.getEmail(), req.getPassword())
        );
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>>getAllUsers(){
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());

    }



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
