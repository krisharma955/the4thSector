package com.K955.the4thSector.Entity;

import com.K955.the4thSector.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    Integer season;

    @Column(nullable = false, unique = true)
    Integer round;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String circuit;

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    LocalDate practiceDate;

    @Column(nullable = false)
    LocalDate qualifyingDate;

    @Column(nullable = false)
    LocalDate raceDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    Status status = Status.SCHEDULED;

    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

}
