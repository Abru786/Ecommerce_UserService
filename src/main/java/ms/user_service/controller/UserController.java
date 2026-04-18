package ms.user_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ms.user_service.dto.LoginRequest;
import ms.user_service.dto.RegisterRequest;
import ms.user_service.entity.User;
import ms.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(
                userService.login(req.getEmail(), req.getPassword())
        );
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>>getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());

    }



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
