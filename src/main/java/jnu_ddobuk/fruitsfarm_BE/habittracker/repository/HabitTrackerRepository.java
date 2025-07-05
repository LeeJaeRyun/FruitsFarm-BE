package jnu_ddobuk.fruitsfarm_BE.habittracker.repository;

import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackerRepository extends JpaRepository<HabitTracker,Long> {
}
