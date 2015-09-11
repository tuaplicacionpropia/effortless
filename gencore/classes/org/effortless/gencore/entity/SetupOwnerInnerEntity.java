package org.effortless.gencore.entity;

import org.effortless.core.StringUtils;
import org.effortless.orm.InnerEntity;
import org.effortless.gencore.Transforms;
import org.effortless.jast.GApp;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.Transform;

//@StageTransform("preModel")
public class SetupOwnerInnerEntity extends Object implements Transform {

	@Override
	public void process(GNode node) {
		GClass cg = (GClass)node;
		if (cg != null && cg.isType(org.effortless.orm.Entity.class)) {
			boolean inner = cg.isInner();
			GApp app = cg.getApplication();
			GClass ownerType = (GClass)app.getClassAttribute(cg.getName(), Transforms.OWNER_INFO);
			if (true && cg != null && (inner || ownerType != null)) {
				String ownerName = null;
				if (inner) {
					ownerName = cg.getOwnerName();
					ownerType = cg.getOwnerType();
				}
				else {
//					ownerName = "owner";//cg.getOwnerName();
					ownerName = StringUtils.uncapFirst(ownerType.getNameWithoutPackage());
				}

				if (ownerName != null && ownerType != null) {
					GField field = cg.addField(ownerType, ownerName);
					field.setAttribute(Transforms.OWNER_INNER_ENTITY, Boolean.TRUE);
//					field.addAnnotation(OwnerInnerEntity.class);
				}
				
				GField ownerField = cg.getField(ownerName);
				String setterMethod = ownerField.getSetterName();

				cg.addInterface(InnerEntity.class);
				
	//	public void setupOwner (Object owner);	
				GMethod som = cg.addMethod("setupOwner").setPublic(true).addParameter(Object.class, "owner");
	//			setProvincia((Provincia)owner);
	//			som.add(som.call(setterMethod, som.cast(ownerType.getClassNode().getPlainNodeReference(), som.var("owner"))));
				
if (false) {
				som.add(som.call(setterMethod, som.cast(ownerType, som.var("owner"))));
}
				
			}
		}
	}

}
