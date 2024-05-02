package org.bearluxury.product;

import org.bearluxury.room.BED_TYPE;
import org.bearluxury.room.QUALITY_LEVEL;
import org.bearluxury.room.ROOM_TYPE;
import org.bearluxury.room.Room;

import java.io.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The ProductBuilder class is responsible for building a set of products from a CSV file,
 * adding products to the set, and writing the set of products back to a CSV file.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class ProductBuilder {
    Set<Product> productList;


    /**
     * Constructs a ProductBuilder object and initializes the productList set by reading data from a CSV file.
     *
     * @param csvName the name of the CSV file containing product data
     * @throws RuntimeException if an I/O error occurs while reading the file
     */
    public ProductBuilder(String csvName) {
        productList = new TreeSet<>(Comparator.comparing(Product::getName));
        File file = new File(csvName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();
            while ((line = reader.readLine() ) != null) {
                String[] parsedLine = line.split(",");

                Product product = new Product(
                        parsedLine[0],
                        Double.parseDouble(parsedLine[1]),
                        Integer.parseInt(parsedLine[2]),
                        readAsProductType(parsedLine[3])
                        );

                productList.add(product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Retrieves the set of products.
     *
     * @return the set of products
     */
    public Set<Product> getProductList() {
        return productList;
    }

    /**
     * Adds a product to the set of products.
     *
     * @param product the product to add
     * @return true if the product was successfully added, false otherwise
     */

    public boolean addProduct(Product product) {
        return productList.add(product);
    }


    /**
     * Writes the set of products to a CSV file.
     *
     * @param csvName the name of the CSV file to write to
     * @throws RuntimeException if an I/O error occurs while writing the file
     */

    public void writeProduct(String csvName){
        File file = new File(csvName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("name,price,quantity,type\n");

            for(Product product : productList){
                writer.write(product.getName()+","
                        +product.getPrice()+","
                        +product.getQuantity()+","
                        +product.getProductType().toString()+'\n');
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Converts a string representation of a product type to a PRODUCT_TYPE enum value.
     *
     * @param str the string representation of the product type
     * @return the corresponding PRODUCT_TYPE enum value
     */

    public PRODUCT_TYPE readAsProductType(String str) {
        if (str.equals("clothing")) {
            return PRODUCT_TYPE.CLOTHING;
        }
        else if (str.equals("accessory")) {
            return PRODUCT_TYPE.ACCESSORY;
        }
        else if (str.equals("artesian good")) {
            return PRODUCT_TYPE.ARTESIAN_GOOD;
        }
        else if (str.equals("toiletry")) {
            return PRODUCT_TYPE.TOILETRY;
        }

        return PRODUCT_TYPE.PHARMACEUTICAL;
    }
}
