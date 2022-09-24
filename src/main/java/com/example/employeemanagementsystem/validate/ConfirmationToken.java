package com.example.employeemanagementsystem.validate;

import com.example.employeemanagementsystem.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime createAt;
    private LocalDateTime expiredAt;

    public ConfirmationToken(String token, User user, LocalDateTime createAt, LocalDateTime expiredAt){
        this.token = token;
        this.user = user;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
    }
}
