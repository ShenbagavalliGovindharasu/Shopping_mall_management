package onlinefurnitureshopping.model;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.UserBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.exception.RecordNotFoundException;
import onlinefurnitureshopping.util.EmailBuilder;
import onlinefurnitureshopping.util.EmailMessage;
import onlinefurnitureshopping.util.EmailUtility;
import onlinefurnitureshopping.util.HibDataSource;







public class UserModel implements UserModelInt {

	private static Logger log = Logger.getLogger(UserModel.class);

	@Override
	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("User Model add method start");

		System.out.println("HIB add user model");

		long pk = 0;
		
		UserBean BeanExist = findByLogin(bean.getLogin());
		if (BeanExist != null) {
			System.out.println("BeanExist.getLogin :" + BeanExist.getLogin());
			throw new DuplicateRecordException("LoginId is already exist");
		}
		
		
		Transaction tx = null;
		Session session = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(bean);
			pk = bean.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("User Model add method end");
		return pk;
	}

	@Override
	public void delete(UserBean Bean) throws ApplicationException {
		log.debug("User Model delete  method start");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(Bean);
			tx.commit();
		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("User Model delete  method end");
	}

	@Override
	public void update(UserBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("User Model Update method start");

		Session session = null;
		Transaction tx = null;

		UserBean BeanExist = findByLogin(Bean.getLogin());

		System.out.println("Bean.getLogin()" + Bean.getLogin());
		// Check if updated LoginId already exist
		if (BeanExist != null && BeanExist.getId() != Bean.getId()) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		
		

		try {
			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.update(Bean);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in update user ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("User Model Update method end");
	}

	@Override
	public UserBean findByPK(long pk) throws ApplicationException {

		log.debug("User Model findByPK method start");
		Session session = null;
		UserBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (UserBean) session.get(UserBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting user by PK");
		} finally {
			session.close();
		}

		log.debug("User Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("User Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(UserBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of User");
		} finally {
			session.close();
		}

		log.debug("User Model list  method end");
		return list;
	}

	/**
	 * Searches User
	 * 
	 * @param Bean
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(UserBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(UserBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("User Model search method start");

		System.out.println("User Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserBean.class);

			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}
			if (Bean.getFirstName() != null && Bean.getFirstName().length() > 0) {
				criteria.add(Restrictions.like("firstName", Bean.getFirstName() + "%"));
			}
			
			if (Bean.getLastName() != null && Bean.getLastName().length() > 0) {
				criteria.add(Restrictions.like("lastName", Bean.getLastName() + "%"));
			}
		
			
			if (Bean.getLogin() != null && Bean.getLogin().length() > 0) {
				criteria.add(Restrictions.like("login", Bean.getLogin() + "%"));
			}
			
			if (Bean.getRoleId() > 0) {
				criteria.add(Restrictions.eq("roleId", Bean.getRoleId()));
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search user");

		} finally {
			session.close();
		}

		log.debug("User Model search method end");
		return list;
	}

	@Override
	public UserBean findByLogin(String login) throws ApplicationException {

		log.debug("User Model findbylogin method start");
		Session session = null;
		UserBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(UserBean.class);

			criteria.add(Restrictions.like("login", login));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (UserBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
				e.printStackTrace();
			throw new ApplicationException("exception in getting user by login");
		} finally {
			session.close();
		}

		log.debug("User Model findbylogin method end");
		return Bean;
	}
	
	

	@Override
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.debug("User Model changepassword method start");
		boolean flag = false;

		UserBean BeanExist = null;

		BeanExist = findByPK(id);

		if (BeanExist != null && BeanExist.getPassword().equals(oldPassword)) {
			BeanExist.setPassword(newPassword);

			try {
				update(BeanExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("exception in  change password ");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("exception in change password");
		}
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", BeanExist.getLogin());
		map.put("password", BeanExist.getPassword());
		map.put("firstName", BeanExist.getFirstName());
		map.put("lastName", BeanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(BeanExist.getLogin());
		msg.setSubject("Online Furniture Shop Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;
	}

	@Override
	public UserBean authenticate(String login, String password) throws ApplicationException {

		log.debug("User Model authenticate method start");
		Session session = null;
		UserBean Bean = null;

		session = HibDataSource.getSession();

		Query q = session.createQuery("from UserBean where login=? and password =?");

		q.setString(0, login);
		q.setString(1, password);

		List list = q.list();

		if (list.size() > 0) {

			Bean = (UserBean) list.get(0);

		} else {

			Bean = null;
		}
		log.debug("Model authenticate End");
		return Bean;

	}

	@Override
	public long registerUser(UserBean Bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Model registerUser Started");

		long pk = add(Bean);


		log.debug("Model registerUser End");

		return pk;

	}

	@Override
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {

		log.debug("Model forgetPassword Started");

		UserBean userData = findByLogin(login);

		boolean flag = false;

		if (userData == null) {

			throw new RecordNotFoundException("Login Id Does not exist.");

		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(login);
		msg.setSubject("Online Furniture Shop Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);

		flag = true;

		log.debug("Model forgetPassword End");
		return flag;
	}

	
	
}
