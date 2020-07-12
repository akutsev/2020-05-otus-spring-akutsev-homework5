package ru.otus.akutsev.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.akutsev.books.dao.BookDao;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

	private BookDao bookDao;

	@Autowired
	public BookServiceImpl(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void add(Book book) {
		bookDao.add(book);
	}

	public Book getAById(int id) {
		return bookDao.getAById(id);
	}

	public List<Book> getAll() {
		return bookDao.getAll();
	}

	public void updateBook (int id, String newName, Author newAuthor, Genre newGenre) {
		bookDao.updateBook(id, newName, newAuthor, newGenre);
	}

	public void delete (int id) {
		bookDao.delete(id);
	}
}
