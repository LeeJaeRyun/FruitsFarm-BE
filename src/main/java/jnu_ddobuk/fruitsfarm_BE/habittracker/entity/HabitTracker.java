package jnu_ddobuk.fruitsfarm_BE.habittracker.entity;

import jakarta.persistence.*;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HabitTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String type; //grape or watermelon

    @Column(nullable = false)
    private String achievement;

    @Column(nullable = false)
    private String motivation;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Lob
    @Column
    private String progress; // 색칠칸

    public HabitTracker(String type, String achievement, String motivation, LocalDate startDate, LocalDate endDate, String progress, Member member) {
        this.type = type;
        this.achievement = achievement;
        this.motivation = motivation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;
        this.member = member;
    }
}
