package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.PaymentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PaymentModel {

	private static Logger log = Logger.getLogger(PaymentModel.class);

	/* ================= NEXT PK ================= */
	public Integer nextPk() throws DatabaseException {

		log.debug("PaymentModel nextPk started");

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_payment");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel nextPk ended");
		return pk + 1;
	}

	/* ================= ADD ================= */
	public long add(PaymentBean bean) throws ApplicationException {

		log.debug("PaymentModel add started");

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_payment values(?, ?, ?, ?, ?)");
			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getBookingId());
			pstmt.setLong(3, bean.getAmount());
			pstmt.setString(4, bean.getPaymentMode());
			pstmt.setString(5, bean.getPaymentStatus());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel add ended");
		return pk;
	}

	/* ================= UPDATE ================= */
	public void update(PaymentBean bean) throws ApplicationException {

		log.debug("PaymentModel update started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_payment set paymentid=?, bookingid=?, amount=?, paymentmode=?, paymentstatus=?, where id=?");

			pstmt.setLong(1, bean.getBookingId());
			pstmt.setDouble(2, bean.getAmount());
			pstmt.setString(3, bean.getPaymentMode());
			pstmt.setString(4, bean.getPaymentStatus());
			pstmt.setLong(5, bean.getPaymentId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel update ended");
	}

	/* ================= DELETE ================= */
	public void delete(PaymentBean bean) throws ApplicationException {

		log.debug("PaymentModel delete started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"delete from st_payment where id=?");
			pstmt.setLong(1, bean.getPaymentId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel delete ended");
	}

	/* ================= FIND BY PK ================= */
	public PaymentBean findByPk(long pk) throws ApplicationException {

		log.debug("PaymentModel findByPk started");

		Connection conn = null;
		PaymentBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"select * from st_payment where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PaymentBean();
				bean.setPaymentId(rs.getLong(1));
				bean.setBookingId(rs.getLong(2));
				bean.setAmount(rs.getLong(3));
				bean.setPaymentMode(rs.getString(4));
				bean.setPaymentStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Payment by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel findByPk ended");
		return bean;
	}

	/* ================= SEARCH ================= */
	public List<PaymentBean> search(PaymentBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		log.debug("PaymentModel search started");

		Connection conn = null;
		List<PaymentBean> list = new ArrayList<PaymentBean>();

		StringBuffer sql = new StringBuffer("select * from st_payment where 1=1");

		if (bean != null) {
			if (bean.getBookingId() > 0) {
				sql.append(" and bookingid=" + bean.getBookingId());
			}
			if (bean.getPaymentMode() != null && bean.getPaymentMode().length() > 0) {
				sql.append(" and paymentmode like '" + bean.getPaymentMode() + "%'");
			}
			if (bean.getPaymentStatus() != null && bean.getPaymentStatus().length() > 0) {
				sql.append(" and paymentstatus like '" + bean.getPaymentStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PaymentBean();
				bean.setPaymentId(rs.getLong(1));
				bean.setBookingId(rs.getLong(2));
				bean.setAmount(rs.getLong(3));
				bean.setPaymentMode(rs.getString(4));
				bean.setPaymentStatus(rs.getString(5));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("PaymentModel search ended");
		return list;
	}
}
