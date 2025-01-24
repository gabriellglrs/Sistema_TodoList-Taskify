package com.dev.gabriellucas.taskify.exceptions;

public class ResourceNotFoundException extends RuntimeException {
     public ResourceNotFoundException(String message) {
          super(message);
     }
}
