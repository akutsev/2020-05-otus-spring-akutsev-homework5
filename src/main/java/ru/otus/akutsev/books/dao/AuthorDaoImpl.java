package ru.otus.akutsev.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.akutsev.books.model.Author;

import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao{

	private final NamedParameterJdbcOperations jdbc;

	private final RowMapper<Author> authorRowMapper = (resultSet, rowNum) -> {
		int id = resultSet.getInt("id");
		String name = resultSet.getNString("name");

		return new Author(id, name);
	};

	@Autowired
	public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public void add(Author author) {
		Map<String, String> namedParameters = Map.of(
				"authorID", String.valueOf(author.getId()),
				"authorName", author.getName()
		);
		jdbc.update("insert into authors (id, name) values (:authorID, :authorName)", namedParameters);
	}

	@Override
	public Author getAById(int id) {
		Map<String, Integer> namedParameters = Map.of(
				"authorID", id
		);
		return jdbc.queryForObject("select id, name from authors where id=:authorID", namedParameters, authorRowMapper);
	}

	@Override
	public void updateName(int id, String newName) {
		Map<String, String> namedParameters = Map.of(
				"authorID", String.valueOf(id),
				"newAuthorName", newName
		);
		jdbc.update("update authors set name=:newAuthorName where id = :authorID", namedParameters);
	}

	@Override
	public void delete (int id) {
		Map<String, Integer> namedParameters = Map.of(
				"authorID", id
		);
		jdbc.update("delete from authors where id=:authorID", namedParameters);
	}

}
