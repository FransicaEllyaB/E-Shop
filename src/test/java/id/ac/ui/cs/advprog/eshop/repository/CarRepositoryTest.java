package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.UUID;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNoId() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(10, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);
        assertEquals(car.getCarId(), createdCar.getCarId());
        assertEquals(car.getCarName(), createdCar.getCarName());
        assertEquals(car.getCarColor(), createdCar.getCarColor());
        assertEquals(car.getCarQuantity(), createdCar.getCarQuantity());
    }

    @Test
    void testFindCarByIdExists() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getCarId());
        assertNotNull(foundCar);
        assertEquals(car.getCarName(), foundCar.getCarName());
    }

    @Test
    void testFindCarByIdNotFound() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        Car foundCar = carRepository.findById("9999-0000");
        assertNull(foundCar);
    }

    @Test
    void testUpdateCarExists() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarId(UUID.randomUUID().toString());
        updatedCar.setCarName("Toyota");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(10);

        Car returnedCar = carRepository.update(car.getCarId(), updatedCar);

        assertNotNull(returnedCar);
        assertEquals("Black", returnedCar.getCarColor());
        assertEquals(10, returnedCar.getCarQuantity());
    }

    @Test
    void testUpdateCarNotFound() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        Car updatedCar = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Blue");
        car.setCarQuantity(10);

        Car returnedCar = carRepository.update(updatedCar.getCarId(), updatedCar);
        assertNull(returnedCar);
    }

    @Test
    void testDeleteCarExists() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        carRepository.delete(car.getCarId());
        Car deletedCar = carRepository.findById(car.getCarId());

        assertNull(deletedCar);
    }

    @Test
    void testDeleteCarNotFound() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        carRepository.create(car);

        carRepository.delete("9999-0000");
        Car existingCar = carRepository.findById(car.getCarId());

        assertNotNull(existingCar);
    }

    @Test
    void testFindAllCars() {
        Car car1 = new Car();
        car1.setCarId(UUID.randomUUID().toString());
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(10);

        Car car2 = new Car();
        car2.setCarId(UUID.randomUUID().toString());
        car2.setCarName("Suzuki");
        car2.setCarColor("Black");
        car2.setCarQuantity(10);

        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> allCars = carRepository.findAll();
        assertTrue(allCars.hasNext());

        Car firstCar = allCars.next();
        assertEquals("Toyota", firstCar.getCarName());
    }

    @Test
    void testCreateCarWithNoCarId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
        assertFalse(createdCar.getCarId().isEmpty());
    }

}
