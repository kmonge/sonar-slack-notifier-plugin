package com.koant.sonar.slacknotifier.exception;

public class TemplateCompileException extends RuntimeException {

    public TemplateCompileException(String message, Exception e) {

        super(message, e);
    }
}
