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

	private final RowMapper<Book> bookRowMapper = (resultSet, rowNum) -> {
		int bookId = resultSet.getInt("book_id");
		String bookName = resultSet.getNString("book_name");

		int authorId = resultSet.getInt("author_id");
		String authorName = resultSet.getNString("author_name");
		Author author = new Author(authorId, authorName);

		int genreId = resultSet.getInt("genre_id");
		String genreName = resultSet.getNString("genre_name");
		Genre genre = new Genre(genreId, genreName);

		return new Book(bookId, bookName, author, genre);
	};

	@Autowired
	public BookDaoImpl(NamedParameterJdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
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

	@Override
	public Book getAById(int id) {
		Map<String, Integer> namedParameters = Map.of(
				"bookID", id
		);
		return jdbc.queryForObject("SELECT books.id AS book_id, books.name AS book_name, authors.id AS author_id" +
				", authors.name AS author_name, genres.id AS genre_id, genres.genre_name AS genre_name FROM " +
				"((books INNER JOIN authors ON books.author_id = authors.id)" +
				"INNER JOIN genres ON books.genre_id = genres.id) WHERE books.id= :bookID", namedParameters, bookRowMapper);
	}

	@Override
	public List<Book> getAll() {
		return jdbc.query("SELECT books.id AS book_id, books.name AS book_name, authors.id AS author_id" +
				", authors.name AS author_name, genres.id AS genre_id, genres.genre_name AS genre_name FROM " +
				"((books INNER JOIN authors ON books.author_id = authors.id)" +
				"INNER JOIN genres ON books.genre_id = genres.id)", bookRowMapper);
	}

	@Override
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

	@Override
	public void delete (int id) {
		Map<String, Integer> namedParameters = Map.of(
				"bookID", id
		);
		jdbc.update("delete from books where id=:bookID", namedParameters);
	}

}
