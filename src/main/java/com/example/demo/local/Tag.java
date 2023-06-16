package com.example.demo.local;

import static lombok.AccessLevel.PROTECTED;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TAGS")
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Tag {
    @Id
    @Column(name = "TAG_NAME", nullable = false)
    private String name;
}
