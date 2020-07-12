package ru.otus.akutsev.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao{
	private final NamedParameterJdbcOperations jdbc;
	private AuthorDaoImpl authorDaoImpl;
	private GenreDaoImpl genreDaoImpl;

	private final RowMapper<Book> bookRowMapper = (resultSet, rowNum) -> {
		int id = resultSet.getInt("id");
		String bookName = resultSet.getNString("name");
		Author author = authorDaoImpl.getAById(resultSet.getInt("author_id"));
		Genre genre = genreDaoImpl.getAById(resultSet.getInt("genre_id"));

		return new Book(id, bookName, author, genre);
	};

	@Autowired
	public BookDaoImpl(NamedParameterJdbcOperations jdbc, AuthorDaoImpl authorDaoImpl, GenreDaoImpl genreDaoImpl) {
		this.jdbc = jdbc;
		this.authorDaoImpl = authorDaoImpl;
		this.genreDaoImpl = genreDaoImpl;
	}

	public void add(Book book) {
		Map<String, String> namedParameters = Map.of(
				"bookID", String.valueOf(book.getId()),
				"bookName", book.getName(),
				"authorID", String.valueOf(book.getAuthor().getId()),
				"genreID", String.valueOf(book.getGenre().getId())
		);
		jdbc.update("insert into books (id, name, author_ID, genre_ID) " +
				"values (:bookID, :bookName, :authorID, :genreID)", namedParameters);
	}

	public Book getAById(int id) {
		Map<String, Integer> namedParameters = Map.of(
				"bookID", id
		);
		return jdbc.queryForObject("select id, name, author_id, genre_id from books " +
				"where id=:bookID", namedParameters, bookRowMapper);
	}

	public List<Book> getAll() {
		return jdbc.query("select id, name, author_id, genre_id from books", bookRowMapper);
	}

	public void updateBook (int id, String newName, Author newAuthor, Genre newGenre) {
		Map<String, String> namedParameters = Map.of(
				"bookID", String.valueOf(id),
				"bookName", newName,
				"authorID", String.valueOf(newAuthor.getId()),
				"genreID", String.valueOf(newGenre.getId())
		);
		jdbc.update("update books set name=:bookName, author_id=:authorID, genre_id=:genreID where id=:bookID",
				namedParameters);
	}

	public void delete (int id) {
		Map<String, Integer> namedParameters = Map.of(
				"bookID", id
		);
		jdbc.update("delete from books where id=:bookID", namedParameters);
	}

}
