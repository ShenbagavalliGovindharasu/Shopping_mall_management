package onlinefurnitureshopping.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cart")
public class CartBean extends BaseBean {

	@Column(name = "User_Id")
	private long userId;
	@Column(name = "User_name", length = 225)
	private String userName;
	@Column(name = "Product_Id")
	private long productId;
	@Column(name = "Product_Name", length = 225)
	private String pruductName;
	@Column(name = "Quantity")
	private long quantity;
	@Column(name = "Final_amount")
	private double finalAmount;

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getPruductName() {
		return pruductName;
	}

	public void setPruductName(String pruductName) {
		this.pruductName = pruductName;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
