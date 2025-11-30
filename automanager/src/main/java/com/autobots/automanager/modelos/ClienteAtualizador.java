package com.autobots.automanager.modelos;

import com.autobots.automanager.entidades.Cliente;

public class ClienteAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

    private void atualizarDados(Cliente cliente, Cliente atualizacao) {
        if (!verificador.verificar(atualizacao.getNome())) {
            cliente.setNome(atualizacao.getNome());
        }
        if (!verificador.verificar(atualizacao.getNomeSocial())) {
            cliente.setNomeSocial(atualizacao.getNomeSocial());
        }
        if (atualizacao.getDataNascimento() != null) {
            cliente.setDataNascimento(atualizacao.getDataNascimento());
        }
        if (atualizacao.getDataCadastro() != null) {
            cliente.setDataCadastro(atualizacao.getDataCadastro());
        }
    }

    public void atualizar(Cliente cliente, Cliente atualizacao) {
        atualizarDados(cliente, atualizacao);
        enderecoAtualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
    }
}
