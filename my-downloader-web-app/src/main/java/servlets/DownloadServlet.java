package servlets;

import download.*;
import uniquestrings.NiceStringGenerator;
import uniquestrings.UniqueStringsHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        HttpSession session = req.getSession(true);
        Object unsafeCount = session.getAttribute("count");
        int count;
        if (Objects.isNull(unsafeCount)) {
            count = 1;
            session.setAttribute("count", count);
        } else {
            count = (int) unsafeCount;
        }

        printWriter.println("<body>\n" +
                "<form method=\"post\" action=\"./download\">\n" +
                "<input name=\"command\" type=\"hidden\" value=\"download\">");

        for (int i = 0; i < count; i++) {
            printWriter.println("<div><input name=\"url" + i + "\" type=\"text\" placeholder=\"your URL\"/></div>");
        }

        printWriter.println("<input type=\"submit\" value=\"Download\"/>\n" +
                "</form>\n");

        printWriter.println("<form method=\"post\" action=\"./download\">\n" +
                "<input name=\"command\" type=\"hidden\" value=\"add_field\">" +
                "<input type=\"submit\" value=\"Add field\"/>\n" +
                "</form>\n");

        printWriter.println("<form method=\"post\" action=\"./download\">\n" +
                "<input name=\"command\" type=\"hidden\" value=\"remove_field\">" +
                "<input type=\"submit\" value=\"Remove field\"/>\n" +
                "</form>\n");

        printWriter.println("</body>\n");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter("command");
        if (command == null) {
            resp.sendRedirect("./download");
            return;
        }

        int count;
        Object unsafeCount;
        HttpSession session = req.getSession(false);
        switch (command) {
            case "add_field":
                unsafeCount = session.getAttribute("count");
                count = Objects.isNull(unsafeCount) ? 1 : (int) unsafeCount;
                session.setAttribute("count", count + 1);
                resp.sendRedirect("./download");
                return;
            case "remove_field":
                unsafeCount = session.getAttribute("count");
                count = Objects.isNull(unsafeCount) ? 1 : (int) unsafeCount;
                session.setAttribute("count", count - 1);
                resp.sendRedirect("./download");
                return;
            case "download":
                count = ((int) session.getAttribute("count"));
                List<String> urls = IntStream
                        .range(0, count)
                        .mapToObj(x -> req.getParameter("url" + x))
                        .collect(Collectors.toList());

                DownloadMaster downloadMaster = new DownloadMaster();

                File downloadDir = new File("../downloads/");
                downloadDir.mkdir();

                Set<String> filenames = Arrays.stream(downloadDir.listFiles())
                        .filter(File::isFile)
                        .map(File::getName)
                        .collect(Collectors.toSet());

                UniqueStringsHandler handler = new UniqueStringsHandler(filenames, new NiceStringGenerator());

                for (String urlString : urls) {
                    String filename = urlString.substring(urlString.lastIndexOf("/") + 1);
                    filename = handler.getUniqueFilename(filename);
                    handler.register(filename);
                    downloadMaster.addDownload(new FileDownload(new URL(urlString), new File(downloadDir.getAbsoluteFile() + File.separator + filename)));
                }

                downloadMaster.runAllDownloads();
                resp.sendRedirect("./download");
                return;
        }

    }
}
