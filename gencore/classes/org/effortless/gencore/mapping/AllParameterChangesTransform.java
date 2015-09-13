package org.effortless.gencore.mapping;

import java.util.List;

import org.effortless.core.Collections;
import org.effortless.jast.Expression;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public class AllParameterChangesTransform extends AbstractBaseTransform {

	public AllParameterChangesTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		add_getAllParameterChanges(clazz);
	}

	/*

	protected Object[] _getAllParameterChanges() {
		return _concatAllParameterChanges(new Object[] {Collections.asList("NOMBRE", "CCOMMENT", "PROVINCIA_ID"), Collections.asList(this.nombre, this.comentario, this.provincia)}, super._getAllParameterChanges());
	}

	 */
	protected void add_getAllParameterChanges (GClass cg) {
		List fields = cg.getProperties();
		if (fields != null) {
//			ListExpression columns = new ListExpression();
//			ListExpression values = new ListExpression();
			
			List<Expression> columns = new java.util.ArrayList<Expression>();
			List<Expression> values = new java.util.ArrayList<Expression>();
			int fieldsSize = (fields != null ? fields.size() : 0);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				if (_isSimpleColumn(field)) {
					String columnName = _getColumnName(field);
					columns.add(cg.cte(columnName));
					values.add(cg.field(field));
				}
			}

//			Expression listNames = cg.callStatic(Collections.class, "asList", columns.toArray(new Expression[0]));
//			Expression listValues = cg.callStatic(Collections.class, "asList", values.toArray(new Expression[0]));
//			Expression twoObjects = cg.callStatic(Collections.class, "asArray", listNames, listValues);
			
			GMethod mg = cg.addMethod("_getAllParameterChanges").setProtected(true).setReturnType(Object[].class);
//			mg.addReturn(mg.call("_concatAllParameterChanges", twoObjects, mg.call(mg.cteSuper(), "_getAllParameterChanges")));
			
			
//			return super._concatAllParameterChanges(
//					new Object[] {
//						new String[] {"NAME", "SURNAMES", "COMMENT", "AGE", "ENABLED"}, 
//						new Object[] {this.name, this.surnames, this.comment, this.age, this.enabled}
//					}, 
//					super._getAllParameterChanges());

			Expression listNames = cg.cteArray(String.class, columns.toArray(new Expression[0]));
			Expression listValues = cg.cteArray(Object.class, values.toArray(new Expression[0]));
			Expression twoObjects = cg.cteArray(Object.class, listNames, listValues);
			
			mg.addReturn(mg.call("_concatAllParameterChanges", twoObjects, mg.call(mg.cteSuper(), "_getAllParameterChanges")));
			
		}
	}
	

	
}
