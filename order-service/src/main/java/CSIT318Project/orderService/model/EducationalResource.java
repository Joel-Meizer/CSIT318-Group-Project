package CSIT318Project.orderService.model;

import java.util.UUID;

public class EducationalResource {
    private UUID id;
    private String title;
    private String author;
    private String category;
    private Double price;

    // Constructors
    public EducationalResource() {}

    public EducationalResource(UUID id, String title, String author, String category, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}