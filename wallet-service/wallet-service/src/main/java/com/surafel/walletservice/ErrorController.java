package com.surafel.walletservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<String> errorPage() {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}