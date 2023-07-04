package com.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author luis.buelna
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    private Set<String> roles;
}
