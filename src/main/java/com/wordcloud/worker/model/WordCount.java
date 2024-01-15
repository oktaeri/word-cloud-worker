package com.wordcloud.worker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "word_count")
public class WordCount extends BaseEntity {
    @NotNull
    private Integer count;

    @NotNull
    private String word;

    @ManyToOne
    @JoinColumn(name = "user_token_id", referencedColumnName = "id")
    private UserToken userToken;
}