package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter("/*")
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        File logDir = new File("../logs/");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        Date date = new Date();

        File logFile = new File(logDir.getAbsolutePath() + File.separator + "Log.txt");
        if (!logFile.exists())
            logFile.createNewFile();
        FileWriter fileWriter = new FileWriter(logFile, true);
        fileWriter.append("date ").append(dateFormat.format(date)).append("\n");
        fileWriter.append("method ").append(((HttpServletRequest) servletRequest).getMethod()).append("\n");
        fileWriter.append("url ").append(String.valueOf(((HttpServletRequest) servletRequest).getRequestURL())).append("\n");
        fileWriter.append("\n");
        fileWriter.close();

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
