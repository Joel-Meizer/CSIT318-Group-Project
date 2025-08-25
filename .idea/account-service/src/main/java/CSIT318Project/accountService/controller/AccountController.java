package CSIT318Project.accountService.controller;

import CSIT318Project.accountService.service.accountService;
import CSIT318Project.accountService.service.dto.BookDTO;
import CSIT318Project.accountService.service.dto.LibraryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private final accountService accountService;

    AccountController(accountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/books")
    List<BookDTO> allBooks() {
        return accountService.getAllBooks();
    }

    @GetMapping("/books/{isbn}")
    BookDTO findBook(@PathVariable String isbn) {
        return accountService.getBook(isbn);
    }

    @GetMapping("/books/{isbn}/available")
    List<LibraryDTO> availableLibraries(@PathVariable String isbn) {
        return accountService.getAvailableLibraries(isbn);
    }

    @PutMapping("/books/borrow/{isbn}/{libraryId}")
    void borrow(@PathVariable String isbn, @PathVariable String libraryId) {
        accountService.borrowBook(isbn, Long.parseLong(libraryId));
    }

    @PutMapping("/books/return/{isbn}/{libraryId}")
    void return1(@PathVariable String isbn, @PathVariable String libraryId) {
        accountService.returnBook(isbn, Long.parseLong(libraryId));
    }
}