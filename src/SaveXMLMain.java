import backend.unit.properties.ActiveAbility;
import backend.util.ImmutableVoogaObject;
import backend.util.VoogaEntity;
import backend.util.io.JSONSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class SaveXMLMain {
	public static void main(String[] args) {
		Class<? extends VoogaEntity> clazz = ActiveAbility.class;
//		XMLSerializer<? super VoogaEntity> serializer = new XMLSerializer<>();
		JSONSerializer<? super VoogaEntity> serializer = new JSONSerializer<>(clazz);
		String path = System.getProperty("user.dir") + "/data/core/abilities/active_abilities/%s.json";
		ImmutableVoogaObject.getPredefined(clazz).forEach(e -> {
			try {
				Files.write(Paths.get(String.format(path, e.getName().replace(" ", "_"))), ((String) serializer.serialize(e)).getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
}
