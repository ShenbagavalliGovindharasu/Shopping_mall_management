package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.ProductBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface ProductModelInt {

	public long add(ProductBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(ProductBean bean) throws ApplicationException;

	public void update(ProductBean bean) throws ApplicationException, DuplicateRecordException;

	public ProductBean findByPK(long pk) throws ApplicationException;
	
	public ProductBean findByProductCode(long code) throws ApplicationException;

	public ProductBean findByName(String name) throws ApplicationException;
	
	public ProductBean findByCategoryAndName(long cId,String name) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(ProductBean Bean) throws ApplicationException;

	public List search(ProductBean Bean, int pageNo, int pageSize) throws ApplicationException;
	
	public long nextProductCode() throws ApplicationException;

	

}
