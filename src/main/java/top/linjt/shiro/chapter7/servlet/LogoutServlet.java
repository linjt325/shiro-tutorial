package top.linjt.shiro.chapter7.servlet;

import org.apache.shiro.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//urlPatterns 需要以/ 开头
@WebServlet(name = "logout",urlPatterns = "/chapter7/logout")
public class LogoutServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SecurityUtils.getSubject().logout();

//        req.getRequestDispatcher("/Html/login.jsp").forward(req, resp);

    }
}
