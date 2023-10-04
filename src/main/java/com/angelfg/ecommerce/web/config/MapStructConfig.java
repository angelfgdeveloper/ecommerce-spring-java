package com.angelfg.ecommerce.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.angelfg.ecommerce.persistence.mapper")
public class MapStructConfig {}