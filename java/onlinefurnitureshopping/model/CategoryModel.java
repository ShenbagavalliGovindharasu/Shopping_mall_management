package onlinefurnitureshopping.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.CategoryBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.HibDataSource;

public class CategoryModel implements CategoryModelInt {

	private static Logger log = Logger.getLogger(CategoryModel.class);

	@Override
	public long add(CategoryBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Category Model add method start");

		System.out.println("HIB add Category model");

		long pk = 0;

		CategoryBean BeanExist = findByName(bean.getName());
		if (BeanExist != null) {
			throw new DuplicateRecordException("Category Name is already exist");
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
			throw new ApplicationException("Exception in Category Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Category Model add method end");
		return pk;
	}

	@Override
	public void delete(CategoryBean Bean) throws ApplicationException {
		log.debug("Category Model delete  method start");
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
			throw new ApplicationException("Exception in Category Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Category Model delete  method end");
	}

	@Override
	public void update(CategoryBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Category Model Update method start");

		Session session = null;
		Transaction tx = null;

		CategoryBean BeanExist = findByName(Bean.getName());

		// Check if updated LoginId already exist
		if (BeanExist != null && BeanExist.getId() != Bean.getId()) {
			throw new DuplicateRecordException("Category Name is already exist");
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

			throw new ApplicationException("Exception in update Category ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Category Model Update method end");
	}

	@Override
	public CategoryBean findByPK(long pk) throws ApplicationException {

		log.debug("Category Model findByPK method start");
		Session session = null;
		CategoryBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (CategoryBean) session.get(CategoryBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Category by PK");
		} finally {
			session.close();
		}

		log.debug("Category Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Category Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CategoryBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Category");
		} finally {
			session.close();
		}

		log.debug("Category Model list  method end");
		return list;
	}

	/**
	 * Searches Category
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(CategoryBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(CategoryBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Category Model search method start");

		System.out.println("Category Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CategoryBean.class);

			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}
			if (Bean.getName() != null && Bean.getName().length() > 0) {
				criteria.add(Restrictions.like("name", Bean.getName() + "%"));
			}
			if (Bean.getDescription() != null && Bean.getDescription().length() > 0) {
				criteria.add(Restrictions.like("description", Bean.getDescription() + "%"));
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Category");

		} finally {
			session.close();
		}

		log.debug("Category Model search method end");
		return list;
	}

	@Override
	public CategoryBean findByName(String name) throws ApplicationException {

		log.debug("Category Model findbylogin method start");
		Session session = null;
		CategoryBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CategoryBean.class);

			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (CategoryBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Category by login");
		} finally {
			session.close();
		}

		log.debug("Category Model findbylogin method end");
		return Bean;
	}

}
