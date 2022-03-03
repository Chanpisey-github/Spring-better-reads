package org.pisey.betterreadapp.books;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@EnableCassandraRepositories
public class BookController {
    @Autowired
    private BookRepository bookRepository;


    @GetMapping(value = "/books/{bookid}")
    public String getBook(@PathVariable String bookId , Model model){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-found";
    }
    
}
