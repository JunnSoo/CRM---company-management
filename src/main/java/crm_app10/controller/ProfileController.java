package crm_app10.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.StatusServices;
import crm_app10.services.TaskServices;
import crm_app10.services.UserServices;
import entity.Tasks;
import entity.Users;

@WebServlet(name="profileController", urlPatterns = {"/profile","/profile-edit"})
public class ProfileController extends HttpServlet {
    private final UserServices userServices = new UserServices();
    private final TaskServices taskServices = new TaskServices();
    private final StatusServices statusServices = new StatusServices();

    // --- Helper: lấy uid từ cookie ---
    private Integer getUidFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if ("uid".equals(c.getName())) {
                try { return Integer.parseInt(c.getValue()); }
                catch (NumberFormatException ignore) { return null; }
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        Integer uid = getUidFromCookie(req);
        if (uid == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            switch (path) {
                case "/profile": {
                    Users user = userServices.getUserById(uid);
                    if (user == null) {
                        resp.sendRedirect(req.getContextPath() + "/login?msg=user_not_found");
                        return;
                    }
                    List<Tasks> listTask = taskServices.getTaskByUserId(uid);

                    Map<String, Integer> percents = taskServices.getTaskPercentages(uid);
                    req.setAttribute("percentTodo", percents.getOrDefault("percentTodo", 0));
                    req.setAttribute("percentInProgress", percents.getOrDefault("percentInProgress", 0));
                    req.setAttribute("percentDone", percents.getOrDefault("percentDone", 0));

                    req.setAttribute("listTask", listTask);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/profile.jsp").forward(req, resp);
                    return;
                }

                case "/profile-edit": {
                    String idStr = req.getParameter("id");
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendRedirect(req.getContextPath() + "/profile?msg=missing_id");
                        return;
                    }
                    int id;
                    try { id = Integer.parseInt(idStr); }
                    catch (NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/profile?msg=bad_id");
                        return;
                    }

                    Tasks task = taskServices.getTaskById(id);
                    if (task == null) {
                        resp.sendRedirect(req.getContextPath() + "/profile?msg=task_not_found");
                        return;
                    }
                    // Nếu muốn chặn user sửa task của người khác (trừ admin), kiểm tra:
                    // if (task.getUser_id() != uid && !isAdmin(uid)) { resp.sendError(403); return; }

                    req.setAttribute("status", statusServices.getAllStatus());
                    req.setAttribute("task", task);
                    req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
                    return;
                }

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/profile?msg=exception");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        try {
            switch (path) {
                case "/profile-edit": {
                    Integer uid = getUidFromCookie(req);
                    if (uid == null) {
                        resp.sendRedirect(req.getContextPath() + "/login");
                        return;
                    }

                    String idStr = req.getParameter("id");
                    String name = req.getParameter("name");
                    String start_date = req.getParameter("start_date");
                    String end_date = req.getParameter("end_date");
                    String jobIdStr = req.getParameter("job_id");
                    String statusIdStr = req.getParameter("status_id");

                    if (idStr == null || jobIdStr == null || statusIdStr == null) {
                        resp.sendRedirect(req.getContextPath() + "/profile?msg=missing_params");
                        return;
                    }

                    int id, job_id, status_id;
                    try {
                        id = Integer.parseInt(idStr);
                        job_id = Integer.parseInt(jobIdStr);
                        status_id = Integer.parseInt(statusIdStr);
                    } catch (NumberFormatException e) {
                        resp.sendRedirect(req.getContextPath() + "/profile-edit?id=" + idStr + "&msg=bad_params");
                        return;
                    }

                    // (tùy yêu cầu) kiểm tra quyền sở hữu task
                    // Tasks t = taskServices.getTaskById(id);
                    // if (t == null || t.getUser_id() != uid) { resp.sendError(403); return; }

                    // DÙNG uid từ cookie thay vì user_id từ form:
                    boolean ok = taskServices.updateTaskByID(id, name, start_date, end_date, uid, job_id, status_id);

                    resp.sendRedirect(req.getContextPath() + (ok ? "/profile?msg=updated"
                                                                 : "/profile-edit?id=" + id + "&msg=failed"));
                    return;
                }

                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/profile?msg=exception");
        }
    }
}

