package com.wordcloud.worker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_tokens")
public class UserToken extends BaseEntity {
    @NotNull
    private String token;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    private boolean processing;
}
