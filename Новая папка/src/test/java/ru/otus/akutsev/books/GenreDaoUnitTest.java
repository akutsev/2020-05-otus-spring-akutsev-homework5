package ru.otus.akutsev.books;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.akutsev.books.dao.GenreDao;
import ru.otus.akutsev.books.dao.GenreDaoImpl;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Genre;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест ДАО работы с авторами")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoUnitTest {

	@Autowired
	GenreDao genreDao;

	@DisplayName("вставка нового жанра и получение его по ИД")
	@Test
	public void addGenreGetByIdTest() {
		int id = 95;
		String name = "Non fiction";
		Genre genre = new Genre(id, name);

		assertThrows(EmptyResultDataAccessException.class, () -> genreDao.getAById(id));

		genreDao.add(genre);

		Genre genreFromDb = genreDao.getAById(id);
		assertEquals(name, genreFromDb.getGenreName());
	}

	@DisplayName("изменение имени жанра")
	@Test
	public void updateGenreTest() {
		int id = 2;
		String newName = "Romance";

		genreDao.updateGenreName(id,newName);
		Genre updatedGenre = genreDao.getAById(id);

		assertEquals(newName, updatedGenre.getGenreName());
	}

	@DisplayName("удаление жанра")
	@Test
	public void deleteGenreTest() {
		int id = 99;
		String name = "Fantasy";
		Genre genre = new Genre(id, name);
		genreDao.add(genre);

		assertDoesNotThrow(() -> genreDao.getAById(id));

		genreDao.delete(id);

		assertThrows(EmptyResultDataAccessException.class, () -> genreDao.getAById(id));
	}
}
