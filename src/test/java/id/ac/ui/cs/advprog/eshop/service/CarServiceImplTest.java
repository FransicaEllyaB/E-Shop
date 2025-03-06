package id.ac.ui.cs.advprog.eshop.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

class CarServiceImplTest {

    @Mock
    private CarRepository mockCarRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        when(mockCarRepository.create(any(Car.class))).thenReturn(car);

        Car createdCar = carService.create(car);

        verify(mockCarRepository, times(1)).create(car);
        assertNotNull(createdCar);
        assertEquals("Toyota", createdCar.getCarName());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("1");
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(10);

        Car car2 = new Car();
        car2.setCarId("2");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(5);

        // Mengatur carRepository untuk mengembalikan iterator yang berisi mobil
        List<Car> cars = Arrays.asList(car1, car2);
        when(mockCarRepository.findAll()).thenReturn(cars.iterator());

        // Testing findAll
        List<Car> foundCars = carService.findAll();

        // Verifikasi bahwa carRepository.findAll dipanggil satu kali
        verify(mockCarRepository, times(1)).findAll();
        // Verifikasi bahwa jumlah mobil yang ditemukan sesuai
        assertEquals(2, foundCars.size());
        assertEquals("Toyota", foundCars.get(0).getCarName());
        assertEquals("Honda", foundCars.get(1).getCarName());
    }

    @Test
    void testFindById() {
        // Setup: Membuat mobil untuk diuji
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        // Mengatur carRepository untuk mengembalikan mobil dengan carId "1"
        when(mockCarRepository.findById("1")).thenReturn(car);

        // Testing findById
        Car foundCar = carService.findById("1");

        // Verifikasi bahwa carRepository.findById dipanggil satu kali
        verify(mockCarRepository, times(1)).findById("1");
        // Verifikasi bahwa car yang ditemukan sesuai
        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getCarName());
        assertEquals("Red", foundCar.getCarColor());
    }

    @Test
    void testUpdateCar() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car updatedCar = new Car();
        car.setCarId("2");
        updatedCar.setCarName("Toyota Updated");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(15);

        carService.update("1", updatedCar);
        verify(mockCarRepository, times(1)).update(eq("1"), any(Car.class));
    }

    @Test
    void testDeleteCarById() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        doNothing().when(mockCarRepository).delete(eq("1"));
        carService.deleteCarById("1");
        verify(mockCarRepository, times(1)).delete(eq("1"));
    }
}