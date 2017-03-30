package backend;

import backend.io.XMLsavable;

import java.nio.file.Path;

/**
 * @author Created by th174 on 3/30/2017.
 */
public interface GameObject extends XMLsavable {
    String getName();

    String getDescription();

    Path getImgPath();
}
