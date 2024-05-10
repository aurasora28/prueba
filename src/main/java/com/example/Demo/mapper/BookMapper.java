package com.example.Demo.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.example.Demo.dto.BookDTO;

public class BookMapper implements RowMapper<BookDTO> {
    @Override
    public BookDTO map(ResultSet rs, StatementContext ctx) throws SQLException{
        BookDTO BookDTO=new BookDTO();
        BookDTO.setIdBook(rs.getInt("idBook"));
        BookDTO.setBook(rs.getString("book"));
        BookDTO.setauthor(rs.getString("author"));
        BookDTO.setYear(rs.getString("year"));
        BookDTO.setComments(rs.getString("comments"));
        BookDTO.setImage(rs.getString("image"));

        return BookDTO;
    }
}
