package com.example.Demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Demo.dao.BookDAO;

@RestController
@RequestMapping("/book")

public class BookController {
    private final BookDAO BookDAO;

    @Autowired
    public BookController(BookDAO BookDAO) {
        this.BookDAO = BookDAO;
    }

    @RequestMapping("/data")
    public ResponseEntity<?> books() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", BookDAO.Status());
        } catch (Exception e) {
            response.put("code", "ERROR" + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "NOK");
        try {
            String title = request.get("title").toString();
            String author = request.get("author").toString();
            int year = Integer.parseInt(request.get("year").toString());
            String description = request.get("description").toString();
            String image = request.get("image").toString();
            BookDAO.createBook(title, author, year, description, image);
            response.put("code", "OK");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", "ERROR");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/updateComment")
    public ResponseEntity<?> update(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int idBook = Integer.parseInt(request.get("idBook").toString());
            String comments = request.get("comments").toString();

            Boolean flag = BookDAO.updateComment(idBook, comments);
            response.put("code", flag);
        } catch (Exception e) {
            response.put("code", "ERROR" + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
