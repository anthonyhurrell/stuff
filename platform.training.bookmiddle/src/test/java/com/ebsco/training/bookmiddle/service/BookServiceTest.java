package com.ebsco.training.bookmiddle.service;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ebsco.training.bookmiddle.dao.BookDao;
import com.ebsco.training.bookmiddle.dto.BookDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
	
	@Autowired
	private BookService service;
	
    @MockBean
    BookService bookService;
    
    @MockBean
    BookDao bookDao;
    
    @Mock
    BookDto bookDto;
    
    @Before
    public void setup() {
        // initialize all the @Mock objects
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getBookByIdTest() {
        // Given
    		String id = "1";
    		BookDto book = new BookDto("1", "test title", "test authro", "test genre");
    		// reate book dto return Optional.of(bookdto)
    		Optional<BookDto> expectedBook = Optional.of(book);
        
    		given(service.getBookById(id)).willReturn(expectedBook);
    		
    		Optional<BookDto> actualBook = service.getBookById(id);
    		System.out.println("actual book id ::: " + actualBook.get().getId());
    	    assert(actualBook.get().getId().equals(id));
    }

}
