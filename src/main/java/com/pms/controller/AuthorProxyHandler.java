package com.pms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pms.entity.AuthorProxy;
import com.pms.service.AuthorProxyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class AuthorProxyHandler {
	@Resource(name = "authorProxyServiceImpl")
	private AuthorProxyService authorProxyService;

	@ResponseBody
	@RequestMapping(value = "/author_proxy/show", method = RequestMethod.POST)
	public List<AuthorProxy> show(@RequestParam("paperproxy_id") String paperproxy_id, HttpServletRequest request) {
		List<AuthorProxy> authors = authorProxyService.findAuthorProxyListByPaperProxyId(paperproxy_id);
		return authors;
	}
}
