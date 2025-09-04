package crm_app10.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_app10.services.JobsServices;
import entity.Jobs;
@WebServlet(name="jobscontroller",urlPatterns = {"/jobs"})
public class JobsController extends HttpServlet {
	private JobsServices jobsServices = new JobsServices();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Jobs> listJobs = jobsServices.getAllJobs();
		System.out.println("danh sach: " + listJobs.size());
		System.out.println(listJobs.get(0).getName());
		req.setAttribute("listJobs", listJobs);
		req.getRequestDispatcher("jobs.jsp").forward(req, resp);
	}
}
