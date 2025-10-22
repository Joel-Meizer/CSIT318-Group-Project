package CSIT318Project.orderService.model;

import CSIT318Project.orderService.enums.KnowledgeLevel;
import CSIT318Project.orderService.enums.KnowledgeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String description;
    private String[] authors;
    private Date publicationDate;
    private String genre;
    private String url;
    private KnowledgeLevel knowledgeLevel;
    private KnowledgeType knowledgeType;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    // Constructors
    public OrderItem() {
    }

    public OrderItem(UUID productId, String productName, Integer quantity, Double price, String description, String[] authors, Date publicationDate, String genre, String url, KnowledgeLevel knowledgeLevel, KnowledgeType knowledgeType) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.authors = authors;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.url = url;
        this.knowledgeLevel = knowledgeLevel;
        this.knowledgeType = knowledgeType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String[] getAuthors() { return authors; }
    public void setAuthors(String[] authors) { this.authors = authors; }

    public Date getPublicationDate() { return publicationDate; }
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public KnowledgeLevel getKnowledgeLevel() { return knowledgeLevel; }
    public void setKnowledgeLevel(KnowledgeLevel knowledgeLevel) { this.knowledgeLevel = knowledgeLevel; }

    public KnowledgeType getKnowledgeType() { return knowledgeType; }
    public void setKnowledgeType(KnowledgeType knowledgeType) { this.knowledgeType = knowledgeType; }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}