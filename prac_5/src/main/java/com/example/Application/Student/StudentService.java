package com.example.Application.Student;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class StudentService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final StudentRepository studentRepo;

    public  StudentService(StudentRepository studentRepo){this.studentRepo=studentRepo;}

    public Optional<Students> findById(Long newsId) {
        return studentRepo.findById(newsId);
    }

    public List<Students> findAll(Integer page, Integer size)
    {
        if (page<0) { page=0; }
        if (size>1000){size=1000;}
        return studentRepo.findAll(PageRequest.of(page,size)).getContent();
    }

    public Students create(Students std)
    {
        return studentRepo.save(std);

    }

    public Optional<Students> update(Long id, Students std){
        Optional<Students> exsisting = studentRepo.findById(id);
        if(exsisting.isPresent())
        {
            exsisting.get().setGrade(std.getGrade());
            exsisting.get().setName(std.getName());
            exsisting=Optional.of(studentRepo.save(exsisting.get()));
        }
        return  exsisting;
    }

    public Boolean delete(Long id)
    {
        Optional<Students> exsisting = studentRepo.findById(id);

        if(exsisting.isPresent())
        {
            studentRepo.delete(exsisting.get());
            return true;
        }
        return false;
    }

    private String lastWeather = "";
//    @Value("${weather.api.key}")
//
//    @Scheduled(fixedRate = 10)
//    public void checkWeather() {
//        System.out.println("Checking weather...");
//        fetchWeatherAsync().thenAccept(weather -> {
//            if (!weather.equals(lastWeather)) {
//                System.out.println("Weather changed: " + weather);
//                lastWeather = weather;
//            } else {
//                System.out.println("Weather remains the same: " + weather);
//            }
//        }).exceptionally(e -> {
//            System.err.println("Failed to fetch weather: " + e.getMessage());
//            return null;
//        });
//    }
//    @Cacheable(cacheNames = "weather",key="#wID")
//    //@GetMapping("/weather")
//    @Async
//    public CompletableFuture<String> fetchWeatherAsync() {
//        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=London&appid=%s", apiKey);
//        RestTemplate restTemplate = new RestTemplate();
//        String weather = restTemplate.getForObject(url, String.class);
//        return CompletableFuture.completedFuture(weather);
   // }


}
