package ru.otus.akutsev.books.dao;

import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.List;

public interface BookDao {

	public void add(Book book);
	public Book getAById(int id);
	public List<Book> getAll();
	public void updateBook (int id, String newName, Author newAuthor, Genre newGenre);
	public void delete (int id);

}
