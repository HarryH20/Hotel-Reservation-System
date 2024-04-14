package org.bearluxury.product;

import org.bearluxury.room.BED_TYPE;
import org.bearluxury.room.QUALITY_LEVEL;
import org.bearluxury.room.ROOM_TYPE;
import org.bearluxury.room.Room;

import java.io.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ProductBuilder {
    Set<Product> productList;

    public ProductBuilder(String csvName) {
        productList = new TreeSet<>(Comparator.comparing(Product::getProductType));
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

    public Set<Product> getProductList() {
        return productList;
    }

    public boolean addProduct(Product product) {
        return productList.add(product);
    }

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
