package com.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;


    @RequestMapping({"/register"})
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    )
    {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @RequestMapping({"/authenticate"})
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    )
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
