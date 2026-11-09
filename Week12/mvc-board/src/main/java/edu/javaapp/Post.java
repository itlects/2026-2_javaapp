package edu.javaapp;

public class Post {
    private final int id;
    private final String title;
    private final String author;
    private final String content;
    private final String createdAt;

    public Post(int id, String title, String author, String content, String createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }
}
