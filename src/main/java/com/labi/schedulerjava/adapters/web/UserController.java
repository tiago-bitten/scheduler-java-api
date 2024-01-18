package com.labi.schedulerjava.adapters.web;

import com.labi.schedulerjava.core.usecases.UseCase;
import com.labi.schedulerjava.core.usecases.user.ApproveUserUseCase;
import com.labi.schedulerjava.core.usecases.user.FindUserMinistriesUseCase;
import com.labi.schedulerjava.core.usecases.user.FindUsersToApproveUseCase;
import com.labi.schedulerjava.dtos.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private FindUsersToApproveUseCase findUsersToApproveUseCase;

    @Autowired
    private ApproveUserUseCase approveUserUseCase;

    @Autowired
    private FindUserMinistriesUseCase findUserMinistriesUseCase;

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

    @GetMapping("/ministries")
    public ResponseEntity<UseCase.OutputValues> findUserMinistries(@RequestHeader("Authorization") String authHeader) {
        UseCase.OutputValues outputValues =
                findUserMinistriesUseCase.execute(new FindUserMinistriesUseCase.InputValues(authHeader));
        return new ResponseEntity<>(outputValues, HttpStatus.OK);
    }
}