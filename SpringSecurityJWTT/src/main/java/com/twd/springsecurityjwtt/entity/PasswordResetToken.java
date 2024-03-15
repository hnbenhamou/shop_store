package com.twd.springsecurityjwtt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken {

    private static final int EXPIRATION_TIME=10;

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String token;
    private Date expirationTime;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_TOKEN"))
    private OurUsers user;

    public PasswordResetToken(OurUsers user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);

    }



    private Date calculateExpirationDate(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());

    }
}
