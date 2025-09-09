package CSIT318Project.suggestionService.repository;

import CSIT318Project.suggestionService.model.event.SuggestionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionEventRepository extends JpaRepository<SuggestionEvent, Long> {
}
