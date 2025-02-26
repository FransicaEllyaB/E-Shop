# Tutorial Pemrograman Lanjut
## Fransisca Ellya Bunaren - 2306152286
Link Deployment : https://fransisca-ellya-bunaren-e-shop-advpro.koyeb.app/

## Module 1

<details>
<summary>Reflection 1</summary>

> You already implemented two new features using Spring Boot. Check again your source code
and evaluate the coding standards that you have learned in this module. Write clean code
principles and secure coding practices that have been applied to your code.  If you find any
mistake in your source code, please explain how to improve your code.

<br><b>Clean code</b>
1. Meaningful Names
   Nama variabel harus deskriptif, memiliki makna, menghindari <i>number-series</i> contoh a1, dan menghindari encodings. Contohnya :
```
private List<Product> productData = new ArrayList<>();
```
Product Data memiliki makna data-data produk sehingga menggunakan ArrayList.

2. Function
   Fungsi harus melakukan satu hal, memiliki deskriptif nama, tidak memiliki efek, dan <i>Command Query Separation</i>. Contohnya :
```
public void delete(Product product) {
    productData.remove(product);
}
```

3. Comments
   Comments harus menjelaskan kode tidak mengulang penjelasan variabel. Contohnya :
```   
/**
 * Fungsi ini memanggil halaman create product dengan metode get
 */
@GetMapping("/create")
public String createProductPage(Model model) {
    Product product = new Product();
    model.addAttribute("product", product);
    return "createProduct";
}
```

4. Objects and Data Structure
   Menyembunyikan struktur internal untuk mengexposed-nya menggunakan method getter dan setter. Contoh :
```
package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class Product {
    private static int counter = 0;
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = Integer.toString(counter++);
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
```

5. Error Handling
   Menggunakan Exceptions daripada mengembalikan kode.
```
public String createProductPost(@ModelAttribute Product product, Model model) {
    try{
        service.create(product);
        return "redirect:list";
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "createProduct";
    }
}
```

<b>Secure Coding</b>
Terdapat input validation untuk memvalidasi input, seperti hanya angka saja dan nama tidak boleh hanya spasi.

### Mistake in my code
* Cara untuk mencari id masih tidak efektif yang berakibat untuk mencari product menggunakan ID tidak efektif. Peningkatan yang bisa dilakukan adalah menggunakan HashMap agar pencarian ID lebih cepat.

</details>

<details>
<summary>Reflection 2</summary>

>After writing the unit test, how do you feel? How many unit tests should be made in a
class? How to make sure that our unit tests are enough to verify our program? It would be
good if you learned about code coverage. Code coverage is a metric that can help you
understand how much of your source is tested. If you have 100% code coverage, does
that mean your code has no bugs or errors?

Dengan unit tests, tentu membawa rasa percaya diri karena membantu untuk menangkap error. Dalam satu class, unit test dapat sangat banyak tergantung dengan kompleksitas program. Umumnya, unit test harus meng-cover semua method setidaknya satu, meng-test scenario yang berbeda, dan mengikuti SRP (single responsibility). Sebanyak 100% code coverage tidak menunjukkan bahwa kode free bugs karena coverage hanya mengecek jika kode dijalankan, tidak menjamin semua edge cases dan kasus sesungguhnya ditest, dan beberapa error dapat terjadi karena interaksi yang tidak sesuai ekspektasi.

>Suppose that after writing the CreateProductFunctionalTest.java along with the
corresponding test case, you were asked to create another functional test suite that
verifies the number of items in the product list. You decided to create a new Java class
similar to the prior functional test suites with the same setup procedures and instance
variables.
What do you think about the cleanliness of the code of the new functional test suite? Will
the new code reduce the code quality? Identify the potential clean code issues, explain
the reasons, and suggest possible improvements to make the code cleaner!

Membuat kelas Java baru untuk memverifikasi jumlah item dalam daftar produk dengan menduplikasi prosedur setup dan variabel instance dapat menyebabkan masalah kebersihan kode. Meskipun tujuannya adalah untuk menjaga struktur suite pengujian, pendekatan ini dapat menyebabkan redundansi kode, yang mengurangi kualitas kode secara keseluruhan.
</br><b>Potensi Masalah Clean Code : </b>
1. Duplikasi Kode
   Jika prosedur setup dan variabel instance diulang di beberapa kelas pengujian, hal ini menyebabkan redundansi.
