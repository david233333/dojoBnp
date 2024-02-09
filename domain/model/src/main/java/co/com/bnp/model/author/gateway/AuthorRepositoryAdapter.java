package co.com.bnp.model.author.gateway;

import co.com.bnp.model.author.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorRepositoryAdapter {

    Flux<Author> getAllAutores();

    Mono<Author> findAuthorById(Long autorId);

    Mono<Author> save(Author author);

    Mono<Void> delete(Long id);
}
