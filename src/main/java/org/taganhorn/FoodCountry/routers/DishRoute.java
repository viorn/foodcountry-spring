package org.taganhorn.FoodCountry.routers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.taganhorn.FoodCountry.entities.response.DishResponseBody;
import org.taganhorn.FoodCountry.entities.response.DishesListResponseBody;
import org.taganhorn.FoodCountry.services.DishesService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("dish")
public class DishRoute {
    @Autowired
    private DishesService dishesService;

    @GetMapping("list")
    Mono<DishesListResponseBody> getAllDishes(@RequestParam Integer page, @RequestParam Integer limit) {
        if (page == null)
            page = 10;
        final Integer finalPage = page;
        return Mono.fromCallable(() -> {
            return dishesService.getDishesList(finalPage, limit);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("{id}")
    Mono<DishResponseBody> getAllDishes(@PathVariable("id") Long id) {
        return Mono.fromCallable(() -> {
            return dishesService.getDishesById(id);
        }).subscribeOn(Schedulers.elastic());
    }

    static final String UPLOADED_FOLDER = "./";

    @PostMapping("upload")
    Mono<String> singleFileUpload(@RequestParam("file") MultipartFile file) {
        return Mono.fromCallable(() -> {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                return "ok";
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        });
    }
}
