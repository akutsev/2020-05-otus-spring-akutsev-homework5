package ru.otus.akutsev.books.dao;

import ru.otus.akutsev.books.model.Genre;

import java.util.Map;

public interface GenreDao {

	public void add(Genre genre);
	public Genre getAById(int id);
	public void updateGenreName(int id, String newName);
	public void delete (int id);
}
