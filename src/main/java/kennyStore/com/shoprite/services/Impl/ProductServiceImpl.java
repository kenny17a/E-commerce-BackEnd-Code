package kennyStore.com.shoprite.services.Impl;

import kennyStore.com.shoprite.data.models.Product;
import kennyStore.com.shoprite.data.repositories.ProductRepository;
import kennyStore.com.shoprite.dtos.request.CreateProductRequest;
import kennyStore.com.shoprite.dtos.request.UpdateCartProductRequest;
import kennyStore.com.shoprite.dtos.response.CreateProductResponse;
import kennyStore.com.shoprite.exceptions.ProductException;
import kennyStore.com.shoprite.services.CartProductService;
import kennyStore.com.shoprite.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductService cartProductService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        if (productRepository.findByProductNameIgnoreCase(createProductRequest.getName()).isPresent()) throw new ProductException("Product already exist");
        Product product = Product.builder().
                productName(createProductRequest.getName().toLowerCase()).
                quantity(createProductRequest.getQuantity()).
                unitPrice(createProductRequest.getPrice()).
                category(createProductRequest.getCategory()).
                build();
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, CreateProductResponse.class);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByProductNameIgnoreCase(name);

//        if (productRepository.findProductByName(name.toLowerCase()).isEmpty())throw new ProductException("Product not found");
//        return productRepository.findProductByName(name);

    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductException("Product does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public String deleteAllProducts() {
        productRepository.deleteAll();
        return "All Deleted Successfully";
    }

    @Override
    public String updateProduct(UpdateCartProductRequest updateCartProductRequest, Product product) {
        Optional<Product> foundProduct = getProductByName(product.getProductName());
        if (updateCartProductRequest.getPrice() != null)foundProduct.get().setUnitPrice(updateCartProductRequest.getPrice());
        if (updateCartProductRequest.getProductName() != null)foundProduct.get().setProductName(updateCartProductRequest.getProductName());
        if (updateCartProductRequest.getCategory() != null)foundProduct.get().setCategory(updateCartProductRequest.getCategory());
        if (updateCartProductRequest.getQuantity() != null)foundProduct.get().setQuantity(updateCartProductRequest.getQuantity());
        productRepository.save(foundProduct.get());
        cartProductService.updateListOfCartProducts(updateCartProductRequest.getProductName(), updateCartProductRequest.getPrice());
        return "Product successfully updated";
    }
}
