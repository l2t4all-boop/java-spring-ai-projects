package com.l2t.sia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "state_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateInfo {

    @Id
    private Integer id;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String capital;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
