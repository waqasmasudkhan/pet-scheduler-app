package com.udacity.jdnd.course3.critter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee Not Found")
public class EmployeeNotFound extends RuntimeException{
}
