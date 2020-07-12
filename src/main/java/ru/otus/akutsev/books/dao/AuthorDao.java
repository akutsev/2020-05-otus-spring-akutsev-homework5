package ru.otus.akutsev.books.dao;

import ru.otus.akutsev.books.model.Author;

import java.util.Map;

public interface AuthorDao {

	public void add(Author author);
	public Author getAById(int id);
	public void updateName(int id, String newName);
	public void delete (int id);

}
