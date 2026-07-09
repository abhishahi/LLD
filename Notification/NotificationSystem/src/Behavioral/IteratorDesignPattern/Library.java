package Behavioral.IteratorDesignPattern;

import java.util.List;

public class Library implements Aggregator<Book>
{
    private final List<Book> bookList;

    public Library(List<Book> list)

    {
        this.bookList = list;
    }
    
    public void addBook(Book book)
    {
        bookList.add(book);
    }

    public void removeBook(String bookId) {
        bookList.removeIf(book -> book.getBookId().equals(bookId));
    }
    
    @Override
    public Iterator<Book> createIterator()
    {
        return new BookIterator(bookList);
    }
}