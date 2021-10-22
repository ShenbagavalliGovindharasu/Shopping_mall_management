package onlinefurnitureshopping.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.RoleBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.HibDataSource;

public class RoleModel implements RoleModelInt {

	private static Logger log = Logger.getLogger(RoleModel.class);

	@Override
	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Role Model add method start");

		System.out.println("HIB add Role model");

		long pk = 0;

		RoleBean BeanExist = findByName(bean.getName());
		if (BeanExist != null) {
			throw new DuplicateRecordException("Role Name is already exist");
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
			throw new ApplicationException("Exception in Role Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Role Model add method end");
		return pk;
	}

	@Override
	public void delete(RoleBean Bean) throws ApplicationException {
		log.debug("Role Model delete  method start");
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
			throw new ApplicationException("Exception in Role Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Role Model delete  method end");
	}

	@Override
	public void update(RoleBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Role Model Update method start");

		Session session = null;
		Transaction tx = null;

		RoleBean BeanExist = findByName(Bean.getName());

		// Check if updated LoginId already exist
		if (BeanExist != null && BeanExist.getId() != Bean.getId()) {
			throw new DuplicateRecordException("Role Name is already exist");
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

			throw new ApplicationException("Exception in update Role ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Role Model Update method end");
	}

	@Override
	public RoleBean findByPK(long pk) throws ApplicationException {

		log.debug("Role Model findByPK method start");
		Session session = null;
		RoleBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (RoleBean) session.get(RoleBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Role by PK");
		} finally {
			session.close();
		}

		log.debug("Role Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Role Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(RoleBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Role");
		} finally {
			session.close();
		}

		log.debug("Role Model list  method end");
		return list;
	}

	/**
	 * Searches Role
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(RoleBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(RoleBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Role Model search method start");

		System.out.println("Role Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleBean.class);

			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}
			if (Bean.getName() != null && Bean.getName().length() > 0) {
				criteria.add(Restrictions.like("name", Bean.getName() + "%"));
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Role");

		} finally {
			session.close();
		}

		log.debug("Role Model search method end");
		return list;
	}

	@Override
	public RoleBean findByName(String name) throws ApplicationException {

		log.debug("Role Model findbylogin method start");
		Session session = null;
		RoleBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(RoleBean.class);

			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (RoleBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Role by login");
		} finally {
			session.close();
		}

		log.debug("Role Model findbylogin method end");
		return Bean;
	}

}
