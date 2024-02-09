package co.com.bnp.model.author.dto;

import co.com.bnp.model.author.model.Author;
import co.com.bnp.model.book.model.Book;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Author autor;
    private List<Book> libros;

}
