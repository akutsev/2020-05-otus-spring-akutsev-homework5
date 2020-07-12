package ru.otus.akutsev.books.model;

import java.util.Objects;

public class Book {

	private int id;
	private String name;
	private Author author;
	private Genre genre;

	public Book(int id, String name, Author author, Genre genre) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", author=" + author.getName() +
				", genre=" + genre.getGenreName() +
				"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Book)) return false;
		Book book = (Book) o;
		return id == book.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
