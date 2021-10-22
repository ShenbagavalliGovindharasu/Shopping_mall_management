package onlinefurnitureshopping.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User JavaBean encapsulates TimeTable attributes
 * 
 */
@Entity
@Table(name="USER")
public class UserBean extends BaseBean {

	/**
	 * First Name of User
	 */
	@Column(name="firstName",length = 225)
	private String firstName;
	/**
	 * Last Name of User
	 */
	@Column(name="lastName",length = 225)
	private String lastName;
	/**
	 * Login of User
	 */
	@Column(name="Login",length = 225)
	private String login;
	/**
	 * Password of User
	 */
	@Column(name="Password",length = 225)
	private String password;
	/**
	 * Confirm Password of User
	 */
	@Transient
	private String confirmPassword;
	
	/**
	 * MobielNo of User
	 */
	@Column(name="Mobile_No",length = 225)
	private String mobileNo;
	/**
	 * Role of User
	 */
	@Column(name="Role_Id",length = 225)
	private long roleId;
	
	
	@Column(name="ShippingAddress",length = 1500)
	private String shippingAddress;
	
	@Column(name="BillingAddress",length = 1500)
	private String billingAddress;
	
	@Column(name="EmailAddress",length = 225)
	private String emailAddress;
	
	
	

	
	

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	/**
	 * @return FirstName Of User
	 */

	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param FirstName
	 *            To set User FirstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return LastName Of User
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param LastName
	 *            To set User LastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Login id Of User
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param Login
	 *            Id To set User Login ID
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return Password Of User
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param Password
	 *            To set User Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Confirm Password Of User
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param Confirm
	 *            PAssword To set User Confirm Password
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	

	/**
	 * @return Mobile No Of User
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param Mobile
	 *            No To set User Mobile No
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return ROle Id Of User
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param Role
	 *            Id To set User ROle Id
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}


	public String getKey() {
		return id + "";
	}

	public String getValue() {

		return firstName + " " + lastName;
	}
}
