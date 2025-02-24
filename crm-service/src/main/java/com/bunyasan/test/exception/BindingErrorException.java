package com.bunyasan.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class BindingErrorException extends RuntimeException{

    private final BindingResult bindingResult;

    public HttpStatusCode getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public String getDefaultDetail() {
        return "Invalid Request Data";
    }

    public ProblemDetail getBody() {
        ProblemDetail problem = initProblemDetail();
        problem.setProperty("details", errorsToMap(bindingResult));
        return problem;
    }

    protected ProblemDetail initProblemDetail() {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(getStatusCode(), getDefaultDetail());
        problem.setProperty("timestamp", new Date());
        problem.setProperty("class", getClass().getSimpleName());
        return problem;
    }

    public static Map<String, Object> errorsToMap(Errors errors) {
        Map<String, Object> map = new HashMap<>();
        for (ObjectError objectError : errors.getAllErrors()) {
            if (objectError instanceof FieldError)
                map.put(((FieldError) objectError).getField(), objectError.getDefaultMessage());
            else
                map.put(objectError.getObjectName(), objectError.getDefaultMessage());
        }
        return map;
    }

}
