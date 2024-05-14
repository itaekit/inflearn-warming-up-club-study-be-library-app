package com.warmingup.libraryapp.domain.user;

import com.warmingup.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = userLoanHistories
                .stream()
                .filter(history->history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Book not found"));

        targetHistory.doReturn();
    }
}
