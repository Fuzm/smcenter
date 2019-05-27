package com.irdstudio.ssm.framework.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdstudio.ssm.admin.service.impl.E4AServiceImpl;
import com.irdstudio.ssm.framework.vo.UserInfo;

public class SessionManager {

	private static final Logger logger = LoggerFactory.getLogger(E4AServiceImpl.class);

	private String name;
	private Map sessions = new HashMap();
	private Timer timer;
	private int sessionTimeOut = 900000;
	private int sessionCheckInterval = 60000;
	private String access;
	private String alias;

	public void removeSession(HttpSession session) {
		synchronized (this.sessions) {
			this.sessions.remove(session.getId());
		}
		
		session.removeAttribute(UserInfo.SEESION_USER_KEY);
		//session.invalidate();
	}

	public void removeSession(String sessionId) {
		removeSession((HttpSession) this.sessions.get(sessionId));
	}

	public HttpSession getSession(HttpServletRequest requestObj, boolean createWhenNotCreate) {
		HttpServletRequest request = (HttpServletRequest) requestObj;
		HttpSession session = request.getSession(createWhenNotCreate);
		if (session != null) {
			if (createWhenNotCreate) {
				logger.debug("Create new Session:" + session.getId() + " Total session size: " + (this.sessions.size() + 1));
				synchronized (this.sessions) {
					this.sessions.put(session.getId(), session);
				}
			}
			return session;
		}
		return null;
	}

	public void setLoginInfo(UserInfo userInfo, HttpServletRequest requestObj) {
		HttpSession session = this.getSession(requestObj, true);
		session.setAttribute(UserInfo.SEESION_USER_KEY, userInfo);
	}

	public UserInfo getLoginInfo(String sessionId) {
		HttpSession session = (HttpSession) this.sessions.get(sessionId);
		if (session != null) {
			return (UserInfo) session.getAttribute(UserInfo.SEESION_USER_KEY);
		} else {
			return null;
		}
	}

	public void initialize() {
		this.timer = new Timer();
		this.timer.schedule(new SessionCheckTask(), this.sessionTimeOut, this.sessionCheckInterval);
	}

	private class SessionCheckTask extends TimerTask {
		private SessionCheckTask() {
		}

		public void run() {
			Object[] keys = (Object[]) null;
			try {
				keys = SessionManager.this.sessions.keySet().toArray();
			} catch (Exception e) {
				logger.error("Failed to do session time out check", e);
				return;
			}
			logger.info("Current Session Count: " + keys.length);
			for (int i = 0; i < keys.length; i++) {
				try {
					HttpSession session = (HttpSession) SessionManager.this.sessions.get(keys[i]);
					if (session != null) {
						logger.info("Session Check: " + session.getId());
						if (System.currentTimeMillis()
								- session.getLastAccessedTime() > SessionManager.this.sessionTimeOut) {
							logger.info("Session time out: " + session.getId() + " in [" + SessionManager.this.name + "]");

							synchronized (SessionManager.this.sessions) {
								SessionManager.this.sessions.remove(session.getId());
							}
						}
					}
				} catch (Exception e) {
					logger.error("Failed to do session time out check");
				}
			}
		}
	}

	public String encodeURL(HttpServletRequest request, HttpServletResponse response, String url, String method) {
		return response.encodeURL(url);
	}

	public int getSessionCheckInterval() {
		return this.sessionCheckInterval;
	}

	public void setSessionCheckInterval(int sessionCheckInterval) {
		this.sessionCheckInterval = sessionCheckInterval;
	}

	public int getSessionTimeOut() {
		return this.sessionTimeOut;
	}

	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppendPostField(HttpServletRequest request, String targetClient) {
		return "";
	}

	public void setId(String value) {
		setName(value);
	}

	public int getSessionCount() {
		return this.sessions.size();
	}

	public Map getSessions() {
		return this.sessions;
	}

	public void setSessions(Map sessions) {
		this.sessions = sessions;
	}

	public void terminate() {
		if (this.timer == null) {
			return;
		}
		logger.info("Terminate the SessionManager [" + this.name + "]");
		try {
			this.timer.cancel();
			this.timer = null;
			this.sessions = new HashMap();
		} catch (Exception e) {
			logger.error("failed to Terminate the SessionManager [" + this.name + "]", e);
		}
	}

	public void setAlias(String value) {
		this.alias = value;
	}

	public String getAlias() {
		return this.alias;
	}

	public boolean isSingleton() {
		return true;
	}

	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
}
