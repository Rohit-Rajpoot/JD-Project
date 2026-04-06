package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ArtBean;
import in.co.rays.proj4.bean.PrescriptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PrescriptionModel {
//NextPK****-----------------
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_prescription");
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

		return pk + 1;
	}
//ADD***************--------------------
	public long add(PrescriptionBean bean) throws ApplicationException, DuplicateRecordException, SQLException{

		/*
		 * ArtBean duplicate = findByName(bean.getName());
		 * 
		 * if (duplicate != null) { throw new
		 * DuplicateRecordException("already exists"); }
		 */
		Connection conn = null;
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_prescription values(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getName());
			
		
			pstmt.setString(4, bean.getMedicines());
			pstmt.setString(5, bean.getStatus());
		
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			System.out.println(i + "inserted");
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {
			conn.rollback();
			throw new ApplicationException("Exception : Exceptionin add Role");
		
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}
	//Delete*****************-----------------------------
	public void delete(PrescriptionBean bean) throws SQLException, ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_prescription where id=?");
			pstmt.setLong(1, bean.getId());
			
		int i=pstmt.executeUpdate();
		System.out.println( i +"row affected");
		conn.commit();
	
		pstmt.close();
		}
		
		catch(Exception e) {
			try{conn.rollback();
			}
			
			catch(Exception ex) {
		throw new ApplicationException("Exception : Delete rollback");

			}
			 throw new ApplicationException("Exception : Exception in delete Role");
			
			
		}
		 finally {
				JDBCDataSource.closeConnection(conn);
			}
	}
	
	//update*****************--------------------------------
	
	public void update(PrescriptionBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		Connection conn=null;
		
		PrescriptionBean duplicate = findByName(bean.getName()); // Check if updated Role
		  if (duplicate != null && duplicate.getId() !=bean.getId()) { 
			  throw new DuplicateRecordException("Name already exists");
			  }
	
		 
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_prescription set code=? , name=?, medicines=?,status=?  ,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getMedicines());
	
			pstmt.setString(4, bean.getStatus());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			
			int i=pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
			conn.rollback();
			}
			catch(Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
				}
			
			throw new ApplicationException("Exception in updating Role ");
			}
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	//find by pk***************************---------------------------------
	public PrescriptionBean findByPk(long pk) throws ApplicationException {
	
		PrescriptionBean bean=null;
		Connection conn=null;
		try {
	conn=JDBCDataSource.getConnection();
	PreparedStatement pstmt=conn.prepareStatement("select * from st_prescription where id=?");
	pstmt.setLong(1, pk);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		bean = new PrescriptionBean();
		bean.setId(rs.getLong(1));
		bean.setCode(rs.getString(2));
		bean.setName(rs.getString(3));
	
		bean.setMedicines(rs.getString(4));
		bean.setStatus(rs.getString(5));
		bean.setCreatedBy(rs.getString(6));
		bean.setModifiedBy(rs.getString(7));
		bean.setCreatedDatetime(rs.getTimestamp(8));
		bean.setModifiedDatetime(rs.getTimestamp(9));

	}
		}
		catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
	return bean;
	}
	
	//find by name***************************---------------------------------
	public PrescriptionBean findByName(String name) throws ApplicationException {
		StringBuffer sql=new StringBuffer("select * from st_prescription where name=?");
		PrescriptionBean bean=null;
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setName(rs.getString(3));
			
				bean.setMedicines(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				
			}
		}
			catch(Exception e) {
				throw new ApplicationException("Exception : Exception in geting User by emailId");
				
			}
			finally {
				JDBCDataSource.closeConnection(conn);
			}
			return bean;
		
		
		
	}
	//find by filter without pagination ***************-----------------------------------
	public List search(PrescriptionBean bean) {
		
		return search(bean);
	}
	
	
	
	//search by filter************** with pagination---------------------
	public List search(PrescriptionBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_prescription where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getCode() != null && bean.getCode().length() > 0) {
				sql.append(" AND code like '" + bean.getCode() + "%'");
			}
		
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			 

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setName(rs.getString(3));
			
				bean.setMedicines(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
	 throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	
//list all data search
	

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_prescription");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PrescriptionBean bean = new PrescriptionBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setName(rs.getString(3));
			
				bean.setMedicines(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception Geting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		return list;
	}
	
}