package com.medicalmine.chase.bean.request;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Testing {
	public static void main(String[] args) throws JAXBException {

		NewOrder order = new NewOrder("AC", 12341234L, 2500D, "VISA", "5454545454545454", "0112", "Name", "Line 1",
				"Line 2", "Cupertino", "California", "95014", "8124746412", Boolean.FALSE);

		Request request = new Request(order);

		StringWriter xml = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(Request.class);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(request, xml);

		System.out.println("\n\nNew Order Request\n\n");

		System.out.println(xml);

		MarkForCapture markForCapture = new MarkForCapture(12341234L, 2500D);

		request = new Request(markForCapture);

		xml = new StringWriter();

		marshaller.marshal(request, xml);

		System.out.println("\n\nMark For Capture Request\n\n");

		System.out.println(xml);

		AccountUpdater accountUpdater = new AccountUpdater("Customer Reference Number", "Profile Action",
				"Scheduled Date");

		request = new Request(accountUpdater);

		xml = new StringWriter();

		marshaller.marshal(request, xml);

		System.out.println("\n\nAccount Update Request\n\n");

		System.out.println(xml);

	}
}
