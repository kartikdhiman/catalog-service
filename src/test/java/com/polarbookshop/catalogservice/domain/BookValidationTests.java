package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class BookValidationTests {
	private static Validator validator;

	@BeforeAll
	static void setUp() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	@Test
	void whenAllFieldsCorrectThenValidationSucceeds() {
		var book = Book.of("1234567890", "Title", "Author", 9.90, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}

	@Test
	void whenIsbnNotDefinedThenValidationFails() {
		var book = Book.of("", "Title", "Author", 9.90, "Polarsophia");
		var violations = validator.validate(book);
		var constraintViolationMessages = violations
						.stream()
						.map(ConstraintViolation::getMessage)
						.toList();
		assertThat(violations).hasSize(2);
		assertThat(constraintViolationMessages)
						.contains("The book ISBN must be defined.")
						.contains("The ISBN format must be valid.");
	}

	@Test
	void whenIsbnDefinedButIncorrectThenValidationFails() {
		var book = Book.of("a234567890", "Title", "Author", 9.90, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
						.isEqualTo("The ISBN format must be valid.");
	}

	@Test
	void whenTitleIsNotDefinedThenValidationFails() {
		var book = Book.of("1234567890", "", "Author", 9.90, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
						.isEqualTo("The book title must be defined.");
	}

	@Test
	void whenAuthorIsNotDefinedThenValidationFails() {
		var book = Book.of("1234567890", "Title", "", 9.90, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
						.isEqualTo("The book author must be defined.");
	}

	@Test
	void whenPriceIsNotDefinedThenValidationFails() {
		var book = Book.of("1234567890", "Title", "Author", null, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
						.isEqualTo("The book price must be defined.");
	}

	@ParameterizedTest
	@ValueSource(doubles = {0.0, -9.90})
	void whenPriceDefinedButZeroOrNegativeThenValidationFails(double price) {
		var book = Book.of("1234567890", "Title", "Author", price, "Polarsophia");
		var violations = validator.validate(book);
		assertThat(violations).hasSize(1);
		assertThat(violations.iterator().next().getMessage())
						.isEqualTo("The book price must be greater than zero.");
	}

	@Test
	void whenPublisherIsNotDefinedThenValidationSucceeds() {
		Book book = Book.of("1234567890", "Title", "Author", 9.90, "");
		var violations = validator.validate(book);
		assertThat(violations).isEmpty();
	}
}
