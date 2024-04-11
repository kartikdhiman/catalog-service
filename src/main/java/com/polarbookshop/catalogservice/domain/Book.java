package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Book(
				@Id
				Long id,

				@NotBlank(message = "{isbn.notBlank}")
				@Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "{isbn.invalid}")
				String isbn,

				@NotBlank(message = "{title.notBlank}")
				String title,

				@NotBlank(message = "{author.notBlank}")
				String author,

				@NotNull(message = "{price.notNull}")
				@Positive(message = "{price.positive}")
				Double price,

				String publisher,

				@CreatedDate
				Instant createdDate,

				@LastModifiedDate
				Instant lastModifiedDate,

				@Version
				int version
) {
	public static Book of(String isbn, String title, String author, Double price, String publisher) {
		return new Book(null, isbn, title, author, price, publisher, null, null, 0);
	}
}
