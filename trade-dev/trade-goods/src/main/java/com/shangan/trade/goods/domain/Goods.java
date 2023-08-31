package com.shangan.trade.goods.domain;

import java.util.Date;

public class Goods {
    private Long id;

    private String title;

    private String brand;

    private String image;

    private String description;

    private Integer price;

    private Integer status;

    private String keyword;

    private String category;

    private Integer availableStock;

    private Integer lockStock;

    private Integer saleNum;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", brand=").append(brand);
        sb.append(", image=").append(image);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", keyword=").append(keyword);
        sb.append(", category=").append(category);
        sb.append(", availableStock=").append(availableStock);
        sb.append(", lockStock=").append(lockStock);
        sb.append(", saleNum=").append(saleNum);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}