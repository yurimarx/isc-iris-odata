package com.intersystems.iris.odata.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intersystems.iris.odata.service.IRISEdmProvider;
import com.intersystems.iris.odata.service.IRISEntityCollectionProcessor;
import com.intersystems.iris.odata.service.IRISEntityProcessor;

public class IRISODataServlet extends HttpServlet {

	  private static final long serialVersionUID = 1L;
	  private static final Logger LOG = LoggerFactory.getLogger(IRISODataServlet.class);

	  protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	    try {
	      OData odata = OData.newInstance();
	      ServiceMetadata edm = odata.createServiceMetadata(new IRISEdmProvider(), new ArrayList<EdmxReference>());
	      ODataHttpHandler handler = odata.createHandler(edm);
	      handler.register(new IRISEntityCollectionProcessor());
	      handler.register(new IRISEntityProcessor());

	      handler.process(req, resp);
	    } catch (RuntimeException e) {
	      LOG.error("Server Error occurred in IRISODataServlet", e);
	      throw new ServletException(e);
	    }
	  }
	}