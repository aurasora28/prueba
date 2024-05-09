package com.example.Demo.dao;

import java.sql.Array;
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
            String sql = "SELECT * from books order by id desc;";
            return handle.createQuery(sql)
                    .map(new BookMapper())
                    .list();
        }
    }

    public long createBook(String book, String autor, String year, String comments, String link) throws Exception {
        try (Handle handle = jdbi.open()) {
            String sql = "INSERT INTO  books (book, autor, year, comments, link_image) VALUES ( '" + book + "'','"+ autor + "', '" + year + "','" + comments + "','" + link + "')";
            return handle.createUpdate(sql)
                    .executeAndReturnGeneratedKeys()
                    .mapTo(long.class)
                    .one();
        }
    }

    public Boolean updateBook(int id, String book, String autor, String year, String comments, String link) throws Exception{
        try (Handle handle = jdbi.open()) {
            String sql = "UPDATE books SET book = '"+book+"',autor = '"+autor+"',year = '"+year+"',comments = '"+comments+"',link_image = '"+link+"' WHERE id="+id;
            return handle.createUpdate(sql).execute() == 1;
        }
    }

    public boolean deleteBook(int id) throws Exception{
        try (Handle handle = jdbi.open()) {
            String sql = "DELETE FROM books where id="+id;
            return handle.createUpdate(sql)
                        .execute()==1;
        }
    }

}
