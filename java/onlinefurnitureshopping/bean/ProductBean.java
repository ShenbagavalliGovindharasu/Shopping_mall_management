package onlinefurnitureshopping.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class ProductBean extends BaseBean {

	@Column(name = "CategoryId")
	private long categoryId;
	@Column(name = "Category_Name", length = 225)
	private String categoryName;
	@Column(name = "Product_Code", nullable = false, columnDefinition = "int default 0")
	private long productCode;
	@Column(name = "Name", length = 225)
	private String name;
	@Column(name = "Description", length = 225)
	private String description;
	@Column(name = "Price")
	private double price;
	@Column(name = "Image", length = 225)
	private String image;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getProductCode() {
		return productCode;
	}

	public void setProductCode(long productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
