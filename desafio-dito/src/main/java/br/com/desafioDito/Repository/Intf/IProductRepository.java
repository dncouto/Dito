package br.com.desafioDito.Repository.Intf;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.desafioDito.Model.ProductModel;

/**
 *
 * @author Daniel Couto
 */

@Repository
public interface IProductRepository extends MongoRepository<ProductModel, Long> {
	
    Optional<ProductModel> findById(String id);
}
