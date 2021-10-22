package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.CartBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface CartModelInt {

	public long add(CartBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(CartBean bean) throws ApplicationException;

	public void update(CartBean bean) throws ApplicationException, DuplicateRecordException;

	public CartBean findByPK(long pk) throws ApplicationException;

	public CartBean findByName(String name) throws ApplicationException;
	
	public CartBean findByproIdAndUserId(long proId, long UserId) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(CartBean Bean) throws ApplicationException;

	public List search(CartBean Bean, int pageNo, int pageSize) throws ApplicationException;

	

}
