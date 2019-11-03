package br.com.desafioDito.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.desafioDito.Data.CategorytDTO;
import br.com.desafioDito.Data.EventDTO;
import br.com.desafioDito.Data.ProductDTO;
import br.com.desafioDito.Model.ProductModel;
import br.com.desafioDito.Repository.Intf.IProductRepository;
import br.com.desafioDito.Service.Intf.INavigationService;


@Service
public class NavigationService implements INavigationService{

	@Autowired
    private IProductRepository productRepository;
	

	@Override
	public void loadProductList() {
	    if (productRepository.count() == 0) {
	        List<ProductModel> fullList = new ArrayList<ProductModel>(); 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<CategorytDTO[]> categorySampleList = restTemplate.getForEntity("http://fipeapi.appspot.com/api/1/carros/marcas.json", CategorytDTO[].class);
	        CategorytDTO[] categoryList = categorySampleList.getBody();
	        for (int i = 0; i < 10; i++) {
	            ResponseEntity<ProductDTO[]> productSampleList = restTemplate.getForEntity("http://fipeapi.appspot.com/api/1/carros/veiculos/"+categoryList[i].getId()+".json", ProductDTO[].class);
	            ProductDTO[] productList = productSampleList.getBody();
	            for (int j = 0; j < 10; j++) {
    	            ProductModel product = new ProductModel();
    	            product.setDescription(productList[j].getMarca() + " - " + productList[j].getName());
    	            product.setPrice(Math.round(Math.random() * 10000000)/100.0);
    	            fullList.add(product);
	            }
            }
	        fullList.sort(Comparator.comparing(ProductModel::getDescription));
	        for (ProductModel item : fullList) {
	            productRepository.save(item);
            }
	    }
	}
	
	@Override
	public List<ProductModel> getAllProductList() {
	    return productRepository.findAll();
	}
	
	@Override
    public ProductModel getProductById(String id) {
	    Optional<ProductModel> product = productRepository.findById(id);
	    if (product.isPresent())
	        return product.get();
	    else 
	        return null;
    }
	
	@Override
	public void generateEventNavigation (ProductModel product) {
	    try {
            /*Chamada assíncrona da API REST que coleta eventos de navegação(microserviço Events) */
            RestTemplate restTemplate = new RestTemplate();
            EventDTO event = new EventDTO();
            event.setEventId(product.getId());
            event.setEventName(product.getDescription());
            event.setUrl(ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());
            restTemplate.postForEntity("http://localhost:8081/generateEvent/", event, Object.class);
            /**************************************************************************************/    
        } catch (Exception e) {
            System.out.println("Não foi possível gerar o evento de navegação para o produduto " + product.getId() + ": " + e.getLocalizedMessage());
        }
        
        //System.out.println(new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.sss").format(new Date()));
	}
	        
}
