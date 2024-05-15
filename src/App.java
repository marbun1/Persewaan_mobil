import models.Car;
import models.Rental;
import models.User;
import services.CarService;
import services.RentalService;
import services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static CarService carService = new CarService();
    private static RentalService rentalService = new RentalService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Registrasi Pengguna");
            System.out.println("2. Tambah Mobil");
            System.out.println("3. Cari Mobil");
            System.out.println("4. Lihat Semua Mobil");
            System.out.println("5. Pesan Mobil");
            System.out.println("6. Kembalikan Mobil");
            System.out.println("7. Keluar");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            try {
                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        addCar();
                        break;
                    case 3:
                        searchCar();
                        break;
                    case 4:
                        listAllCars();
                        break;
                    case 5:
                        rentCar();
                        break;
                    case 6:
                        returnCar();
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opsi tidak valid.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerUser() throws SQLException {
        System.out.println("Registrasi Pengguna");
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();
        System.out.print("Nomor Telepon: ");
        String nomorTelepon = scanner.nextLine();
        System.out.print("Nomor SIM: ");
        String nomorSim = scanner.nextLine();

        User user = new User(nama, alamat, nomorTelepon, nomorSim);
        userService.registerUser(user);
        System.out.println("Pengguna terdaftar.");
    }

    private static void addCar() throws SQLException {
        System.out.println("Tambah Mobil");
        System.out.print("Merek: ");
        String merek = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Nomor Plat: ");
        String nomorPlat = scanner.nextLine();
        System.out.print("Tarif Sewa per Hari: ");
        double tarifSewaPerHari = scanner.nextDouble();
        scanner.nextLine();  // consume newline

        Car car = new Car(merek, model, nomorPlat, tarifSewaPerHari, true);
        carService.addCar(car);
        System.out.println("Mobil ditambahkan.");
    }

    private static void searchCar() throws SQLException {
        System.out.println("Cari Mobil");
        System.out.print("Merek: ");
        String merek = scanner.nextLine();
        List<Car> cars = carService.getAllAvailableCars();
        for (Car car : cars) {
            if (car.getMerek().equalsIgnoreCase(merek)) {
                System.out.println("ID: " + car.getId() + ", Merek: " + car.getMerek() + ", Model: " + car.getModel() + ", Nomor Plat: " + car.getNomorPlat() + ", Tarif: " + car.getTarifSewaPerHari());
            }
        }
    }

    private static void listAllCars() throws SQLException {
        System.out.println("Daftar Semua Mobil");
        List<Car> cars = carService.getAllAvailableCars();
        for (Car car : cars) {
            System.out.println("ID: " + car.getId() + ", Merek: " + car.getMerek() + ", Model: " + car.getModel() + ", Nomor Plat: " + car.getNomorPlat() + ", Tarif: " + car.getTarifSewaPerHari());
        }
    }

    private static void rentCar() throws SQLException {
        System.out.println("Pesan Mobil");
        System.out.print("ID Pengguna: ");
        int userId = scanner.nextInt();
        System.out.print("ID Mobil: ");
        int carId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.print("Tanggal Mulai (yyyy-mm-dd): ");
        String tanggalMulai = scanner.nextLine();
        System.out.print("Tanggal Selesai (yyyy-mm-dd): ");
        String tanggalSelesai = scanner.nextLine();

        Rental rental = new Rental(userId, carId, java.sql.Date.valueOf(tanggalMulai), java.sql.Date.valueOf(tanggalSelesai), 0);
        Car car = carService.getCarById(carId);
        if (car != null) {
            long diffInMillies = Math.abs(java.sql.Date.valueOf(tanggalSelesai).getTime() - java.sql.Date.valueOf(tanggalMulai).getTime());
            long diff = diffInMillies / (24 * 60 * 60 * 1000);
            rental.setBiayaTotal(car.getTarifSewaPerHari() * diff);
            rentalService.rentCar(rental);
            System.out.println("Mobil dipesan.");
        } else {
            System.out.println("Mobil tidak tersedia.");
        }
    }

    private static void returnCar() throws SQLException {
        System.out.println("Kembalikan Mobil");
        System.out.print("Nomor Plat: ");
        String nomorPlat = scanner.nextLine();
        System.out.print("ID Pengguna: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // consume newline

        rentalService.returnCar(nomorPlat, userId);
        System.out.println("Mobil dikembalikan.");
    }
}
