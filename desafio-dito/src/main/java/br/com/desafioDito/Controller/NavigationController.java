package br.com.desafioDito.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.desafioDito.Data.ResponseWrapper;
import br.com.desafioDito.Model.ProductModel;
import br.com.desafioDito.Service.Intf.INavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="NavigationController", description="Controla os endpoints de navegação do site")
@Controller
public class NavigationController {

    @Autowired
    INavigationService navigationService;
    
    @GetMapping("/")
    public String index() {
        navigationService.loadProductList();
        return "browser";
    }
    
    @ApiOperation(value = "Carrega a página de navegação de produtos", response = String.class)
    @GetMapping("/browser")
    public String browser() {
        navigationService.loadProductList();
        return "browser";
    }
    
    @ApiOperation(value = "Carrega a página de detalhe do produto", response = String.class)
    @GetMapping("/detail/{idProd}")
    public String detail(
            @ApiParam( value = "ID do produto que se deseja ver detalhes", required = true )
            @PathVariable(name="idProd", required=true) String idProd, Model model) {
        
        ProductModel product = navigationService.getProductById(idProd);
        
        navigationService.generateEventNavigation(product);
        
        model.addAttribute("productId", idProd);
        model.addAttribute("description", product.getDescription());
        model.addAttribute("price", product.getPrice().toString());
        
        return "detail";
    }
    
    @ApiOperation(value = "Carrega a página de pesquisa rápida", response = String.class)
    @GetMapping("/search")
    public String search() {
        return "searching";
    }
    
    @ApiOperation(value = "Retona um JSON com a lista de produtos", response = String.class)
    @ResponseBody
    @GetMapping("/browser/getAllProducts")
    public ResponseWrapper getAllProducts() {
        
        ResponseWrapper response = new ResponseWrapper();
        
        try {
            List<ProductModel> result = navigationService.getAllProductList();
            
            response.setResult("SUCCESS");
            response.setMsgSaida(result);
                    
        } catch (Exception e) {
            response.setResult("ERROR");
            response.setError(new ArrayList<>());
            response.getError().add(e.getMessage());
        }

        return response;
    }

}