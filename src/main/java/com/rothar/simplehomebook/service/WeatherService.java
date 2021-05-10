package com.rothar.simplehomebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rothar.simplehomebook.repository.LoginRepository;

@Service
public class WeatherService {
	
	@Autowired
	LoginRepository repo;

    public String getWeatherForecast() {
        return "It's gonna snow a lot. Brace yourselves, the winter is coming.";
    }
}
