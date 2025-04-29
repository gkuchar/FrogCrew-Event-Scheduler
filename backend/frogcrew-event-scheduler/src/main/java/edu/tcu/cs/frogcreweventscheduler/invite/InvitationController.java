// src/main/java/edu/tcu/cs/frogcreweventscheduler/invite/InvitationController.java
package edu.tcu.cs.frogcreweventscheduler.invite;

import edu.tcu.cs.frogcreweventscheduler.invite.dto.InviteRequestDto;
import edu.tcu.cs.frogcreweventscheduler.invite.dto.InvitationDto;
import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
public class InvitationController {

    private final InvitationService service;

    public InvitationController(InvitationService service) {
        this.service = service;
    }

    @PostMapping
    public Result invite(@Valid @RequestBody InviteRequestDto body) {
        List<String> emails = service.invite(body.getEmails());
        return new Result(true, StatusCode.SUCCESS, "Invite Success", emails);
    }

    @GetMapping("/{token}")
    public Result validate(@PathVariable String token) {
        var inv = service.validate(token);
        var dto = new InvitationDto();
        dto.setToken(inv.getToken());
        return new Result(true, StatusCode.SUCCESS, "Invitation valid", dto);
    }
}
