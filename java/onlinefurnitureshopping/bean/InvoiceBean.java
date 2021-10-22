package onlinefurnitureshopping.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Invoice")
public class InvoiceBean extends BaseBean {

	@Column(name = "Order_Id", nullable = false, columnDefinition = "int default 0")
	private long orderId;
	@Column(name = "user_Id")
	private long userId;
	@Column(name = "User_Name", length = 225)
	private String userName;
	@Column(name = "Product_Id")
	private long productId;
	@Column(name = "Product_Name", length = 225)
	private String productName;

	@Column(name = "Date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(name = "Mobile_No", length = 225)
	private String mobileNo;
	@Column(name = "Shipping_address", length = 1500)
	private String shippingAddress;
	@Column(name = "billing_Address", length = 1500)
	private String billingAddress;
	@Column(name = "Email_Id", length = 225)
	private String emailId;
	@Column(name = "Prodcut_Description", length = 755)
	private String productDescription;
	@Column(name = "Product_image", length = 755)
	private String productImage;
	@Column(name = "Amount" )
	private double amount;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
