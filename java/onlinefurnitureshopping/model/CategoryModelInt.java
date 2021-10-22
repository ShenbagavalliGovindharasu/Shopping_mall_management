package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.CategoryBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface CategoryModelInt {

	public long add(CategoryBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(CategoryBean bean) throws ApplicationException;

	public void update(CategoryBean bean) throws ApplicationException, DuplicateRecordException;

	public CategoryBean findByPK(long pk) throws ApplicationException;

	public CategoryBean findByName(String name) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(CategoryBean Bean) throws ApplicationException;

	public List search(CategoryBean Bean, int pageNo, int pageSize) throws ApplicationException;

	

}
