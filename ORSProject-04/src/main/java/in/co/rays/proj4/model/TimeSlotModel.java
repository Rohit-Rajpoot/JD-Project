package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TimeSlotBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TimeSlotModel {
	
	public Long nextPk() throws DatabaseException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_slot");
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

	public long add(TimeSlotBean bean) throws DuplicateRecordException, ApplicationException {

		Connection conn = null;
		long pk = 0;

		TimeSlotBean existBean = findBySlotCode(bean.getSlotCode());

		if (existBean != null) {
			throw new DuplicateRecordException("Slot Code Already Exist..!!");
		}

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_slot values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSlotCode());
			pstmt.setDate(3,new java.sql.Date(bean.getStartTime().getTime()));			
			pstmt.setDate(4,new java.sql.Date(bean.getEndTime().getTime()));
			pstmt.setString(5, bean.getSlotStatus());
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

	public void update(TimeSlotBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TimeSlotBean existBean = findBySlotCode(bean.getSlotCode());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("Slot Code Already Exist..!!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_slot set slot_code = ?, start_time = ?, end_time = ?, slot_status = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getSlotCode());
			pstmt.setDate(2,new java.sql.Date(bean.getStartTime().getTime()));			
			pstmt.setDate(3,new java.sql.Date(bean.getEndTime().getTime()));
			pstmt.setString(4, bean.getSlotStatus());
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

	public void delete(TimeSlotBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_slot where id = ?");

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

	public TimeSlotBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		TimeSlotBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_slot where id = ?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TimeSlotBean();
				bean.setId(rs.getLong(1));
				bean.setSlotCode(rs.getString(2));
				bean.setStartTime(rs.getDate(3));
				bean.setEndTime(rs.getDate(4));
				bean.setSlotStatus(rs.getString(5));
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
	

	public TimeSlotBean findBySlotCode(String slotCode) throws ApplicationException {

		Connection conn = null;
		TimeSlotBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_slot where slot_code = ?");

			pstmt.setString(1, slotCode);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new TimeSlotBean();
				bean.setId(rs.getLong(1));
				bean.setSlotCode(rs.getString(2));
				bean.setStartTime(rs.getDate(3));
				bean.setEndTime(rs.getDate(4));
				bean.setSlotStatus(rs.getString(5));
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
	

	public List search(TimeSlotBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_slot where 1=1");

		if (bean != null) {
			if (bean.getSlotCode() != null && bean.getSlotCode().length() > 0) {
				sql.append(" and slot_code like '" + bean.getSlotCode() + "%'");
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
				bean = new TimeSlotBean();
				bean.setId(rs.getLong(1));
				bean.setSlotCode(rs.getString(2));
				bean.setStartTime(rs.getDate(3));
				bean.setEndTime(rs.getDate(4));
				bean.setSlotStatus(rs.getString(5));
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
