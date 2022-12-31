package com.tjise.mapper;
import com.tjise.entity.Book;
import java.util.List;

public interface BookMapper {
    List<Book> findBookList();
    Book findBookById(int bookid);
    List<Book> findBookByName(String bookname);
    List<Book> findBookByAuthor(String author);
    List<Book> findBookByPublish(String publish);
    int insertBook(Book book);
    int updateBook(Book book);
    int deleteBook(int bookid);
}
