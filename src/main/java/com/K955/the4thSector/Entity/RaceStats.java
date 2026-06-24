package com.K955.the4thSector.Entity;

import com.K955.the4thSector.Enum.RaceStatus;
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
@Table(name = "race_stats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"race_id", "driver_id"})
})
public class RaceStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    Driver driver;

    @Column(nullable = false)
    Integer position;

    @Builder.Default
    Integer points = 0;

    @Builder.Default
    Integer lapsCompleted = 0;

    Integer gapToLeader;

    @Builder.Default
    Integer pitStops = 0;

    @Builder.Default
    RaceStatus raceStatus = RaceStatus.RUNNING;

    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

}
