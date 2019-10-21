package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File("../download/");
        file.mkdir();
        //file.createNewFile();

        System.out.println(Arrays.stream(file.listFiles()).filter(File::isFile).map(File::getName).collect(Collectors.toList()));
        System.out.println(Arrays.toString(new File(getServletContext().getRealPath("./download/")).listFiles()));
    }
}
