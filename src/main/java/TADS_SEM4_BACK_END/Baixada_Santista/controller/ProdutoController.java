package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.model.Produto;
import TADS_SEM4_BACK_END.Baixada_Santista.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<Page<Produto>> lista(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = produtoRepository.findAllByAtivoTrue(pageable);
        return ResponseEntity.ok(page);
    }
}
