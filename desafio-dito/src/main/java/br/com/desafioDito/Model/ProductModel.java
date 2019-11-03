package br.com.desafioDito.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class ProductModel {

	@Id
    @GeneratedValue
	private String id;
	
	private String description;
	  
	private Double price;
	  
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductModel() {
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
	  
}
