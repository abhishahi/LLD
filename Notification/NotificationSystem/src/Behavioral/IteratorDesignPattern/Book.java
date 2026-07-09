package Behavioral.IteratorDesignPattern;

class Book
{
    private String bookId;
    private String bookName;
    private int noOfAvailableBook;
    public Book(String bookId, String bookName, int noOfAvailableBook)
    {
        this.bookId = bookId;
        this.bookName = bookName;
        this.noOfAvailableBook = noOfAvailableBook;
    }
    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    
    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    
    public int getNoOfAvailableBook()
    {
        return noOfAvailableBook;
    }

    public void setNoOfAvailableBook(int num)
    {
        this.noOfAvailableBook = num;
    }
}