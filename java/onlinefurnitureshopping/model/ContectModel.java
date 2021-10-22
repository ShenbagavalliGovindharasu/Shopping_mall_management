package onlinefurnitureshopping.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.ContectBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.HibDataSource;

public class ContectModel implements ContectModelInt {

	private static Logger log = Logger.getLogger(ContectModel.class);

	@Override
	public long add(ContectBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Contect Model add method start");


		long pk = 0;

		/*
		 * ContectBean BeanExist = findByName(bean.getName()); if (BeanExist != null) {
		 * throw new DuplicateRecordException("Contect Name is already exist"); }
		 */
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
			throw new ApplicationException("Exception in Contect Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Contect Model add method end");
		return pk;
	}

	@Override
	public void delete(ContectBean Bean) throws ApplicationException {
		log.debug("Contect Model delete  method start");
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
			throw new ApplicationException("Exception in Contect Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Contect Model delete  method end");
	}

	@Override
	public void update(ContectBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Contect Model Update method start");

		Session session = null;
		Transaction tx = null;

		/*
		 * ContectBean BeanExist = findByName(Bean.getName());
		 * 
		 * // Check if updated LoginId already exist if (BeanExist != null &&
		 * BeanExist.getId() != Bean.getId()) { throw new
		 * DuplicateRecordException("Contect Name is already exist"); }
		 */

		try {
			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.update(Bean);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in update Contect ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Contect Model Update method end");
	}

	@Override
	public ContectBean findByPK(long pk) throws ApplicationException {

		log.debug("Contect Model findByPK method start");
		Session session = null;
		ContectBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (ContectBean) session.get(ContectBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Contect by PK");
		} finally {
			session.close();
		}

		log.debug("Contect Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Contect Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ContectBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Contect");
		} finally {
			session.close();
		}

		log.debug("Contect Model list  method end");
		return list;
	}

	/**
	 * Searches Contect
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(ContectBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(ContectBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Contect Model search method start");

		System.out.println("Contect Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ContectBean.class);

			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}
			if (Bean.getName() != null && Bean.getName().length() > 0) {
				criteria.add(Restrictions.like("name", Bean.getName() + "%"));
			}
			if (Bean.getEmail() != null && Bean.getEmail().length() > 0) {
				criteria.add(Restrictions.like("email", Bean.getEmail() + "%"));
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Contect");

		} finally {
			session.close();
		}

		log.debug("Contect Model search method end");
		return list;
	}

	@Override
	public ContectBean findByName(String name) throws ApplicationException {

		log.debug("Contect Model findbylogin method start");
		Session session = null;
		ContectBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ContectBean.class);

			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (ContectBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Contect by login");
		} finally {
			session.close();
		}

		log.debug("Contect Model findbylogin method end");
		return Bean;
	}

}
