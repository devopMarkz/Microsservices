package io.github.devopMarkz.book_service.repository;

import io.github.devopMarkz.book_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
