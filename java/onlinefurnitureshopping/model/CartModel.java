package onlinefurnitureshopping.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.CartBean;
import onlinefurnitureshopping.bean.ProductBean;
import onlinefurnitureshopping.bean.UserBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.HibDataSource;

public class CartModel implements CartModelInt {

	private static Logger log = Logger.getLogger(CartModel.class);

	@Override
	public long add(CartBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Cart Model add method start");

		System.out.println("HIB add Cart model");

		long pk = 0;

		UserModel uModel = new UserModel();
		ProductModel pModel = new ProductModel();

		ProductBean pBean = pModel.findByPK(bean.getProductId());
		UserBean uBean = uModel.findByPK(bean.getUserId());

		bean.setUserName(uBean.getFirstName() + " " + uBean.getLastName());

		bean.setPruductName(pBean.getName());
		bean.setFinalAmount(pBean.getPrice() * bean.getQuantity());

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
			throw new ApplicationException("Exception in Cart Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Cart Model add method end");
		return pk;
	}

	@Override
	public void delete(CartBean Bean) throws ApplicationException {
		log.debug("Cart Model delete  method start");
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
			throw new ApplicationException("Exception in Cart Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Cart Model delete  method end");
	}

	@Override
	public void update(CartBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Cart Model Update method start");

		Session session = null;
		Transaction tx = null;

		UserModel uModel = new UserModel();
		ProductModel pModel = new ProductModel();

		ProductBean pBean = pModel.findByPK(Bean.getProductId());
		UserBean uBean = uModel.findByPK(Bean.getUserId());

		Bean.setUserName(uBean.getFirstName() + " " + uBean.getLastName());

		Bean.setPruductName(pBean.getName());
		Bean.setFinalAmount(pBean.getPrice() * Bean.getQuantity());

		try {
			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.update(Bean);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in update Cart ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Cart Model Update method end");
	}

	@Override
	public CartBean findByPK(long pk) throws ApplicationException {

		log.debug("Cart Model findByPK method start");
		Session session = null;
		CartBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (CartBean) session.get(CartBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Cart by PK");
		} finally {
			session.close();
		}

		log.debug("Cart Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Cart Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CartBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Cart");
		} finally {
			session.close();
		}

		log.debug("Cart Model list  method end");
		return list;
	}

	/**
	 * Searches Cart
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(CartBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(CartBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Cart Model search method start");

		System.out.println("Cart Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CartBean.class);

			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}

			if (Bean.getUserId() > 0) {

				criteria.add(Restrictions.eq("userId", Bean.getUserId()));
			}
			if (Bean.getProductId() > 0) {

				criteria.add(Restrictions.eq("productId", Bean.getProductId()));
			}

			if (Bean.getPruductName() != null && Bean.getPruductName().length() > 0) {
				criteria.add(Restrictions.like("pruductName", Bean.getPruductName() + "%"));
			}
			
			if (Bean.getUserName() != null && Bean.getUserName().length() > 0) {
				criteria.add(Restrictions.like("userName", Bean.getUserName() + "%"));
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Cart");

		} finally {
			session.close();
		}

		log.debug("Cart Model search method end");
		return list;
	}

	@Override
	public CartBean findByName(String name) throws ApplicationException {

		log.debug("Cart Model findbylogin method start");
		Session session = null;
		CartBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CartBean.class);

			criteria.add(Restrictions.like("productName", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (CartBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Cart by login");
		} finally {
			session.close();
		}

		log.debug("Cart Model findbylogin method end");
		return Bean;
	}

	@Override
	public CartBean findByproIdAndUserId(long proId, long UserId) throws ApplicationException {

		log.debug("Cart Model findbylogin method start");
		Session session = null;
		CartBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CartBean.class);

			criteria.add(Restrictions.like("productId", proId));
			criteria.add(Restrictions.like("userId", UserId));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (CartBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Cart by login");
		} finally {
			session.close();
		}

		log.debug("Cart Model findbylogin method end");
		return Bean;
	}

}
