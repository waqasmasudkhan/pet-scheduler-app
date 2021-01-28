package com.udacity.jdnd.course3.critter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Pets already in Database")
public class PetExistsException extends RuntimeException{
}
