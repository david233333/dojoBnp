package co.com.bnp.api;

import co.com.bnp.model.author.dto.AuthorDTO;
import co.com.bnp.model.author.model.Author;

import co.com.bnp.model.book.model.Book;
import co.com.bnp.usecase.AuthorUseCase;
import co.com.bnp.usecase.BookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AuthorHandler {

    private  final AuthorUseCase authorUseCase;
    private  final BookUseCase bookUseCase;
    public Mono<ServerResponse> getAllAutores(ServerRequest serverRequest) {
        Flux<Author> authorFlux = authorUseCase.getAllAutores();
        return ServerResponse.ok().body(authorFlux,Author.class)
                .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Error getting authors"))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAuthorById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return authorUseCase.findAuthorById(id)
                .flatMap(autor -> ServerResponse.ok().bodyValue(autor))
                .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Error getting author"))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateAuthor(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<Author> autorMono = request.bodyToMono(Author.class);
        return autorMono.flatMap(autor -> authorUseCase.findAuthorById(id)
                        .flatMap(existingAutor -> {
                            existingAutor.setNombre(autor.getNombre());
                            return authorUseCase.save(existingAutor);
                        }))
                .flatMap(autor -> ok().bodyValue(autor))
                .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Error updating author"))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveAutorWithBooks(ServerRequest request) {

        Mono<AuthorDTO> autorConLibrosMono = request.bodyToMono(AuthorDTO.class);

        return autorConLibrosMono.flatMap(autorConLibros -> {
                    Author author = autorConLibros.getAutor();
                    List<Book> libros = autorConLibros.getLibros();

                    return authorUseCase.save(author)
                            .flatMap(savedAutor -> {
                                libros.forEach(libro -> libro.setAutorId(savedAutor.getId()));
                                return bookUseCase.save(libros).collectList()
                                        .map(savedLibros -> new AuthorDTO(savedAutor, savedLibros));
                            });
                }).flatMap(savedAutorConLibros -> ServerResponse.ok().bodyValue(savedAutorConLibros))
                .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Error saving author"))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return authorUseCase.delete(id)
                .then(ServerResponse.ok().build())
                .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Error deleting author"))
                .switchIfEmpty(ServerResponse.notFound().build());
    }






}
