package CSIT318Project.suggestionService.service;

import CSIT318Project.suggestionService.model.event.SuggestionEvent;
import CSIT318Project.suggestionService.repository.SuggestionEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EventHandler {

    private final SuggestionEventRepository suggestionEventRepository;

    EventHandler(SuggestionEventRepository suggestionEventRepository) {
        this.suggestionEventRepository = suggestionEventRepository;
    }

    @TransactionalEventListener
    public void handleBorrowEvent(SuggestionEvent suggestionEvent){
        suggestionEventRepository.save(suggestionEvent);
        System.out.println(suggestionEvent);
    }

    @EventListener
    public void handleReturnEvent(SuggestionEvent suggestionEvent) {
        suggestionEventRepository.save(suggestionEvent);
        System.out.println(suggestionEvent);
    }
}
