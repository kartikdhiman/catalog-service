package com.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	@Test
	void testAddBookToCatalog() {
		var bookToCreate = Book.of("1234561232", "Title", "Author", 9.90, "Polarsophia");
		when(bookRepository.save(bookToCreate)).thenReturn(bookToCreate);
		assertThat(bookService.addBookToCatalog(bookToCreate))
						.isEqualTo(bookToCreate);
	}

	@Test
	void whenBookToCreateAlreadyExistsThenThrows() {
		var bookIsbn = "1234561232";
		var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
		when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);
		assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
						.isInstanceOf(BookAlreadyExistsException.class)
						.hasMessage("A book with ISBN: %s already exists.".formatted(bookIsbn));
	}

	@Test
	void whenBookToReadDoesNotExistThenThrows() {
		var bookIsbn = "1234561232";
		when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> bookService.viewBookDetails(bookIsbn))
						.isInstanceOf(BookNotFoundException.class)
						.hasMessage("The book with ISBN: %s was not found.".formatted(bookIsbn));
	}
}
