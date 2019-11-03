package br.com.microserviceDito.Data.Timeline;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductDTO {
    
    @JsonIgnore
    private Long transaction_id;
    
    private String name;
    
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }
    
}
