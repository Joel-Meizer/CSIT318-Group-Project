package CSIT318Project.bookService.service;

import CSIT318Project.bookService.model.event.BookEvent;
import CSIT318Project.bookService.repository.AccountEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EventHandler {

    private final AccountEventRepository accountEventRepository;

    EventHandler(AccountEventRepository accountEventRepository) {
        this.accountEventRepository = accountEventRepository;
    }

    @TransactionalEventListener
    public void handleBorrowEvent(BookEvent bookEvent){
        accountEventRepository.save(bookEvent);
        System.out.println(bookEvent);
    }

    @EventListener
    public void handleReturnEvent(BookEvent bookEvent) {
        accountEventRepository.save(bookEvent);
        System.out.println(bookEvent);
    }
}
