package com.somacode.template.config.exception

class ServiceUnavailableException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(): super("ServiceUnavailable")
}