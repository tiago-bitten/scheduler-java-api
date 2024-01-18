package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.auth.SignUpUseCase;
import com.labi.schedulerjava.dtos.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private SignUpUseCase signUpUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignUpDto dto) {
        signUpUseCase.execute(new SignUpUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
