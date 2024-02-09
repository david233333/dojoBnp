package co.com.bnp.config;

import co.com.bnp.model.author.gateway.AuthorRepositoryAdapter;
import co.com.bnp.model.book.gateway.BookRepositoryAdapter;
import co.com.bnp.usecase.AuthorUseCase;
import co.com.bnp.usecase.BookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "co.com.bnp.usecase")
public class UseCasesConfig {

        @Bean
        public AuthorUseCase authorUseCase(AuthorRepositoryAdapter authorRepositoryAdapter){
                return new AuthorUseCase(authorRepositoryAdapter);
        }

        @Bean
        public BookUseCase bookUseCase(BookRepositoryAdapter bookRepositoryAdapter){
                return new BookUseCase(bookRepositoryAdapter);
        }
}
