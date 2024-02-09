package co.com.bnp.adapters;

import co.com.bnp.entities.AuthorEntity;
import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.author.gateway.AuthorRepositoryAdapter;
import co.com.bnp.repositories.AuthorRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AuthorRepositoryAdapterImpl implements AuthorRepositoryAdapter {

    private final AuthorRepository authorRepository;

    public AuthorRepositoryAdapterImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Flux<Author> getAllAutores() {
        return authorRepository.findAll()
                .map(this::convertToModel);
    }

    @Override
    public Mono<Author> findAuthorById(Long id) {
        return authorRepository.findById(String.valueOf(id))
                .map(this::convertToModel);
    }

    @Override
    public Mono<Author> save(Author author) {
        return authorRepository.save(convertToEntity(author))
                .map(this::convertToModel);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return authorRepository.deleteById(String.valueOf(id));
    }

    public Author convertToModel(AuthorEntity authorEntity){
        return Author.builder()
                .id(authorEntity.getId())
                .nombre(authorEntity.getNombre())
                .build();

    }

    public AuthorEntity convertToEntity(Author author){
        return AuthorEntity.builder()
                .id(author.getId())
                .nombre(author.getNombre())
                .build();

    }

}
