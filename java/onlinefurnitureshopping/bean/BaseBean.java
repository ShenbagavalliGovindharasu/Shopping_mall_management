package onlinefurnitureshopping.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class BaseBean implements DropdownListBean, Comparable<BaseBean> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	protected long id;

	@Column(name="CREATED_BY")
	protected String createdBy;
	
	@Column(name="MODIFIED_BY")
	protected String modifiedBy;
	
	@Column(name="CREATED_DATETIME")
	protected Timestamp createdDatetime;
	
	@Column(name="MODIFIED_DATETIME")
	protected Timestamp modifiedDatetime;

	

	/**
	 * @return ID of Child
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param Id
	 *            To set Child ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Created By Child
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param CreatedBy
	 *            To set Child Creatd By
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Modified By Child
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param Modified
	 *            by To set Child ModifiedBY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return Created Date And TIme of Child
	 */

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * @param Created
	 *            Date and Time To set Child Created dATE aND tiME
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * @return Modified Date And Time of Child
	 */

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * @param Modified
	 *            By To set Child ModiFied By
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	public int compareTo(BaseBean next) {
		// TODO Auto-generated method stub
		return getValue().compareTo(next.getValue());
	}
}
