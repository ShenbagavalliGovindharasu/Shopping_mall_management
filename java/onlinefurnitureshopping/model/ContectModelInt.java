package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.ContectBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface ContectModelInt {

	public long add(ContectBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(ContectBean bean) throws ApplicationException;

	public void update(ContectBean bean) throws ApplicationException, DuplicateRecordException;

	public ContectBean findByPK(long pk) throws ApplicationException;

	public ContectBean findByName(String name) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(ContectBean Bean) throws ApplicationException;

	public List search(ContectBean Bean, int pageNo, int pageSize) throws ApplicationException;

	

}
