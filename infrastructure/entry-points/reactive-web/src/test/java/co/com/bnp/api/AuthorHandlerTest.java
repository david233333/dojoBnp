package co.com.bnp.api;

import co.com.bnp.model.author.model.Author;
import co.com.bnp.usecase.AuthorUseCase;
import co.com.bnp.usecase.BookUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class AuthorHandlerTest {

    @InjectMocks
    private AuthorHandler authorHandler;

    @Mock
    private AuthorUseCase authorUseCase;
    @Mock
    private BookUseCase bookUseCase;


    @BeforeEach
    void setUp() {
        authorHandler = new AuthorHandler(authorUseCase, bookUseCase);
    }

    @Test
    void getAllAutores() {
        Author author1 = new Author(1L, "John Doe");
        Author author2 = new Author(2L, "Jane Smith");
        Flux<Author> authorFlux = Flux.just(author1, author2);
        when(authorUseCase.getAllAutores()).thenReturn(authorFlux);

        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);

        Mono<ServerResponse> responseMono = authorHandler.getAllAutores(serverRequest);

        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .expectComplete()
                .verify();


        verify(authorUseCase).getAllAutores();
    }

    @Test
    void testFindAuthorById() {
        Author author1 = new Author(1L, "John Doe");
        Mono<Author> authorMono = Mono.just(author1);
        when(authorUseCase.findAuthorById(24L)).thenReturn(authorMono);


        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("24");

        Mono<ServerResponse> responseMono = authorHandler.findAuthorById(serverRequest);

        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .expectComplete()
                .verify();

        verify(authorUseCase).findAuthorById(24L);
    }

    @Test
    void testFindAuthorById_Error() {

        when(authorUseCase.findAuthorById(24L)).thenReturn(Mono.error(new RuntimeException("Error")));


        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("24");


        Mono<ServerResponse> responseMono = authorHandler.findAuthorById(serverRequest);


        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .expectComplete()
                .verify();

        verify(authorUseCase).findAuthorById(24L);
    }

    @Test
    void testFindAuthorById_NotFound() {

        when(authorUseCase.findAuthorById(24L)).thenReturn(Mono.empty());


        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("24");


        Mono<ServerResponse> responseMono = authorHandler.findAuthorById(serverRequest);


        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .expectComplete()
                .verify();

        verify(authorUseCase).findAuthorById(24L);
    }

    @Test
    void testUpdateAuthor_Success() {

        Author existingAuthor = new Author(1L, "Existing Author");
        Author updatedAuthor = new Author(1L, "Updated Author");
        Mono<Author> existingAuthorMono = Mono.just(existingAuthor);
        Mono<Author> updatedAuthorMono = Mono.just(updatedAuthor);
        when(authorUseCase.findAuthorById(1L)).thenReturn(existingAuthorMono);
        when(authorUseCase.save(existingAuthor)).thenReturn(updatedAuthorMono);


        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(serverRequest.bodyToMono(Author.class)).thenReturn(Mono.just(updatedAuthor));


        Mono<ServerResponse> responseMono = authorHandler.updateAuthor(serverRequest);


        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .expectComplete()
                .verify();


        verify(authorUseCase).findAuthorById(1L);


        verify(authorUseCase).save(existingAuthor);
    }

    @Test
    void testUpdateAuthor_Error() {

        when(authorUseCase.findAuthorById(1L)).thenReturn(Mono.error(new RuntimeException("Error")));

        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(serverRequest.bodyToMono(Author.class)).thenReturn(Mono.just(new Author(1L, "Updated Author")));


        Mono<ServerResponse> responseMono = authorHandler.updateAuthor(serverRequest);


        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .expectComplete()
                .verify();

        verify(authorUseCase).findAuthorById(1L);
    }

    @Test
    void testUpdateAuthor_NotFound() {

        when(authorUseCase.findAuthorById(1L)).thenReturn(Mono.empty());


        ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(serverRequest.bodyToMono(Author.class)).thenReturn(Mono.just(new Author(1L, "Updated Author")));

        Mono<ServerResponse> responseMono = authorHandler.updateAuthor(serverRequest);

        create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .expectComplete()
                .verify();


        verify(authorUseCase).findAuthorById(1L);
    }


}