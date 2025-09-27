package com.icsdetec.aprendendospring.infrastruture.exceptions;

public class ResourceNotFoundExecption extends RuntimeException {
   public ResourceNotFoundExecption(String mensagem){
      super(mensagem);
   }
   public ResourceNotFoundExecption(String mensagem, Throwable throwable){
      super(mensagem,throwable);
   }
}
