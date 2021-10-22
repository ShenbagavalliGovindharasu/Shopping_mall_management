package onlinefurnitureshopping.model;

import java.util.List;

import onlinefurnitureshopping.bean.InvoiceBean;
import onlinefurnitureshopping.exception.ApplicationException;
import onlinefurnitureshopping.exception.DatabaseException;
import onlinefurnitureshopping.exception.DuplicateRecordException;





public interface InvoiceModelInt {

	public long add(InvoiceBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException;

	public void delete(InvoiceBean bean) throws ApplicationException;

	public void update(InvoiceBean bean) throws ApplicationException, DuplicateRecordException;

	public InvoiceBean findByPK(long pk) throws ApplicationException;
	
	public InvoiceBean findByOrderId(long  OrderId) throws ApplicationException;

	public InvoiceBean findByName(String name) throws ApplicationException;
	

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(InvoiceBean Bean) throws ApplicationException;

	public List search(InvoiceBean Bean, int pageNo, int pageSize) throws ApplicationException;
	
	public long nextOrderId() throws ApplicationException;

	

}
