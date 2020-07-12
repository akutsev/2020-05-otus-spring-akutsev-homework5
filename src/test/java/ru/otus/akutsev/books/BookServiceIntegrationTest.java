package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;
import ru.otus.akutsev.books.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Тест приложения с поднятием контекста")
@SpringBootTest
public class BookServiceIntegrationTest {

	@Autowired
	BookService bookService;

	@DisplayName("на примере добавления+извлечения книги")
	@Test
	public void addBookGetByIdTest() {
		int id = 1386;
		String name = "Anna Karenina";
		Author author = new Author(1, "Lev Tolstoy");
		Genre genre = new Genre(2, "Drama");
		Book book = new Book(id, name, author, genre);

		assertFalse(bookService.getAll().contains(book));

		bookService.add(book);

		Book bookFromDb = bookService.getAById(id);
		assertEquals(name, bookFromDb.getName());
		assertEquals(author, bookFromDb.getAuthor());
		assertEquals(genre, bookFromDb.getGenre());
	}

}
