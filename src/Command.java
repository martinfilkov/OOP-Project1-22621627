import java.io.IOException;
import java.util.List;

public interface Command {
    void execute(List<String> args) throws IOException;
}
