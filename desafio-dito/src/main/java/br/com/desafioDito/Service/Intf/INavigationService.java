package br.com.desafioDito.Service.Intf;

import java.util.List;

import br.com.desafioDito.Model.ProductModel;

public interface INavigationService {

    void loadProductList();
    
    List<ProductModel> getAllProductList();
    
    ProductModel getProductById(String id);

    void generateEventNavigation(ProductModel product);
}