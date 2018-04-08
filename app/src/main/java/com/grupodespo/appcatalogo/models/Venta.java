package com.grupodespo.appcatalogo.models;
public class Venta {
    int id;
    int clientId;
    int productId;
    String productName;
    String productDetail;
    String productImage;

    public Venta(int id, int clientId, int productId, String productName, String productDetail, String productImage) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.productName = productName;
        this.productDetail = productDetail;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}

