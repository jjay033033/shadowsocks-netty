package priv.lmoon.shadowsupdate.vo;

public class ConfVO {
	
	private String server;
	
	private int server_port;
	
	private String password;
	
	private String method;
	
	private String remarks = "";

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getServer_port() {
		return server_port;
	}

	public void setServer_port(int server_port) {
		this.server_port = server_port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ConfVo [server=" + server + ", server_port=" + server_port + ", password=" + password + ", method="
				+ method + ", remarks=" + remarks + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		result = prime * result + server_port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ConfVO other = (ConfVO) obj;
		if (method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!method.equals(other.method)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (remarks == null) {
			if (other.remarks != null) {
				return false;
			}
		} else if (!remarks.equals(other.remarks)) {
			return false;
		}
		if (server == null) {
			if (other.server != null) {
				return false;
			}
		} else if (!server.equals(other.server)) {
			return false;
		}
		if (server_port != other.server_port) {
			return false;
		}
		return true;
	}
	
	
	
}