2. Melanggar Prinsip DRY (Don't Repeat Yourself)
   Menulis ulang logika setup yang serupa di berbagai kelas pengujian bertentangan dengan prinsip DRY.
3. Mengurangi Maintainability (Kemudahan Pemeliharaan)
   Semakin banyak kode yang diduplikasi, semakin sulit untuk diperbarui, diperbaiki, atau direfaktor.
4. Kemungkinan Melanggar Prinsip Single Responsibility (SRP)
   Jika kelas pengujian baru memiliki terlalu banyak tanggung jawab di luar verifikasi jumlah produk, perlu dilakukan refactoring menjadi test case yang lebih fokus.

<b>Saran untuk Membuat Kode Lebih Bersih : </b>
1. Ekstrak Logika Setup Umum ke dalam Kelas Pengujian Dasar (Base Test Class)
2. Gunakan Kembali Metode Umum dalam Suite Pengujian Fungsional
3. Gunakan Pengujian Parameterized (Parameterized Tests)
4. Gunakan Page Object Model (POM) untuk Struktur Pengujian yang Lebih Baik
</details>

## Module 2
<details>
<summary>Reflection 1</summary>
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy 
on fixing them. <br>
   <br> - Menghapus modifier public di `ProductService.java` karena berupa interface
   <br> - Menghapus import yang tidak digunakan
   <br> - Menghapus Code Duplication
   <br> - Meningkatkan test coverage dengan skenario yang lebih lengkap

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current 
implementation has met the definition of Continuous Integration and Continuous 
Deployment? Explain the reasons (minimum 3 sentences)! <br><br>
Continuous Integration merupakan penggabungan perubahan kode dari berbagai pengembang ke dalam satu proyek perangkat lunak secara otomatis, seperti pada project ini, saya menggunakan `scorecard.yml`, `ci.yml`, dan `cmd.yml` di file `.github\workflows`. Setiap commit akan memicu untuk menjalankan test suite dan menganalisis isu keamanan. Continuous Deployment adalah pengembangan perangkat lunak yang mengotomatisasi proses pengujian dan pengiriman perangkat lunak ke pengguna. Continuous Deployment (CD) adalah praktik pengembangan perangkat lunak yang mengotomatisasi proses deployement ke suatu server tertentu. Implementasi yang telah dilakukan adalah pembuatan script `deploy.yml` sehingga aplikasi akan otomatis deploy setiap kali push ke branch master. 
</details>

## Module 3
<details>
<summary>Reflection</summary>

>1) Explain what principles you apply to your project!
* Single Responsibility Principle (SRP)
Saya mengimplementasikan SRP dengan memisahkan tanggung jawab antara class `ProductController` dengan `CarController` sehingga setiap class hanya berinteraksi dengan satu model. 
* Open-Closed Principle (OCP)
Untuk menghindari modifikasi kode lama, saya menggunakan ekstensi dari Model `Product` dan `Car` untuk menambahkan fungsionalitas baru.
* Liskov Substitution Principle (LSP)
Saya sudah menerapkan prinsip LSP pada `ProductService` dengan `ProductServiceImpl` atau `CarService` dengan `CarServiceImpl`. Dengan ini, subclass `ProductServiceImpl` dapat menggantikan superclass `ProductService` begitu juga dengan `CarService` dan `CarServiceImpl`
* Interface Segregation Principle (ISP)
Saya sudah menerapkan princip ISP dengan memisahkan Interface di `CarService` dan `ProductService` sehingga class `ProductServiceImpl` dan `CarServiceImpl` dapat mengimplementasikan interface yang diperlukan saja.
* Depedency Inversion Principle (DIP)
Saya menerapkan prinsip DIP dengan mendeklarasikan `ProductService` sebagai dependency dalam ProductController, bukan menggunakan implementasi konkretnya (ProductServiceImpl). Dependency injection memastikan ProductController tidak bergantung langsung pada implementasi spesifik sehingga memudahkan penggantian atau pengujian dengan mock.

>2) Explain the advantages of applying SOLID principles to your project with examples.
* Kode lebih mudah dipahami dan dirawatkarena setiap class hanya memiliki satu tanggung jawab utama. Contoh pemisahan tanggung jawab antara class `ProductController` dengan `CarController`.
* Memudahkan penambahan fitur baru sehingga tidak perlu mengubah kode lama cukup menambahkan ekstensi. Contoh model `Product` dan `Car` yang dapat diextensi seperti di `ProductRepository` dan `CarRepository`
* Memastikan subclass bisa digunakan tanpa mengubah program sehingga subclass harus bisa menggantikan superclass tanpa mengubah perilaku program. Contoh subclass `ProductServiceImpl` dapat menggantikan superclass `ProductService`.
* Kode tidak terlalu berat dan mudah digunakan. Contoh pemisahan interface di `CarService` dan `ProductService`.
* Memudahkan testing dan menghindari ketergantungan yang kuat. Jika ProductController langsung menggunakan ProductServiceImpl, maka sulit untuk mengganti implementasi atau melakukan unit testing. Dengan DIP, kita gunakan interface ProductService sehingga kita bisa mengganti implementasinya dengan mudah.

>3) Explain the disadvantages of not applying SOLID principles to your project with examples.

Jika dalam project ini, saya tidak mengimplementasikan SOLID principles. Maka, sulit untuk untuk memantain kode dan menambahkan fitur baru, rentan terhadap bug yang tidak terduga, class harus mengimplementasikan metode yang tidak diperlukan, dan sulit untuk melakukan testing. Jika satu class memiliki terlalu banyak tanggung jawab, setiap perubahan kecil dapat menyebabkan efek samping yang tidak terduga. Contoh jika `ProductController` dan `CarController` masih dalam satu file. Terdapat pewarisan atribut atau method yang tidak diperkukan. Selain itu, perubahan pada suatu method dapat berpengaruh terhadap method di class lain.

</details>