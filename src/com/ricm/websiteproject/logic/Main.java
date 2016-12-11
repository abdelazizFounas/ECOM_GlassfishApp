package com.ricm.websiteproject.logic;

import java.util.ArrayList;

import javax.servlet.ServletContextEvent;

import com.ricm.websiteproject.beans.CarpoolingInfo;
import com.ricm.websiteproject.beans.Voiture;

public class Main implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("DEBUT MAIN");

		StaticClass.alVoitures = new ArrayList<Voiture>();
		StaticClass.alCustomers = new ArrayList<CarpoolingInfo>();
		StaticClass.main = "main";

		System.out.println("FIN MAIN");
	}
}
