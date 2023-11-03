package y_project.y_project_ecommerce.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import y_project.y_project_ecommerce.Servicies.ProductDTOService;
import y_project.y_project_ecommerce.Servicies.ProductService;
import y_project.y_project_ecommerce.entities.Product;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService ps;
    private final ProductDTOService pDtoS;

    @PreAuthorize("hasAuthority ('ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity addProduct (@RequestBody Product p) {
        try {
            return new ResponseEntity (ps.addProduct(p), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority ('ADMIN')")
    @GetMapping("/getProduct")
    public ResponseEntity getProduct (@RequestParam("codProdotto") String codProdotto) {
        try {
            return new ResponseEntity (ps.getProduct(codProdotto), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }      
    }

    @PreAuthorize("hasAuthority ('ADMIN')")
    @GetMapping("/findAllProduct")
    public ResponseEntity findAllProduct (@RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        return ResponseEntity.ok(ps.findAllProduct(nPage, dPage));
    }

    @PreAuthorize("hasAuthority ('ADMIN')")
    @GetMapping("/findProductByCat")
    public ResponseEntity findProductByCat (@RequestParam("categoria") String categoria, @RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        try {
            return new ResponseEntity (ps.getByCategoria(categoria, nPage, dPage), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }      
    }


    @PreAuthorize("hasAuthority ('ADMIN')")
    @PostMapping("/getProduct")
    public ResponseEntity modifyProduct (@RequestParam("codProdotto") String codProdotto, @RequestParam("titolo") String titolo, @RequestParam("descrizione") String descrizione, @RequestParam("prezzo") Double prezzo, @RequestParam("qd") int qd) {
        try {
            return new ResponseEntity (ps.modifyProduct(codProdotto, titolo, descrizione, prezzo, qd), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }      
    }

    @PreAuthorize("hasAuthority ('ADMIN')")
    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct (@RequestParam("codProdotto") String codProdotto, @RequestParam("titolo") String titolo) {
        try {
            return new ResponseEntity (ps.deleteProduct(codProdotto, titolo), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/viewProductDTO")
    public ResponseEntity viewProductDTO (@RequestParam("codProdotto") String codProdotto) {
        try {
            return new ResponseEntity (pDtoS.viewProductDTO(codProdotto), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }      
    }

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/findAllDTO")
    public ResponseEntity findAllDTO (@RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        return ResponseEntity.ok(pDtoS.findAllDTO(nPage, dPage));
    }

    //getDTOByCategoria
    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/getDTOByCat")
    public ResponseEntity getDTOByCat (@RequestParam("categoria") String categoria, @RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        try {
            return new ResponseEntity (pDtoS.getDTOByCategoria(categoria, nPage, dPage), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    //getDTOByCategoriaAndPrezzo
    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/getDTOByCatAndP")
    public ResponseEntity getDTOByCatAndP (@RequestParam("categoria") String categoria, @RequestParam("pMin") Double pMIn, @RequestParam("pMax") Double pMax, @RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        try {
            return new ResponseEntity (pDtoS.getDTOByCategoriaAndPrezzo(categoria, pMIn, pMax, nPage, dPage), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    //getDTOByKeyword
    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/getDTOByKeyword")
    public ResponseEntity getDTOByKeyword (@RequestParam("categoria") String categoria, @RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        try {
            return new ResponseEntity (pDtoS.getDTOByKeyword(categoria, nPage, dPage), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
    @GetMapping("/getDTOByKeywordAndPrezzo")
    public ResponseEntity getDTOByKeywordAndPrezzo (@RequestParam("categoria") String categoria, @RequestParam("pMin") Double pMIn, @RequestParam("pMax") Double pMax, @RequestParam("nPage") int nPage, @RequestParam("dPage") int dPage) {
        try {
            return new ResponseEntity (pDtoS.getDTOByKeywordAPrezzo(categoria, pMIn, pMax, nPage, dPage), HttpStatus.OK);
        } catch (RuntimeException e) {
            String ex = e.getClass().getSimpleName();
            return new ResponseEntity (ex, HttpStatus.BAD_REQUEST);
        }
    }
}


