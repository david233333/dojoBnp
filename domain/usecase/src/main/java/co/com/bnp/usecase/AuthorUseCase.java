package co.com.bnp.usecase;


import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.author.gateway.AuthorRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AuthorUseCase {

    private final AuthorRepositoryAdapter authorRepositoryAdapter;

    public Flux<Author> getAllAutores() {
        return authorRepositoryAdapter.getAllAutores();
    }

    public Mono<Author> findAuthorById(Long id){
        return authorRepositoryAdapter.findAuthorById(id);
    }

    public Mono<Author> save(Author author){
        return  authorRepositoryAdapter.save(author);
    }

    public Mono<Void> delete(Long id){
        return  authorRepositoryAdapter.delete(id);
    }

}
