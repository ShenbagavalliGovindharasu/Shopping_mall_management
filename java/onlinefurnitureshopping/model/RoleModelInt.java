package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.RoleBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface RoleModelInt {

	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(RoleBean bean) throws ApplicationException;

	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException;

	public RoleBean findByPK(long pk) throws ApplicationException;

	public RoleBean findByName(String name) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(RoleBean Bean) throws ApplicationException;

	public List search(RoleBean Bean, int pageNo, int pageSize) throws ApplicationException;

	

}
