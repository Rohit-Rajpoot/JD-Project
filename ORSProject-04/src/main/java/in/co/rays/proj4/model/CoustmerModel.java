package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.CJCommunicationsException;

import in.co.rays.proj4.bean.CoustmerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseDownException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CoustmerModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_coustmer");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	// ================= ADD =================
	public long add(CoustmerBean bean) throws ApplicationException {
		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_coustmer values(?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getProduct());
			pstmt.setString(4, bean.getAddress());
			pstmt.setString(5, bean.getMobileno());
			pstmt.setString(6, bean.getRate());
			pstmt.setString(7, bean.getPaymentstatus());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Coustmer " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	// ================= UPDATE =================
	public void update(CoustmerBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_coustmer set name=?, product=?, address=?, mobileno=?, rate=?, paymentstatus=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getProduct());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getMobileno());
			pstmt.setString(5, bean.getRate());
			pstmt.setString(6, bean.getPaymentstatus());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in update Coustmer " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================
	public void delete(CoustmerBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_coustmer where id=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete Coustmer " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public CoustmerBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		CoustmerBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_coustmer where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CoustmerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setProduct(rs.getString(3));
				bean.setAddress(rs.getString(4));
				bean.setMobileno(rs.getString(5));
				bean.setRate(rs.getString(6));
				bean.setPaymentstatus(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			throw new ApplicationException("Exception in findByPk " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= LIST =================
	public List<CoustmerBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================
	public List<CoustmerBean> search(CoustmerBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<CoustmerBean> list = new ArrayList<CoustmerBean>();
		StringBuffer sql = new StringBuffer("select * from st_coustmer where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" and product like '" + bean.getProduct() + "%'");
			}
			if (bean.getPaymentstatus() != null && bean.getPaymentstatus().length() > 0) {
				sql.append(" and paymentstatus like '" + bean.getPaymentstatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CoustmerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setProduct(rs.getString(3));
				bean.setAddress(rs.getString(4));
				bean.setMobileno(rs.getString(5));
				bean.setRate(rs.getString(6));
				bean.setPaymentstatus(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (CJCommunicationsException e) {
        	e.printStackTrace();
        	throw new DatabaseDownException("Database Server Down!!!");
		}catch (Exception e) {
			throw new ApplicationException("Exception in search Coustmer " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
