package org.effortless.orm.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.effortless.core.UnusualException;

public class DbStatement extends Object {

	public DbStatement () {
		super();
		initiate();
	}

	public DbStatement (Connection conn, String query, Object owner, ColumnEncoder[] encoders) {
		this();
		this.batchSize = 200;
		this.conn = conn;

		this.owner = owner;
		this.encoders = encoders;
//		this.types = types;
		_buildStm(query);
	}
	
	protected void _throwException (Exception e) {
		throw new UnusualException(e);
	}
	
	protected void initiate () {
		this.reset();
	}
	
	protected void _buildStm (String query) {
		_doBatch(false);
		if (false || (query != null && query.indexOf("?") > -1)) {
			if (true || this._ps == null) {
				try {
					this._ps = this.conn.prepareStatement(query);
				} catch (SQLException e) {
					_throwException(e);
				}
			}
			else {
				try {
					this._ps.addBatch(query);
				} catch (SQLException e) {
					_throwException(e);
				}
				this._numInpPs++;
			}
		}
		else {
			if (this._stm == null) {
				try {
					this._stm = this.conn.createStatement();
				} catch (SQLException e) {
					_throwException(e);
				}
			}
			try {
				this._stm.addBatch(query);
			} catch (SQLException e) {
				_throwException(e);
			}
			this._numInpStm++;
		}
	}
	
	protected Connection conn;
	
//	protected int[] types;
	protected Object owner;
	protected ColumnEncoder[] encoders;
	
	protected Statement _stm;
	protected int _numOutStm;
	protected int _numInpStm;

	protected PreparedStatement _ps;
	protected int _numOutPs;
	protected int _numInpPs;

	protected void reset () {
//		this.types = null;
		this.owner = null;
		this.encoders = null;
		
		this._stm = null;
		this._numOutStm = 0;
		this._numInpStm = 0;

		this._ps = null;
		this._numOutPs = 0;
		this._numInpPs = 0;
	}
	
	
	
	protected int batchSize;
	
	public int getBatchSize () {
		return this.batchSize;
	}
	
	public void setBatchSize (int newValue) {
		this.batchSize = newValue;
	}
	
	public DbStatement end () {
		_doBatch(true);
		_close();
		return this;
	}

	public DbStatement add (String sql) {
		_doBatch(false);
		_buildStm(sql);
		return this;
	}
	
	public DbStatement add (String sql, ColumnEncoder[] encoders) {
		_doBatch(false);
		this.encoders = encoders;
//		this.types = types;
		_buildStm(sql);
		return this;
	}
	
	protected void _doBatch (boolean force) {
		int numInp = (this._numInpStm + this._numInpPs);
		
		if ((numInp >= this.batchSize && this._numInpStm > 0) || (force && this._numInpStm > 0)) {
			int[] res = null;
			try {
				res = this._stm.executeBatch();
			} catch (SQLException e) {
				_throwException(e);
			}
			this._numOutStm += (res != null ? res.length : 0);
			this._numInpStm = 0;
		}

		if ((numInp >= this.batchSize && this._numInpPs > 0) || (force && this._numInpPs > 0)) {
			int[] res = null;
			try {
				res = this._ps.executeBatch();
			} catch (SQLException e) {
				_throwException(e);
			}
			this._numOutPs += (res != null ? res.length : 0);
			this._numInpPs = 0;
		}
	}

	public DbStatement apply (Object[] params) {
		_doBatch(false);
		int idx = 1;
		int paramsLength = (params != null ? params.length : 0);
		for (int i = 0; i < paramsLength; i++) {
			this.encoders[i].encode(this.owner, this._ps, idx++, params[i]);
//			this._ps.setObject(idx++, params[i], this.types[i]);
		}
		this._numInpPs++;
		try {
			this._ps.addBatch();
		} catch (SQLException e) {
			_throwException(e);
		}
//		this.stm.executeUpdate();
		return this;
	}
	
	protected void _close () {
		_close(this._stm);
		this._stm = null;
		_close(this._ps);
		this._ps = null;
		this.reset();
	}
	
	protected void _close (Statement stm) {
		boolean notClosed = false;
		try {
			notClosed = (stm != null && !stm.isClosed());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (notClosed) {
			try {
				stm.close();
			} catch (SQLException e) {
				_throwException(e);
			}
			stm = null;
		}
	}
	
	protected void _close (ResultSet rs) {
		boolean notClosed = false;
		try {
			notClosed = (rs != null && !rs.isClosed());
		} catch (SQLException e) {
			_throwException(e);
		}
		if (notClosed) {
			try {
				rs.close();
			} catch (SQLException e) {
				_throwException(e);
			}
			rs = null;
		}
	}
	
	protected void _closeAndStm (ResultSet rs) {
		Statement stm = null;
		try {
			stm = (rs != null ? rs.getStatement() : null);
		} catch (SQLException e) {
			_throwException(e);
		}
		_close(rs);
		_close(stm);
	}
	
}
