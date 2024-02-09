package co.com.bnp.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterRest {

@Bean
public RouterFunction<ServerResponse> routerFunction(AuthorHandler handler) {
    return route(GET("/api/autores"), handler::getAllAutores)
            .andRoute(GET("/api/autores/{id}"), handler::findAuthorById)
            .andRoute(PUT("/api/autores/{id}"), handler::updateAuthor)
            .andRoute(POST("/api/autores"), handler::saveAutorWithBooks)
            .andRoute(DELETE("/api/autores/{id}"), handler::delete);

    }
}
