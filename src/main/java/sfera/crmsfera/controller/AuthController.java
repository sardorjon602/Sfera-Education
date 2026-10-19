package sfera.crmsfera.controller;

import sfera.crmsfera.configuration.TrackAction;
import sfera.crmsfera.dto.ApiResponse;
import sfera.crmsfera.dto.request.ReqStudent;
import sfera.crmsfera.dto.request.Token;
import sfera.crmsfera.entity.enums.ActionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sfera.crmsfera.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @TrackAction(
            type = ActionType.LOGIN,
            description = "Login qilindi"
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @Pattern(
                  regexp = "^998(9[012345789]|6[0123456789]|7[0123456789]|8[0123456789]|3[0123456789]|5[0123456789])[0-9]{7}$",
                  message = "Telefon raqam xato kiritilgan"
            )
            @Valid @RequestParam String phone,
            @RequestParam String password
    ){
        return ResponseEntity.ok(authService.login(phone, password));
    }


    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> checkToken(@RequestBody Token token){
        return ResponseEntity.ok(authService.validate(token));
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody ReqStudent reqStudent){
        return ResponseEntity.ok(authService.registerUser(reqStudent));
    }

}
