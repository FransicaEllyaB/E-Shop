# Tutorial Pemrograman Lanjut
## Fransisca Ellya Bunaren - 2306152286

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