package com.satyam.security.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, // The prePostEnabled property enables support for Spring’s @PreAuthorize and @PostAuthorize annotations.
		//  securedEnabled = true, // The securedEnabled property enables support for the @Secured annotation. 
		  jsr250Enabled = true) // The jsr250Enabled property enables support for the @RolesAllowed annotation. 
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

}
