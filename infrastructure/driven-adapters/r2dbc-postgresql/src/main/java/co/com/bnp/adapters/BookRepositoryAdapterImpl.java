package co.com.bnp.adapters;

import co.com.bnp.entities.AuthorEntity;
import co.com.bnp.entities.BookEntity;
import co.com.bnp.model.author.gateway.AuthorRepositoryAdapter;
import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.book.gateway.BookRepositoryAdapter;
import co.com.bnp.model.book.model.Book;
import co.com.bnp.repositories.AuthorRepository;
import co.com.bnp.repositories.BookRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class BookRepositoryAdapterImpl implements BookRepositoryAdapter {

    private final BookRepository bookRepository;

    public BookRepositoryAdapterImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Flux<Book> save(List<Book> book) {
        return Flux.fromIterable(book)
                .flatMap(book1 -> bookRepository.save(convertToEntity(book1)))
                .map(this::convertToModel);
    }


    public Book convertToModel(BookEntity bookEntity){
        return Book.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .autorId(bookEntity.getAutorId())
                .build();

    }

    public BookEntity convertToEntity(Book book){
        return BookEntity.builder()
                .id(book.getId())
                .title(book.getTitle())
                .autorId(book.getAutorId())
                .build();

    }

}
