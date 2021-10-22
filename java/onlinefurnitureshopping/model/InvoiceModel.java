package onlinefurnitureshopping.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import onlinefurnitureshopping.bean.InvoiceBean;
import onlinefurnitureshopping.bean.ProductBean;
import onlinefurnitureshopping.bean.UserBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.HibDataSource;

public class InvoiceModel implements InvoiceModelInt {

	private static Logger log = Logger.getLogger(InvoiceModel.class);

	@Override
	public long add(InvoiceBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Invoice Model add method start");

		System.out.println("HIB add Invoice model");

		long pk = 0;

		UserModel uModel = new UserModel();
		UserBean uBean = uModel.findByPK(bean.getUserId());
		bean.setUserName(uBean.getFirstName() + " " + uBean.getLastName());
		bean.setMobileNo(uBean.getMobileNo());
		bean.setEmailId(uBean.getEmailAddress());
		bean.setShippingAddress(uBean.getShippingAddress());
		bean.setBillingAddress(uBean.getBillingAddress());

		ProductModel pModel = new ProductModel();
		ProductBean pBean = pModel.findByPK(bean.getProductId());
		bean.setProductName(pBean.getName());
		bean.setProductDescription(pBean.getDescription());
		bean.setAmount(pBean.getPrice());
		bean.setProductImage(pBean.getImage());

		bean.setDate(new java.util.Date());
		bean.setOrderId(nextOrderId());
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
			throw new ApplicationException("Exception in Invoice Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Invoice Model add method end");
		return pk;
	}

	@Override
	public void delete(InvoiceBean Bean) throws ApplicationException {
		log.debug("Invoice Model delete  method start");
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
			throw new ApplicationException("Exception in Invoice Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Invoice Model delete  method end");
	}

	@Override
	public void update(InvoiceBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Invoice Model Update method start");

		Session session = null;
		Transaction tx = null;

		/*
		 * InvoiceBean BeanExist = findByName(Bean.getName());
		 * 
		 * // Check if updated LoginId already exist if (BeanExist != null &&
		 * BeanExist.getId() != Bean.getId()) { throw new
		 * DuplicateRecordException("Invoice Name is already exist"); }
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

			throw new ApplicationException("Exception in update Invoice ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Invoice Model Update method end");
	}

	@Override
	public InvoiceBean findByPK(long pk) throws ApplicationException {

		log.debug("Invoice Model findByPK method start");
		Session session = null;
		InvoiceBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (InvoiceBean) session.get(InvoiceBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Invoice by PK");
		} finally {
			session.close();
		}

		log.debug("Invoice Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Invoice Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(InvoiceBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Invoice");
		} finally {
			session.close();
		}

		log.debug("Invoice Model list  method end");
		return list;
	}

	/**
	 * Searches Invoice
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(InvoiceBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(InvoiceBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Invoice Model search method start");

		System.out.println("Invoice Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(InvoiceBean.class);
			if(Bean !=null) {
			if (Bean.getId() > 0) {

				criteria.add(Restrictions.eq("id", Bean.getId()));
			}

			if (Bean.getProductId() > 0) {
				criteria.add(Restrictions.eq("productId", Bean.getProductId()));
			}

			if (Bean.getUserId() > 0) {
				criteria.add(Restrictions.eq("userId", Bean.getUserId()));
			}
			if (Bean.getOrderId() > 0) {
				criteria.add(Restrictions.eq("orderId", Bean.getOrderId()));
			}

			if (Bean.getProductName() != null && Bean.getProductName().length() > 0) {
				criteria.add(Restrictions.like("productName", Bean.getProductName() + "%"));
			}
			
			if (Bean.getUserName() != null && Bean.getUserName().length() > 0) {
				criteria.add(Restrictions.like("userName", Bean.getUserName() + "%"));
			}
			}
			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Invoice");

		} finally {
			session.close();
		}

		log.debug("Invoice Model search method end");
		return list;
	}

	@Override
	public InvoiceBean findByName(String name) throws ApplicationException {

		log.debug("Invoice Model findbylogin method start");
		Session session = null;
		InvoiceBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(InvoiceBean.class);

			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (InvoiceBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Invoice by login");
		} finally {
			session.close();
		}

		log.debug("Invoice Model findbylogin method end");
		return Bean;
	}

	@Override
	public InvoiceBean findByOrderId(long orderId) throws ApplicationException {

		log.debug("Invoice Model findbylogin method start");
		Session session = null;
		InvoiceBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(InvoiceBean.class);

			criteria.add(Restrictions.like("orderId", orderId));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (InvoiceBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Invoice by login");
		} finally {
			session.close();
		}

		log.debug("Invoice Model findbylogin method end");
		return Bean;
	}

	public long nextOrderId() throws ApplicationException {

		log.debug("User Model findbylogin method start");
		Session session = null;
		InvoiceBean bean = null;
		try {
			session = HibDataSource.getSession();

			DetachedCriteria maxId = DetachedCriteria.forClass(InvoiceBean.class)
					.setProjection(Projections.max("orderId"));

			Criteria criteria = session.createCriteria(InvoiceBean.class);
			criteria.add(Property.forName("orderId").eq(maxId));
			List list = criteria.list();

			if (list.size() > 0) {
				bean = (InvoiceBean) list.get(0);
			} else {
				bean = new InvoiceBean();
			}
		} catch (HibernateException e) {

			throw new ApplicationException("exception in getting user by login");
		} finally {
			session.close();
		}

		log.debug("User Model findbylogin method end");

		if (bean.getOrderId() > 0) {
			return bean.getOrderId() + 1;
		} else {
			return 200101;
		}
	}

}
