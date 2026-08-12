package ru.yandex.practicum.analyzer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "scenarios", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"hub_id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hub_id")
    private String hubId;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "scenario_conditions",
        joinColumns = @JoinColumn(name = "scenario_id"),
        inverseJoinColumns = @JoinColumn(name = "condition_id")
    )
    @Builder.Default
    private Set<Condition> conditions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "scenario_actions",
        joinColumns = @JoinColumn(name = "scenario_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    @Builder.Default
    private Set<Action> actions = new HashSet<>();
}