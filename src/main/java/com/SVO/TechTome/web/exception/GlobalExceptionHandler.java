package com.SVO.TechTome.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.SVO.TechTome.constants.Constants.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ModelAndView handleDomainException(DomainException ex) {
        log.warn("Domain error: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject(STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        mav.addObject(MESSAGE, ex.getMessage());
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.setStatus(HttpStatus.FORBIDDEN);
        mav.addObject(STATUS_CODE, HttpStatus.FORBIDDEN.value());
        mav.addObject(MESSAGE, "You do not have permission to access this page.");
        return mav;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResource(NoResourceFoundException ex) {
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.setStatus(HttpStatus.NOT_FOUND);
        mav.addObject(STATUS_CODE, HttpStatus.NOT_FOUND.value());
        mav.addObject(MESSAGE, "The requested resource was not found.");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject(STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.addObject(MESSAGE, "Something went wrong. Please try again later.");
        return mav;
    }
}
