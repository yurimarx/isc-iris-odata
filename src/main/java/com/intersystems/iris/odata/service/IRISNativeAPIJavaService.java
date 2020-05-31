package com.intersystems.iris.odata.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import com.intersystems.iris.odata.model.SQLElement;
import com.intersystems.iris.odata.util.CatalogUtil;
import com.intersystems.iris.odata.util.JDBCUtil;
import com.intersystems.iris.odata.util.ODataUtil;
import com.intersystems.jdbc.IRIS;
import com.intersystems.jdbc.IRISConnection;
import com.intersystems.jdbc.IRISObject;

public class IRISNativeAPIJavaService {

	public static Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams)
			throws ODataApplicationException {

		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		EntityCollection entitySet = getData(edmEntitySet);

		Entity requestedEntity = ODataUtil.findEntity(edmEntityType, entitySet, keyParams);

		if (requestedEntity == null) {
			throw new ODataApplicationException("Entity for requested key doesn't exist",
					HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ENGLISH);
		}

		return requestedEntity;

	}

	private static EntityCollection getData(EdmEntitySet edmEntitySet) {

		ArrayList<SQLElement> columns = CatalogUtil.getSQLColumns(edmEntitySet.getName());

		EntityCollection collection = new EntityCollection();
		List<Entity> entityList = collection.getEntities();

		Connection conn = JDBCUtil.getInstance().connection;

		String sql = "SELECT * FROM " + edmEntitySet.getEntityContainer().getName() + "." + edmEntitySet.getName();

		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();

			while (result.next()) {

				Entity e = new Entity();

				for (SQLElement sqlColumn : columns) {
					Property property = new Property();
					property.setName(sqlColumn.getName());
					property.setValue(ValueType.PRIMITIVE, result.getObject(sqlColumn.getName()));
					e.addProperty(property);
				}

				e.setId(createId(edmEntitySet.getName(), result.getInt("ID")));
				entityList.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return collection;
		} finally {

			try {
				if (statement != null) {
					statement.close();
				}

				if (result != null) {
					result.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return collection;

	}

	private static URI createId(String entitySetName, Object id) {
		try {
			return new URI(entitySetName + "(" + String.valueOf(id) + ")");
		} catch (URISyntaxException e) {
			throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
		}
	}

	public static Entity createEntityData(EdmEntitySet edmEntitySet, Entity entityToCreate) {

		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		return createEntity(edmEntityType, entityToCreate);

	}

	private static Entity createEntity(EdmEntityType edmEntityType, Entity entity) {

		IRISConnection conn = (IRISConnection) JDBCUtil.getInstance().connection;
		
		String classPackage = edmEntityType.getNamespace().replaceAll("_", ".");
		
		String irisClass = classPackage + "." + edmEntityType.getName();
		
		IRIS iris;
		
		try {
			iris = IRIS.createIRIS(conn);
			
			IRISObject instance = (IRISObject) iris.classMethodObject(irisClass, "%New");

			for (Property property : entity.getProperties()) {
				instance.set(property.getName(), property.getValue());
			}

			instance.invokeObject("%Save");

		} catch (SQLException e) {
			e.printStackTrace();
			return entity;
		}
		
		
		return entity;

	}

	public static void deleteEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams)
			throws ODataApplicationException {

		EdmEntityType edmEntityType = edmEntitySet.getEntityType();

		deleteEntity(edmEntityType, keyParams);

	}

	private static void deleteEntity(EdmEntityType edmEntityType, List<UriParameter> keyParams)
			throws ODataApplicationException {

		Connection conn = JDBCUtil.getInstance().connection;

		try {

			conn.createStatement().execute("DELETE FROM " + edmEntityType.getNamespace() + "." + edmEntityType.getName()
					+ " WHERE ID = " + keyParams.get(0).getText());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
