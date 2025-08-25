package CSIT318Project.bookService.service;

import CSIT318Project.bookService.model.event.BookEvent;
import CSIT318Project.bookService.repository.BookEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EventHandler {

    private final BookEventRepository bookEventRepository;

    EventHandler(BookEventRepository bookEventRepository) {
        this.bookEventRepository = bookEventRepository;
    }

    @TransactionalEventListener
    public void handleBorrowEvent(BookEvent bookEvent){
        bookEventRepository.save(bookEvent);
        System.out.println(bookEvent);
    }

    @EventListener
    public void handleReturnEvent(BookEvent bookEvent) {
        bookEventRepository.save(bookEvent);
        System.out.println(bookEvent);
    }
}
