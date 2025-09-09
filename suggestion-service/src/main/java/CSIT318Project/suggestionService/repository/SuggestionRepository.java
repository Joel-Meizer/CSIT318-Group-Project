package CSIT318Project.suggestionService.repository;

import CSIT318Project.suggestionService.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, String> {
}
