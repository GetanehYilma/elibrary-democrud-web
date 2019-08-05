package edu.mum.cs.cs425.demos.elibrarydemocrudweb.service.impl;

import edu.mum.cs.cs425.demos.elibrarydemocrudweb.model.Book;
import edu.mum.cs.cs425.demos.elibrarydemocrudweb.repository.BookRepository;
import edu.mum.cs.cs425.demos.elibrarydemocrudweb.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Override
    public Iterable<Book> getAllBooks() {     //stream heregleed naalaa
//        return ((List<Book>)repository.findAll()).stream()
//        		.sorted(Comparator.comparing(Book::getTitle))
//        		.collect(Collectors.toList());
    	
    	return repository.findAll(Sort.by("title", "isbn"));   //JpaRepository extends hiisen uchraas findAll deer sort nemegdev
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public Book getBookById(Integer bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        repository.deleteById(bookId);
    }

	@Override
	public Page<Book> getAllBooksPaged(int pageNo) {
		// TODO Auto-generated method stub
		return repository.findAll(PageRequest.of(pageNo, 5, Sort.by("title", "isbn"))); 
		//Uuniig nemlee Paging hiijiin 2 booktei
	}

	@Override
	public Page<Book> getSearchedBooks(int pageNo, Book book) {
		// SOD stream ashiglaj hiiv. Yanztai!!!
		
		List<Book> foundBooks = new ArrayList<Book>();
		foundBooks = repository.findAll();
		if(book.getTitle() != "")
			foundBooks = foundBooks.stream()
			.filter(b->b.getTitle().equals(book.getTitle()))
			.collect(Collectors.toList());
		if(book.getIsbn() != "")
			foundBooks = foundBooks.stream()
			.filter(b->b.getIsbn().equals(book.getIsbn()))
			.collect(Collectors.toList());
		if(book.getPublisher() != "")
			foundBooks = foundBooks.stream()
			.filter(b->b.getPublisher().equals(book.getPublisher()))
			.collect(Collectors.toList());
		//System.out.println(repository.find("J").toString());
		return new PageImpl<Book>(foundBooks, PageRequest.of(pageNo, 5, Sort.by("title", "isbn")), foundBooks.size());
		
		//return repository.findAll(example, PageRequest.of(pageNo, 5, Sort.by("title", "isbn")));
	}
//	@Override
//	public Page<Book> getSearchedBooks(int pageNo, Book book) {
//		// SOD stream ashiglaj hiiv. Yanztai!!!
//		
//		List<Book> foundBooks = repository.findBookByIsbn(book.getIsbn());
//		//foundBooks.add(repository.findBookByIsbn(book.getIsbn()).get());
//		
//		return new PageImpl<Book>(foundBooks, PageRequest.of(pageNo, 5, Sort.by("title", "isbn")), foundBooks.size());
//	}
	

}
