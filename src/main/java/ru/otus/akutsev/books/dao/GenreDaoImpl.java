package ru.otus.akutsev.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.akutsev.books.model.Genre;

import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao{

	private final NamedParameterJdbcOperations jdbc;

	private final RowMapper<Genre> genreRowMapper = (resultSet, rowNum) -> {
		int id = resultSet.getInt("id");
		String genreName = resultSet.getNString("genre_name");

		return new Genre(id, genreName);
	};

	@Autowired
	public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public void add(Genre genre) {
		Map<String, String> namedParameters = Map.of(
				"genreID", String.valueOf(genre.getId()),
				"genreName", genre.getGenreName()
		);
		jdbc.update("insert into genres (id, genre_name) values (:genreID, :genreName)", namedParameters);
	}

	@Override
	public Genre getAById(int id) {
		Map<String, Integer> namedParameters = Map.of(
				"genreID", id
		);
		return jdbc.queryForObject("select id, genre_name from genres where id=:genreID", namedParameters, genreRowMapper);
	}

	@Override
	public void updateGenreName(int id, String newName) {
		Map<String, String> namedParameters = Map.of(
				"genreID", String.valueOf(id),
				"newGenreName", newName
		);
		jdbc.update("update genres set genre_name=:newGenreName where id=:genreID", namedParameters);
	}

	@Override
	public void delete (int id) {
		Map<String, Integer> namedParameters = Map.of(
				"genreID", id
		);
		jdbc.update("delete from genres where id=:genreID", namedParameters);
	}
}
