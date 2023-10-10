package peaksoft.dto;

import peaksoft.entity.User;

public record UserRequest(
        String fullName,
        String email,
        String password,
        Role role
) {
    public User build(){
       return User.builder()
                .fullName(this.fullName)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
