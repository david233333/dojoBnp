package co.com.bnp.model.book.gateway;

import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.book.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookRepositoryAdapter {

    Flux<Book> save(List<Book> book);

}
