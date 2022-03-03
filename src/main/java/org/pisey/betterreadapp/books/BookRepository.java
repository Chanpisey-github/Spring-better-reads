package org.pisey.betterreadapp.books;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookRepository extends CassandraRepository<Book, String>  {
    
}
