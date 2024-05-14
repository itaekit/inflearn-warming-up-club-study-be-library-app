package com.warmingup.libraryapp.service.book;

import com.warmingup.libraryapp.domain.book.Book;
import com.warmingup.libraryapp.domain.book.BookRepository;
import com.warmingup.libraryapp.domain.user.User;
import com.warmingup.libraryapp.domain.user.UserRepository;
import com.warmingup.libraryapp.domain.user.loanhistory.UserLoanHistory;
import com.warmingup.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import com.warmingup.libraryapp.dto.book.request.BookCreateRequest;
import com.warmingup.libraryapp.dto.book.request.BookLoanRequest;
import com.warmingup.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(
            BookRepository bookRepository,
            UserLoanHistoryRepository userLoanHistoryRepository,
            UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        Book book = bookRepository
                .findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        boolean bookNotAvailable = userLoanHistoryRepository
                .existsByBookNameAndIsReturn(book.getName(), false);

        if (bookNotAvailable) {
            throw new IllegalArgumentException("Book already loaned");
        }

        User user = userRepository
                .findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.loanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository
                .findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.returnBook(request.getBookName());
    }
}
