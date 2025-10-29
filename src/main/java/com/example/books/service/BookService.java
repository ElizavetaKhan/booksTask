package com.example.books.service;

import com.example.books.entity.Book;
import com.example.books.exception.NotFoundException;
import com.example.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Book getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
    }

    public Book create(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("Title is required");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("Author is required");
        return repo.save(book);
    }

    public Book update(Long id, Book updated) {
        Book book = getById(id);
        if (updated.getTitle() != null) book.setTitle(updated.getTitle());
        if (updated.getAuthor() != null) book.setAuthor(updated.getAuthor());
        book.setPublishYear(updated.getPublishYear());
        book.setDescription(updated.getDescription());
        return repo.save(book);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Book with id " + id + " not found");
        repo.deleteById(id);
    }
}