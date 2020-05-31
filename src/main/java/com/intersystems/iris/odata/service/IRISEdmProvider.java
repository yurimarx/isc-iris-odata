package com.intersystems.iris.odata.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

import com.intersystems.iris.odata.model.SQLElement;
import com.intersystems.iris.odata.util.CatalogUtil;


public class IRISEdmProvider extends CsdlAbstractEdmProvider {

	public static final String NAMESPACE = "Contest_Data";
	
	public static final String CONTAINER_NAME = "Contest_Data";
	
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	
	private FullQualifiedName getEdmType(String JDBCType) {

		if (JDBCType.equals("integer") || JDBCType.equals("bigint") || JDBCType.equals("bigint")) {
			return EdmPrimitiveTypeKind.Int32.getFullQualifiedName();
		} else if (JDBCType.equals("char") || JDBCType.equals("varchar")) {
			return EdmPrimitiveTypeKind.String.getFullQualifiedName();
		} else {
			return EdmPrimitiveTypeKind.String.getFullQualifiedName();
		}

	}

	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {

		try {

			ArrayList<CsdlProperty> collums = new ArrayList<CsdlProperty>();

			ArrayList<SQLElement> sqlColumns = CatalogUtil.getSQLColumns(entityTypeName.getName());

			for (SQLElement sqlColumn : sqlColumns) {
				CsdlProperty collumn = new CsdlProperty();
				collumn.setName(sqlColumn.getName());
				collumn.setType(getEdmType(sqlColumn.getType()));
				collums.add(collumn);
			}

			CsdlPropertyRef propertyRef = new CsdlPropertyRef();
			propertyRef.setName("ID");

			CsdlEntityType entityType = new CsdlEntityType();
			entityType.setName(entityTypeName.getName());
			entityType.setProperties(collums);
			entityType.setKey(Collections.singletonList(propertyRef));

			return entityType;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		
		
		CsdlEntitySet entitySet = new CsdlEntitySet();
		entitySet.setName(entitySetName);
		entitySet.setType(new FullQualifiedName(NAMESPACE, entitySetName));
		return entitySet;
	
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
		if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
			CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
			entityContainerInfo.setContainerName(CONTAINER);
			return entityContainerInfo;
		}

		return null;
	}

	@Override
	public List<CsdlSchema> getSchemas() throws ODataException {
		
		List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
		
		CsdlSchema schema = new CsdlSchema();
		schema.setNamespace(NAMESPACE);
		
		ArrayList<SQLElement> sqlTables = CatalogUtil.getSQLTables(CONTAINER_NAME);
		
		List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
		
		for(SQLElement sqlTable:sqlTables) {
			
			FullQualifiedName entityNamespace = new FullQualifiedName(schema.getNamespace(), sqlTable.getName());
			
			entityTypes.add(getEntityType(entityNamespace));
			
		}
		
		schema.setEntityTypes(entityTypes);
		
		schema.setEntityContainer(getEntityContainer());
				
		schemas.add(schema);

		return schemas;

	}

	@Override
	public CsdlEntityContainer getEntityContainer() throws ODataException {
		
		List<SQLElement> sqlTables = CatalogUtil.getSQLTables(CONTAINER_NAME);
		
		List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
		
		for(SQLElement sqlTable:sqlTables) {
			entitySets.add(getEntitySet(CONTAINER, sqlTable.getName()));
		}
		
		CsdlEntityContainer entityContainer = new CsdlEntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}

}
