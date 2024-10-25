package com.petwellness.api;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegisterDTO;
import com.petwellness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register/customer")
    public ResponseEntity<UserProfileDTO> registerCustomer(@Valid @RequestBody UserRegisterDTO userRegistroDTO) {
        UserProfileDTO userProfileDTO = userService.registerCliente(userRegistroDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/register/veterinario")
    public ResponseEntity<UserProfileDTO> registerVet(@Valid @RequestBody UserRegisterDTO userRegistroDTO) {
        UserProfileDTO userProfileDTO = userService.registerVeterinario(userRegistroDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/register/albergue")
    public ResponseEntity<UserProfileDTO> registerAlbergue(@Valid @RequestBody UserRegisterDTO userRegistroDTO) {
        UserProfileDTO userProfileDTO = userService.registerAlbergue(userRegistroDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid  @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponse = userService.login(loginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
