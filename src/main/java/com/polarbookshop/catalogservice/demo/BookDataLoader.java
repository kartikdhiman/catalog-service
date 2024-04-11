package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {
	private final BookRepository bookRepository;

	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadBookTestData() {
		bookRepository.deleteAll();
		var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Polarsophia");
		var book2 = Book.of("1234567898", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia");
		var book3 = Book.of("1234567899", "Northern Lights", "J.K. Rowling", 9.90, "Bloomsbury Publishing");
		var book4 = Book.of("1234567892", "Polar Sunset", "George R.R. Martin", 12.90, "Bantam Books");
		var book5 = Book.of("1234567893", "Desert Mirage", "Stephen King", 15.90, "Scribner");
		bookRepository.saveAll(List.of(book1, book2, book3, book4, book5));
	}
}