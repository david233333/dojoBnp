package co.com.bnp.repositories;

import co.com.bnp.entities.AuthorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface AuthorRepository extends ReactiveCrudRepository<AuthorEntity, String> {

}
