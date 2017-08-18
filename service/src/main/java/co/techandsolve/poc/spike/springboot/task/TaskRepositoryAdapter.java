package co.techandsolve.poc.spike.springboot.task;

import co.techandsolve.poc.spike.core.domain.Task;
import co.techandsolve.poc.spike.core.domain.TaskManager;
import co.techandsolve.poc.spike.core.persistence.InMemoryTaskRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter extends InMemoryTaskRepository implements TaskManager {

    @Override
    public Flux<Task> listByTag(String tag) {
        return all().filter(task ->
                Optional.ofNullable(task.getTags())
                        .filter(Objects::nonNull)
                        .orElse(new ArrayList<>())
                        .stream().anyMatch(s -> s.equals(tag))
        );
    }

    @Override
    public Flux<Task> listByStatusDone() {
        return all().filter(Task::isDone);
    }
}
