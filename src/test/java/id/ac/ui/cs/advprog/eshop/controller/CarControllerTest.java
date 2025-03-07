package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCarPage() {
        Model model = new BindingAwareModelMap();
        String viewName = carController.createCarPage(model);

        assertEquals("createCar", viewName);
        assertEquals(Car.class, model.getAttribute("car").getClass());
    }

    @Test
    void testCreateCarPost() {
        Car car = new Car();
        car.setCarId("CAR-123");
        car.setCarName("Toyota Corolla");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        when(carService.create(any(Car.class))).thenReturn(car);

        String redirectUrl = carController.createCarPost(car);

        assertEquals("redirect:listCar", redirectUrl);
        verify(carService, times(1)).create(car);
    }

    @Test
    void testCarListPage() {
        List<Car> cars = new ArrayList<>();
        Car car1 = new Car();
        car1.setCarId("CAR-123");
        car1.setCarName("Toyota Corolla");
        cars.add(car1);

        Car car2 = new Car();
        car2.setCarId("CAR-456");
        car2.setCarName("Honda Civic");
        cars.add(car2);

        when(carService.findAll()).thenReturn(cars);

        Model model = new BindingAwareModelMap();
        String viewName = carController.carListPage(model);

        assertEquals("carList", viewName);
        assertEquals(cars, model.getAttribute("cars"));
        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() {
        Car car = new Car();
        car.setCarId("CAR-123");
        car.setCarName("Toyota Corolla");

        when(carService.findById(anyString())).thenReturn(car);

        Model model = new BindingAwareModelMap();
        String viewName = carController.editCarPage("CAR-123", model);

        assertEquals("editCar", viewName);
        assertEquals(car, model.getAttribute("car"));
        verify(carService, times(1)).findById("CAR-123");
    }

    @Test
    void testEditCarPost() {
        Car car = new Car();
        car.setCarId("CAR-123");
        car.setCarName("Toyota Corolla Updated");
        car.setCarColor("Blue");
        car.setCarQuantity(5);

        doNothing().when(carService).update(anyString(), any(Car.class));

        String redirectUrl = carController.editCarPost(car);

        assertEquals("redirect:listCar", redirectUrl);
        verify(carService, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carService).deleteCarById(anyString());

        String redirectUrl = carController.deleteCar("CAR-123");

        assertEquals("redirect:listCar", redirectUrl);
        verify(carService, times(1)).deleteCarById("CAR-123");
    }
}