package com.ebsco.training.bookmiddle.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.ebsco.training.bookmiddle.dto.BookDto;

@Repository
@Profile("devqa")
public class BookDaoDynamo implements BookDaoInterface{

    private static final String BOOK_TABLE = "BookMiddle-PlatformTraining.Books";

    @Autowired
    private AmazonDynamoDB dynamoClient;

    @Override
    public List<BookDto> getBooks() {
        ScanRequest request = new ScanRequest();

        request.withTableName(BOOK_TABLE);

        ScanResult response = dynamoClient.scan(request);
        List<BookDto> books = new ArrayList<>();
 
        for (Map<String, AttributeValue> item : response.getItems()) {
            String id = item.get("book_id").getS();
            String title = item.get("title").getS();
            String author = item.get("author").getS();
            String genre = item.get("genre").getS();

            books.add(new BookDto(id, title, author, genre));
        }

        return books;
    }

    @Override
    public Optional<BookDto> getBookById(String id) {
        Map<String, AttributeValue> items = new HashMap<>();

        items.put("book_id", new AttributeValue(id));

        GetItemRequest request = new GetItemRequest();

        request.withTableName(BOOK_TABLE);
        request.withKey(items);

        GetItemResult response = dynamoClient.getItem(request);
        Map<String, AttributeValue> item = response.getItem();

        if (item == null) {
            return Optional.empty();
        }

        String title = item.get("title").getS();
        String author = item.get("author").getS();
        String genre = item.get("genre").getS();

        return Optional.of(new BookDto(id, title, author, genre));
    }

    @Override
    public Optional<BookDto> deleteBook(String id) {
        Map<String, AttributeValue> items = new HashMap<>();

        items.put("book_id", new AttributeValue(id));

        DeleteItemRequest request = new DeleteItemRequest();

        request.withTableName(BOOK_TABLE);
        request.withKey(items);

        dynamoClient.deleteItem(request);

        return Optional.empty();
    }

    @Override
    public Optional<BookDto> updateBook(String id, String title, String author, String genre) {
        Map<String, AttributeValue> items = new HashMap<>();

        items.put("book_id", new AttributeValue(id));
        items.put("title", new AttributeValue(title));
        items.put("author", new AttributeValue(author));
        items.put("genre", new AttributeValue(genre));

        PutItemRequest request = new PutItemRequest();

        request.withTableName(BOOK_TABLE);
        request.withItem(items);

        dynamoClient.putItem(request);

        return Optional.of(new BookDto(id, title, author, genre));
    }

    @Override
    public BookDto createBook(String title, String author, String genre) {
        String id = UUID.randomUUID().toString();
        return updateBook(id, title, author, genre).get();
    }

}
