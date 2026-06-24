package com.K955.the4thSector.Entity;

import com.K955.the4thSector.Enum.Type;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "discussions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"race_id", "type"})
})
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false, unique = true)
    Race race;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Type type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User createdBy;

    @Column(nullable = false)
    String title;

    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

}
