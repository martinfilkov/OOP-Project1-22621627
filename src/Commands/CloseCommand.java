package Commands;

import Interfaces.Command;
import Manager.FileManager;

import java.util.List;

/**
 * This class represents a command to close the currently opened file.
 * It empties all information stored in memory when the file is closed.
 */
public class CloseCommand implements Command {
    /**
     * Executes the close command. If no file is currently opened, an error message is displayed.
     * Otherwise, it closes the file and clears any stored information.
     *
     * @param args the arguments provided to the command, not used in this command.
     */
    @Override
    public void execute(List<String> args){
        if (FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened to close");
            return;
        }
        FileManager.getInstance().setContent("");
        FileManager.getInstance().setPath(null);
        FileManager.getInstance().setValid(false);
        System.out.println("Successfully closed file");
    }
}
