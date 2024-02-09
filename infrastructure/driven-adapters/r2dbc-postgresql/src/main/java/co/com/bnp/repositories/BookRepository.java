package co.com.bnp.repositories;

import co.com.bnp.entities.BookEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<BookEntity, Long> {
}
