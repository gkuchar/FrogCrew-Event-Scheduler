// src/main/java/edu/tcu/cs/frogcreweventscheduler/invite/dto/InviteRequestDto.java
package edu.tcu.cs.frogcreweventscheduler.invite.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class InviteRequestDto {

    @NotEmpty(message = "At least one email is required.")
    private List<String> emails;

    public InviteRequestDto() { }

    public List<String> getEmails() {
        return emails;
    }
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
