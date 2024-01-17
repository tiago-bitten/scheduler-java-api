package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.user.ApproveUserUseCase;
import com.labi.schedulerjava.core.usecases.user.CreateUserUseCase;
import com.labi.schedulerjava.core.usecases.user.FindUsersToApproveUseCase;
import com.labi.schedulerjava.dtos.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private FindUsersToApproveUseCase findUsersToApproveUseCase;

    @Autowired
    private ApproveUserUseCase approveUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateUserDto dto) {
        createUserUseCase.execute(new CreateUserUseCase.InputValues(dto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/approve")
    public ResponseEntity<UseCase.OutputValues> findUsersToApprove() {
        UseCase.OutputValues outputValues =
                findUsersToApproveUseCase.execute(new FindUsersToApproveUseCase.InputValues());
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }

    @PutMapping("/approve")
    public ResponseEntity<Void> approve(@RequestParam Long userToApproveId,
                                        @RequestParam Long userApproverId,
                                        @RequestParam(defaultValue = "false") Boolean isSuperUser) {
        approveUserUseCase.execute(new ApproveUserUseCase.InputValues(userToApproveId, userApproverId, isSuperUser));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}