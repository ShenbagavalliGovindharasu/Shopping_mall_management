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

import onlinefurnitureshopping.bean.CategoryBean;
import onlinefurnitureshopping.bean.ProductBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;
import onlinefurnitureshopping.util.DataUtility;
import onlinefurnitureshopping.util.HibDataSource;

public class ProductModel implements ProductModelInt {

	private static Logger log = Logger.getLogger(ProductModel.class);

	@Override
	public long add(ProductBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		log.debug("Product Model add method start");

		System.out.println("HIB add Product model");

		long pk = 0;

		ProductBean duplicataProduct = findByCategoryAndName(bean.getCategoryId(), bean.getName());

		// Check if create Product already exist
		if (duplicataProduct != null) {
			throw new DuplicateRecordException("Product Is Already Exist This Category");
		}

		CategoryModel cModel = new CategoryModel();
		CategoryBean cBean = cModel.findByPK(bean.getCategoryId());
		bean.setCategoryName(cBean.getName());
		bean.setProductCode(nextProductCode());

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
			throw new ApplicationException("Exception in Product Add " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}

		}
		log.debug("Product Model add method end");
		return pk;
	}

	@Override
	public void delete(ProductBean Bean) throws ApplicationException {
		log.debug("Product Model delete  method start");
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
			throw new ApplicationException("Exception in Product Delete " + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		log.debug("Product Model delete  method end");
	}

	@Override
	public void update(ProductBean Bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Product Model Update method start");

		Session session = null;
		Transaction tx = null;

		ProductBean duplicataProduct = findByCategoryAndName(Bean.getCategoryId(), Bean.getName());

		// Check if updated Role already exist
		if (duplicataProduct != null && duplicataProduct.getId() != Bean.getId()) {
			throw new DuplicateRecordException("Product Is Already Exist This Category");
		}

		CategoryModel cModel = new CategoryModel();
		CategoryBean cBean = cModel.findByPK(Bean.getCategoryId());
		Bean.setCategoryName(cBean.getName());

		Bean.setProductCode(findByPK(Bean.getId()).getProductCode());

		try {
			session = HibDataSource.getSession();

			tx = session.beginTransaction();

			session.update(Bean);

			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in update Product ");
		} finally {

			if (session != null) {
				session.close();
			}
		}
		log.debug("Product Model Update method end");
	}

	@Override
	public ProductBean findByPK(long pk) throws ApplicationException {

		log.debug("Product Model findByPK method start");
		Session session = null;
		ProductBean Bean = null;

		try {
			session = HibDataSource.getSession();
			Bean = (ProductBean) session.get(ProductBean.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Product by PK");
		} finally {
			session.close();
		}

		log.debug("Product Model findByPK method End");
		return Bean;
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Product Model list  method start");
		Session session = null;

		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductBean.class);

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;// (pageNo - 1) * pageSize+1

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("exception in getting list of Product");
		} finally {
			session.close();
		}

		log.debug("Product Model list  method end");
		return list;
	}

	/**
	 * Searches Product
	 * 
	 * @param Bean : Search Parameters
	 * @throws ApplicationException
	 */
	@Override
	public List search(ProductBean Bean) throws ApplicationException {
		return search(Bean, 0, 0);
	}

	@Override
	public List search(ProductBean Bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Product Model search method start");

		System.out.println("Product Model search method start");

		Session session = null;

		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductBean.class);
			if(Bean !=null) {
			if (Bean.getId() > 0) {
				criteria.add(Restrictions.eq("id", Bean.getId()));
			}

			if (Bean.getCategoryId() > 0) {
				criteria.add(Restrictions.eq("categoryId", Bean.getCategoryId()));
			}

			if (Bean.getProductCode() > 0) {
				criteria.add(Restrictions.eq("productCode", Bean.getProductCode()));
			}

			if (Bean.getName() != null && Bean.getName().length() > 0) {
				criteria.add(Restrictions.like("name", Bean.getName() + "%"));
			}

			if (Bean.getCategoryName() != null && Bean.getCategoryName().length() > 0) {
				criteria.add(Restrictions.like("categoryName", Bean.getCategoryName() + "%"));
			}

			if (Bean.getDescription() != null && Bean.getDescription().length() > 0) {
				criteria.add(Restrictions.like("description", Bean.getDescription() + "%"));
			}

			}
			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in search Product");

		} finally {
			session.close();
		}

		log.debug("Product Model search method end");
		return list;
	}

	@Override
	public ProductBean findByName(String name) throws ApplicationException {

		log.debug("Product Model findbylogin method start");
		Session session = null;
		ProductBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductBean.class);

			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (ProductBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Product by login");
		} finally {
			session.close();
		}

		log.debug("Product Model findbylogin method end");
		return Bean;
	}

	@Override
	public ProductBean findByProductCode(long code) throws ApplicationException {

		log.debug("Product Model findbylogin method start");
		Session session = null;
		ProductBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductBean.class);

			criteria.add(Restrictions.like("productCode", code));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (ProductBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Product by login");
		} finally {
			session.close();
		}

		log.debug("Product Model findbylogin method end");
		return Bean;
	}

	@Override
	public ProductBean findByCategoryAndName(long cId, String name) throws ApplicationException {

		log.debug("Product Model findbylogin method start");
		Session session = null;
		ProductBean Bean = null;
		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ProductBean.class);

			criteria.add(Restrictions.like("categoryId", cId));
			criteria.add(Restrictions.like("name", name));

			List list = criteria.list();

			if (list.size() > 0) {
				Bean = (ProductBean) list.get(0);
			} else {
				Bean = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in getting Product by login");
		} finally {
			session.close();
		}

		log.debug("Product Model findbylogin method end");
		return Bean;
	}

	public long nextProductCode() throws ApplicationException {

		log.debug("User Model findbylogin method start");
		Session session = null;
		ProductBean bean = null;
		try {
			session = HibDataSource.getSession();

			DetachedCriteria maxId = DetachedCriteria.forClass(ProductBean.class)
					.setProjection(Projections.max("productCode"));

			Criteria criteria = session.createCriteria(ProductBean.class);
			criteria.add(Property.forName("productCode").eq(maxId));
			List list = criteria.list();

			if (list.size() > 0) {
				bean = (ProductBean) list.get(0);
			} else {
				bean = new ProductBean();;
			}
		} catch (HibernateException e) {

			throw new ApplicationException("exception in getting user by login");
		} finally {
			session.close();
		}

		// System.out.println(bean.getProductCode());

		log.debug("User Model findbylogin method end");

		
		
		if (bean.getProductCode() > 0) {
			return bean.getProductCode() + 1;
		} else {
			return 100101;
		}
	}

}
