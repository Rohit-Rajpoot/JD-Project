package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.PaymentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PaymentModel {

	// ================= NEXT PK =================
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_payment");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return ++pk;
	}

	// ================= ADD =================
	public long add(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		PaymentBean existbean = findByTransactionId(bean.getTransactionId());

		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_payment values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getTransactionId());
			pstmt.setString(3, bean.getPayerName());
			pstmt.setInt(4, bean.getAmount());
			pstmt.setDate(5, new java.sql.Date(bean.getPaymentDate().getTime()));
			pstmt.setString(6, bean.getPaymentStatus());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	// ================= DELETE =================
	public void delete(PaymentBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_payment where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		PaymentBean existBean = findByTransactionId(bean.getTransactionId());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Transaction ID already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_payment set transaction_id=?, payer_name=?, amount=?, "
							+ "payment_date=?, payment_status=?, created_by=?, modified_by=?, "
							+ "created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getTransactionId());
			pstmt.setString(2, bean.getPayerName());
			pstmt.setInt(3, bean.getAmount());
			pstmt.setDate(4, new java.sql.Date(bean.getPaymentDate().getTime()));
			pstmt.setString(5, bean.getPaymentStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception: " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public PaymentBean findByPK(long pk) throws ApplicationException {

		PaymentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_payment where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setTransactionId(rs.getString(2));
				bean.setPayerName(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setPaymentDate(rs.getDate(5));
				bean.setPaymentStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= FIND BY TRANSACTION ID =================
	public PaymentBean findByTransactionId(String transId) throws ApplicationException {

		PaymentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_payment where transaction_id=?");

			pstmt.setString(1, transId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setTransactionId(rs.getString(2));
				bean.setPayerName(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setPaymentDate(rs.getDate(5));
				bean.setPaymentStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment by Transaction ID");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= LIST =================
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_payment");
		ArrayList list = new ArrayList();

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PaymentBean bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setTransactionId(rs.getString(2));
				bean.setPayerName(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setPaymentDate(rs.getDate(5));
				bean.setPaymentStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	// ================= SEARCH =================
	public List search(PaymentBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_payment where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getTransactionId() != null && bean.getTransactionId().trim().length() > 0) {
				sql.append(" and transaction_id like '" + bean.getTransactionId() + "%'");
			}

			if (bean.getPayerName() != null && bean.getPayerName().trim().length() > 0) {
				sql.append(" and payer_name like '" + bean.getPayerName() + "%'");
			}

			if (bean.getAmount() > 0) {
				sql.append(" and amount=" + bean.getAmount());
			}

			if (bean.getPaymentDate() != null) {
				Date d = new java.sql.Date(bean.getPaymentDate().getTime());
				sql.append(" and payment_date='" + d + "'");
			}

			if (bean.getPaymentStatus() != null && bean.getPaymentStatus().trim().length() > 0) {
				sql.append(" and payment_status like '" + bean.getPaymentStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setTransactionId(rs.getString(2));
				bean.setPayerName(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setPaymentDate(rs.getDate(5));
				bean.setPaymentStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}