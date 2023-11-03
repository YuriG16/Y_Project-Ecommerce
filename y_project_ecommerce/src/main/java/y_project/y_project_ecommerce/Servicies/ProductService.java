package y_project.y_project_ecommerce.Servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import y_project.UTILITY.DTOs.ProductDTO;
import y_project.UTILITY.Exceptions.ProductException.CategoriaNotExistException;
import y_project.UTILITY.Exceptions.ProductException.ProductAlreadyExistException;
import y_project.UTILITY.Exceptions.ProductException.ProductNotExistException;
import y_project.y_project_ecommerce.entities.Product;
import y_project.y_project_ecommerce.repositories.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository pr;

    public Product addProduct (Product p) throws RuntimeException {
        if (pr.existsByCodProdotto(p.getCodProdotto())) {
            throw new ProductAlreadyExistException();
        }
        p = pr.save(p);
        return p;
    }

    public Product modifyProduct (String codProdotto, String titolo, String descrizione, Double prezzo, int qd) throws RuntimeException {
        Product p = getProduct(codProdotto);
        p.setTitolo(titolo);
        p.setDescrizione(descrizione);
        p.setPrezzo(0);
        p.setQd(qd);
        p = pr.save(p);
        return p;
    }

    /*public List<ProductDTO> findAllProductDTO () {
        List<ProductDTO> pDtoList = new ArrayList<>();
        List<Product> pList = pr.findAll();
        for (Product product : pList) {
            ProductDTO pDto = new ProductDTO(product);
            pDtoList.add(pDto);
        }
        
        return pDtoList;   
    }*/

    public Product getProduct (String codProdotto) throws RuntimeException {
        Product p = pr.findByCodProdotto(codProdotto);
        if (!pr.existsByCodProdotto(codProdotto)) {
            throw new ProductNotExistException();
        }
        return p;
    }

    public String deleteProduct (String codProdotto, String titolo) throws RuntimeException {
        Product p = getProduct(codProdotto);
        pr.delete(p);
        return titolo + " eliminato";
    }

    public Page<Product> findAllProduct (int nPage, int dPage) throws RuntimeException {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        return pr.findAll(pageRequest);
    }

    public Page<Product> getByCategoria (String categoria, int nPage, int dPage) throws RuntimeException {
        PageRequest pageRequest = PageRequest.of(nPage, dPage);
        if (!pr.existsByCategoria(categoria)) {
            throw new CategoriaNotExistException();
        }
        return pr.findByCategoria(categoria, pageRequest);
    }

    

}
