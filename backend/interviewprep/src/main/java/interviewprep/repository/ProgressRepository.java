package interviewprep.repository;

import interviewprep.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository
        extends JpaRepository<Progress, Long> {

}