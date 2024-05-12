package com.example.Demo.dao;

import java.util.List;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Demo.dto.BookDTO;
import com.example.Demo.mapper.BookMapper;

@Repository
public class BookDAO {
    private Jdbi jdbi;

    @Autowired
    public BookDAO(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<BookDTO> Status() throws Exception {
        try (Handle handle = jdbi.open()) {
            String sql = "SELECT * from books order by \"idBook\" desc;";
            return handle.createQuery(sql)
                    .map(new BookMapper())
                    .list();
        }
    }

    public long createBook(String book, String author, int year, String description, String image) throws Exception {
        try (Handle handle = jdbi.open()) {
            book=book.replaceAll("\'", "\''");
            author=author.replaceAll("\'", "\''");
            description=description.replaceAll("\'", "\''");
            String sql = "INSERT INTO  books (book, author, year, description, image) VALUES ( '" + book + "','"+ author + "', '" + year + "','" + description + "','" + image + "')";
            return handle.createUpdate(sql)
                    .executeAndReturnGeneratedKeys()
                    .mapTo(long.class)
                    .one();
        }
    }

    public Boolean updateComment(int idBook, String comments) throws Exception{
        try (Handle handle = jdbi.open()) {
            comments=comments.replaceAll("\'", "\''");
            String sql = "UPDATE books SET comments = '"+comments+"' WHERE \"idBook\"="+idBook;
            return handle.createUpdate(sql).execute() == 1;
        }
    }

}
