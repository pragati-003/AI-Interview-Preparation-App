package interviewprep.service;

import interviewprep.model.Progress;
import interviewprep.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository repository;

    public ProgressService(ProgressRepository repository) {
        this.repository = repository;
    }

    public Progress saveProgress(Progress progress) {
        return repository.save(progress);
    }

    public List<Progress> getAllProgress() {
        return repository.findAll();
    }
}