package com.labi.schedulerjava.controller;

import com.labi.schedulerjava.dtos.CreateUserDto;
import com.labi.schedulerjava.dtos.ReadUserDto;
import com.labi.schedulerjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateUserDto dto) {
        userService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/approve")
    public ResponseEntity<List<ReadUserDto>> findUsersToApprove() {
        List<ReadUserDto> users = userService.findUsersToApprove();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
