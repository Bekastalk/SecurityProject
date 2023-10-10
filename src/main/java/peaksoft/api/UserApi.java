package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.save(userRequest));
    }

    @PutMapping("/{userId}")
    @Secured({"USER", "ADMIN"})
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                   @RequestBody UserRequest userRequest,
                                                   Principal principal){
        return  ResponseEntity.ok(userService.update(principal,userId, userRequest));
    }
}
