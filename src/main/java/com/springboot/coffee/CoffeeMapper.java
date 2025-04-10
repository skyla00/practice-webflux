package com.springboot.coffee;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    Coffee coffeePostDtoToCoffee(CoffeeDto.Post coffeePostDto);
    Coffee coffeePatchDtoToCoffee(CoffeeDto.Patch coffeePatchDto);
    CoffeeDto.Response coffeeToCoffeeResponseDto(Coffee coffee);
    List<CoffeeDto.Response> coffeesToCoffeeResponseDtos(List<Coffee> coffees);
}
