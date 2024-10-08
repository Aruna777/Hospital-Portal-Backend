package com.LoginService.LoginService.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column("user_id")
    private Integer userId;

    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("password")
    private String password;

}
