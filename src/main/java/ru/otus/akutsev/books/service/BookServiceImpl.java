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

	@Override
	public void add(Book book) {
		bookDao.add(book);
	}

	@Override
	public Book getAById(int id) {
		return bookDao.getAById(id);
	}

	@Override
	public List<Book> getAll() {
		return bookDao.getAll();
	}

	@Override
	public void updateBook (int id, String newName, Author newAuthor, Genre newGenre) {
		bookDao.updateBook(id, newName, newAuthor, newGenre);
	}

	@Override
	public void delete (int id) {
		bookDao.delete(id);
	}
}
