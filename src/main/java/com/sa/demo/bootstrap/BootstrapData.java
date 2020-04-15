package com.sa.demo.bootstrap;

import com.sa.demo.domain.Address;
import com.sa.demo.domain.Author;
import com.sa.demo.domain.Book;
import com.sa.demo.domain.Publisher;
import com.sa.demo.repositories.AddressRepository;
import com.sa.demo.repositories.AuthorRepository;
import com.sa.demo.repositories.BookRepository;
import com.sa.demo.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AddressRepository addressRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            AddressRepository addressRepository,
            PublisherRepository publisherRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.addressRepository = addressRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Address addr = new Address("Line 1", "Chicago", "IL", "01-660");
        Publisher pub = new Publisher("Pub co.", addr);

        addressRepository.save(addr);
        publisherRepository.save(pub);

        Author markTwen = new Author("Mark", "Twen");
        Book first = new Book("My First Book", "number");

        markTwen.getBooks().add(first);
        first.getAuthors().add(markTwen);
        first.setPublisher(pub);
        pub.getBooks().add(first);

        authorRepository.save(markTwen);
        bookRepository.save(first);

        Author bob = new Author("Bob", "Martin");
        Book secretOfBob = new Book("Secret", "111");

        bob.getBooks().add(secretOfBob);
        secretOfBob.getAuthors().add(bob);
        secretOfBob.setPublisher(pub);
        pub.getBooks().add(secretOfBob);

        authorRepository.save(bob);
        bookRepository.save(secretOfBob);

        System.out.println("Bootstrap");
        System.out.println("Number of books: " + bookRepository.count());

        publisherRepository.save(pub);

        System.out.println("Number of publishers: " + publisherRepository.count());

    }
}
