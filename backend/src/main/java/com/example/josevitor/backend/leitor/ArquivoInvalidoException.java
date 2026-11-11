package com.example.josevitor.backend.leitor;

public class ArquivoInvalidoException extends RuntimeException {

  public ArquivoInvalidoException(String mensagem) {
    super(mensagem);
  }
}
