package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BudgetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BudgetModel {
//NextPK****-----------------
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_budget");
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
	public long add(BudgetBean bean) throws ApplicationException, DuplicateRecordException, SQLException{

		BudgetBean duplicate = findByAmount(bean.getAmount());

		if (duplicate != null) {
			throw new DuplicateRecordException("already exists");
		}
		Connection conn = null;
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_budget values(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setInt(2, bean.getAmount());
			pstmt.setInt(3, bean.getSpentAmount());
			pstmt.setString(4,bean.getDepartment());
		
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

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
	public void delete(BudgetBean bean) throws SQLException, ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_budget where id=?");
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
	
	public void update(BudgetBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		Connection conn=null;
		BudgetBean duplicate = findByAmount(bean.getAmount());
		  if (duplicate != null && duplicate.getId() !=bean.getId()) { 
			  throw new DuplicateRecordException("Role already exists");
			  }
		 
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_budget set amount=? , spent_amount=?, department=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setInt(1, bean.getAmount());
			pstmt.setInt(2, bean.getSpentAmount());
			pstmt.setString(3,bean.getDepartment());
			
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			
			int i=pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
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
	public BudgetBean findByPk(long pk) throws ApplicationException {
	
		BudgetBean bean=null;
		Connection conn=null;
		try {
	conn=JDBCDataSource.getConnection();
	PreparedStatement pstmt=conn.prepareStatement("select * from st_budget where id=?");
	pstmt.setLong(1, pk);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		bean = new BudgetBean();
		bean.setId(rs.getLong(1));
		bean.setAmount(rs.getInt(2));
		bean.setSpentAmount(rs.getInt(3));
		bean.setDepartment(rs.getString(4));
		
		bean.setCreatedBy(rs.getString(5));
		bean.setModifiedBy(rs.getString(6));
		bean.setCreatedDatetime(rs.getTimestamp(7));
		bean.setModifiedDatetime(rs.getTimestamp(8));

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
	public BudgetBean findByAmount(int amount) throws ApplicationException {
		StringBuffer sql=new StringBuffer("select * from st_budget where amount=?");
		BudgetBean bean=null;
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setInt(1, amount);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new BudgetBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setAmount(rs.getInt(2));
				bean.setSpentAmount(rs.getInt(3));
				bean.setDepartment(rs.getString(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
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
	public List search(BudgetBean bean) {
		
		return search(bean);
	}
	
	
	
	//search by filter************** with pagination---------------------
	public List search(BudgetBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_budget where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			
			if (bean.getAmount() > 0) {
				sql.append(" AND amount= " + bean.getAmount());
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
				bean=new BudgetBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setAmount(rs.getInt(2));
				bean.setSpentAmount(rs.getInt(3));
				bean.setDepartment(rs.getString(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
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
		StringBuffer sql = new StringBuffer("select * from st_budget");

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
				BudgetBean bean = new BudgetBean();
				bean.setId(rs.getLong(1));
				bean.setId(rs.getLong(1));
				bean.setAmount(rs.getInt(2));
				bean.setSpentAmount(rs.getInt(3));
				bean.setDepartment(rs.getString(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
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