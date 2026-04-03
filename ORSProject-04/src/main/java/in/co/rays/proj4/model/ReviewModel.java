package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ReviewBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ReviewModel {
	
	public Long nextPk() throws DatabaseException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_review");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(ReviewBean bean) throws DuplicateRecordException, ApplicationException {

		Connection conn = null;
		long pk = 0;

		ReviewBean existBean = findByName(bean.getReviewerName());

		if (existBean != null) {
			throw new DuplicateRecordException("Name already exist..!!");
		}

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_review values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getReviewerName());
			pstmt.setInt(3, bean.getRating());
			pstmt.setString(4, bean.getComment());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

			 int i = pstmt.executeUpdate();
			conn.commit();
			System.out.println(" Data Added Successfully : " + i );

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(ReviewBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ReviewBean existBean = findByName(bean.getReviewerName());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("Name Already Exist..!!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_review set reviewer_name = ?, rating = ?, comment = ?, created_by = ?, modified_by = ? created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getReviewerName());
			pstmt.setInt(2, bean.getRating());
			pstmt.setString(3, bean.getComment());	
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(75, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			
    		int i = pstmt.executeUpdate();
			conn.commit();
			System.out.println("Data Updated => " + i);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(ReviewBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_review where id = ?");

			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();

			System.out.println("Data Deleted => " + i);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ReviewBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		ReviewBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_review where id = ?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new ReviewBean();
				bean.setId(rs.getLong(1));
				bean.setReviewerName(rs.getString(2));
				bean.setRating(rs.getInt(3));
				bean.setComment(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	

	public ReviewBean findByName(String reviewerName) throws ApplicationException {

		Connection conn = null;
		ReviewBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_review where reviewer_name = ?");

			pstmt.setString(1, reviewerName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new ReviewBean();
				bean.setId(rs.getLong(1));
				bean.setReviewerName(rs.getString(2));
				bean.setRating(rs.getInt(3));
				bean.setComment(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List search(ReviewBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_review where 1=1");

		if (bean != null) {
			if (bean.getReviewerName() != null && bean.getReviewerName().length() > 0) {
				sql.append(" and reviewer_name like '" + bean.getReviewerName() + "%'");
			}
		
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		System.out.println("sql ==>> " + sql.toString());

		Connection conn = null;
		List list = new ArrayList();

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new ReviewBean();
				bean.setId(rs.getLong(1));
				bean.setReviewerName(rs.getString(2));
				bean.setRating(rs.getInt(3));
				bean.setComment(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search user " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
