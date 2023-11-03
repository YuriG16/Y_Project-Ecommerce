package y_project.y_project_ecommerce.Servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import y_project.UTILITY.UserMapper;
import y_project.UTILITY.DTOs.ProductDTO;
import y_project.UTILITY.Exceptions.ProductException.CategoriaNotExistException;
import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.repositories.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductDTOService {

    private final ProductService ps;
    private final ProductRepository pr;
    private final UserMapper um;

    public ProductDTO viewProductDTO (String codProdotto) throws RuntimeException {
        Product p = ps.getProduct(codProdotto);
        ProductDTO pDto = um.productToProductDTO(p);
        return pDto;
    }

    public Page<ProductDTO> findAllDTO (int nPage, int dPage) {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        Page<Product> pList = pr.findAll(pageRequest);
        Page<ProductDTO> pDTOPage = pList.map(um::productToProductDTO);
        return pDTOPage;
    }

    public Page<ProductDTO> getDTOByCategoria (String categoria, int nPage, int dPage) throws RuntimeException {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        if (!pr.existsByCategoria(categoria)) {
            throw new CategoriaNotExistException();
        }
        Page<Product> pList = pr.findByCategoria(categoria, pageRequest);
        Page<ProductDTO> pDTOPage = pList.map(um::productToProductDTO);
        return pDTOPage;
    }

    public Page<ProductDTO> getDTOByCategoriaAndPrezzo (String categoria, Double pMin, Double pMax, int nPage, int dPage) throws RuntimeException {
        PageRequest pageRequest = PageRequest.of(nPage, dPage, Sort.by("prezzo").ascending());
        if (!pr.existsByCategoria(categoria)) {
            throw new CategoriaNotExistException();
        }
        Page<Product> pList = pr.findByCategoriaAndPrezzoBetween(categoria, pMin, pMax, pageRequest);
        Page<ProductDTO> pDTOPage = pList.map(um::productToProductDTO);
        return pDTOPage;
    }

    public Page<ProductDTO> getDTOByKeyword (String keyword, int nPage, int dPage) {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        Page<Product> pList = pr.findByTitoloContaining(keyword, pageRequest);
        Page<ProductDTO> pDTOPage = pList.map(um::productToProductDTO);
        return pDTOPage;
    }

    public Page<ProductDTO> getDTOByKeywordAPrezzo (String keyword, Double pMin, Double pMax, int nPage, int dPage) {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        Page<Product> pList = pr.findByTitoloContainingAndPrezzoBetween(keyword, pMin, pMax, pageRequest);
        Page<ProductDTO> pDTOPage = pList.map(um::productToProductDTO);
        return pDTOPage;
    }
    
}
