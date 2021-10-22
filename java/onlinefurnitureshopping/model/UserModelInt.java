package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.UserBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.exception.RecordNotFoundException;

public interface UserModelInt {

	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(UserBean bean) throws ApplicationException;

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException;

	public UserBean findByPK(long pk) throws ApplicationException;

	public UserBean findByLogin(String login) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(UserBean Bean) throws ApplicationException;

	public List search(UserBean Bean, int pageNo, int pageSize) throws ApplicationException;

	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException;

	public UserBean authenticate(String login, String password) throws ApplicationException;

	public long registerUser(UserBean Bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException;

}
