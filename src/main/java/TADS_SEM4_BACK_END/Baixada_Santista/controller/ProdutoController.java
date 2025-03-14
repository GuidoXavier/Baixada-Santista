package TADS_SEM4_BACK_END.Baixada_Santista.controller;

import TADS_SEM4_BACK_END.Baixada_Santista.dto.ProdutoResponseDTO;
import TADS_SEM4_BACK_END.Baixada_Santista.model.ImagemProduto;
import TADS_SEM4_BACK_END.Baixada_Santista.model.Produto;
import TADS_SEM4_BACK_END.Baixada_Santista.repository.ImagensRepository;
import TADS_SEM4_BACK_END.Baixada_Santista.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin("*")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private ImagensRepository imagensRepository;

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<Page<Produto>> lista(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarProduto(@RequestBody ProdutoResponseDTO requestDTO) {
        try {


            //Criando o produto e passando os valores para ele
            Produto produto = new Produto();
            produto.setNomeProduto(requestDTO.getNomeProduto());
            produto.setAvaliacao(BigDecimal.ZERO); //Como não vendeu nada do produto ainda, deixo a nota vazia
            produto.setDescricao(requestDTO.getDescricao());
            produto.setPreco(requestDTO.getPreco());
            produto.setQuantEstoque(requestDTO.getQuantEstoque());
            produto.setAtivo(true); //Como o produto está sendo criado, já deixo ele ativo

            //Salvo o produto antes, para criar o ID e poder referenciar ele na foto
            Produto produtosave = repository.save(produto);

            List<ImagemProduto> imagens = new ArrayList<>();

            for (ImagemProduto imagem : requestDTO.getImagens()) {
                imagens.add(imagem);
            }
            imagensRepository.saveAll(imagens);
            return ResponseEntity.ok().body("Produto salvo com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar o usuário");
        }
    }
}
