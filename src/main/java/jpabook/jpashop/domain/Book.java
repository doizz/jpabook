package jpabook.jpashop.domain;

import javax.persistence.Entity;

@Entity
public class Book extends item{
    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
