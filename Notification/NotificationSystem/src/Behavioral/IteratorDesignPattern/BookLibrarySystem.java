package Behavioral.IteratorDesignPattern;

import java.util.ArrayList;
import java.util.List;

public class BookLibrarySystem {

    private static  List<Book> initializeBooks()
    {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("101", "Let us C", 5));
        bookList.add(new Book("102", "Data Structure", 2));
        bookList.add(new Book("103", "Operating System", 7));
        bookList.add(new Book("104", "Java Programming", 4));
        bookList.add(new Book("105", "Database Management System", 1));
        return bookList;
    }
    public static void main(String[] args) {
        Library library = new Library(initializeBooks());
        Iterator<Book> bookIterator = library.createIterator();

        library.addBook(new Book("106", "Computer Networks", 3));
        library.removeBook("105");
        while(bookIterator.isNextAvail())
        {
            Book book = bookIterator.next();
            System.out.println("Book ID: "+book.getBookId());
            System.out.println("Book Name: "+book.getBookName());
            System.out.println("No of Available Books: "+book.getNoOfAvailableBook());
            System.out.println("--------------------------------------------------");
        }
    }
}
