package com.special.BuidingSite;

public interface LoginListener {
	public void onLoginSuccess();
	public void onLoginFailed();
	public void onLoginError(String msg);
}
