package co.com.bnp.usecase;


import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.book.gateway.BookRepositoryAdapter;
import co.com.bnp.model.book.model.Book;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;

@RequiredArgsConstructor
public class BookUseCase {

    private final BookRepositoryAdapter bookRepositoryAdapter;


    public Flux<Book> save(List<Book> book){
        return  bookRepositoryAdapter.save(book);
    }

}
