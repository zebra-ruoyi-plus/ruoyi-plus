package com.zebra.api.provider.server.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zebra
 *
 */
@RestController
public class BaseServer {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpSession session;
}
