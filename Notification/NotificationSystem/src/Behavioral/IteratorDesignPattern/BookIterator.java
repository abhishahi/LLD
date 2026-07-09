package Behavioral.IteratorDesignPattern;

import java.util.List;

public class BookIterator implements Iterator<Book>
{
    private List<Book> bookList;
    private int index;

    BookIterator(List<Book> list)
    {
        this.bookList = list;
        index = 0;
    }

    @Override
    public boolean isNextAvail()
    {
        return index < bookList.size();
    }

    @Override
    public Book next()
    {
        if(isNextAvail())
        {
            return bookList.get(index++);
        }
        return null;
    }
}