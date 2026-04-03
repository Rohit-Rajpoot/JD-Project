package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CertificateBean;
import in.co.rays.proj4.bean.PriorityBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CertificateModel {
	
	public Long nextPk() throws DatabaseException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_code");
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

	public long add(CertificateBean bean) throws DuplicateRecordException, ApplicationException {

		Connection conn = null;
		long pk = 0;

		CertificateBean existBean = findByCertificate(bean.getCertificateCode());

		if (existBean != null) {
			throw new DuplicateRecordException("Entered Code Already Exist..!!");
		}

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_code values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getCertificateCode());
			pstmt.setString(3, bean.getStudentName());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setDate(5,new java.sql.Date(bean.getIssueDate().getTime()));			
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

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

	public void update(CertificateBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CertificateBean existBean = findByCertificate(bean.getCertificateCode());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("Email Already Exist..!!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_code set certificate_st_code = ?, student_name = ?, course_name = ?, issue_date = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getCertificateCode());
			pstmt.setString(2, bean.getStudentName());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setDate(4,new java.sql.Date(bean.getIssueDate().getTime()));			
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			
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

	public void delete(CertificateBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_code where id = ?");

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

	public CertificateBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		CertificateBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_code where id = ?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CertificateBean();
				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	

	public CertificateBean findByCertificate(String certificatest_code) throws ApplicationException {

		Connection conn = null;
		CertificateBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_code where certificate_st_code = ?");

			pstmt.setString(1, certificatest_code);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CertificateBean();
				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	
	public CertificateBean findByStudent(String studentName) throws ApplicationException {

		Connection conn = null;
		CertificateBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_code where student_name = ?");

			pstmt.setString(1, studentName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CertificateBean();
				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	
	public CertificateBean findByCourse(String courseName) throws ApplicationException {

		Connection conn = null;
		CertificateBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_code where course_name = ?");

			pstmt.setString(1, courseName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CertificateBean();
				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
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

	public List search(CertificateBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_code where 1=1");

		if (bean != null) {
			if (bean.getCertificateCode() != null && bean.getCertificateCode().length() > 0) {
				sql.append(" and certificate_code like '" + bean.getCertificateCode() + "%'");
			}
		
		}
		
		if (bean != null) {
			if (bean.getStudentName() != null && bean.getStudentName().length() > 0) {
				sql.append(" and student_name like '" + bean.getStudentName() + "%'");
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
				bean = new CertificateBean();
				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				
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
