package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.akutsev.books.dao.AuthorDaoImpl;
import ru.otus.akutsev.books.dao.BookDaoImpl;
import ru.otus.akutsev.books.dao.GenreDaoImpl;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест ДАО работы с книгами")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoUnitTest {

	@Autowired
	private BookDaoImpl bookDao;

	@DisplayName("вставка новой книги и получение ее по ИД")
	@Test
	public void addBookGetByIdTest() {
		int id = 1187;
		String name = "Anna Karenina";
		Author author = new Author(1, "Lev Tolstoy");
		Genre genre = new Genre(2, "Drama");
		Book book = new Book(id, name, author, genre);

		assertFalse(bookDao.getAll().contains(book));

		bookDao.add(book);

		Book bookFromDb = bookDao.getAById(id);
		assertEquals(name, bookFromDb.getName());
		assertEquals(author, bookFromDb.getAuthor());
		assertEquals(genre, bookFromDb.getGenre());
	}

	@DisplayName("возвращает ИД всех книг")
	@Test
	public void getAllTest() {
		Integer[] expected = {1, 2};

		List<Book> allBooks = bookDao.getAll();
		Integer[] actual = allBooks.stream()
				.map(Book::getId)
				.toArray(Integer[]::new);

		assertArrayEquals(expected, actual);
	}

	@DisplayName("изменение названия и автора")
	@Test
	public void updateBookTest() {
		int id = 1;
		Book book = bookDao.getAById(id);

		Author newAuthor = new Author(5, "Daria Dontsova");
		String newName = "I am in love with killer";
		Genre oldGenre = book.getGenre();

		bookDao.updateBook(id,newName, newAuthor, oldGenre);
		Book updatedBook = bookDao.getAById(id);

		assertEquals(newName, updatedBook.getName());
		assertEquals(newAuthor, updatedBook.getAuthor());
		assertEquals(oldGenre, updatedBook.getGenre());
	}

	@DisplayName("удаление книги")
	@Test
	public void deleteBookTest() {
		int id = 2;
		assertDoesNotThrow(() -> bookDao.getAById(id));

		bookDao.delete(id);

		assertThrows(EmptyResultDataAccessException.class, () ->
			bookDao.getAById(id)
		);
	}
}
