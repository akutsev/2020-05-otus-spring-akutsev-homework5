package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.akutsev.books.dao.AuthorDao;
import ru.otus.akutsev.books.dao.AuthorDaoImpl;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест ДАО работы с авторами")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoUnitTest {

	@Autowired
	AuthorDao authorDao;

	@DisplayName("вставка нового автора и получение его по ИД")
	@Test
	public void addAuthorGetByIdTest() {
		int id = 657;
		String name = "Aleksandr Pushkin";
		Author author = new Author(id, name);

		assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getAById(id));

		authorDao.add(author);

		Author authorFromDb = authorDao.getAById(id);
		assertEquals(name, authorFromDb.getName());
	}

	@DisplayName("изменение имени автора")
	@Test
	public void updateAuthorTest() {
		int id = 1;
		String newName = "Lev Nikolaevich Tolstoy";

		authorDao.updateName(id,newName);
		Author updatedAuthor = authorDao.getAById(id);

		assertEquals(newName, updatedAuthor.getName());
	}

	@DisplayName("удаление автора")
	@Test
	public void deleteAuthorTest() {
		int id = 657;
		String name = "Aleksandr Pushkin";
		Author author = new Author(id, name);
		authorDao.add(author);

		assertDoesNotThrow(() -> authorDao.getAById(id));

		authorDao.delete(id);

		assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getAById(id));
	}

}
