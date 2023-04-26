package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Asiakas;
import model.dao.Dao;


@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Asiakkaat(){
    	System.out.println("Asiakkaat.Asiakkaat()");
    	
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("Asiakkaat.doGet()");
	String hakusana = request.getParameter("hakusana");
	String strJSON="";
	Dao dao = new Dao();
	ArrayList<Asiakas> asiakkaat;	
	if(hakusana!=null) {//Jos kutsun mukana tuli hakusana
		if(!hakusana.equals("")) {//Jos hakusana ei ole tyhj�
			asiakkaat = dao.getAllItems(hakusana); //Haetaan kaikki hakusanan mukaiset autot							
		}else {
			asiakkaat = dao.getAllItems(); //Haetaan kaikki autot
		}
		strJSON = new Gson().toJson(asiakkaat);	
	}		
	response.setContentType("application/json; charset=UTF-8");
	PrintWriter out = response.getWriter();
	out.println(strJSON);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("Asiakkaat.doPost()");
	//Luetaan JSON-tiedot POST-pyynnön bodysta ja luodaan niiden perusteella uusi auto
	String strJSONInput = request.getReader().lines().collect(Collectors.joining());
	Asiakas asiakas = new Gson().fromJson(strJSONInput, Asiakas.class);	
	//System.out.println(auto);
	Dao dao = new Dao();
	response.setContentType("application/json; charset=UTF-8");
	PrintWriter out = response.getWriter();
	if(dao.addItem(asiakas)) {
		out.println("{\"response\":1}");  //Auton lis��minen onnistui {"response":1}
	}else {
		out.println("{\"response\":0}");  //Auton lis��minen ep�onnistui {"response":0}
	}
	
	}
	

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");	
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		int id = Integer.parseInt(request.getParameter("asiakas_id"));
		Dao dao = new Dao();
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(dao.removeItem(id)) {
			out.println("{\"response\":1}");  //Auton poistaminen onnistui {"response":1}
		}else {
			out.println("{\"response\":0}");  //Auton poistaminen ep�onnistui {"response":0}
		}
	}



}
