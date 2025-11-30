package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private AdicionadorLinkCliente adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(cliente);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterClientes() {
        List<Cliente> clientes = repositorio.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(clientes);
            return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (cliente.getId() == null) {
            repositorio.save(cliente);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);

    }

    @PutMapping
    public ResponseEntity<?> atualizarCliente(@RequestBody Cliente atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Cliente cliente = repositorio.getById(atualizacao.getId());
        if (cliente != null) {
            ClienteAtualizador atualizador = new ClienteAtualizador();
            atualizador.atualizar(cliente, atualizacao);
            repositorio.save(cliente);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping
    public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Cliente cliente = repositorio.getById(exclusao.getId());
        if (cliente != null) {
            repositorio.delete(cliente);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    // Endpoints para Documentos
    @PostMapping("/{clienteId}/documento")
    public ResponseEntity<?> adicionarDocumento(@PathVariable Long clienteId, @RequestBody List<Documento> novosDocumentos) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cliente.getDocumentos().addAll(novosDocumentos);
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{clienteId}/documento/{documentoId}")
    public ResponseEntity<?> atualizarDocumento(@PathVariable Long clienteId, @PathVariable Long documentoId, @RequestBody Documento atualizacao) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Documento documento = cliente.getDocumentos().stream().filter(d -> d.getId().equals(documentoId)).findFirst().orElse(null);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        documento.setTipo(atualizacao.getTipo());
        documento.setNumero(atualizacao.getNumero());
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{clienteId}/documento/{documentoId}")
    public ResponseEntity<?> excluirDocumento(@PathVariable Long clienteId, @PathVariable Long documentoId) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean removed = cliente.getDocumentos().removeIf(d -> d.getId().equals(documentoId));
        if (!removed) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoints para Endereço
    @PutMapping("/{clienteId}/endereco")
    public ResponseEntity<?> atualizarEndereco(@PathVariable Long clienteId, @RequestBody Endereco novoEndereco) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (cliente.getEndereco() != null) {
            novoEndereco.setId(cliente.getEndereco().getId());
        }
        cliente.setEndereco(novoEndereco);
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoints para Telefones
    @PostMapping("/{clienteId}/telefone")
    public ResponseEntity<?> adicionarTelefone(@PathVariable Long clienteId, @RequestBody List<Telefone> novosTelefones) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cliente.getTelefones().addAll(novosTelefones);
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{clienteId}/telefone/{telefoneId}")
    public ResponseEntity<?> atualizarTelefone(@PathVariable Long clienteId, @PathVariable Long telefoneId, @RequestBody Telefone atualizacao) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Telefone telefone = cliente.getTelefones().stream().filter(t -> t.getId().equals(telefoneId)).findFirst().orElse(null);
        if (telefone == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        telefone.setDdd(atualizacao.getDdd());
        telefone.setNumero(atualizacao.getNumero());
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{clienteId}/telefone/{telefoneId}")
    public ResponseEntity<?> excluirTelefone(@PathVariable Long clienteId, @PathVariable Long telefoneId) {
        Cliente cliente = repositorio.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean removed = cliente.getTelefones().removeIf(t -> t.getId().equals(telefoneId));
        if (!removed) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
