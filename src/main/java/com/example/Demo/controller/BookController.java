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
    public ResponseEntity<?> books(){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", BookDAO.Status());
            response.put("code", "OK");    
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
			String book = request.get("book").toString();
			String author = request.get("author").toString();
			String year = request.get("year").toString();
			String comments = request.get("comments").toString();
			String link = request.get("link").toString();
    		BookDAO.createBook(book,author,year,comments,link);
            response.put("code", "OK");
    	} catch (Exception e) {
			e.printStackTrace();
			response.put("code", "ERROR");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(request.get("id").toString());
            String book = request.get("book").toString();
			String author = request.get("author").toString();
			String year = request.get("year").toString();
			String comments = request.get("comments").toString();
			String link = request.get("link").toString();

            Boolean flag = BookDAO.updateBook(id, book,author,year,comments,link);  
            response.put("code", flag);   
        } catch (Exception e) {
            response.put("code", "ERROR" + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> removeValue(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();
        try {           
            BookDAO.deleteBook(Integer.parseInt(request.get("id").toString()));
            response.put("code", "OK");
           
        } catch (Exception e) {
            response.put("code", "ERROR" + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
