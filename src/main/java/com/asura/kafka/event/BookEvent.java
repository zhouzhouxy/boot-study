package com.asura.kafka.event;

import com.asura.kafka.entity.BookTest;
import org.springframework.context.ApplicationEvent;

/**
 * @author zzyx 2021/6/4/004
 */
public class BookEvent extends ApplicationEvent {
    private BookTest bookTest;

    public BookTest getBookTest() {
        return bookTest;
    }

    public void setBookTest(BookTest bookTest) {
        this.bookTest = bookTest;
    }

    public BookEvent(Object source, BookTest bookTest) {
        super(source);
        this.bookTest = bookTest;
    }
}
