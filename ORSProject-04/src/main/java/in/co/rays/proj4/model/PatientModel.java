package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.PatientBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;

import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * @author Rohit Rajpoot
 *
 */
public class PatientModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_patient");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			System.out.println("in next pk method");
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting pk ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(PatientBean bean) throws ApplicationException, DuplicateRecordException {

		PatientModel pmodel = new PatientModel();
		PatientBean exiestbean = pmodel.findbylogin(bean.getPatientloginid());
		if (exiestbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_patient values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getDoctorname());
			pstmt.setString(3, bean.getPatientname());
			pstmt.setDate(4, new java.sql.Date(bean.getPatientdateofbirth().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getPatientloginid());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			System.out.println("data added successfully");

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(PatientBean bean) throws ApplicationException, DuplicateRecordException {

		PatientModel pmodel = new PatientModel();

		PatientBean exiestbean = pmodel.findbylogin(bean.getPatientloginid());
		if (exiestbean != null && exiestbean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Login Id already exists");
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_patient SET doctorname=?, patientname=?, patientdateofbirth=?, gender=?, patientloginid=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");
			pstmt.setString(1, bean.getDoctorname());
			pstmt.setString(2, bean.getPatientname());
			pstmt.setDate(3, new java.sql.Date(bean.getPatientdateofbirth().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getPatientloginid());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			System.out.println("dataUpdate susscessfully");

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("exception : Exception in roll back " + ex.getMessage());
			}
			throw new ApplicationException("exception : Exception in update user " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PatientBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_patient where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			System.out.println("data deleted successfully");
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception in rollback " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete patient " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PatientBean findbylogin(String patientloginid) throws ApplicationException {
		Connection conn = null;
		PatientBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_patient where patientloginid = ?");
			pstmt.setString(1, patientloginid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorname(rs.getString(2));
				bean.setPatientname(rs.getString(3));
				bean.setPatientdateofbirth(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setPatientloginid(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException(
					"Exception : Exception in patientappointment by geting loginid " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public PatientBean findbypk(long pk) throws ApplicationException {
		Connection conn = null;
		PatientBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_patient where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorname(rs.getString(2));
				bean.setPatientname(rs.getString(3));
				bean.setPatientdateofbirth(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setPatientloginid(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException(
					"Exception : Exception in patientappointment by geting loginid " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<PatientBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<PatientBean> search(PatientBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		ArrayList<PatientBean> list = new ArrayList<PatientBean>();
		StringBuffer sql = new StringBuffer("select * from st_patient where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getPatientname() != null && bean.getPatientname().length() > 0) {
				sql.append(" and patientname like '" + bean.getPatientname() + "%'");
			}
			if (bean.getDoctorname() != null && bean.getDoctorname().length() > 0) {
				sql.append(" and doctorname like '" + bean.getDoctorname() + "%'");
			}
			if (bean.getPatientloginid() != null && bean.getPatientloginid().length() > 0) {
				sql.append(" and login like '" + bean.getPatientloginid() + "%'");
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender like '" + bean.getGender() + "%'");
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
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setDoctorname(rs.getString(2));
				bean.setPatientname(rs.getString(3));
				bean.setPatientdateofbirth(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setPatientloginid(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search user " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}